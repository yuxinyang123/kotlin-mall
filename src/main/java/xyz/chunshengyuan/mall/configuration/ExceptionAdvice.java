package xyz.chunshengyuan.mall.configuration;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.chunshengyuan.mall.exceptions.AdminLoginFailedException;
import xyz.chunshengyuan.mall.exceptions.ApiAuthException;
import xyz.chunshengyuan.mall.exceptions.ApiOperationException;
import xyz.chunshengyuan.mall.exceptions.WxRedirectException;
import xyz.chunshengyuan.mall.model.BaseResponse;

/**
 * @author leemaster
 * @Title: ExceptionAdvice
 * @Package xyz.chunshengyuan.mall.configuration
 * @Description:
 * @date 2019-07-1002:03
 */
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({AdminLoginFailedException.class})
    @ResponseBody
    public BaseResponse adminLoginFailedException(AdminLoginFailedException exception){
        return BaseResponse.failed(400,exception.getMessage(),exception.getLocalizedMessage());
    }

    @ExceptionHandler({WxRedirectException.class})
    @ResponseBody
    public BaseResponse wxRedirectException(WxRedirectException exception){
        return BaseResponse.failed(302,exception.getMessage(),exception.getMessage());
    }

    @ExceptionHandler({ApiOperationException.class})
    @ResponseBody
    public BaseResponse apiOperationException(ApiOperationException exception){
        return BaseResponse.failed(400,exception.getMessage(),exception.getRemark());
    }

    @ExceptionHandler({ApiAuthException.class})
    @ResponseBody
    public BaseResponse apiAuthException(ApiAuthException exeption){
        return BaseResponse.failed(403,exeption.getMessage(),exeption.getMessage());
    }

}
