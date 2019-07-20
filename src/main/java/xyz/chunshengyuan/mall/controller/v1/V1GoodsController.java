package xyz.chunshengyuan.mall.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.chunshengyuan.mall.configuration.RequiredRole;
import xyz.chunshengyuan.mall.constrant.UserRole;
import xyz.chunshengyuan.mall.mapper.BannerPlusMapper;
import xyz.chunshengyuan.mall.mapper.GoodsPlusMapper;
import xyz.chunshengyuan.mall.model.BaseResponse;
import xyz.chunshengyuan.mall.model.bo.CategoryNode;
import xyz.chunshengyuan.mall.model.po.Banner;
import xyz.chunshengyuan.mall.model.po.Goods;
import xyz.chunshengyuan.mall.model.v1.V1GoodsInfo;
import xyz.chunshengyuan.mall.model.v1.V1GoodsQuery;
import xyz.chunshengyuan.mall.model.vo.GoodsListVo;
import xyz.chunshengyuan.mall.service.CategoryService;
import xyz.chunshengyuan.mall.service.GoodsService;
import xyz.chunshengyuan.mall.service.v1.V1GoodsListService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author leemaster
 * @Title: V1GoodsController
 * @Package xyz.chunshengyuan.mall.controller.v1
 * @Description:
 * @date 2019-07-1010:40
 */
@RestController
@RequestMapping("/api/v1/goods")
public class V1GoodsController {

    @Resource
    GoodsService goodsService;

    @Resource
    GoodsPlusMapper goodsPlusMapper;

    @Resource
    CategoryService categoryService;

    @Resource
    V1GoodsListService v1GoodsListService;

    @Resource
    BannerPlusMapper bannerPlusMapper;

    @GetMapping
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse getAllGoods(V1GoodsQuery query) {
        GoodsListVo goodsListVo = v1GoodsListService.selectAllGoods(query);
        return BaseResponse.succes(goodsListVo);
    }

    @GetMapping("{goodId}/info")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse getGoodInfo(@PathVariable Long goodId) {
        Goods goods = goodsPlusMapper.selectById(goodId);
        List<Banner> banners = bannerPlusMapper.selectList(Wrappers.<Banner>query().eq("goods_id", goods.getId()).eq("location", "goods"));
        return BaseResponse.succes(new V1GoodsInfo(goods, banners));
    }

    @GetMapping("types")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse getAllType() {
        List<CategoryNode> nodes = categoryService.selectAllCategroyforest();
        return BaseResponse.succes(nodes);
    }
}
