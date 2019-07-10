package xyz.chunshengyuan.mall.model.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author leemaster
 * @Title: User
 * @Package xyz.chunshengyuan.mall.model.po
 * @Description:
 * @date 2019-07-1001:38
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mall_user_tbl")
public class User {
    @TableId
    private Long id;

    private String name;

    private String phone;

    private String password;

    private String wxOpenId;

    private Integer wxBindStatus;

    private String mail;

    private Date addTime;

    private Date updateTime;

    private String wxAvatarUrl;
}
