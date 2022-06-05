package cn.jho.security.uaa.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-01 0:08
 */
@RestController
@RequestMapping("/api")
public class UserResourceController {

    @GetMapping("/hello")
    public String hello() {
        return "hello uaa";
    }

    @GetMapping("/principal")
    public Principal getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
