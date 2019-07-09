package xyz.chunshengyuan.mall.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.chunshengyuan.mall.exceptions.AdminLoginFailedException;
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

    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(AdminLoginFailedException.class)
    @ResponseBody
    public BaseResponse adminLoginFailedException(AdminLoginFailedException exception){
        return BaseResponse.failed(400,exception.getMessage(),exception.getCause().toString());
    }
}
