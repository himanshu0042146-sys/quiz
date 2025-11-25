package com.ajackus.quiz.audit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class AuditAspect {

    // Applies to all controller methods
    @Around("execution(* com.ajackus.quiz.controller..*(..))")
    public Object auditController(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecution(joinPoint, "CONTROLLER");
    }

    // Applies to all service methods
    @Around("execution(* com.ajackus.quiz.service..*(..))")
    public Object auditService(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecution(joinPoint, "SERVICE");
    }

    private Object logExecution(ProceedingJoinPoint joinPoint, String layer) throws Throwable {
        long start = System.currentTimeMillis();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null ? auth.getName() : "ANONYMOUS");

        String methodName = joinPoint.getSignature().toShortString();
        String args = Arrays.toString(joinPoint.getArgs());

        log.info(" [{}] START | User={} | Method={} | Args={}",
                layer, username, methodName, args);

        try {
            Object result = joinPoint.proceed();
            long time = System.currentTimeMillis() - start;

            log.info(" [{}] SUCCESS | User={} | Method={} | ExecutionTime={}ms",
                    layer, username, methodName, time);

            return result;

        } catch (Exception ex) {
            long time = System.currentTimeMillis() - start;

            log.error("[{}] FAILED | User={} | Method={} | ExecutionTime={}ms | Error={}",
                    layer, username, methodName, time, ex.getMessage());

            throw ex;
        }
    }
}

