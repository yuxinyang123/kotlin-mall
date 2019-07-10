package xyz.chunshengyuan.mall.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.chunshengyuan.mall.model.bo.DetailUser;
import xyz.chunshengyuan.mall.utils.JWTHelper;
import xyz.chunshengyuan.mall.utils.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

/**
 * @author leemaster
 * @Title: FilterConfiguration
 * @Package xyz.chunshengyuan.mall.configuration
 * @Description:
 * @date 2019-07-1001:16
 */
@Configuration
public class FilterConfiguration {

    @Autowired private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(FilterConfiguration.class);

    @Bean
    public FilterRegistrationBean corsFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter((req, res, chain) -> {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            response.setHeader("Access-Control-Allow-Origin","*");
            response.setHeader("Access-Control-Allow-Methods","POST, GET, DELETE, OPTIONS, DELETE");
            response.setHeader("Access-Control-Allow-Headers","x-token,Content-Type, x-requested-with, X-Custom-Header");

            if ("OPTIONS".equals(request.getMethod().toUpperCase())){
                response.getWriter().close();
            }else{
                chain.doFilter(req,res);
            }
        });

        bean.setOrder(1);
        bean.setName("CORS FILTER");
        bean.setUrlPatterns(Collections.singletonList("/*"));

        return bean;
    }

    @Bean
    public FilterRegistrationBean baseAuthFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter((req, res, chain) -> {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            String token = request.getHeader("X-TOKEN");

            DetailUser user = JWTHelper.parseToken(token);

            RequestContext.set(user);

            logger.info("[User {} <<->> {} Role ->>> {} To ->> \n {}",user.getName(),user.getId(),user.getUserRole(),request.getRequestURI());

            chain.doFilter(req,res);
        });

        bean.setOrder(10);
        bean.setName("AUTH FILTER");
        bean.setUrlPatterns(Collections.singletonList("/api/*"));

        return bean;
    }
}
