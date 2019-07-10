package xyz.chunshengyuan.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.chunshengyuan.mall.model.po.Category;

/**
 * @author leemaster
 * @Title: CategoryMapper
 * @Package xyz.chunshengyuan.mall.mapper
 * @Description:
 * @date 2019-07-1010:52
 */
@Mapper
public interface CategoryPlusMapper extends BaseMapper<Category> {
}
