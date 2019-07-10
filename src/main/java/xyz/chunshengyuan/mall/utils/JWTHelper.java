package xyz.chunshengyuan.mall.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import xyz.chunshengyuan.mall.constrant.UserRole;
import xyz.chunshengyuan.mall.model.bo.DetailUser;
import xyz.chunshengyuan.mall.model.vo.UserInfo;

import java.util.Map;

/**
 * @author leemaster
 * @Title: JWTHelper
 * @Package xyz.chunshengyuan.mall.utils
 * @Description:
 * @date 2019-07-1001:20
 */
public class JWTHelper {

    private static Algorithm ALGORITHM =  Algorithm.HMAC256("MALL_NEW");

    private static JWTVerifier VERIFYCATION = JWT.require(ALGORITHM).build();

    public static UserInfo createToken(DetailUser user){
        JWTCreator.Builder builder = JWT.create();

        builder.withSubject("MALL_NEW");
        builder.withIssuer("MALL_NEW");

        builder.withClaim("name",user.getName());
        builder.withClaim("id",user.getId());
        builder.withClaim("wxBindStatus",user.getWxBindStatus());
        builder.withClaim("phone",user.getPhone());
        builder.withClaim("wxOpenId",user.getWxOpenId());
        builder.withClaim("role",user.getUserRole());
        builder.withClaim("userStatus",user.getUserStatus());

        String token = builder.sign(ALGORITHM);

        return new UserInfo(
                user.getName(),
                user.getWxAvatarUrl(),
                user.getUserRole(),
                token,
                true
        );
    }

    public static DetailUser parseToken(String token) {

        try {
            DecodedJWT result = VERIFYCATION.verify(token);

            Map<String, Claim> claims = result.getClaims();

            DetailUser user = new DetailUser();
            user.setId(claims.get("id").asLong());
            user.setName(claims.get("name").asString());
            user.setWxBindStatus(claims.get("wxBindStatus").asInt());
            user.setPhone(claims.get("phone").asString());
            user.setWxOpenId(claims.get("wxOpenId").asString());
            user.setUserStatus(claims.get("userStatus").asInt());
            user.setUserRole(claims.get("role").asString());

            return user;

        }catch (JWTVerificationException exception){
            DetailUser user = new DetailUser();
            user.setId(-1L);
            user.setUserRole(UserRole.PRECONSUMER.code);
            user.setName("No Login user");
            return user;
        }

    }


}
