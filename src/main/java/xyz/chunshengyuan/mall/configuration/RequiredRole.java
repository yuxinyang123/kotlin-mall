package xyz.chunshengyuan.mall.configuration;

import xyz.chunshengyuan.mall.constrant.UserRole;

import java.lang.annotation.*;

/**
 * @author leemaster
 * @Title: RequiredRole
 * @Package xyz.chunshengyuan.mall.configuration
 * @Description:
 * @date 2019-07-1019:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RequiredRole {
    UserRole[] roles() default {UserRole.PRECONSUMER};
}
