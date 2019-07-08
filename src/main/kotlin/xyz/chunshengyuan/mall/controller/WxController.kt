package xyz.chunshengyuan.mall.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.chunshengyuan.mall.model.BaseResponse
import xyz.chunshengyuan.mall.model.UserInfo
import xyz.chunshengyuan.mall.model.success

/**
 * @Title: WxController
 * @Package xyz.chunshengyuan.mall.controller
 * @Description:
 * @author leemaster
 * @date 2019-07-06 23:07
 */

@RestController
@RequestMapping("/open/wx")
class WxController @Autowired constructor(

){

    @PostMapping("/admin/login")
    fun adminLogin():BaseResponse<UserInfo?>{

        return success(null)
    }

}