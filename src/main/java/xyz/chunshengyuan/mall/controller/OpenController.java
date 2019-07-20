package xyz.chunshengyuan.mall.controller;

import org.springframework.web.bind.annotation.*;
import xyz.chunshengyuan.mall.configuration.RequiredRole;
import xyz.chunshengyuan.mall.constrant.UserRole;
import xyz.chunshengyuan.mall.exceptions.AdminLoginFailedException;
import xyz.chunshengyuan.mall.exceptions.WxRedirectException;
import xyz.chunshengyuan.mall.model.BaseResponse;
import xyz.chunshengyuan.mall.model.bo.DetailUser;
import xyz.chunshengyuan.mall.model.vo.AdminLoginForm;
import xyz.chunshengyuan.mall.model.vo.WxUserForm;
import xyz.chunshengyuan.mall.service.UserService;
import xyz.chunshengyuan.mall.utils.RequestContext;

import javax.annotation.Resource;

/**
 * @author leemaster
 * @Title: OpenController
 * @Package xyz.chunshengyuan.mall.controller
 * @Description:
 * @date 2019-07-1002:17
 */
@RestController
@RequestMapping("/open")
public class OpenController {

    @Resource
    UserService userService;

    /**
     * 管理端登陆
     *
     * @param form
     * @return
     * @throws AdminLoginFailedException
     */
    @PostMapping("/admin/login")
    public BaseResponse adminLogin(@RequestBody AdminLoginForm form) throws AdminLoginFailedException {
        return BaseResponse.succes(
                userService.adminLogin(
                        form.getPhone(),
                        form.getPassword()
                )
        );
    }

    /**
     * 用户登陆
     *
     * @return
     */
    @PostMapping("/wx/login")
    public BaseResponse wxLogin(@RequestParam("code") String code) throws WxRedirectException, AdminLoginFailedException {
        return BaseResponse.succes(userService.wxAccountLogin(code));
    }

    /**
     * 用户信息补全
     *
     * @return
     */
    @PutMapping("/wx/complete")
    public BaseResponse wxComplete(@RequestBody WxUserForm form) {
        return BaseResponse.succes(userService.wxAccountRegister(form));
    }
}
