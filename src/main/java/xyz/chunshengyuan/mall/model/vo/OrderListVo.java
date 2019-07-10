package xyz.chunshengyuan.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.chunshengyuan.mall.model.bo.OrderGoods;

import java.util.List;

/**
 * @author leemaster
 * @Title: OrderListVo
 * @Package xyz.chunshengyuan.mall.model.vo
 * @Description:
 * @date 2019-07-1019:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListVo {

    private Long total;

    private List<OrderGoods> data;
}
