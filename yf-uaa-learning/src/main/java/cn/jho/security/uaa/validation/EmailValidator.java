package cn.jho.security.uaa.validation;

import cn.jho.security.uaa.anno.ValidEmail;
import lombok.val;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-03 10:07
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final String EMAIL_PATTERN = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validateEmail(value);
    }

    private boolean validateEmail(final String email) {
        val pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }
}
