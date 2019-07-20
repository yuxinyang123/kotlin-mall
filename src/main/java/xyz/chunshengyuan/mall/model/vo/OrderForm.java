package xyz.chunshengyuan.mall.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.chunshengyuan.mall.utils.Long2StringConveter;
import xyz.chunshengyuan.mall.utils.String2LongConverter;

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
    @JsonDeserialize(converter = String2LongConverter.class)
    @JsonSerialize(converter = Long2StringConveter.class)
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
