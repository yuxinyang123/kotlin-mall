package xyz.chunshengyuan.mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author leemaster
 * @Title: OrderForm
 * @Package xyz.chunshengyuan.mall.model.vo
 * @Description:
 * @date 2019-07-1018:51
 */
@Data
public class OrderForm {

    private Long userId;

    private Long addressId;

    private List<Entry> order;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Entry{
        private Long goodsId;

        private Integer goodsNum;
    }
}
