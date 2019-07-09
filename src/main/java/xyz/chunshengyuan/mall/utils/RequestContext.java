package xyz.chunshengyuan.mall.utils;

import sun.jvm.hotspot.debugger.ThreadContext;
import xyz.chunshengyuan.mall.model.bo.DetailUser;
import xyz.chunshengyuan.mall.model.po.User;

/**
 * @author leemaster
 * @Title: RequestContext
 * @Package xyz.chunshengyuan.mall.utils
 * @Description:
 * @date 2019-07-1001:33
 */
public class RequestContext {

    private static final ThreadLocal<DetailUser> local = new ThreadLocal<>();

    public static void set(DetailUser user){
        local.set(user);
    }

    public static DetailUser get(){
        return local.get();
    }
}
