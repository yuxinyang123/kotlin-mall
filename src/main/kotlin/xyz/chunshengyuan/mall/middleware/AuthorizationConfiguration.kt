package xyz.chunshengyuan.mall.middleware

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import xyz.chunshengyuan.mall.model.DetailUser
import xyz.chunshengyuan.mall.model.UserExtBo
import xyz.chunshengyuan.mall.model.UserInfo
import xyz.chunshengyuan.mall.model.failed
import javax.crypto.KeyGenerator
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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

    try {
        val result = VERIFYCATION.verify(token)

        val claims = result.claims

        return DetailUser(
            id = claims.get("id")?.asLong(),
            name = claims.get("name")?.asString(),
            wxBindStatus = claims.get("wxBindStatus")?.asInt(),
            phone = claims.get("phone")?.asString(),
            wxOpenId = claims.get("wxOpenId")?.asString(),
            userRole = claims.get("role")?.asString(),
            userStatus = claims.get("userStatus")?.asInt()
        )

    }catch (e: JWTVerificationException){
        // only return the default user
        return DetailUser(
            userRole = UserExtBo.UserRole.PRECONSUMER.code,
            id = -1
        )
    }

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
    builder.withClaim("wxBindStatus",user.wxBindStatus)
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
class BaseAuthFilter(
    val mapper: ObjectMapper
): Filter{

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        val httpRequest = request as HttpServletRequest

        val token = httpRequest.getHeader("X-TOKEN")

        val user = parseToken(token)

        if (user!!.id == -1L){
            response!!.writer.write(mapper.writeValueAsString(
                failed(403)
            ))
        }

        REQ_CONTEXT.set(user);

        chain!!.doFilter(request,response)
    }

}


@Configuration
open class FilterConfigutation @Autowired constructor(
    val objectMapper: ObjectMapper
){

    @Bean
    open fun corsFilter(): FilterRegistrationBean<Filter> {
        var bean = FilterRegistrationBean<Filter>()
        bean.filter = object : Filter{
            override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
                val httpResponse = response as HttpServletResponse

                response.setHeader("","")
                response.setHeader("","")
                response.setHeader("","")
                response.setHeader("","")
            }

        }
        bean.setName("CORS_FILTER")
        bean.order = 0

        return bean
    }


}