package xyz.chunshengyuan.mall.model.v1;

import lombok.Data;

/**
 * @description: V1GoodsQuery
 * @date: 2019-07-14 15:16
 * @author: yuxinyang
 * @version: 1.0
 */
@Data
public class V1GoodsQuery {
    private Long type;

    private String name;

    private String location;

    private String topPrice;

    private String lowPrice;

    private Integer limit = 10;

    private Integer offset = 1;

    private Boolean descOrderByInventory;

    private Boolean ascOrderByPrice;

    private Boolean descOrderByPrice;
}
