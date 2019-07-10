package xyz.chunshengyuan.mall.constrant;

/**
 * @author leemaster
 * @Title: BannerLocation
 * @Package xyz.chunshengyuan.mall.constrant
 * @Description:
 * @date 2019-07-1012:08
 */
public enum BannerLocation {
    INDEX("index"),GOODS("goods");
    public String code;

    BannerLocation(String code) {
        this.code = code;
    }
}
