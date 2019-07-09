package xyz.chunshengyuan.mall.exceptions;

/**
 * @author leemaster
 * @Title: AdminLoginFailedException
 * @Package xyz.chunshengyuan.mall.exceptions
 * @Description:
 * @date 2019-07-1002:04
 */
public class AdminLoginFailedException extends Exception {

    public AdminLoginFailedException() {
    }

    public AdminLoginFailedException(String message) {
        super(message);
    }

    public AdminLoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminLoginFailedException(Throwable cause) {
        super(cause);
    }

    public AdminLoginFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
