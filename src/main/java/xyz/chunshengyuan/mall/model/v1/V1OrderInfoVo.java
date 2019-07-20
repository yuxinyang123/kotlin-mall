package xyz.chunshengyuan.mall.model.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.chunshengyuan.mall.model.bo.OrderGoods;
import xyz.chunshengyuan.mall.model.po.Address;
import xyz.chunshengyuan.mall.model.po.Order;

import java.util.List;

/**
 * @description: V1OrderInfoVo
 * @date: 2019-07-20 18:30
 * @author: yuxinyang
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class V1OrderInfoVo {
    private List<OrderGoods> list;
    private Order order;
    private Address address;
}
