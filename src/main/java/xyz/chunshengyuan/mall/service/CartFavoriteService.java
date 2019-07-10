package xyz.chunshengyuan.mall.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.chunshengyuan.mall.exceptions.ApiOperationException;
import xyz.chunshengyuan.mall.mapper.CategoryPlusMapper;
import xyz.chunshengyuan.mall.mapper.OrderMapper;
import xyz.chunshengyuan.mall.model.bo.CartGoods;
import xyz.chunshengyuan.mall.model.bo.FavoritesGoods;
import xyz.chunshengyuan.mall.model.po.Category;
import xyz.chunshengyuan.mall.model.vo.CartGoodsVo;
import xyz.chunshengyuan.mall.model.vo.FavoriteGoodsVo;
import xyz.chunshengyuan.mall.utils.RequestContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author leemaster
 * @Title: CartFavoriteService
 * @Package xyz.chunshengyuan.mall.service
 * @Description:
 * @date 2019-07-1016:49
 */
@Service
public class CartFavoriteService {

    @Resource
    OrderMapper orderMapper;

    @Resource
    CategoryPlusMapper categoryPlusMapper;

    public CartGoodsVo CartGoodsVogetCartGoods() throws ApiOperationException {
        try {
            Long userId = RequestContext.get().getId();
            List<CartGoods> list = orderMapper.selectCarts(userId);
            if (list.size() == 0)return new CartGoodsVo();
            Map<Long,List<CartGoods>> cartGoodsMap = list.stream()
                    .collect(
                            Collectors.groupingBy(
                                    CartGoods::getType,
                                    Collectors.toList()
                            )
                    );

            List<Long> typeIds = list.stream().map(CartGoods::getType).collect(Collectors.toList());
            List<Category> categories = categoryPlusMapper.selectBatchIds(typeIds);
            Map<Long,List<Category>> categoriesMap = categories.stream()
                    .collect(
                            Collectors.groupingBy(
                                    Category::getParent,
                                    Collectors.toList()
                            )
                    );
            List<Category> parents = categoryPlusMapper.selectBatchIds(
                    categories.stream().map(Category::getParent).collect(Collectors.toList())
            );

            CartGoodsVo vo = new CartGoodsVo();
            vo.setGoods(
                    parents.stream().map(parent -> {
                        CartGoodsVo.Node node = new CartGoodsVo.Node();
                        node.setName(parent.getName());
                        node.setOrder(parent.getId().intValue());
                        node.setList(new ArrayList<>());
                        List<Category> childCategory = categoriesMap.get(parent.getId());
                        node.setChildren(
                                childCategory.stream().map( child -> {
                                            CartGoodsVo.Node childNode = new CartGoodsVo.Node();
                                            childNode.setName(child.getName());
                                            childNode.setOrder(child.getId().intValue());
                                            childNode.setList(cartGoodsMap.get(child.getId()));
                                            return childNode;
                                        }).collect(Collectors.toList())
                        );
                        return node;
                    }).collect(Collectors.toList())
            );
            return vo;
        }catch (NullPointerException e){
            throw new ApiOperationException("用户信息不存在","");
        }
    }

    public FavoriteGoodsVo getFacorites() throws ApiOperationException {
        try {
            Long userId = RequestContext.get().getId();
            List<FavoritesGoods> goodsList = orderMapper.selectFavorite(userId);
            if (goodsList.size() == 0) return new FavoriteGoodsVo(new ArrayList<>());

            Map<Long,List<FavoritesGoods>> favoriteMap = goodsList.stream()
                    .collect(
                            Collectors.groupingBy(
                                    FavoritesGoods::getType,
                                    Collectors.toList()
                            )
                    );
            List<Long> typeIds = goodsList.stream().map(FavoritesGoods::getType).collect(Collectors.toList());
            List<Category> categories = categoryPlusMapper.selectBatchIds(typeIds);
            Map<Long,List<Category>> categoriesMap = categories.stream()
                    .collect(
                            Collectors.groupingBy(
                                    Category::getParent,
                                    Collectors.toList()
                            )
                    );

            List<Category> parents = categoryPlusMapper.selectBatchIds(
                    categories.stream().map(Category::getParent).collect(Collectors.toList())
            );

            FavoriteGoodsVo vo = new FavoriteGoodsVo();
            vo.setGoods(
                    parents.stream().map(parent -> {
                        FavoriteGoodsVo.Node node = new FavoriteGoodsVo.Node();
                        node.setName(parent.getName());
                        node.setOrder(parent.getId().intValue());
                        node.setList(new ArrayList<>());
                        List<Category> childCategory = categoriesMap.get(parent.getId());
                        node.setChildren(
                                childCategory.stream().map( child -> {
                                    FavoriteGoodsVo.Node childNode = new FavoriteGoodsVo.Node();
                                    childNode.setName(child.getName());
                                    childNode.setOrder(child.getId().intValue());
                                    childNode.setList(favoriteMap.get(child.getId()));
                                    return childNode;
                                }).collect(Collectors.toList())
                        );
                        return node;
                    }).collect(Collectors.toList())
            );
            return vo;
        }catch (NullPointerException e){
            throw new ApiOperationException("用户信息不存在","");
        }
    }

    @Transactional
    public void createFavorite(Long goodsId) throws ApiOperationException {
        try{
            Long userId = RequestContext.get().getId();
            orderMapper.insertFavorite(goodsId,userId);
        }catch (NullPointerException e){
            throw new ApiOperationException("用户信息不存在","");
        }
    }

    @Transactional
    public void addToCart(Long goodsId,Integer sum) throws ApiOperationException {
        try{
            Long userId = RequestContext.get().getId();
            orderMapper.insertCart(goodsId,userId,sum);
        }catch (NullPointerException e){
            throw new ApiOperationException("用户信息不存在","");
        }
    }

    @Transactional
    public void deleteFavorit(Long goodsId) throws ApiOperationException {
        try{
            Long userId = RequestContext.get().getId();
            orderMapper.deleteFavorite(goodsId,userId);
        }catch (NullPointerException e){
            throw new ApiOperationException("用户信息不存在","");
        }
    }

    @Transactional
    public void updateCart(Long goodsId,Integer sum) throws ApiOperationException {
        try{
            Long userId = RequestContext.get().getId();
            orderMapper.updateCart(goodsId,userId,sum);
        }catch (NullPointerException e){
            throw new ApiOperationException("用户信息不存在","");
        }
    }

    @Transactional
    public void deleteCart(Long goodsId) throws ApiOperationException {
        try{
            Long userId = RequestContext.get().getId();
            orderMapper.deleteCart(goodsId,userId);
        }catch (NullPointerException e){
            throw new ApiOperationException("用户信息不存在","");
        }
    }

}
