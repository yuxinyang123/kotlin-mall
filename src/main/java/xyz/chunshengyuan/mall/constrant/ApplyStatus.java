package xyz.chunshengyuan.mall.constrant;

/**
 * @author leemaster
 * @Title: ApplyStatus
 * @Package xyz.chunshengyuan.mall.constrant
 * @Description:
 * @date 2019-07-1015:32
 */
public enum ApplyStatus {

    WAITING("waiting"),
    HANDLING("handling"),
    REJECT("reject"),
    COMPLETE("complete"),
    CANCEL("cancel");

    public String code;

    ApplyStatus(String code) {
        this.code = code;
    }
}
