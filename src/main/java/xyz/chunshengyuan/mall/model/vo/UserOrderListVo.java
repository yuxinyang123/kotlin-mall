package xyz.chunshengyuan.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.chunshengyuan.mall.model.bo.UserOrder;

import java.util.List;

/**
 * @author leemaster
 * @Title: UserOrderListVo
 * @Package xyz.chunshengyuan.mall.model.vo
 * @Description:
 * @date 2019-07-1018:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderListVo {

    private Long total;

    private List<UserOrder> data;
}
