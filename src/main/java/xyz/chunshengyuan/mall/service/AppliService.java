package xyz.chunshengyuan.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.chunshengyuan.mall.constrant.ApplyStatus;
import xyz.chunshengyuan.mall.constrant.UserRole;
import xyz.chunshengyuan.mall.exceptions.ApiOperationException;
import xyz.chunshengyuan.mall.mapper.ApplyPlusMapper;
import xyz.chunshengyuan.mall.mapper.UserMapper;
import xyz.chunshengyuan.mall.mapper.UserPlusMapper;
import xyz.chunshengyuan.mall.model.po.Apply;
import xyz.chunshengyuan.mall.model.po.User;
import xyz.chunshengyuan.mall.model.vo.ApplyInfoVo;
import xyz.chunshengyuan.mall.model.vo.ApplyListVo;
import xyz.chunshengyuan.mall.utils.RequestContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author leemaster
 * @Title: AppliService
 * @Package xyz.chunshengyuan.mall.service
 * @Description:
 * @date 2019-07-1015:34
 */
@Service
public class AppliService {

    @Resource
    ApplyPlusMapper applyPlusMapper;

    @Resource
    UserPlusMapper userPlusMapper;

    @Resource
    UserMapper userMapper;

    public ApplyListVo listApply(Integer limit, Integer offset, List<String> status){
        Page<Apply> applyInfoVoPage = new Page<>();
        applyInfoVoPage.setSize(limit);
        applyInfoVoPage.setCurrent(offset);

        IPage<Apply> applyIPage = applyPlusMapper.selectPage(
                applyInfoVoPage,
                Wrappers.<Apply>query().in(
                        status.size() > 0,
                        "status",
                        status
                )
        );

        if (applyIPage.getTotal() <= 0){
            return new ApplyListVo(0L,new ArrayList<>());
        }

        List<Long> userIds = applyIPage.getRecords().stream()
                .map(Apply::getUserId).collect(Collectors.toList());

        if(userIds.size() == 0){
            return new ApplyListVo(0L,new ArrayList<>());
        }

        Map<Long, User> userMap = userPlusMapper.selectBatchIds(
                userIds
        ).stream().collect(
                Collectors.toMap(
                        User::getId,
                        item -> item
                )
        );

        ApplyListVo listVo = new ApplyListVo();

        listVo.setTotal(applyIPage.getTotal());
        listVo.setData(
                applyIPage.getRecords().stream()
                .map(item -> {
                    ApplyInfoVo infoVo = new ApplyInfoVo();
                    infoVo.setApply(item);
                    infoVo.setUser(userMap.get(item.getUserId()));
                    return infoVo;
                }).collect(Collectors.toList())
        );

        return listVo;
    }

    @Transactional
    public void createApply(Apply apply) throws ApiOperationException {
        try {
            Long userId = RequestContext.get().getId();
            apply.setUserId(userId);
            apply.setStatus(ApplyStatus.HANDLING.code);
            applyPlusMapper.insert(apply);
        }catch (NullPointerException e){
            throw new ApiOperationException("用户不存在","当前请求失败");
        }

    }

    @Transactional
    public void confirmApply(Long applyId) throws ApiOperationException {
        Apply apply = applyPlusMapper.selectById(applyId);
        if (Objects.isNull(apply)){
            throw new ApiOperationException("申请不存在","申请不存在，ID为" + applyId.toString());
        }
        userMapper.updateUserExt(apply.getUserId(),null,UserRole.CONSUMER);
    }

    @Transactional
    public void deleteApply(Long applyId){
        applyPlusMapper.deleteById(applyId);
    }

    public void changeApplyStatus(Long applyId,String remark,ApplyStatus status){
        Apply apply = new Apply();
        apply.setId(applyId);
        apply.setStatus(status.code);
        apply.setRemark(remark);
        applyPlusMapper.updateById(apply);
    }



}
