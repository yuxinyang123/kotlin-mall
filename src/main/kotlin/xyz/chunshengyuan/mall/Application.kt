package xyz.chunshengyuan.mall

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * @Title: A
 * @Package xyz.chunshengyuan.mall
 * @Description:
 * @author leemaster
 * @date 2019-07-06 22:36
 */

@SpringBootApplication
open class Application


fun main(args:Array<String>){
    runApplication<Application>(*args)
}