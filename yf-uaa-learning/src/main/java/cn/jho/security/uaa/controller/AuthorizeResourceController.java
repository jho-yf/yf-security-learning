package cn.jho.security.uaa.controller;

import cn.jho.security.uaa.domain.vo.UserVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-03 1:06
 */
@RestController
@RequestMapping("/authorize")
public class AuthorizeResourceController {

    @PostMapping("/register")
    public UserVO register(@RequestBody @Valid UserVO vo) {
        return vo;
    }

}
