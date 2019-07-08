package xyz.chunshengyuan.mall.mapper

import org.apache.ibatis.annotations.*
import xyz.chunshengyuan.mall.model.DetailUser
import xyz.chunshengyuan.mall.model.UserBo
import xyz.chunshengyuan.mall.model.UserExtBo

/**
 * @Title: User
 * @Package xyz.chunshengyuan.mall.mapper
 * @Description:
 * @author leemaster
 * @date 2019-07-07 00:49
 */

@Mapper
interface UserMapper{

    /**
     * 获取全部用户信息
     */
    @Select(value = [
        "select",
        "U.id as id,U.name as name,U.password as password,U.phone as phone,U.mail as mail,U.wx_open_id as wx_open_id,U.wx_bind_status as wx_bind_status,",
        "E.user_status as user_status,E.user_role as user_role,",
        "U.add_time as add_time,U.update_time as update_time",
        "from mall_user_tbl as U , mall_user_ext_tbl as E",
        "where U.id=E.id"
    ])
    fun selectUserDetailsAll(): List<DetailUser>

    /**
     * 按照 OpenId 查询用户 因为涉及到Token 签发逻辑
     */
    @Select(value = [
        "select ",
        "U.id as id,U.name as name,U.password as password,U.phone as phone,U.mail as mail,U.wx_open_id as wx_open_id,U.wx_bind_status as wx_bind_status,",
        "E.user_status as user_status,E.user_role as user_role ,",
        "U.add_time as add_time,U.update_time as update_time",
        "from mall_user_tbl as U , mall_user_ext_tbl as E ",
        "where U.id=E.id and U.open_id=#{openId}"
    ])
    fun selectUserByWxId(@Param("openId")openId: String): DetailUser

    /**
     * 用户账号密码登陆搜索是否正确
     */
    @Select(value = [
        "select ",
        "U.id as id,U.name as name,U.password as password,U.phone as phone,U.mail as mail,U.wx_open_id as wx_open_id,U.wx_bind_status as wx_bind_status,",
        "E.user_status as user_status,E.user_role as user_role,",
        "U.add_time as add_time,U.update_time as update_time",
        "from mall_user_tbl as U , mall_user_ext_tbl as E ",
        "where U.id=E.id and U.phone=#{phone}"
    ])
    fun selectUserbyPhone(@Param("phone")phone: String): DetailUser

    /**
     * 验证手机号是否存在
     */
    @Select("select count(0) from mall_user_tbl where phone=#{phone}")
    fun selectPhoneExits(@Param("phone")phone: String): Boolean


    /**
     * 新增用户信息
     */
    @Insert(value = [
        "insert into",
        "mall_user_tbl(id,name,phone,password,mail,wx_open_id,wx_bind_status)",
        "value",
        "(#{id},#{user.name},#{user.phone},#{user.password},#{user.mail},#{user.wx_open_id},#{user.wx_bind_status})"
    ])
    fun createUserBase(@Param("user")user: UserBo): Int

    /**
     * 同步更新用户其他信息
     */
    @Insert(value = [
        "insert into",
        "mall_user_ext_tbl(id,user_status,user_role)",
        "value",
        "(#{id},#{status.code},#{role.code})"
    ])
    fun createUserExt(
        @Param("id")id:Long,
        @Param("status")status:UserExtBo.UserStatus,
        @Param("role")role:UserExtBo.UserRole
    ): Int

    /**
     * 更新用户基本信息
     */
    @Update(value = [
        "<script>",
        "update mall_user_tbl",
        "   <set>"+
                "<if test='user.name != null and user.name != '' '>"+
                "    name = #{user.name}" +
                "</if>" +
                "<if test='user.phone != null and user.phone != '' >" +
                "   phone = #{user.phone}" +
                "</if>" +
                "<if test='user.password != null and user.password != ''>" +
                "   password = #{user.password}" +
                "</if>" +
                "<if test='user.wxOpenId != null and user.wxOpenId != ''>" +
                "   wxOpenId=#{user.wxOpenId}" +
                "</if>" +
                "<if test='user.wxBindStatus != null>" +
                "   wxBindStatus=#{user.wxBindStatus}" +
                "</if>" +
                "<if test='user.mail != null and user.mail != ''>" +
                "   mail=#{user.mail}" +
                "</if>"+
        "   </set>",
        "where id = #{user.id}",
        "</script>"
    ])
    fun updateUser(@Param("user")user: UserBo): Int

    /**
     * 修改用户角色和状态
     */
    @Update(value =  [
        "<script>",
        "update mall_user_ext_tbl",
                "<set>" +
                "   <if test='status != null' >" +
                        "status = #{status.code}" +
                    "</if>" +
                    "<if test='role'>" +
                        "role = #{role.code}" +
                    "</if>"+
                "</set>" ,
        "where id=#{id}",
        "</script>"
    ])
    fun updateUserExt(
        @Param("id")id:Long,
        @Param("status")status:UserExtBo.UserStatus,
        @Param("role")role:UserExtBo.UserRole
    ):Int
}