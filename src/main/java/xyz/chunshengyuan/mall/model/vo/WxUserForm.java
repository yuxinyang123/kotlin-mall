package xyz.chunshengyuan.mall.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author leemaster
 * @Title: WxUserForm
 * @Package xyz.chunshengyuan.mall.model.vo
 * @Description:
 * 只能拿到 头像和用户名
 * @date 2019-07-1010:15
 */
@Data
public class WxUserForm {

    @NotNull
    private String avatar;

    @NotNull
    private String name;

    @NotNull
    private String openId;
}
