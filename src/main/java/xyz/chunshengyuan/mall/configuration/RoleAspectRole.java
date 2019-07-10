package xyz.chunshengyuan.mall.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import xyz.chunshengyuan.mall.exceptions.ApiAuthException;
import xyz.chunshengyuan.mall.utils.RequestContext;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author leemaster
 * @Title: RoleAspectRole
 * @Package xyz.chunshengyuan.mall.configuration
 * @Description:
 * @date 2019-07-1019:22
 */
@Component
@Aspect
@Slf4j
public class RoleAspectRole {

    @Pointcut("@annotation(RequiredRole)")
    public void pointcut(){}

    @Before("pointcut()")
    public void checkUserRole(JoinPoint joinPoint) throws Exception {

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getMethod(methodSignature.getName(),
                methodSignature.getParameterTypes());

        RequiredRole requiredRole = method.getAnnotation(RequiredRole.class);

        Set<String> roleSet = Arrays.asList(requiredRole.roles()).stream()
                .map(item -> item.code).collect(Collectors.toSet());

        String userRole = RequestContext.get().getUserRole();

        if (roleSet.contains(userRole))return;

        log.info("[Now user is role {} and the method required roles {}",userRole,requiredRole.roles());

        throw new ApiAuthException("用户权限不足,接口调用失败");
    }
}
