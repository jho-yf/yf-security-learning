package cn.jho.security.uaa.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * JwtUtil
 *
 * @author JHO xu-jihong@qq.com
 * @date 2022-06-19 10:46
 */
public class JwtUtil {

    private JwtUtil() {
        
    }

    /**
     * 用于签名
     */
    public static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static String createJwtToken(UserDetails userDetails) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setId("jho")
                .claim("authorities", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 60_000))
                .signWith(KEY, SignatureAlgorithm.HS512)
                .compact();
    }


}
