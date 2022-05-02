package cn.jho.security.uaa.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-02 17:24
 */
@RequiredArgsConstructor
public class RestAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        try {
            InputStream is = request.getInputStream();
            val jsonNode = objectMapper.readTree(is);
            String username = jsonNode.get("username").textValue();
            String password = jsonNode.get("password").textValue();
            authRequest = new UsernamePasswordAuthenticationToken(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("用户名或密码错误！");
        }
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
