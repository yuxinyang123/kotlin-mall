package xyz.chunshengyuan.mall.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.chunshengyuan.mall.mapper.UserMapper

/**
 * @Title: UserService
 * @Package xyz.chunshengyuan.mall.service
 * @Description:
 * @author leemaster
 * @date 2019-07-08 23:54
 */

@Service
class CommonUserService @Autowired(required = false) constructor(
    val userMapper: UserMapper
){


}