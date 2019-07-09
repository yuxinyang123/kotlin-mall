package xyz.chunshengyuan.mall.model.vo.wx;

import lombok.Data;

/**
 * @author leemaster
 * @Title: WxAccessTokenResponse
 * @Package xyz.chunshengyuan.mall.model.vo.wx
 * @Description:
 * @date 2019-07-1002:16
 */
@Data
public class WxAccessTokenResponse {

    private String access_token;

    private Integer expires_in;

    private String errcode;

    private String errmsg;
}
