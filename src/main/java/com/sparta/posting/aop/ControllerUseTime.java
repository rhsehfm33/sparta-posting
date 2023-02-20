package com.sparta.posting.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ControllerUseTime {
    private static final Logger log = LoggerFactory.getLogger(ControllerUseTime.class);

    @Around("execution(public * com.sparta.posting.controller..*(..))")
    public synchronized Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        // 측정 시작 시간
        long startTime = System.currentTimeMillis();

        try {
            // 핵심기능 수행
            Object output = joinPoint.proceed();
            return output;
        } finally {
            // 측정 종료 시간
            long endTime = System.currentTimeMillis();

            // 수행시간 = 종료 시간 - 시작 시간
            long runTime = endTime - startTime;

            // 사용된 controller class
            String controllerClassName = joinPoint.getTarget().getClass().getSimpleName();

            log.info("[Controller Used Time] Controller: " + controllerClassName + ", Total Time: " + runTime + " ms");
        }
    }
}