package cn.jho.security.uaa.anno;

import cn.jho.security.uaa.validation.PasswordMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-03 11:43
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
public @interface PasswordMatch {

    String message() default "Password Not Matched";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
