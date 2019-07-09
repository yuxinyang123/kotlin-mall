package xyz.chunshengyuan.mall.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.chunshengyuan.mall.mapper.UserMapper
import xyz.chunshengyuan.mall.middleware.createToken
import xyz.chunshengyuan.mall.model.AdminLoginFailedException
import xyz.chunshengyuan.mall.model.UserExtBo
import xyz.chunshengyuan.mall.model.UserInfo

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

    @Throws(AdminLoginFailedException::class)
    fun adminUserLogin(phone: String,password: String): UserInfo{
        val detailUser = userMapper.selectUserbyPhone(phone)
        when(detailUser){
            null -> throw AdminLoginFailedException("用户不存在")
            else -> {
                if(detailUser.password.equals(password)) return createToken(detailUser)
                else throw AdminLoginFailedException("用户账户密码错误")
            }
        }
    }

    fun checkUserPhoneExists(phone: String): Boolean = userMapper.selectPhoneExits(phone)




}