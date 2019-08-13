package com.example.login.utils;


import com.example.login.reqparam.LoginParam;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;
/**
 * JWT校验工具类
 * <ol>
 * <li>iss: jwt签发者</li>
 * <li>sub: jwt所面向的用户</li>
 * <li>aud: 接收jwt的一方</li>
 * <li>exp: jwt的过期时间，这个过期时间必须要大于签发时间</li>
 * <li>nbf: 定义在什么时间之前，该jwt都是不可用的</li>
 * <li>iat: jwt的签发时间</li>
 * <li>jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击</li>
 * </ol>
 */
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    public static final long EXPIRATION_TIME = 432_000_000; //5天
    public static final String SECRET = "P@rsw02d";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";
    public static final String USER_ID = "testId";


    /**
     * 创建token
     *
     * @param user
     * @return
     */
    public static String generateToken(LoginParam user) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            map.put(USER_ID, user.getName());

        } catch (Exception e) {
            System.out.println("Encryption failed. " + e.getMessage());
            throw new RuntimeException("Encrypton failed");
        }
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))//过期时间
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return jwt;
    }

    /**
     * 解密jwt
     * @param request
     * @return 主题
     * @throws Exception 如果发生  JwtException 说明该密钥无效
     */

    public static HttpServletRequest validateTokenAndAddUserIdToHeader(HttpServletRequest request) throws Exception {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            try {
                Map<String, Object> body = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();
                String userId = (String) body.get(USER_ID);
                return new CustomHttpServletRequest(request, userId);
            } catch (Exception e) {
                return request;
//                logger.info(e.getMessage());
//                throw new TokenValidationException(e.getMessage());
            }
        } else {
//            return request;
//            logger.info("Missing token");
//            throw new Exception("123123");
            throw new Exception("token不能为空");

        }
    }

    public static class CustomHttpServletRequest extends HttpServletRequestWrapper {
        private String userId;

        public CustomHttpServletRequest(HttpServletRequest request, String userId) {
            super(request);
            this.userId = userId;
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if (name != null && (name.equals(USER_ID))) {
                return Collections.enumeration(Arrays.asList(userId));
            }
            return super.getHeaders(name);
        }
    }

    static class TokenValidationException extends RuntimeException {
        public TokenValidationException(String msg) {
            super(msg);
        }
    }
}