package cn.jho.security.uaa.validation;

import cn.jho.security.uaa.anno.ValidPassword;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.passay.*;
import org.passay.spring.SpringMessageResolver;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-03 11:16
 */
@RequiredArgsConstructor
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    private final SpringMessageResolver messageResolver;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        val validator = new PasswordValidator(messageResolver, Arrays.asList(
                // 长度8-30
                new LengthRule(8, 30),
                // 至少一个大写字母
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // 至少一个小写字母
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // 至少一个特殊字符
                new CharacterRule(EnglishCharacterData.Special, 1),
                // 不允许连续5个字母
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                // 不允许连续5个数字
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),
                // 不允许键盘上连续5个字母
                new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false),
                // 空格
                new WhitespaceRule()
        ));
        val validate = validator.validate(new PasswordData(password));
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(String.join(",", validator.getMessages(validate)))
                .addConstraintViolation();
        return validate.isValid();
    }
}
