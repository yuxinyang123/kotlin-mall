package xyz.chunshengyuan.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xyz.chunshengyuan.mall.model.po.Goods;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author leemaster
 * @Title: GoodsPlusMapper
 * @Package xyz.chunshengyuan.mall.mapper
 * @Description:
 * @date 2019-07-1012:04
 */
@Mapper
public interface GoodsPlusMapper extends BaseMapper<Goods> {

    @Select(value = {
            "<script>",
            "select * from mall_goods_tbl",
            "   <where>",
            "       <if test=\"name != null and nam != ''\">",
            "           name = #{name}",
            "       </if>",
            "       <if test=\"type != null and type != 0\">",
            "           type = #{type}",
            "       </if>",
            "       <if test=\" low != null and heigh != null\">",
            "           and price between #{log} and #{height}",
            "       </if>",
            "   </where>",
            "</script>"

    })
    List<Goods> selectByPage(
            Page page,
            @Param("type") Long type,
            @Param("name") String name,
            @Param("low")BigDecimal low,
            @Param("heigh") BigDecimal heigh
            );

    @Select(value = {
            "select C.name",
            "from mall_category_tbl as C",
            "where C.id = #{typeId} group by typeId"
    })
    List<String> selectLocation(@Param("typeId")Long typeId);
}
