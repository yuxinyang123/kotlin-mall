package xyz.chunshengyuan.mall.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.web.bind.annotation.*;
import xyz.chunshengyuan.mall.configuration.RequiredRole;
import xyz.chunshengyuan.mall.constrant.ApplyStatus;
import xyz.chunshengyuan.mall.constrant.UserRole;
import xyz.chunshengyuan.mall.exceptions.ApiOperationException;
import xyz.chunshengyuan.mall.mapper.ApplyPlusMapper;
import xyz.chunshengyuan.mall.model.BaseResponse;
import xyz.chunshengyuan.mall.model.bo.DetailUser;
import xyz.chunshengyuan.mall.model.po.Apply;
import xyz.chunshengyuan.mall.service.AppliService;
import xyz.chunshengyuan.mall.utils.RequestContext;

import javax.annotation.Resource;

/**
 * @description: V1ApplicationController
 * @date: 2019-07-11 01:25
 * @author: yuxinyang
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/v1/application")
public class V1ApplicationController {

    @Resource
    AppliService appliService;

    @Resource
    ApplyPlusMapper applyPlusMapper;

    @PostMapping
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse createApplication(@RequestBody Apply form) throws ApiOperationException {

        appliService.createApply(form);
        return BaseResponse.success();
    }

    @DeleteMapping
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse cancelApplication(@RequestBody Apply form) {
        form.setName(RequestContext.get().getName());
        appliService.changeApplyStatus(form.getId(), form.getRemark(), ApplyStatus.CANCEL);
        return BaseResponse.success();
    }

    @GetMapping
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse getApplication() {
        DetailUser detailUser = RequestContext.get();
        Apply apply = applyPlusMapper.selectOne(Wrappers.<Apply>query().eq("user_id", detailUser.getId()));
        return BaseResponse.succes(apply);
    }
}
