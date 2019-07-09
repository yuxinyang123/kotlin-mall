package xyz.chunshengyuan.mall

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import xyz.chunshengyuan.mall.utils.SnowFlakeIdGenerator

/**
 * @Title: A
 * @Package xyz.chunshengyuan.mall
 * @Description:
 * @author leemaster
 * @date 2019-07-06 22:36
 */

@SpringBootApplication
@EnableScheduling
@MapperScan("xyz.chunshengyuan.mall.mapper")
open class Application{

    @Bean
    open fun snowflakeGenerator(): SnowFlakeIdGenerator{
        return SnowFlakeIdGenerator(1L,2L)
    }
}

fun main(args:Array<String>){


    runApplication<Application>(*args)
}