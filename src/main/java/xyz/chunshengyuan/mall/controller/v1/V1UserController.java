package xyz.chunshengyuan.mall.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.web.bind.annotation.*;
import xyz.chunshengyuan.mall.configuration.RequiredRole;
import xyz.chunshengyuan.mall.constrant.UserRole;
import xyz.chunshengyuan.mall.exceptions.ApiOperationException;
import xyz.chunshengyuan.mall.mapper.AddressPlusMapper;
import xyz.chunshengyuan.mall.model.BaseResponse;
import xyz.chunshengyuan.mall.model.bo.DetailUser;
import xyz.chunshengyuan.mall.model.po.Address;
import xyz.chunshengyuan.mall.model.v1.V1UserShoppingPo;
import xyz.chunshengyuan.mall.model.vo.CartGoodsVo;
import xyz.chunshengyuan.mall.model.vo.FavoriteGoodsVo;
import xyz.chunshengyuan.mall.service.AddressService;
import xyz.chunshengyuan.mall.service.CartFavoriteService;
import xyz.chunshengyuan.mall.utils.RequestContext;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: V1UserController
 * @date: 2019-07-10 23:40
 * @author: yuxinyang
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/v1/user")
public class V1UserController {

    @Resource
    AddressService addressService;

    @Resource
    AddressPlusMapper addressPlusMapper;

    @Resource
    CartFavoriteService cartFavoriteService;


    @GetMapping("address")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse getAllAddress() {
        DetailUser detailUser = RequestContext.get();
        List<Address> addresses = addressService.selectUserAllAddr(detailUser.getId());
        return BaseResponse.succes(addresses);
    }

    @PostMapping("address")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse addAddress(@RequestBody Address userAddressPo) throws ApiOperationException {
        addressService.createAddress(userAddressPo);
        return BaseResponse.success();
    }

    @PutMapping("address")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse updateAddress(@RequestBody Address userAddressPo) {
        int update = addressPlusMapper.update(userAddressPo, Wrappers.<Address>update().eq("id", userAddressPo.getId()));
        return BaseResponse.succes(update);
    }

    @DeleteMapping("address")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse deleteAddress(@RequestBody List<Long> ids) {
        ids.forEach(i -> addressService.deleteAddress(i));
        return BaseResponse.success();
    }

    @GetMapping("collection")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse getCollections(@RequestParam int pageNum, @RequestParam int pageSize) throws ApiOperationException {
        FavoriteGoodsVo facorites = cartFavoriteService.getFacorites();
        return BaseResponse.succes(facorites);
    }

    @GetMapping("collection/info")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse isCollections(@RequestParam Long goodId) throws ApiOperationException {
        FavoriteGoodsVo facorites = cartFavoriteService.getFacorites();
        boolean match = facorites.getGoods().stream().anyMatch(
                i -> i.getChildren().stream().anyMatch(
                        c -> c.getList().stream().anyMatch(
                                g -> g.getId().equals(goodId))));
        return BaseResponse.succes(match);
    }

    @PostMapping("collection/{goodsId}")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse addCollection(@PathVariable Long goodsId) throws ApiOperationException {
        cartFavoriteService.createFavorite(goodsId);
        return BaseResponse.success();
    }


    @DeleteMapping("collection")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse<Object> deleteCollection(@RequestParam Long id) throws ApiOperationException {
        cartFavoriteService.deleteFavorit(id);
        return BaseResponse.success();
    }

    @GetMapping("shopping-cart")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse getAllCart() throws ApiOperationException {
        CartGoodsVo cartGoodsVo = cartFavoriteService.CartGoodsVogetCartGoods();
        return BaseResponse.succes(cartGoodsVo);
    }

    @PostMapping("shopping-cart")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse addCart(@RequestBody V1UserShoppingPo userShoppingPo) throws ApiOperationException {
        cartFavoriteService.addToCart(userShoppingPo.getGoodsId(), userShoppingPo.getSum());
        return BaseResponse.success();
    }

    @DeleteMapping("shopping-cart")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse deleteCart(@RequestParam("goods_id") Long goodsId) throws ApiOperationException {
        cartFavoriteService.deleteCart(goodsId);
        return BaseResponse.success();
    }

    @PutMapping("shopping-cart")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse updateCart(@RequestBody V1UserShoppingPo userShoppingPo) throws ApiOperationException {
        cartFavoriteService.updateCart(userShoppingPo.getGoodsId(), userShoppingPo.getSum());
        return BaseResponse.success();
    }
}
