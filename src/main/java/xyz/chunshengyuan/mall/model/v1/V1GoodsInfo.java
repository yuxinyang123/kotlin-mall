package xyz.chunshengyuan.mall.model.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.chunshengyuan.mall.model.po.Banner;
import xyz.chunshengyuan.mall.model.po.Goods;

import java.util.List;

/**
 * @description: V1GoodsInfo
 * @date: 2019-07-14 16:53
 * @author: yuxinyang
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class V1GoodsInfo {
    private Goods good;
    private List<Banner> banners;
}
