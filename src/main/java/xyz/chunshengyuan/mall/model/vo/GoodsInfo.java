package xyz.chunshengyuan.mall.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import xyz.chunshengyuan.mall.model.po.Banner;
import xyz.chunshengyuan.mall.model.po.Category;
import xyz.chunshengyuan.mall.utils.Long2StringConveter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author leemaster
 * @Title: GoodsEditInfo
 * @Package xyz.chunshengyuan.mall.model.vo
 * @Description:
 *
 * @date 2019-07-1012:13
 */
@Data
public class GoodsInfo {
    @JsonDeserialize(converter = Long2StringConveter.class)
    @JsonSerialize(converter = Long2StringConveter.class)
    private Long id;

    private String name;

    private BigDecimal price;

    private String introduce;

    private String location;

    private String details;

    private String avatar;

    private Integer inventory;

    private Integer isEnable;

    private Integer isDel;

    private Date addTime;

    private Date updateTime;

    private List<Banner> banners;

    private Category category;
}
