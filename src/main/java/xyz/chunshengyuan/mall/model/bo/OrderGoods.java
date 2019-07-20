package xyz.chunshengyuan.mall.model.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.chunshengyuan.mall.utils.Long2StringConveter;
import xyz.chunshengyuan.mall.utils.String2LongConverter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author leemaster
 * @Title: OrderGoods
 * @Package xyz.chunshengyuan.mall.model.bo
 * @Description:
 * @date 2019-07-1016:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGoods {

    @JsonDeserialize(converter = String2LongConverter.class)
    @JsonSerialize(converter = Long2StringConveter.class)
    private Long id;

    private String name;

    private BigDecimal price;

    private String introduce;

    private String avatar;

    private Date addTime;

    private Date updateTime;

    private Integer goodsNum;

}
