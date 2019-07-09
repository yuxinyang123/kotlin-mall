package xyz.chunshengyuan.mall.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author leemaster
 * @Title: AddressMapper
 * @Package xyz.chunshengyuan.mall.mapper
 * @Description:
 * @date 2019-07-1002:43
 */
@Mapper
public class AddressMapper {

//    @Select(value = [
//            "select * from mall_address_tbl",
//            "where user_id=#{id} and is_default=1"
//            ])
//    fun selectUserDefaulAddress(@Param("id")id: Long): UserAddressBo
//
//    @Select(value = [
//            "select * from mall_address_tbl",
//            "where user_id=#{id}"
//            ])
//    fun selectUserAllAddress(@Param("id")id: Long): List<UserAddressBo>
//
//    @Insert(value = [
//            "insert into mall_address_tbl",
//            "(user_id,province,is_default,city,country,details,remark)",
//            "values",
//            "(#{address.userId},#{address.province},#{address.isDefault},#{address.city},#{address.country},#{address.details},#{address.remark})"
//            ])
//    fun createUserAddress(@Param("address")address: UserAddressBo): Int
//
//    @Update(value = [
//            "<script>",
//            "update mall_address_tbl",
//            "<set>",
//            "<if test=\"address.province != null and address.province != '' \">province=#{address.province}</if>",
//            "<if test=\"address.city != null and address.city !='' \">city=#{address.city}</if>",
//            "<if test=\"address.country != null and address.country != '' \">country=#{address.country}</if>",
//            "<if test=\"address.details != null and address.details != '' \">details=#{address.details}</if>",
//            "<if test=\"address.isDefault != null \">is_default=#{address.isDefault}</if>",
//            "<if test=\"address.remark != null and address.remark != ''\">remark=#{address.remark}</if>",
//            "</set>",
//            "where id = #{address.id}",
//            "</script>"
//            ])
//    fun updateUserAddress(@Param("address")address: UserAddressBo): Int
//
//
//    @Delete(value = [
//            "delete from mall_address_tbl where id=#{id}"
//            ])
//    fun deleteUserAddress(@Param("id")id: Long): Int
}
