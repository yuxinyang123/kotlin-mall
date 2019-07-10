package xyz.chunshengyuan.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.chunshengyuan.mall.constrant.BannerLocation;
import xyz.chunshengyuan.mall.constrant.BannerType;
import xyz.chunshengyuan.mall.constrant.PushType;
import xyz.chunshengyuan.mall.mapper.*;
import xyz.chunshengyuan.mall.model.po.Banner;
import xyz.chunshengyuan.mall.model.po.Category;
import xyz.chunshengyuan.mall.model.po.Goods;
import xyz.chunshengyuan.mall.model.po.Push;
import xyz.chunshengyuan.mall.model.vo.*;
import xyz.chunshengyuan.mall.utils.SnowFlakeIdGenerator;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author leemaster
 * @Title: GoodsService
 * @Package xyz.chunshengyuan.mall.service
 * @Description:
 * @date 2019-07-1012:21
 */
@Service
public class GoodsService {

    @Resource
    GoodsPlusMapper goodsPlusMapper;

    @Resource
    CategoryPlusMapper categoryPlusMapper;

    @Resource
    BannerPlusMapper bannerPlusMapper;

    @Resource
    SnowFlakeIdGenerator snowFlakeIdGenerator;

    @Resource
    PushPlusMapper pushMapper;

    @Resource
    GoodsMapper goodsMapper;

    public GoodsListVo selectAllGoods(GoodsQuery query){
        Page<Goods> pageDefinition = new Page<>();
        pageDefinition.setCurrent(query.getOffset());
        pageDefinition.setSize(query.getLimit());
        IPage<Goods> page = goodsPlusMapper.selectPage(
                pageDefinition,
                Wrappers.<Goods>query()
                .and(query.getType() != null,i -> i.eq("type",query.getType()))
                        .and(query.getType() != null,i -> i.like("name","%" + query.getType() + "%"))
                        .and(
                                query.getTopPrice() != null && query.getLowPrice() != null,
                                i -> i.between(
                                        "price",
                                        query.getLowPrice(),
                                        query.getTopPrice()
                                )
                        ).and(
                                query.getLocation() != null,
                        i -> i.eq("location",query.getLocation())
                )

        );

        if(page.getTotal() == 0L){
            return new GoodsListVo(page.getTotal(),new ArrayList<>());
        }

        List<Long> types = page.getRecords().stream().map(Goods::getType).collect(Collectors.toList());

        List<Long> goodsIds = page.getRecords().stream().map(Goods::getId).collect(Collectors.toList());

        Map<Long,Category> categories = categoryPlusMapper.selectBatchIds(types)
                .stream().collect(
                        Collectors.toMap(
                                Category::getId,
                                (item) -> item
                        )
                );

        Map<Long,List<Banner>> banners = bannerPlusMapper.selectList(
                Wrappers.<Banner>query().in(
                        "goods_id",
                        goodsIds
                )
        ).stream().collect(
                Collectors.groupingBy(
                        Banner::getGoodsId
                )
        );

        List<GoodsInfo> goodsInfos = page.getRecords().stream()
                .map((item) -> {
                    GoodsInfo info = new GoodsInfo();
                    info.setAddTime(item.getAddTime());
                    info.setUpdateTime(item.getUpdateTime());
                    info.setBanners(banners.get(item.getId()));
                    info.setCategory(categories.get(item.getType()));
                    info.setPrice(item.getPrice());
                    info.setDetails(item.getDetails());
                    info.setInventory(item.getInventory());
                    info.setAvatar(item.getAvatar());
                    info.setIsDel(item.getIsDel());
                    info.setIsEnable(item.getIsEnable());
                    info.setLocation(item.getLocation());
                    info.setIntroduce(item.getIntroduce());
                    return info;
                }).collect(Collectors.toList());

        return new GoodsListVo(page.getTotal(),goodsInfos);
    }

    private Goods handleGoodsForm(GoodsForm goodsForm){
        Goods goods = new Goods();
        goods.setName(goodsForm.getName());
        goods.setDetails(goodsForm.getDetails());
        goods.setIntroduce(goodsForm.getIntroduce());
        goods.setLocation(goodsForm.getLocation());
        goods.setInventory(goodsForm.getInventory());
        goods.setIsDel(goodsForm.getIsDel());
        goods.setIsEnable(goodsForm.getIsEnable());
        goods.setType(goodsForm.getType());
        goods.setAvatar(goodsForm.getBanners().get(0));

        return goods;
    }

    private List<Banner> handleBanner(List<String> banners,Long id,String name){
        List<Banner> banners1 = new ArrayList<>();

        for(int i = 0; i < banners.size();i++){
            Banner banner = new Banner();
            banner.setGoodsId(id);
            banner.setSerial(i);
            banner.setLocation(BannerLocation.GOODS.code);
            banner.setType(BannerType.COMMON.code);
            banner.setName(name);
            banner.setUrl(banners.get(i));
            banners1.add(banner);
        }

        return banners1;
    }

    @Transactional
    public void addNewGoods(GoodsForm goodsForm){
        Goods goods = handleGoodsForm(goodsForm);
        Long id = snowFlakeIdGenerator.nextId();
        goods.setId(id);
        goodsPlusMapper.insert(goods);
        List<Banner> banners = handleBanner(
                goodsForm.getBanners(),
                id,
                goodsForm.getName()
        );
        for(Banner banner : banners){
            bannerPlusMapper.insert(banner);
        }
    }

    @Transactional
    public void updateGoode(GoodsForm form,Long id){
        Goods goods = handleGoodsForm(form);
        goods.setId(id);
        Goods dbGoods = goodsPlusMapper.selectById(id);
        if(dbGoods.getName().equals(goods.getName())){
            goods.setName(dbGoods.getName());
        }
        goodsPlusMapper.updateById(goods);
        if(form.getBanners() != null || form.getBanners().size() != 0){
            bannerPlusMapper.delete(
                    Wrappers.<Banner>query().eq("goods_id",id)
            );
            List<Banner> banners = handleBanner(
                    form.getBanners(),
                    id,
                    goods.getName()
            );
            for(Banner banner : banners){
                bannerPlusMapper.insert(banner);
            }
        }
    }

    @Transactional
    public void deleteGoods(Long id){
        bannerPlusMapper.delete(
                Wrappers.<Banner>query().eq("goods_id",id)
        );
        goodsPlusMapper.deleteById(id);
    }

    @Transactional
    public void createPush(Long goodsId, PushType type){
        Goods goods = goodsPlusMapper.selectById(goodsId);
        Push push = new Push();
        push.setIcon("-");
        push.setCoverUrl(goods.getAvatar());
        push.setPushType(type.code);
        push.setLink(goodsId.toString());
        pushMapper.insert(push);
    }

    public PushListVo listPush(Long limit,Long offset,String type){
        Page<Push> pushPage = new Page<>();
        pushPage.setCurrent(offset);
        pushPage.setSize(limit);
        IPage<Push> pushIPage = pushMapper.selectPage(
                pushPage,
                Wrappers.<Push>query()
                .eq("push_type",type)
        );

        if (pushIPage.getTotal() == 0){
            return new PushListVo(0L,new ArrayList<>());
        }

        List<Long> goodsIds  = pushIPage.getRecords().stream().map(
                item -> Long.valueOf(item.getLink())
        ).collect(Collectors.toList());

        Map<String,Goods> goodsMap = goodsPlusMapper.selectBatchIds(goodsIds)
                .stream().collect(
                        Collectors.toMap(
                                item -> item.getId().toString(),
                                item -> item
                        )
                );

        return new PushListVo(
          pushIPage.getTotal(),
          pushIPage.getRecords().stream().map(
                  (item) -> {
                      PushInfoVo infoVo = new PushInfoVo();
                      infoVo.setGoods(goodsMap.get(item.getLink()));
                      infoVo.setPush(item);
                      return infoVo;
                  }
          )      .collect(Collectors.toList())
        );
    }

    @Transactional
    public void deletePush(List<Long> pushId){
        pushMapper.deleteBatchIds(pushId);
    }

    public List<String> selectGoodsLocation(Long typeId){
        return goodsPlusMapper.selectLocation(typeId);
    }

}
