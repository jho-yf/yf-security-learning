package cn.jho.security.uaa.anno;

import cn.jho.security.uaa.validation.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-03 10:10
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {

    String message() default "{ValidEmail.email}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
