package xyz.chunshengyuan.mall.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * @Title: Request
 * @Package xyz.chunshengyuan.mall.utils
 * @Description:
 * @author leemaster
 * @date 2019-07-08 23:58
 */

@Component
class WxMiniProgramClient{

    @Value("\${system.wxappid}")
    fun _set_wx_app_id(id: String){
        WxMiniProgramClient.wxAppId = id
    }

    @Value("\${system.wxsceret}")
    fun _set_wx_sceret_key(key:String){
        WxMiniProgramClient.wxAppSceret = key
    }




    companion object {
        var wxAppId: String = ""
        var wxAppSceret: String = ""
    }
}