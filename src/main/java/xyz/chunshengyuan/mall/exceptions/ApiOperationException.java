package xyz.chunshengyuan.mall.exceptions;

import lombok.Data;

/**
 * @author leemaster
 * @Title: ApiOperationException
 * @Package xyz.chunshengyuan.mall.exceptions
 * @Description:
 * @date 2019-07-1011:21
 */
@Data
public class ApiOperationException extends Exception {

    private String remark;

    public ApiOperationException(String message, String remark) {
        super(message);
        this.remark = remark;
    }

    public ApiOperationException() {
    }

    public ApiOperationException(String message) {
        super(message);
    }

    public ApiOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiOperationException(Throwable cause) {
        super(cause);
    }

    public ApiOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
