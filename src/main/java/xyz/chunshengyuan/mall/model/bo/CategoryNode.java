package xyz.chunshengyuan.mall.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author leemaster
 * @Title: CategoryNode
 * @Package xyz.chunshengyuan.mall.model.bo
 * @Description:
 * @date 2019-07-1010:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryNode {

    private String id;

    private String name;

    private String parent;

    private String icon;

    private String color;

    private Integer level;

    private List<CategoryNode> children;

    private Date addTime;

    private Date updateTime;
}
