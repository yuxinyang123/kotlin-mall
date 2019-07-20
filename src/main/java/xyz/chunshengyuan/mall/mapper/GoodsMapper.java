package xyz.chunshengyuan.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.chunshengyuan.mall.model.po.Goods;

/**
 * @author leemaster
 * @Title: GoodsMapper
 * @Package xyz.chunshengyuan.mall.mapper
 * @Description:
 * @date 2019-07-1012:16
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
}
