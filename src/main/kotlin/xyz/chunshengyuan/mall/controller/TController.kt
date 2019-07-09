package xyz.chunshengyuan.mall.controller


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.chunshengyuan.mall.mapper.UserMapper
import xyz.chunshengyuan.mall.model.BaseResponse
import xyz.chunshengyuan.mall.model.success
import xyz.chunshengyuan.mall.utils.SnowFlakeIdGenerator

/**
 * @Title: TController
 * @Package xyz.chunshengyuan.mall.controller
 * @Description:
 * @author leemaster
 * @date 2019-07-07 16:58
 */

@RestController
@RequestMapping("/test")
class TController @Autowired(required = false) constructor(
    val snowFlakeIdGenerator: SnowFlakeIdGenerator,
    val userMapper: UserMapper
){

    @PostMapping("/user/insert")
    fun testInsertUser(): BaseResponse<in Any>{

        return success(
            userMapper.selectUserDetailsAll()
        )
    }

}