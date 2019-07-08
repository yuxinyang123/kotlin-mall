package xyz.chunshengyuan.mall.middleware

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import xyz.chunshengyuan.mall.model.DetailUser
import xyz.chunshengyuan.mall.model.UserInfo
import javax.crypto.KeyGenerator
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

/**
 * @Title: AuthorizationConfiguration
 * @Package xyz.chunshengyuan.mall.middleware
 * @Description:
 * @author leemaster
 * @date 2019-07-06 23:48
 */



@Target(AnnotationTarget.FUNCTION)
annotation class RequiredRole(val role:String){
    companion object {
        const val ADMIN_ROLE: String = "admin"
        const val COMSUMER_ROLE: String = "consumer"
    }
}

val REQ_CONTEXT: ThreadLocal<DetailUser> = ThreadLocal()

val ALGORITHM = Algorithm.HMAC256("MALL_NEW")

val VERIFYCATION = JWT.require(ALGORITHM).build()

/**
 * parse the token to DetailUser
 */
fun parseToken(token: String): DetailUser? {

    val result = VERIFYCATION.verify(token)

    val claims = result.claims


    //TODO

    return null
}

/**
 * create the JWT Token
 */
fun createToken(user: DetailUser): UserInfo?{

    val builder = JWT.create()
    builder.withSubject("MALL_NEW")
    builder.withIssuer("MALL_NEW")

    builder.withClaim("name",user.name)
    builder.withClaim("id",user.id)
    builder.withClaim("phone",user.phone)
    builder.withClaim("wxOpenId",user.wxOpenId)
    builder.withClaim("role",user.userRole)
    builder.withClaim("userStatus",user.userStatus)

    val token = builder.sign(ALGORITHM)

    return UserInfo(
        user.name!!,
        token,
        user.userRole!!
    )
}

/**
 * Auth filter to recognize the admin and consumer
 */
class BaseAuthFilter: Filter{

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {


        chain!!.doFilter(request,response)
    }

}