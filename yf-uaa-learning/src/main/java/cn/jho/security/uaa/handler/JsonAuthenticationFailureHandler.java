package cn.jho.security.uaa.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-02 17:09
 */
public class JsonAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        resp.setCharacterEncoding("UTF-8");
        val errData = Map.of(
                "title", "认证失败",
                "details", e.getMessage()
        );
        resp.getWriter().println(new ObjectMapper().writeValueAsString(errData));
    }
}
