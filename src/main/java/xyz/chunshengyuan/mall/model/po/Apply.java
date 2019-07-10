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
 * @Title: Apply
 * @Package xyz.chunshengyuan.mall.model.po
 * @Description:
 * @date 2019-07-1015:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "mall_apply_tbl")
public class Apply {

    @JsonDeserialize(converter = Long2StringConveter.class)
    @JsonSerialize(converter = Long2StringConveter.class)
    @TableId
    private Long id;

    private Long userId;

    private String title;

    private String status;

    private String content;

    private String phone;

    private String name;

    private String remark;

    private Date addTime;

    private Date updateTime;

}
