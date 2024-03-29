package xyz.chunshengyuan.mall.model.bo;

import com.baomidou.mybatisplus.annotation.TableId;
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
 * @Title: CartGoods
 * @Package xyz.chunshengyuan.mall.model.bo
 * @Description:
 * @date 2019-07-1016:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartGoods {

    @JsonDeserialize(converter = Long2StringConveter.class)
    @JsonSerialize(converter = Long2StringConveter.class)
    @TableId
    private Long id;

    private String name;

    private Long type;

    private BigDecimal price;

    private String introduce;

    private String avatar;

    private Integer inventory;

    private String location;

    private Date addTime;

    private Date updateTime;

    private Integer goodsNum;
}
