package cn.jho.security.uaa.validation;

import cn.jho.security.uaa.anno.PasswordMatch;
import cn.jho.security.uaa.domain.vo.UserVO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-03 11:42
 */
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserVO> {

    @Override
    public boolean isValid(UserVO vo, ConstraintValidatorContext context) {
        return vo.getPassword().equals(vo.getMatchingPassword());
    }
}
