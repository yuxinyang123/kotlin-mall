package xyz.chunshengyuan.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author leemaster
 * @Title: PushListVo
 * @Package xyz.chunshengyuan.mall.model.vo
 * @Description:
 * @date 2019-07-1014:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushListVo {

    private Long total;

    private List<PushInfoVo> data;
}
