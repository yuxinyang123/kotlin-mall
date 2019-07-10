package xyz.chunshengyuan.mall.constrant;

/**
 * @author leemaster
 * @Title: PushType
 * @Package xyz.chunshengyuan.mall.constrant
 * @Description:
 * @date 2019-07-1014:05
 */
public enum PushType {
    INDEX("index"),
    NEW("new"),
    HOT("hot");

    public String code;

    PushType(String code) {
        this.code = code;
    }
}
