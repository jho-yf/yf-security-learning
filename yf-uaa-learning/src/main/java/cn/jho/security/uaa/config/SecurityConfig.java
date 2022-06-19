package cn.jho.security.uaa.config;

import cn.jho.security.uaa.handler.JsonAuthenticationFailureHandler;
import cn.jho.security.uaa.handler.JsonAuthenticationSuccessHandler;
import cn.jho.security.uaa.security.auth.ldap.LdapMultiAuthenticationProvider;
import cn.jho.security.uaa.security.auth.ldap.LdapUserRepo;
import cn.jho.security.uaa.security.filter.RestAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import java.util.Map;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-01 0:17
 */
@EnableWebSecurity
@RequiredArgsConstructor
@Import(SecurityProblemSupport.class)
@Order(99)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;

    private final SecurityProblemSupport securityProblemSupport;

    private final UserDetailsService userDetailsService;

    private final UserDetailsPasswordService userDetailsPasswordService;

    private final LdapUserRepo ldapUserRepo;

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

        http.requestMatchers(req -> req.mvcMatchers("/authorize/**", "/admin/**", "/api/**"))
                // 设置无状态的session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e.accessDeniedHandler(securityProblemSupport).authenticationEntryPoint(securityProblemSupport))
                .authorizeRequests(req -> req
                        .antMatchers("/authorize/**").permitAll()
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .antMatchers("/api/**").hasRole("USER"))
                // 替代内置filter
                .addFilterAt(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 匹配路径禁用csrf
                // .csrf(csrf -> csrf.ignoringAntMatchers("/authorize/**", "/admin/**", "/api/**"))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/public/**", "/error/**", "/h2-console/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        auth.authenticationProvider(ldapMultiAuthenticationProvider());
    }

    @Bean
    public LdapMultiAuthenticationProvider ldapMultiAuthenticationProvider() {
        return new LdapMultiAuthenticationProvider(ldapUserRepo);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsPasswordService(userDetailsPasswordService);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 默认编码算法的 Id
        val idForEncode = "bcrypt";
        // 要支持的多种编码器
        val encoders = Map.of(
                idForEncode, new BCryptPasswordEncoder(),
                "SHA-1", new MessageDigestPasswordEncoder("SHA-1")
        );
        return new DelegatingPasswordEncoder(idForEncode, encoders);
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
