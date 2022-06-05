package cn.jho.security.uaa.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-23 07:43
 */
@Configuration
@Order(100)
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.authorizeRequests(req -> req.anyRequest().authenticated())
                 // 指定登录页面
                 .formLogin(form -> form.loginPage("/login")
                         // 与前端name字段对应
                         .usernameParameter("username")
                         .defaultSuccessUrl("/")
                         .permitAll())
                 .logout(logout -> logout.logoutUrl("/perform_logout"))
                 .rememberMe(rm -> rm.tokenValiditySeconds(24 * 60 * 60).rememberMeCookieName("someKeyToRemember"));
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/public/**", "/error/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

}
