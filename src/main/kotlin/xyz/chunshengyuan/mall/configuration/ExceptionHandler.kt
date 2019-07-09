package xyz.chunshengyuan.mall.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import xyz.chunshengyuan.mall.model.AdminLoginFailedException
import xyz.chunshengyuan.mall.model.BaseResponse
import xyz.chunshengyuan.mall.model.failed

/**
 * @Title: ExceptionHandler
 * @Package xyz.chunshengyuan.mall.configuration
 * @Description:
 * @author leemaster
 * @date 2019-07-07 22:48
 */

@ControllerAdvice
open class ExceptionHandler @Autowired constructor(
    private val objectMapper: ObjectMapper
){

    @ExceptionHandler(AdminLoginFailedException::class)
    @ResponseBody
    fun handleAdminLoginFailed(e: AdminLoginFailedException): BaseResponse<Nothing> = failed(403, e.message)


}