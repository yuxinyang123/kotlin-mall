package xyz.chunshengyuan.mall.utils


import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

/**
 * @Title: Request
 * @Package xyz.chunshengyuan.mall.utils
 * @Description:
 * @author leemaster
 * @date 2019-07-08 23:58
 */

@Component
class WxMiniProgramClient @Autowired constructor(
    private val objectMapper: ObjectMapper
): InitializingBean{
    private val log = LoggerFactory.getLogger(WxMiniProgramClient::class.java)


    override fun afterPropertiesSet() {
        log.info("Firstly Start to init the Wx Access Token ")
        accessWxAccessToken()
        log.info("Firstly Ended to complete the Token")
    }


    @Value("\${system.wxappid}")
    fun _set_wx_app_id(id: String){
        WxMiniProgramClient.wxAppId = id
    }

    @Value("\${system.wxsceret}")
    fun _set_wx_sceret_key(key:String){
        WxMiniProgramClient.wxAppSceret = key
    }

    data class AccessTokenResponse(
        val access_token: String? = null,
        val expires_in: Int? = 0,
        val errcode: String? = null,
        val errmsg: String? = null
    )

    private fun accessWxAccessToken(){
        var response = HttpManager.instace().httpClient!!.newCall(
            Request.Builder()
                .get()
                .url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=${WxMiniProgramClient.wxAppId}&secret=${WxMiniProgramClient.wxAppSceret}")
                .build()
        ).execute()
        val responseString = response.body()?.string()
        log.info("[Get new Wx Token Response {}]",responseString)

        //TODO WxMiniProgramClient.accessToekn = objectMapper.readValue(responseString,AccessTokenResponse::class.java).access_token!!

        log.info("[Get new accssToken ${WxMiniProgramClient.accessToekn}]")
    }

    @Scheduled(cron = "0 */40 */1 * * ?") // 1小时 40 分 刷新 token
    private fun refreshWxAccessToken(){
        log.info("Start to init the Wx Access Token ")
        accessWxAccessToken()
        log.info("Ended to complete the Token")
    }

    companion object {
        var wxAppId: String = ""
        var wxAppSceret: String = ""
        var accessToekn: String = ""


    }

    fun getHttpClient() = HttpManager.instace().httpClient
    fun getWxAppId() = wxAppId
    fun getWxAppSceret() = wxAppSceret
    fun getAccessToken() = accessToekn
}

class HttpManager private constructor() {

    var httpClient: OkHttpClient? = null
    val timeUnit: TimeUnit = TimeUnit.SECONDS
    val connectTimeOut: Long = 10
    val readTimeOut: Long = 10
    val writeTimeOut: Long = 10

    companion object {
        fun instace() = Holder.INSTACE
    }

    private object Holder {
        val INSTACE = HttpManager()

        init {
            INSTACE.initHttpClient()
        }
    }

    private fun initHttpClient(): OkHttpClient? {
        var builder = OkHttpClient.Builder()
            .connectTimeout(connectTimeOut, timeUnit)
            .readTimeout(readTimeOut, timeUnit)
            .writeTimeout(writeTimeOut, timeUnit)
            .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
            .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
        httpClient = builder.build()
        return httpClient
    }

}

