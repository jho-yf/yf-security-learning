package cn.jho.security.uaa.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-03 15:58
 */
@ControllerAdvice
public class ExceptionHandler implements ProblemHandling {

    @Override
    public boolean isCausalChainsEnabled() {
        return true;
    }
}
