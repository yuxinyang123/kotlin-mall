package xyz.chunshengyuan.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.chunshengyuan.mall.model.bo.CartGoods;

import java.util.List;

/**
 * @author leemaster
 * @Title: CartGoodsVo
 * @Package xyz.chunshengyuan.mall.model.vo
 * @Description:
 * @date 2019-07-1016:55
 */
@Data
public class CartGoodsVo {

    private List<Node> goods;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Node {
        private List<CartGoods> list;

        private String name;

        private List<Node> children;

        private Integer order;
    }
}
