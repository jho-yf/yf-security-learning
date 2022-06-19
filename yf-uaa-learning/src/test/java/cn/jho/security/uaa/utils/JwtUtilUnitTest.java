package cn.jho.security.uaa.utils;

import cn.jho.security.uaa.domain.Role;
import cn.jho.security.uaa.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

/**
 * JwtUtilUnitTest
 *
 * @author JHO xu-jihong@qq.com
 * @date 2022-06-19 10:54
 */
@ExtendWith(SpringExtension.class)
class JwtUtilUnitTest {

    @Test
    void givenUserDetails_thenCreateTokenSuccess() {
        String username = "user";
        Set<Role> authorities = Set.of(
                Role.builder().authority("ROLE_USER").build(),
                Role.builder().authority("ROLE_ADMIN").build());
        User user = User.builder()
                .username(username)
                .authorities(authorities)
                .build();
        // 创建jwt
        String token = JwtUtil.createJwtToken(user);
        // 解析
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(JwtUtil.KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        assert username.equals(claims.getSubject());
    }

}
