package xyz.chunshengyuan.mall.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.chunshengyuan.mall.mapper.UserRepository

/**
 * @Title: UGoodsController
 * @Package xyz.chunshengyuan.mall.controller
 * @Description:
 * @author leemaster
 * @date 2019-07-06 23:47
 */
@RestController
@RequestMapping("/test")
class UGoodsController @Autowired constructor(
    val userRepository: UserRepository
) {

    @GetMapping("")
    fun ping(): String{
        return userRepository.getOne(1).name
    }
}