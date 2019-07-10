package xyz.chunshengyuan.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.chunshengyuan.mall.model.bo.FavoritesGoods;

import java.util.List;

/**
 * @author leemaster
 * @Title: FavoriteGoodsVo
 * @Package xyz.chunshengyuan.mall.model.vo
 * @Description:
 * @date 2019-07-1017:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteGoodsVo {

    private List<Node> goods;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Node{

        private List<FavoritesGoods> list;

        private String name;

        private List<Node> children;

        private Integer order;
    }
}
