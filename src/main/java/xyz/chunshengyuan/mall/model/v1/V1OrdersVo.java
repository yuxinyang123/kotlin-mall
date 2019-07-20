package xyz.chunshengyuan.mall.model.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.chunshengyuan.mall.model.bo.OrderGoods;
import xyz.chunshengyuan.mall.model.po.Order;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: V1OrdersVo
 * @date: 2019-07-20 21:46
 * @author: yuxinyang
 * @version: 1.0
 */
@Data
@NoArgsConstructor
public class V1OrdersVo {
    private Order order;
    private OrderGoodsList goods;

    public V1OrdersVo(Order order, List<OrderGoods> list) {
        this.order = order;
        this.goods = new OrderGoodsList();
        this.goods.setList(list);
        BigDecimal sum = new BigDecimal(0);
        int num = 0;
        for (OrderGoods g : list) {
            sum = sum.add(BigDecimal.valueOf(g.getGoodsNum()).multiply(g.getPrice()));
            num += g.getGoodsNum();
        }
        this.goods.setNum(num);
        this.goods.setSum(sum);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class OrderGoodsList {
        private List<OrderGoods> list;
        private Integer num;
        private BigDecimal sum;
    }
}
