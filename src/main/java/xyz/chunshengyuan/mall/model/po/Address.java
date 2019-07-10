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
 * @Title: Address
 * @Package xyz.chunshengyuan.mall.model.po
 * @Description:
 * @date 2019-07-1010:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("mall_address_tbl")
public class Address {
    @JsonDeserialize(converter = Long2StringConveter.class)
    @JsonSerialize(converter = Long2StringConveter.class)
    @TableId
    private Long id;

    private Long userId;

    private String province;

    private String city;

    private String country;

    private String details;

    private String connect;

    private Integer isDefault;

    private String remark;

    private Date addTime;

    private Date updateTime;

}
