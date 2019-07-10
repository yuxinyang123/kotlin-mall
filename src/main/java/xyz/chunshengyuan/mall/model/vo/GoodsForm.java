package xyz.chunshengyuan.mall.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author leemaster
 * @Title: GoodsForm
 * @Package xyz.chunshengyuan.mall.model.vo
 * @Description:
 * @date 2019-07-1012:13
 */
@Data
public class GoodsForm {

    private String name;

    private String price;

    private Long type;

    private String introduce;

    private String details;

    private String avatar;

    private Integer inventory;

    private List<String> banners;

    private Integer isEnable;

    private Integer isDel;

    private String location;
}
