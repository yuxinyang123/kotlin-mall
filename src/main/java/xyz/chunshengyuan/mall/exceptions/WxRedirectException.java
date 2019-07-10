package xyz.chunshengyuan.mall.exceptions;

import lombok.Data;

/**
 * @author leemaster
 * @Title: WxRedirectException
 * @Package xyz.chunshengyuan.mall.exceptions
 * @Description:
 * @date 2019-07-1003:42
 */
@Data
public class WxRedirectException extends Exception {

    private String id;

    public WxRedirectException() {
    }

    public WxRedirectException(String message, String id) {
        super(message);
        this.id = id;
    }

    public WxRedirectException(String message) {
        super(message);
    }

    public WxRedirectException(String message, Throwable cause) {
        super(message, cause);
    }

    public WxRedirectException(Throwable cause) {
        super(cause);
    }

    public WxRedirectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
