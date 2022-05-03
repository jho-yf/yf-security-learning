package cn.jho.security.uaa.config;

import org.passay.MessageResolver;
import org.passay.spring.SpringMessageResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-02 13:39
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public MessageResolver messageResolver(MessageSource messageSource) {
        return new SpringMessageResolver(messageSource);
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将webjars静态资源加到映射里面
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/")
                .resourceChain(false)
                .addResolver(new WebJarsResourceResolver())
                .addResolver(new PathResourceResolver());
        registry.setOrder(1);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/").setViewName("index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
