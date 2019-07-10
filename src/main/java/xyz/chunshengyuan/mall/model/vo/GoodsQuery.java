package xyz.chunshengyuan.mall.model.vo;

import lombok.Data;

/**
 * @author leemaster
 * @Title: GoodsQuery
 * @Package xyz.chunshengyuan.mall.model.vo
 * @Description:
 * @date 2019-07-1012:17
 */
@Data
public class GoodsQuery {

    private Long type;

    private String name;

    private String location;

    private String topPrice;

    private String lowPrice;

    private Integer limit = 10;

    private Integer offset = 1;

}
