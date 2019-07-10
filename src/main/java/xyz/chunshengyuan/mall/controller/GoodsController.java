package xyz.chunshengyuan.mall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.chunshengyuan.mall.model.BaseResponse;
import xyz.chunshengyuan.mall.model.vo.GoodsQuery;
import xyz.chunshengyuan.mall.service.GoodsService;

import javax.annotation.Resource;

/**
 * @author leemaster
 * @Title: GoodsController
 * @Package xyz.chunshengyuan.mall.controller
 * @Description:
 * 1. 用户商品列表
 * 2. 用户购物车和收藏 CURD
 * 3 管理员接口
 * @date 2019-07-1004:04
 */
@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    @Resource
    GoodsService goodsService;

    @GetMapping("")
    public BaseResponse getAllGoods(GoodsQuery query){
        return BaseResponse.succes(goodsService.selectAllGoods(query));
    }
}
