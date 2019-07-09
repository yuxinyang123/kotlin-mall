package xyz.chunshengyuan.mall.model

import java.math.BigDecimal
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

    val name: String?,

    val phone: String?,

    val password: String?,

    val wxOpenId: String?,

    val wxBindStatus: Int?
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
        PRECONSUMER("pre-consumer"),
        CONSUMER("consumer");

        companion object {
            fun valueOf(name: String): UserRole{
                return when(name){
                    "admin" -> ADMIN
                    "pre-consumer" -> PRECONSUMER
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

// address
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

// category
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

// goods
data class GoodsBo(
    val name: String? = null,
    val price: BigDecimal = BigDecimal(0.0),
    val type: Long? = null,
    val introduce: String? = "",
    val details: String? = "",
    val avatar: String? = "",
    val inventory: Int? = 0,
    val location: String? = "",
    val isEnable: Int? = 1,
    val isDel: Int = 0
){
    var addTime: Date? = null
    var updateTime: Date? = null
}

data class BannerBo(
    val name: String? = "-",
    val url: String? = "-",
    val link: String? = "",
    val serial: Int = 0,
    val type: String? = "item",
    val location: String? = "index"
){
    var addTime: Date? = null
    var updateTime: Date? = null

    enum class BannerType(
        val code: String
    ){
        ITEM("item"),
        CATEGORY("category"),
        REMOTE("remote");
        companion object{
            fun valueOf(name: String): BannerType{
                return when(name){
                    "item" -> ITEM
                    "category" -> CATEGORY
                    else -> REMOTE
                }
            }
        }
    }
}

data class PushBo(
    val icon: String? = "",
    val coverUrl: String? = "",
    val pushType: String? = "index",
    val link: String? = ""
){
    var addTime: Date? = null
    var updateTime: Date? = null

    enum class PustType(
        val code: String
    ) {
        INDEX("index"),
        NEW("new"),
        CATEGORY("category"),
        HOT("hot");

        companion object {
            fun valueOf(name: String): PustType{
                return when(name){
                    "index" -> INDEX
                    "hot" -> NEW
                    "category" -> CATEGORY
                    else -> HOT
                }
            }
        }
    }
}

