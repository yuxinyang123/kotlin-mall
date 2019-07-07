package xyz.chunshengyuan.mall.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*

/**
 * @Title: VisualBusinessObject
 * @Package xyz.chunshengyuan.mall.model
 * @Description:
 * @author leemaster
 * @date 2019-07-07 15:38
 */

data class DetailUser constructor(
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

    val userRole: String?= "consumer"
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

