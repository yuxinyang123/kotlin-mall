package xyz.chunshengyuan.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;
import xyz.chunshengyuan.mall.model.bo.CartGoods;
import xyz.chunshengyuan.mall.model.bo.FavoritesGoods;
import xyz.chunshengyuan.mall.model.bo.OrderGoods;
import xyz.chunshengyuan.mall.model.bo.UserOrder;
import xyz.chunshengyuan.mall.model.po.Order;

import java.util.List;

/**
 * @author leemaster
 * @Title: OrderMapper
 * @Package xyz.chunshengyuan.mall.mapper
 * @Description:
 * @date 2019-07-1016:08
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Select(value = {
            "select",
            "G.id as id,G.name as name,G.price as price,G.introduce as introduce,G.avatar as avatar,O.add_time as add_time,O.update_time as update_time,R.goods_sum as goods_sum",
            "from mall_order_tbl as O,mall_goods_tbl as G,mall_goods_order_tbl as R",
            "where R.goods_id = G.id and O.id = R.order_id and O.user_id=#{userId} order by O.add_time desc "
    })
    IPage<OrderGoods> selectAllOrderGoods(Page page,@Param("userId")Long userId);

    @Select(value = {
            "select",
            "G.id as id,G.name as name,G.price as price,G.introduce as introduce,G.avatar as avatar,O.add_time as add_time,O.update_time as update_time,R.goods_num as goods_num",
            "from mall_order_tbl as O,mall_goods_tbl as G,mall_goods_order_tbl as R",
            "where O.id=#{orderId} and R.goods_id = G.id and O.id = R.order_id and O.user_id=#{userId} order by O.add_time desc "
    })
    OrderGoods selectOrderById(@Param("orderId")Long orderId,@Param("userId")Long userId);



    @Select(value = {
            "select",
            "O.id as id,O.sum as order_sum,O.status as status,O.logistics as logistics, O.carrier_name as carrier_name,U.name as user_ame,U.phone as user_phone",
            "from mall_order_tbl as O,mall_user_tbl as U",
            "where O.user_id=U.id order by O.add_time desc"
    })
    IPage<UserOrder> selectAllUserOrders(Page page);

    @Select(value = {
            "select",
            "O.id as id,O.sum as order_sum,O.status as status,O.logistics as logistics, O.carrier_name as carrier_name,U.name as user_ame,U.phone as user_phone",
            "from mall_order_tbl as O,mall_user_tbl as U",
            "where U.id = #{userId} and O.user_id=U.id order by O.add_time desc"
    })
    IPage<UserOrder> selectAllUserOrdersByuserID(Page page, @Param("userId")Long userId);

    @Select(value = {
            "select ",
            "C.goods_num as goods_num,G.name as name," +
                    "G.id as id ,G.price as price,G.type as type ," +
                    "G.introduce as introduce," +
                    "G.avatar as avatar," +
                    "G.inventory as inventory,G.location as location," +
                    "C.add_time as add_tim ,C.update_time as update_time ",
            "from mall_cart_tbl as C,mall_goods_tbl as G",
            "where C.user_id=#{userId} and C.goods_id =G.id"
    })
    List<CartGoods> selectCarts(@Param("userId")Long userId);


    @Select(value = {
            "select ",
            "G.name as name,G.id as id ," +
                    "G.price as price,G.introduce as introduce,G.type as type ," +
                    "G.avatar as avatar,G.inventory as inventory," +
                    "G.location as location,C.add_time as add_time ,C.update_time as update_time ",
            "from mall_favorite_tbl as C,mall_goods_tbl as G",
            "where C.user_id=#{userId} and C.goods_id =G.id"
    })
    List<FavoritesGoods> selectFavorite(@Param("userId")Long userId);


    @Insert("insert into mall_cart_tbl(goods_id,user_id,goods_num) value(#{goodsId},#{userId},#{num})")
    Integer insertCart(@Param("goodsId") Long goodsId,@Param("userId")Long userId,@Param("num")Integer num);

    @Insert("insert into mall_favorite_tbl(goods_id,user_id) value(#{goodsId},#{userId})")
    Integer insertFavorite(@Param("goodsId") Long goodsId,@Param("userId")Long userId);


    @Update("update mall_cart_tbl set goods_num=#{num} where goods_id=#{goodsId} and user_id =#{userId}")
    Integer updateCart(@Param("goodsId") Long goodsId,@Param("userId")Long userId,@Param("num")Integer num);


    @Delete("delete from mall_cart_tbl where goods_id =#{goodsId} and user_id =#{userId}")
    Integer deleteCart(@Param("goodsId") Long goodsId,@Param("userId")Long userId);

    @Delete("delete from mall_favorite_tbl where goods_id =#{goodsId} and user_id =#{userId}")
    Integer deleteFavorite(@Param("goodsId") Long goodsId,@Param("userId")Long userId);


}
