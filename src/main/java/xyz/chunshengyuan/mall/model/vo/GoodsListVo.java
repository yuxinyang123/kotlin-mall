package xyz.chunshengyuan.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author leemaster
 * @Title: GoodsListVo
 * @Package xyz.chunshengyuan.mall.model.vo
 * @Description:
 * @date 2019-07-1012:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsListVo {

    private Long total;

    private List<GoodsInfo> data;


}
