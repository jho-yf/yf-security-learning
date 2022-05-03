package cn.jho.security.uaa.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-03 0:58
 */
@Data
public class User implements Serializable {

    private String username;

    private String password;

    private String email;

    private String name;

}
