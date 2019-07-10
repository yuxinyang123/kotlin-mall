package xyz.chunshengyuan.mall.model.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.chunshengyuan.mall.utils.Long2StringConveter;

import java.util.Date;

/**
 * @author leemaster
 * @Title: Category
 * @Package xyz.chunshengyuan.mall.model.po
 * @Description:
 * @date 2019-07-1010:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "mall_category_tbl")
public class Category {

    @JsonDeserialize(converter = Long2StringConveter.class)
    @JsonSerialize(converter = Long2StringConveter.class)
    @TableId
    private Long id;

    private String name;

    private String icon;

    private String color;

    @JsonDeserialize(converter = Long2StringConveter.class)
    @JsonSerialize(converter = Long2StringConveter.class)
    private Long parent;

    private Integer level;

    private Date addTime;

    private Date updateTime;
}
