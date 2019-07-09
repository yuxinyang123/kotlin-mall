package xyz.chunshengyuan.mall.constrant;

/**
 * @author leemaster
 * @Title: UserRole
 * @Package xyz.chunshengyuan.mall.constrant
 * @Description:
 * @date 2019-07-1001:43
 */
public enum UserRole {
    ADMIN("admin"),
    PRECONSUMER("pre-consumer"),
    CONSUMER("consumer");

    public String code;

    UserRole(String code) {
        this.code = code;
    }
}
