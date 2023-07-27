package com.freefeather.lab.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class ReducerAspect {

    @Around("execution(* com.freefeather.lab.controller.ProducerController.*(..))")
    public Object around(ProceedingJoinPoint pip) throws Throwable {
        StopWatch watch = new StopWatch();
        watch.start(pip.getSignature().toString());
        Object obj = pip.proceed();
        watch.stop();
        log.info("執行方法: " + pip.getSignature().toString());
        log.info("執行時間: " + watch.getTotalTimeMillis() + " ms");
        return obj;
    }
}
