package cn.jho.security.uaa.config;

import cn.jho.security.uaa.handler.JsonAuthenticationFailureHandler;
import cn.jho.security.uaa.handler.JsonAuthenticationSuccessHandler;
import cn.jho.security.uaa.security.filter.RestAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-01 0:17
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.authorizeRequests(req -> req.anyRequest().authenticated())
        //         // 指定登录页面
        //         .formLogin(form -> form.loginPage("/login")
        //                 // 与前端name字段对应
        //                 .usernameParameter("username")
        //                 .defaultSuccessUrl("/")
        //                 .successHandler(new JsonAuthenticationSuccessHandler())
        //                 .failureHandler(new JsonAuthenticationFailureHandler())
        //                 .permitAll())
        //         .csrf(Customizer.withDefaults())
        //         .logout(logout -> logout.logoutUrl("/perform_logout"))
        //         .rememberMe(rm -> rm.tokenValiditySeconds(24 * 60 * 60));

        http.authorizeRequests(req -> req
                        .antMatchers("/authorize/**").permitAll()
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .antMatchers("/api/**").hasRole("USER"))
                // 替代内置filter
                .addFilterAt(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 匹配路径禁用csrf
                .csrf(csrf -> csrf.ignoringAntMatchers("/authorize/**", "/admin/**", "/api/**"));

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/public/**", "/error")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("1"))
                .roles("USER", "ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestAuthenticationFilter restAuthenticationFilter() throws Exception {
        RestAuthenticationFilter filter = new RestAuthenticationFilter(objectMapper);
        filter.setAuthenticationSuccessHandler(jsonAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(jsonAuthenticationFailureHandler());
        filter.setAuthenticationManager(authenticationManager());
        filter.setFilterProcessesUrl("/authorize/login");
        return filter;
    }

    @Bean
    public JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler() {
        return new JsonAuthenticationSuccessHandler();
    }

    @Bean
    public JsonAuthenticationFailureHandler jsonAuthenticationFailureHandler() {
        return new JsonAuthenticationFailureHandler();
    }

}
