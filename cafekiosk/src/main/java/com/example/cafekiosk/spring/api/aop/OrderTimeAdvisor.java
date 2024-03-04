package com.example.cafekiosk.spring.api.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Slf4j
@Component
public class OrderTimeAdvisor {
    @Around("execution(* com.example.cafekiosk.spring.api.service.order.OrderService.createOrder(..))")
    public Object stopWatch(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        Object result;
        try {
            stopWatch.start();
            result = joinPoint.proceed();
        } catch (Throwable throwable){
            stopWatch.stop();
            log.info("주문 생성 실패! {} ms", stopWatch.getLastTaskTimeMillis());
            throw throwable;
        }
        stopWatch.stop();
        log.info("주문 생성 완료! {} ms", stopWatch.getLastTaskTimeMillis());
        return result;
    }
}
