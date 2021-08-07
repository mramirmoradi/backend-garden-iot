package com.project.garden.core.devlog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Before("@annotation(DevLog)")
    public void logging(JoinPoint joinPoint) {
        StringBuilder massage = new StringBuilder();
        massage.append("Running METHOD: ").append(joinPoint.toString());
        if (joinPoint.getArgs().length > 0)
            massage.append("Passed args: ").append(Arrays.toString(joinPoint.getArgs()));
    }
}
