package cn.jho.security.uaa.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理
 *
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-02 17:00
 */
@Slf4j
public class JsonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
        resp.setStatus(HttpStatus.OK.value());
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        resp.getWriter().println(new ObjectMapper().writeValueAsString(auth));
        log.info("认证成功！");
    }

}
