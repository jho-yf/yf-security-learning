package cn.jho.security.uaa.domain.vo;

import cn.jho.security.uaa.anno.PasswordMatch;
import cn.jho.security.uaa.anno.ValidEmail;
import cn.jho.security.uaa.anno.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-03 1:00
 */
@Data
@PasswordMatch
public class UserVO implements Serializable {

    @NotNull
    @NotBlank
    @Size(min = 4, max = 50, message = "用户名长度必须在4到50个字符之间")
    private String username;

    @NotNull
    @NotBlank
    @ValidPassword
    private String password;

    @NotNull
    @NotBlank
    private String matchingPassword;

    @NotNull
    @ValidEmail
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 4, max = 50, message = "姓名长度必须在4到50个字符之间")
    private String name;

}
