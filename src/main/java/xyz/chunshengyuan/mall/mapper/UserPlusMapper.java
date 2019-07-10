package xyz.chunshengyuan.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.chunshengyuan.mall.model.po.User;

/**
 * @author leemaster
 * @Title: UserPlusMapper
 * @Package xyz.chunshengyuan.mall.mapper
 * @Description:
 * @date 2019-07-1003:23
 */
@Mapper
public interface UserPlusMapper extends BaseMapper<User> {
}
