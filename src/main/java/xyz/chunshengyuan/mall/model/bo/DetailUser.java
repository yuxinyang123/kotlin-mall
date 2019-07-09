package xyz.chunshengyuan.mall.model.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import xyz.chunshengyuan.mall.utils.Long2StringConveter;

import java.util.Date;

/**
 * @author leemaster
 * @Title: DetailUser
 * @Package xyz.chunshengyuan.mall.model.bo
 * @Description:
 * @date 2019-07-1001:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailUser {

    @JsonDeserialize(converter = Long2StringConveter.class)
    @JsonSerialize(converter = Long2StringConveter.class)
    private Long id;

    private String name;

    private String phone;

    @JsonIgnore
    private String password;

    private String wxOpenId;

    private Integer wxBindStatus;

    private String mail;

    private Date addTime;

    private Date updateTime;

    private Integer userStatus;

    private String wxAvatarUrl = "-";

    private String userRole = "pre-consumer";

}
