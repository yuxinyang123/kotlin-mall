package xyz.chunshengyuan.mall.mapper;

import org.apache.ibatis.annotations.*;
import xyz.chunshengyuan.mall.constrant.UserRole;
import xyz.chunshengyuan.mall.constrant.UserStatus;
import xyz.chunshengyuan.mall.model.bo.DetailUser;
import xyz.chunshengyuan.mall.model.po.User;

import java.util.List;

/**
 * @author leemaster
 * @Title: UserMapper
 * @Package xyz.chunshengyuan.mall.mapper
 * @Description:
 * @date 2019-07-1001:34
 */
@Mapper
public interface UserMapper {

    @Select(value = {
            "select",
            "U.id as id,U.name as name,U.password as password,U.phone as phone," +
                    "U.mail as mail,U.wx_open_id as wx_open_id,U.wx_bind_status as wx_bind_status,",
            "E.user_status as user_status,E.user_role as user_role,",
            "U.wx_avatar_url as wx_avatar_url , U.add_time as add_time,U.update_time as update_time",
            "from mall_user_tbl as U , mall_user_ext_tbl as E",
            "where U.id=E.id"
    })
    List<DetailUser> selectUserDetailsAll();


    @Select(value = {
            "select ",
            "U.id as id,U.name as name,U.password as password,U.phone as phone," +
                    "U.mail as mail,U.wx_open_id as wx_open_id,U.wx_bind_status as wx_bind_status,",
            "E.user_status as user_status,E.user_role as user_role ,",
            "U.wx_avatar_url as wx_avatar_url ,U.add_time as add_time,U.update_time as update_time",
            "from mall_user_tbl as U , mall_user_ext_tbl as E ",
            "where U.id=E.id and U.open_id=#{openId}"
    })
    DetailUser selectUserByWxId(@Param("openId")String openId);

    @Select(value = {
            "select ",
            "U.id as id,U.name as name,U.password as password,U.phone as phone," +
                    "U.mail as mail,U.wx_open_id as wx_open_id,U.wx_bind_status as wx_bind_status,",
            "E.user_status as user_status,E.user_role as user_role,",
            "U.wx_avatar_url as wx_avatar_url ,U.add_time as add_time,U.update_time as update_time",
            "from mall_user_tbl as U , mall_user_ext_tbl as E ",
            "where U.id=E.id and U.phone=#{phone}"
    })
    DetailUser selectUserbyPhone(@Param("phone")String phone);

    @Select("select count(0) from mall_user_tbl where phone=#{phone}")
    Boolean selectPhoneExits(@Param("phone")String phone);


    @Insert(value = {
            "insert into",
            "mall_user_tbl(id,name,phone,password,mail,wx_open_id,wx_bind_status,wx_avatar_url)",
            "value",
            "(#{id},#{user.name},#{user.phone},#{user.password},#{user.mail},#{user.wx_open_id},#{user.wx_bind_status},#{user.wxAvatarUrl})"
    })
    Integer createUserBase(@Param("user")User user);


    @Insert(value = {
            "insert into",
            "mall_user_ext_tbl(id,user_status,user_role)",
            "value",
            "(#{id},#{status.code},#{role.code})"
    })
    Integer createUserExt(
            @Param("id") Long id,
            @Param("status")UserStatus status,
            @Param("role")UserRole role);

    @Update(value = {
            "<script>",
            "update mall_user_tbl",
            "   <set>"+
                    "<if test='user.name != null and user.name != \"\" '>"+
                    "    name = #{user.name}" +
                    "</if>" +
                    "<if test='user.phone != null and user.phone != \"\"' >" +
                    "   phone = #{user.phone}" +
                    "</if>" +
                    "<if test='user.password != null and user.password != \"\"'>" +
                    "   password = #{user.password}" +
                    "</if>" +
                    "<if test='user.wxOpenId != null and user.wxOpenId != \"\"'>" +
                    "   wxOpenId=#{user.wxOpenId}" +
                    "</if>" +
                    "<if test='user.wxBindStatus != null' >" +
                    "   wxBindStatus=#{user.wxBindStatus}" +
                    "</if>" +
                    "<if test='user.mail != null and user.mail != \"\"'>" +
                    "   mail=#{user.mail}" +
                    "</if>"+
                    "   </set>",
            "where id = #{user.id}",
            "</script>"
    })
    Integer updateUser(@Param("user")User user);

    @Update(value = {
            "<script>",
            "update mall_user_ext_tbl",
            "<set>" +
                    "   <if test='status != null' >" +
                    "status = #{status.code}" +
                    "</if>" +
                    "<if test='role != null'>" +
                    "role = #{role.code}" +
                    "</if>"+
                    "</set>" ,
            "where id=#{id}",
            "</script>"
    })
    Integer updateUserExt(
            @Param("id") Long id,
            @Param("status")UserStatus status,
            @Param("role")UserRole role);
}
