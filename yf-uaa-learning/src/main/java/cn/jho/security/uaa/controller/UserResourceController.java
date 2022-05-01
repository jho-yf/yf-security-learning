package cn.jho.security.uaa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-01 0:08
 */
@RestController
@RequestMapping("/api")
public class UserResourceController {

    @GetMapping("hello")
    public String hello() {
        return "hello uaa";
    }

}
