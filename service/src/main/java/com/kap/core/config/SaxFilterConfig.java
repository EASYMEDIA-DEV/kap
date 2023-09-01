package com.kap.core.config;

import com.kap.core.utility.SaxFilterUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SaxFilterConfig {
    @Before("execution(* com.kap..*Controller.insert*(..)) || execution(* com.kap..*Controller.update*(..))")
    public void saxFilter(JoinPoint joinPoint) throws Throwable {
        if(joinPoint.getArgs() != null) {
            for (Object arg : joinPoint.getArgs()) {
                SaxFilterUtil.checkAnnotation(arg);
            }
        }
    }
}