package xyz.chunshengyuan.mall.service

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Request
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.chunshengyuan.mall.utils.WxMiniProgramClient
import java.lang.StringBuilder

/**
 * @Title: WxService
 * @Package xyz.chunshengyuan.mall.service
 * @Description:
 * @author leemaster
 * @date 2019-07-09 00:04
 */

@Service
class WxService @Autowired constructor(
    private val wxMiniProgramClient: WxMiniProgramClient,
    private val objectMapper: ObjectMapper
) {

    private fun param2QueryStr(param: Map<String,String>): String{
        var splitor = "?"
        val builder = StringBuilder()
        builder.append(splitor)
        param.forEach{
            if(it.key.isNotBlank()){
                builder.append(it.key).append(splitor).append(it.value)
            }
            splitor = "&"
        }
        return builder.toString()
    }

    data class WxUserSession constructor(
        val openid: String? = null,
        @JsonProperty(value = "session_key")
        val sessionKey: String? = null,
        val unionid: String? = null,
        val errcode: Int? = 0,
        val errmsg: String? = null
    )

    /**
     * @see https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html
     */
    fun wxCode2Session(code:String): WxUserSession{
        val paramter = param2QueryStr(
            mapOf(
                "appid" to WxMiniProgramClient.wxAppId,
                "secret" to WxMiniProgramClient.wxAppSceret,
                "js_code" to code,
                "grant_type" to "authorization_code"
            )
        )
        return wxMiniProgramClient.getHttpClient()!!.newCall(
            Request.Builder()
                .url("https://api.weixin.qq.com/sns/jscode2session${paramter}")
                .get()
                .build()
        ).execute()
            .body().let {
                objectMapper.readValue(it.toString(),WxUserSession::class.java)
            }
    }


}