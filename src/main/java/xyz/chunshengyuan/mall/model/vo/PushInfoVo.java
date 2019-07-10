package xyz.chunshengyuan.mall.model.vo;

import lombok.Data;
import xyz.chunshengyuan.mall.model.po.Goods;
import xyz.chunshengyuan.mall.model.po.Push;

/**
 * @author leemaster
 * @Title: PushInfoVo
 * @Package xyz.chunshengyuan.mall.model.vo
 * @Description:
 * @date 2019-07-1014:13
 */
@Data
public class PushInfoVo {

    private Push push;

    private Goods goods;
}
