package xyz.chunshengyuan.mall.service.v1;

import org.springframework.stereotype.Service;
import xyz.chunshengyuan.mall.exceptions.ApiOperationException;
import xyz.chunshengyuan.mall.mapper.CategoryPlusMapper;
import xyz.chunshengyuan.mall.mapper.OrderMapper;
import xyz.chunshengyuan.mall.model.bo.FavoritesGoods;
import xyz.chunshengyuan.mall.model.po.Category;
import xyz.chunshengyuan.mall.model.vo.FavoriteGoodsVo;
import xyz.chunshengyuan.mall.utils.RequestContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: V1FavouriteService
 * @date: 2019-07-15 01:13
 * @author: yuxinyang
 * @version: 1.0
 */
@Service
public class V1FavouriteService {


    @Resource
    OrderMapper orderMapper;

    @Resource
    CategoryPlusMapper categoryPlusMapper;

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
}
