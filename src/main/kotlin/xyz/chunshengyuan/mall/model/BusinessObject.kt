package xyz.chunshengyuan.mall.model

import com.fasterxml.jackson.annotation.JsonValue
import java.util.*

/**
 * @Title: BusinessObject
 * @Package xyz.chunshengyuan.mall.model
 * @Description:
 * @author leemaster
 * @date 2019-07-07 00:42
 */


data class UserBo(

    val id: Long?,

    val name: String,

    val phone: String,

    val wxOpenId: String,

    val wxBindStatus: Int
){

    var mail: String? = null

    var addTime: Date? = null

    var updateTime: Date? = null

    enum class WxBindStatus constructor(
        var code: Int
    ){
        UNBIND(0),BINDED(1);

    }
}



data class UserExtBo(
    val id: Long?,
    val userStatus: Int,
    val userRole: String
){
    var addTime: Date? = null
    var updateTime: Date? = null

    enum class UserRole constructor(
        var code: String
    ){
        ADMIN("admin"),
        CONSUMER("consumer");

        companion object {
            fun valueOf(name: String): UserRole{
                return when(name){
                    "admin" -> ADMIN
                    else -> CONSUMER
                }
            }
        }
    }

    enum class UserStatus constructor(
        var code: Int
    ){
        OPEN(1),CLOSE(2),FORBIDDEN(3)

    }
}





data class UserAddressBo(
    val userId:Long?,
    val province: String?,
    val city: String?,
    val country: String?,
    val details: String?
){
    val id: Long? = null
    var remark: String? = null
    var addTime: Date? = null
    var updateTime: Date? = null

}

data class GoodsCategoryBo(
    val level: Int,
    val name: String,
    val icon: String,
    val color: String

){
    val id: Long? = null
    val parent: Long = 0
    var addTime: Date? = null
    var updateTime: Date? = null
}
