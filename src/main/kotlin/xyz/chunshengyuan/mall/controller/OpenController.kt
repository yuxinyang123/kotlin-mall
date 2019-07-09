package xyz.chunshengyuan.mall.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.chunshengyuan.mall.mapper.UserMapper
import xyz.chunshengyuan.mall.model.BaseResponse
import xyz.chunshengyuan.mall.model.UserInfo
import xyz.chunshengyuan.mall.model.success
import xyz.chunshengyuan.mall.service.CommonUserService

/**
 * @Title: OpenController
 * @Package xyz.chunshengyuan.mall.controller
 * @Description:
 * @author leemaster
 * @date 2019-07-06 23:07
 */

@RestController
@RequestMapping("/open/wx")
class WxController @Autowired constructor(

){


}


@RestController
@RequestMapping("/open/admin")
class AdminController @Autowired constructor(
    private val commonUserService: CommonUserService
){


    data class AdminLoginForm(
        val phone:String? = null,
        val password:String? = null
    )
    @PostMapping("/login")
    fun adminLogin(@RequestBody form: AdminLoginForm): BaseResponse<UserInfo> = success(commonUserService.adminUserLogin(form.phone ?: "",form.password ?: ""))



}