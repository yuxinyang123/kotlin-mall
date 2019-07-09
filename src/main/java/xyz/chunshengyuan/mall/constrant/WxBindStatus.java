package xyz.chunshengyuan.mall.constrant;

/**
 * @author leemaster
 * @Title: WxBindStatus
 * @Package xyz.chunshengyuan.mall.constrant
 * @Description:
 * @date 2019-07-1001:40
 */
public enum  WxBindStatus {
    UNBIND(0),BINDED(1);
    public Integer code;

    WxBindStatus(Integer code) {
        this.code = code;
    }
}
