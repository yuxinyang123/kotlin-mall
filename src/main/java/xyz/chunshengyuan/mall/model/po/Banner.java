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
 * @Title: Banner
 * @Package xyz.chunshengyuan.mall.model.po
 * @Description:
 * @date 2019-07-1012:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "mall_banner_tbl")
public class Banner {

    @JsonDeserialize(converter = Long2StringConveter.class)
    @JsonSerialize(converter = Long2StringConveter.class)
    @TableId
    private Long id;

    @JsonDeserialize(converter = Long2StringConveter.class)
    @JsonSerialize(converter = Long2StringConveter.class)
    private Long goodsId;

    private String name;

    private String url;

    private String link;

    private Integer serial;

    private String type;

    private String location;

    private Date addTime;

    private Date updateTime;
}
