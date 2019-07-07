package xyz.chunshengyuan.mall.middleware

/**
 * @Title: AuthorizationConfiguration
 * @Package xyz.chunshengyuan.mall.middleware
 * @Description:
 * @author leemaster
 * @date 2019-07-06 23:48
 */




@Target(AnnotationTarget.FUNCTION)
annotation class RequiredRole(val role:String){
    // 常量部分
    companion object {
        // 权限常量
        val ADMIN_ROLE: String = "ADMIN"
        val COMSUMER_ROLE: String = "CONSUMER"
    }
}

