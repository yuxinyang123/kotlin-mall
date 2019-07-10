package xyz.chunshengyuan.mall.model.vo;

import lombok.Data;
import xyz.chunshengyuan.mall.model.po.Apply;
import xyz.chunshengyuan.mall.model.po.User;

/**
 * @author leemaster
 * @Title: ApplyInfoVo
 * @Package xyz.chunshengyuan.mall.model.vo
 * @Description:
 * @date 2019-07-1015:36
 */
@Data
public class ApplyInfoVo {

    private Apply apply;

    private User user;
}
