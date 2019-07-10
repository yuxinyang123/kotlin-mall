package xyz.chunshengyuan.mall.model.bo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.chunshengyuan.mall.utils.Long2StringConveter;

import java.math.BigDecimal;

/**
 * @author leemaster
 * @Title: UserOrder
 * @Package xyz.chunshengyuan.mall.model.bo
 * @Description:
 * # 订单表
 * create table mall_order_tbl(
 *   id bigint unsigned not null auto_increment,
 *   sum decimal(20,4) not null default 0.0,
 *   status int not null default 0 comment '0 初始化 1 等待支付 2 等待派送 3 派送中 4 订单结束 5 订单取消',
 *   user_id bigint unsigned not null ,
 *   logistics varchar(255) not null default '-',
 *   carrier_name varchar(255) not null default '普通物流 中通申通',
 *   add_time datetime not null default current_timestamp,
 *   update_time datetime not null  default current_timestamp on update current_timestamp,
 *   primary key (id)
 * );
 * @date 2019-07-1017:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrder {

    @JsonDeserialize(converter = Long2StringConveter.class)
    @JsonSerialize(converter = Long2StringConveter.class)
    private Long id;

    private BigDecimal orderSum;

    private Integer status;

    private String logistics;

    private String carrierName;

    private String userName;

    private String userPhone;

}
