package xyz.chunshengyuan.mall.constrant;

/**
 * @author leemaster
 * @Title: WxServiceTmpl
 * @Package xyz.chunshengyuan.mall.constrant
 * @Description:
 * @date 2019-07-1002:24
 */
public class WxServiceTmpl {

    public static final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    public static final String CODE_2_SESSION = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";


}
