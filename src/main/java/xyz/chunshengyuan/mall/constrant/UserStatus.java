package xyz.chunshengyuan.mall.constrant;

/**
 * @author leemaster
 * @Title: UserStatus
 * @Package xyz.chunshengyuan.mall.constrant
 * @Description:
 * @date 2019-07-1001:43
 */
public enum UserStatus {
    OPEN(1),CLOSE(2),FORBIDDEN(3);

    public Integer code;

    UserStatus(Integer code) {
        this.code = code;
    }
}
