package xyz.chunshengyuan.mall.model.v1;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author yang
 * @since 2019-06-04
 */
@Data
public class V1UserShoppingPo implements Serializable {

    private Long goodsId;
    private Integer sum;
}
