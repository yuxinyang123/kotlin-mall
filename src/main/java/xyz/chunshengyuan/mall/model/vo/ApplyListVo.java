package xyz.chunshengyuan.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author leemaster
 * @Title: ApplyListVo
 * @Package xyz.chunshengyuan.mall.model.vo
 * @Description:
 * @date 2019-07-1015:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyListVo {
    private Long total;

    private List<ApplyInfoVo> data;
}
