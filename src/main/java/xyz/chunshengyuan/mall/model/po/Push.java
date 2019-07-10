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
 * @Title: Push
 * @Package xyz.chunshengyuan.mall.model.po
 * @Description:
 * @date 2019-07-1014:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mall_push_tbl")
public class Push {

    @JsonDeserialize(converter = Long2StringConveter.class)
    @JsonSerialize(converter = Long2StringConveter.class)
    @TableId
    private Long id;

    private String icon;

    private String coverUrl;

    private String pushType;

    private String link;


    private Date addTime;

    private Date updateTime;
}
