package xyz.chunshengyuan.mall.model.vo.wx;

import lombok.Data;

/**
 * @author leemaster
 * @Title: WxUserSessionResponse
 * @Package xyz.chunshengyuan.mall.model.vo.wx
 * @Description:
 * @date 2019-07-1002:37
 */
@Data
public class WxUserSessionResponse {
    private String openid;

    private String session_key;

    private String unionid;

    private String errcode;

    private String errmsg;
}
