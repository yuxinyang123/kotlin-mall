package xyz.chunshengyuan.mall.model.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.chunshengyuan.mall.model.po.Category;
import xyz.chunshengyuan.mall.model.po.Push;

/**
 * @description: V1IndexTypesVo
 * @date: 2019-07-13 20:57
 * @author: yuxinyang
 * @version: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class V1IndexTypesVo {
    private Push records;
    private Category types;
}
