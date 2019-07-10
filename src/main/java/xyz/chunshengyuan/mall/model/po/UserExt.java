package xyz.chunshengyuan.mall.model.po;

import lombok.Data;

import java.util.Date;

/**
 * @author leemaster
 * @Title: UserExt
 * @Package xyz.chunshengyuan.mall.model.po
 * @Description:
 * @date 2019-07-1003:38
 */
@Data
public class UserExt {

    private Long id;

    private Integer userStatus;

    private String userRole;

    private Date addTime;

    private Date updateTime;
}
