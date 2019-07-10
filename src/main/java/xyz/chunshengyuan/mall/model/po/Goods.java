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
 * @Title: Goods
 * @Package xyz.chunshengyuan.mall.model.po
 * @Description:
 * @date 2019-07-1011:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "mall_goods_tbl")
public class Goods {

    @JsonDeserialize(converter = Long2StringConveter.class)
    @JsonSerialize(converter = Long2StringConveter.class)
    @TableId
    private Long id;

    private String name;

    private BigDecimal price;

    private Long type;

    private String introduce;

    private String details;

    private String avatar;

    private Integer inventory;

    private Integer isEnable;

    private Integer isDel;

    private String location;

    private Date addTime;

    private Date updateTime;

}
