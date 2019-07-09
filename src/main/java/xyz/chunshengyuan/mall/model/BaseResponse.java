package xyz.chunshengyuan.mall.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author leemaster
 * @Title: BaseResponse
 * @Package xyz.chunshengyuan.mall.model
 * @Description:
 * @date 2019-07-1002:05
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    private Integer code;

    private String message;

    private String error ;

    private T data;


    public static BaseResponse success(){
        return new BaseResponse(200,"success","null",null);
    }

    public static <T> BaseResponse succes(T data){
        return new BaseResponse(200,"success","null",data);
    }

    public static BaseResponse failed(Integer code){
        return new BaseResponse(code,"success","null",null);
    }

    public static <T> BaseResponse failed(Integer code,String data){
        return new BaseResponse(code,"success",data,data);
    }

    public static BaseResponse failed(Integer code,String msg,String data){
        return new BaseResponse(code,msg,data,data);
    }

}
