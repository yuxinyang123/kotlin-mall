package xyz.chunshengyuan.mall.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.chunshengyuan.mall.constrant.UserRole;
import xyz.chunshengyuan.mall.constrant.UserStatus;
import xyz.chunshengyuan.mall.constrant.WxBindStatus;
import xyz.chunshengyuan.mall.exceptions.AdminLoginFailedException;
import xyz.chunshengyuan.mall.exceptions.WxRedirectException;
import xyz.chunshengyuan.mall.mapper.UserMapper;
import xyz.chunshengyuan.mall.mapper.UserPlusMapper;
import xyz.chunshengyuan.mall.model.bo.DetailUser;
import xyz.chunshengyuan.mall.model.po.User;
import xyz.chunshengyuan.mall.model.po.UserExt;
import xyz.chunshengyuan.mall.model.vo.UserInfo;
import xyz.chunshengyuan.mall.model.vo.WxUserForm;
import xyz.chunshengyuan.mall.model.vo.wx.WxUserSessionResponse;
import xyz.chunshengyuan.mall.utils.JWTHelper;
import xyz.chunshengyuan.mall.utils.SnowFlakeIdGenerator;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

/**
 * @author leemaster
 * @Title: UserService
 * @Package xyz.chunshengyuan.mall.service
 * @Description:
 * @date 2019-07-1002:57
 */
@Service
@Slf4j
public class UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    UserPlusMapper userPlusMapper;

    @Resource
    WxHttpService wxHttpService;

    @Resource
    SnowFlakeIdGenerator snowFlakeIdGenerator;


    public UserInfo adminLogin(String phone,String password) throws AdminLoginFailedException {
        DetailUser user = userMapper.selectUserbyPhone(phone);
        if (Objects.nonNull(user)){
            if (user.getPassword().equals(password)){
                if (user.getUserStatus().equals(UserStatus.OPEN.code)){
                    return JWTHelper.createToken(user);
                }else{
                    throw new AdminLoginFailedException("当前用户无法登陆");
                }
            }
        }
        throw new AdminLoginFailedException("用户不存在或密码错误");
    }

    /**
     * 微信用户登陆流程
     * @param code
     * @return
     */
    @Transactional
    public UserInfo wxAccountLogin(String code) throws AdminLoginFailedException, WxRedirectException {
        try {
            WxUserSessionResponse response =  wxHttpService.wxCode2Session(code);

            if (Objects.nonNull(response)){
                if (response.getErrcode().equals("40029")){
                    throw new AdminLoginFailedException("用户请求超时，请重新登陆");
                }
                String openId = response.getOpenid();
                User user = userPlusMapper.selectOne(
                        Wrappers.<User>query().and(
                                item -> item.eq("wx_open_id",openId).eq("wx_bind_status", WxBindStatus.BINDED.code)
                        )
                );
                if (Objects.nonNull(user)){
                    DetailUser detailUser = userMapper.selectUserByWxId(openId);
                    //创建TOKEN 返回相当于登陆完成
                    return JWTHelper.createToken(detailUser);
                }else {
                    //Fake User
                    User newUser = new User();
                    newUser.setId(snowFlakeIdGenerator.nextId());
                    newUser.setMail("-");
                    newUser.setName("-");
                    newUser.setPassword("-");
                    newUser.setWxAvatarUrl("-");
                    newUser.setWxOpenId(openId);
                    newUser.setPhone("-");
                    userPlusMapper.insert(newUser);

                    userMapper.createUserExt(newUser.getId(),UserStatus.OPEN,UserRole.PRECONSUMER);
                    log.info("[User with the open_id ->>>> {} to save in DB]",openId);
                    throw new WxRedirectException("用户注册成功，准备完善信息",openId);
                }
            }else {
                log.error("[Call wx service failed to change the code to session ]");
            }

            return null;
        } catch (IOException e) {
            throw new AdminLoginFailedException("微信登陆服务不可用，请稍后重试");
        }
    }

    @Transactional
    public UserInfo wxAccountRegister(WxUserForm form){
        User user = new User();
        user.setWxAvatarUrl(form.getAvatar());
        user.setName(form.getName());
        user.setWxBindStatus(WxBindStatus.BINDED.code);
        userPlusMapper.update(user,Wrappers.<User>update().eq("wx_open_id",form.getOpenId()));

        DetailUser detailUser = userMapper.selectUserByWxId(form.getOpenId());

        return JWTHelper.createToken(detailUser);
    }

}
