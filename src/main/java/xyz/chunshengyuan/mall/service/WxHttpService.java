package xyz.chunshengyuan.mall.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.chunshengyuan.mall.constrant.WxServiceTmpl;
import xyz.chunshengyuan.mall.model.vo.wx.WxAccessTokenResponse;
import xyz.chunshengyuan.mall.model.vo.wx.WxUserSessionResponse;
import xyz.chunshengyuan.mall.utils.SSLSocketClient;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author leemaster
 * @Title: WxHttpService
 * @Package xyz.chunshengyuan.mall.service
 * @Description:
 * @date 2019-07-1002:14
 */
@Service
@Setter
@Getter
@Slf4j
public class WxHttpService implements InitializingBean {

    private static OkHttpClient client;

    public static String ACCESS_TOKEN = "";

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        synchronized (this){
            client = new OkHttpClient.Builder()
                    .connectTimeout(10L, TimeUnit.SECONDS)
                    .readTimeout(10L, TimeUnit.SECONDS)
                    .writeTimeout(10L, TimeUnit.SECONDS)
                    .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier()).build();
        }

        log.info("[Initialize the Wx Token]");
        refreshWxAccessToken();
        log.info("[Initialize the Wx Token Done ]");
    }

    @Value("${system.wxappid}")
    private String wxAppId;

    @Value("${system.wxsceret}")
    private String wxSceret;

    @Scheduled(cron = "0 */40 */1 * * ?")
    private void refreshWxAccessToken() throws IOException {
        WxAccessTokenResponse response =  this.getAccessToken();
        String token = response.getAccess_token();
        if(Strings.isBlank(token)){
            log.error("[Refresh the Wx Token FAILED ->>>> {}]",response);
        }else{
            ACCESS_TOKEN = token;
            log.info("[Refresh the Wx Token Success {}]",ACCESS_TOKEN);
        }
    }

    /**
     * 获取 Access Token
     * @return
     */
    private WxAccessTokenResponse getAccessToken() throws IOException {
        String url = String.format(WxServiceTmpl.GET_ACCESS_TOKEN,wxAppId,wxSceret);
        Response response = client.newCall(
                (new Request.Builder())
                .url(url)
                .get()
                .build()
        ).execute();
        String content = response.body().string();
        return objectMapper.readValue(content,WxAccessTokenResponse.class);
    }

    // ----- Other function to call the api

    private String formatQueryString(Map<String,String> parameters){
        final String[] splitor = {"?"};
        StringBuilder builder = new StringBuilder();
        parameters.forEach((key,value) -> {
            builder.append(splitor[0]).append(key).append("=").append(value);
            splitor[0] = "&";
        });
        return builder.toString();
    }

    /**
     * 用户 code 换取 Session
     * @param code
     * @return
     * @throws IOException
     */
    public WxUserSessionResponse wxCode2Session(String code) throws IOException {
        String url = String.format(WxServiceTmpl.CODE_2_SESSION,wxAppId,wxSceret,code);
        Response response = client.newCall(
                (new Request.Builder())
                        .url(url)
                        .get()
                        .build()
        ).execute();
        String content = response.body().string();
        return objectMapper.readValue(content,WxUserSessionResponse.class);
    }

}
