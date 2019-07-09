package xyz.chunshengyuan.mall.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.databind.util.Converter
import java.util.*

/**
 * @Title: VisualBusinessObject
 * @Package xyz.chunshengyuan.mall.model
 * @Description:
 * @author leemaster
 * @date 2019-07-07 15:38
 */

class Long2StringConverter(): Converter<Long,String>{
    override fun getOutputType(typeFactory: TypeFactory?): JavaType {
        return typeFactory!!.constructType(String::class.java)
    }

    override fun convert(value: Long?): String {
        return value!!.toString()
    }

    override fun getInputType(typeFactory: TypeFactory?): JavaType {
        return typeFactory!!.constructType(Long::class.java)
    }

}

data class DetailUser constructor(

    @JsonSerialize(converter = Long2StringConverter::class)
    @JsonDeserialize(converter = Long2StringConverter::class)
    val id: Long? = null,

    val name: String?=null,

    val phone: String?=null,

    @JsonIgnore
    val password: String? = null,

    val wxOpenId: String?=null,

    val wxBindStatus: Int?=null,

    var mail: String? = null,

    var addTime: Date? = null,

    var updateTime: Date? = null,

    val userStatus: Int?=0,

    var wxAvatarUrl: String? = "-",

    val userRole: String?= "pre-consumer"
)

// JSON Response
data class UserInfo constructor(
    val name: String,
    val token: String,
    val role: String,
    val avatar: String
)

// 分类树
data class CategoryNode(
    val id: Long,
    val level: Int,
    val name: String,
    val icon: String,
    val color: String,
    val children: List<CategoryNode>? = null
){
    var addTime: Date? = null
    var updateTime: Date? = null
}

