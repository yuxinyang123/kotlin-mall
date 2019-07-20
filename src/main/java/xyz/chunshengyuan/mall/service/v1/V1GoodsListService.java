package xyz.chunshengyuan.mall.service.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import xyz.chunshengyuan.mall.mapper.BannerPlusMapper;
import xyz.chunshengyuan.mall.mapper.CategoryPlusMapper;
import xyz.chunshengyuan.mall.mapper.GoodsPlusMapper;
import xyz.chunshengyuan.mall.model.po.Banner;
import xyz.chunshengyuan.mall.model.po.Category;
import xyz.chunshengyuan.mall.model.po.Goods;
import xyz.chunshengyuan.mall.model.v1.V1GoodsQuery;
import xyz.chunshengyuan.mall.model.vo.GoodsInfo;
import xyz.chunshengyuan.mall.model.vo.GoodsListVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author leemaster
 * @Title: V1GoodsListService
 * @Package xyz.chunshengyuan.mall.service.v1
 * @Description:
 * @date 2019-07-1010:41
 */
@Service
public class V1GoodsListService {

    @Resource
    GoodsPlusMapper goodsPlusMapper;

    @Resource
    CategoryPlusMapper categoryPlusMapper;

    @Resource
    BannerPlusMapper bannerPlusMapper;

    public GoodsListVo selectAllGoods(V1GoodsQuery query) {
        Page<Goods> pageDefinition = new Page<>();
        pageDefinition.setCurrent(query.getOffset());
        pageDefinition.setSize(query.getLimit());
        IPage<Goods> page = goodsPlusMapper.selectPage(
                pageDefinition,
                Wrappers.<Goods>query()
                        .and(query.getType() != null, i -> i.eq("type", query.getType()))
                        .and(query.getName() != null, i -> i.like("name", "%" + query.getName() + "%"))
                        .and(
                                query.getTopPrice() != null && query.getLowPrice() != null,
                                i -> i.between(
                                        "price",
                                        query.getLowPrice(),
                                        query.getTopPrice()
                                )
                        )
                        .and(
                                query.getLocation() != null,
                                i -> i.eq("location", query.getLocation())
                        )
                        .orderByAsc(query.getAscOrderByPrice(), "price")
                        .orderByDesc(query.getDescOrderByPrice(), "price")
                        .orderByDesc(query.getDescOrderByInventory(), "inventory")

        );

        if (page.getTotal() == 0L) {
            return new GoodsListVo(page.getTotal(), new ArrayList<>());
        }

        List<Long> types = page.getRecords().stream().map(Goods::getType).collect(Collectors.toList());

        List<Long> goodsIds = page.getRecords().stream().map(Goods::getId).collect(Collectors.toList());

        Map<Long, Category> categories = categoryPlusMapper.selectBatchIds(types)
                .stream().collect(
                        Collectors.toMap(
                                Category::getId,
                                (item) -> item
                        )
                );

        Map<Long, List<Banner>> banners = bannerPlusMapper.selectList(
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
                    info.setName(item.getName());
                    info.setId(item.getId());
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

        return new GoodsListVo(page.getTotal(), goodsInfos);
    }
}
