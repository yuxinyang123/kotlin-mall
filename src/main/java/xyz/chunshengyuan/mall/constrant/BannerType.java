package xyz.chunshengyuan.mall.constrant;

/**
 * @author leemaster
 * @Title: BannerType
 * @Package xyz.chunshengyuan.mall.constrant
 * @Description:
 * @date 2019-07-1012:06
 */
public enum BannerType {
    COMMON("common"),ITEM("item"),CATEGORY("category"),REMOTE("remote");

    public String code;

    BannerType(String code) {
        this.code = code;
    }
}
