package xyz.chunshengyuan.mall.exceptions;

/**
 * @author leemaster
 * @Title: ApiAuthException
 * @Package xyz.chunshengyuan.mall.exceptions
 * @Description:
 * @date 2019-07-1019:45
 */
public class ApiAuthException extends Exception {
    public ApiAuthException() {
    }

    public ApiAuthException(String message) {
        super(message);
    }

    public ApiAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiAuthException(Throwable cause) {
        super(cause);
    }

    public ApiAuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
