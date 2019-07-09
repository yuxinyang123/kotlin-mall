package xyz.chunshengyuan.mall.middleware

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.fasterxml.jackson.databind.ObjectMapper
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMapping
import xyz.chunshengyuan.mall.model.*
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
annotation class RequiredRole(
    val role:Array<String> = [PRE_CONSUMER_ROLE]
    ){
    companion object {
        const val ADMIN_ROLE: String = "admin"
        const val COMSUMER_ROLE: String = "consumer"
        const val PRE_CONSUMER_ROLE: String = "pre-consumer"
    }
}

@Aspect
class RoleAspect {

    private final val log = LoggerFactory.getLogger(RoleAspect::class.java)


    @Throws(Throwable::class)
    @Around(value = "@annotation(xyz.chunshengyuan.mall.middleware.RequiredRole)")
    fun roleChecker(joinPoint: ProceedingJoinPoint, requiredRole: RequiredRole) : Any{
        log.info("[ the user ${REQ_CONTEXT.get().name} will check ${REQ_CONTEXT.get().userRole}")

        val settedRoles = requiredRole.role.asList().toSet()
        val userRole = REQ_CONTEXT.get().userRole

        if(settedRoles.contains(userRole))return joinPoint.proceed()

        throw ApiAuthorizationException("无权限访问 ${joinPoint.signature.name}")
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
            name = "No Login user",
            id = -1
        )
    }

}

/**
 * create the JWT Token
 */
fun createToken(user: DetailUser): UserInfo{

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
        user.userRole ?: UserExtBo.UserRole.PRECONSUMER.code,
        user.wxAvatarUrl ?: "-"
    )
}


@Configuration
open class FilterConfigutation @Autowired constructor(
    val objectMapper: ObjectMapper // CSRF 服务器需要使用
){

    private val log = LoggerFactory.getLogger("AccessLogger")

    @Bean
    open fun corsFilter(): FilterRegistrationBean<Filter> {
        val bean = FilterRegistrationBean<Filter>()
        bean.filter = Filter { request, response, chain ->
            val httpResponse = response as HttpServletResponse
            val httpRequest = request as HttpServletRequest

            response.setHeader("Access-Control-Allow-Origin","*")
            response.setHeader("Access-Control-Allow-Methods","POST, GET, DELETE, OPTIONS, DELETE")
            response.setHeader("Access-Control-Allow-Headers","x-token,Content-Type, x-requested-with, X-Custom-Header")

            when(request.method.toUpperCase()){
                "OPTIONS" -> response.status = 204
                else -> chain!!.doFilter(request,response)
            }
        }
        bean.setName("CORS_FILTER")
        bean.order = 0
        bean.urlPatterns = listOf("/*")
        log.info("Complete register the Cors filter")
        return bean
    }

    @Bean
    @ConditionalOnProperty(value = ["auth"],prefix = "system",havingValue = "true")
    open fun baseAuthFilter(): FilterRegistrationBean<Filter>{
        val bean = FilterRegistrationBean<Filter>()

        bean.filter = Filter { request, response, chain ->

            val httpRequest = request as HttpServletRequest

            val token = httpRequest.getHeader("X-TOKEN")

            val user = parseToken(token)

            REQ_CONTEXT.set(user)

            log.info("[User with name ${user?.name} and id ${user?.id} and the role is ${user?.userRole} to ${httpRequest.requestURI}]")

            chain!!.doFilter(request,response)
        }
        bean.setName("JWT_FILTER")
        bean.order = 1
        bean.urlPatterns = listOf("/api/**")
        log.info("Complete register the BaseAuth JWT filter")
        return bean
    }

    @Bean
    open fun accessLogFilter(): FilterRegistrationBean<Filter>{
        val bean = FilterRegistrationBean<Filter>()

        bean.filter = Filter { request, response, chain ->

            val httpRequest = request as HttpServletRequest

            log.info("[Recive a request ${httpRequest.requestURI}]")

            chain!!.doFilter(request,response)
        }
        bean.setName("LOG_FILTER")
        bean.order = 2
        bean.urlPatterns = listOf("/*")
        return bean
    }

}