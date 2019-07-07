package xyz.chunshengyuan.mall.model

/**
 * @Title: ResponseObjects
 * @Package xyz.chunshengyuan.mall.model
 * @Description:
 * @author leemaster
 * @date 2019-07-07 22:58
 */
//TODO kotlin 的泛型修正
data class BaseResponse<T>(
    val code: Int = 200,
    val message: String = "success",
    val error: String = "",
    val data: T?
)

fun <T> success(data:T) = BaseResponse(data = data)

fun success() = BaseResponse(data = null)

fun <T> success(code: Int,data: T) = BaseResponse(code=code,data = data)