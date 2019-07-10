package xyz.chunshengyuan.mall.model.vo;

import lombok.Data;

/**
 * @author leemaster
 * @Title: CategoryForm
 * @Package xyz.chunshengyuan.mall.model.vo
 * @Description:
 * @date 2019-07-1011:18
 */
@Data
public class CategoryForm {

    private String name;

    private String icon;

    private String color;

    private Long parent;

    private Long id;
}
