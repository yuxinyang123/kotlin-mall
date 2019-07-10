package xyz.chunshengyuan.mall;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import xyz.chunshengyuan.mall.utils.SnowFlakeIdGenerator;

/**
 * @author leemaster
 * @Title: MallApplication
 * @Package xyz.chunshengyuan.mall
 * @Description:
 * @date 2019-07-1001:37
 */
@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = {"xyz.chunshengyuan.mall.mapper"})
public class MallApplication {

    @Bean
    public SnowFlakeIdGenerator snowFlakeIdGenerator(){
        return new SnowFlakeIdGenerator(1L,2L);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class,args);
    }
}
