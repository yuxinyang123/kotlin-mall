package xyz.chunshengyuan.mall.model.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.chunshengyuan.mall.utils.Long2StringConveter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author leemaster
 * @Title: Order
 * @Package xyz.chunshengyuan.mall.model.po
 * @Description:
 * @date 2019-07-1016:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "mall_order_tbl")
public class Order {

    @JsonDeserialize(converter = Long2StringConveter.class)
    @JsonSerialize(converter = Long2StringConveter.class)
    @TableId
    private Long id;

    private BigDecimal sum;

    private Integer status;

    private Long addressId;

    private Long userId;

    private String logistics;

    private String carrierName;

    private Date addTime;

    private Date updateTime;
}
