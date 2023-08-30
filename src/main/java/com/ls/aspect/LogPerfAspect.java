package com.ls.aspect;

import com.ls.annotation.LogPerf;
import com.ls.constants.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;

@Aspect
@Component
@Slf4j
public class LogPerfAspect {

    @Around("@annotation(com.ls.annotation.LogPerf)")
    public Object logPeformanceAdvice(ProceedingJoinPoint pjp) throws Throwable{
        LogPerf logPerf = ((MethodSignature)pjp.getSignature()).getMethod()
                .getAnnotation(LogPerf.class);

        TimeUnit unit = logPerf.unit();
        int start = LocalTime.now().get(getTemporalFieldForUnit(unit));
        Object res = pjp.proceed();
        int end = LocalTime.now().get(getTemporalFieldForUnit(unit));

        //log perf
        log.info(pjp.getTarget().getClass().getName()+ " "+pjp.getSignature().getName()+ " Perf Time : "+(end-start)+" "+unit.name());

        return res;
    }

    public TemporalField getTemporalFieldForUnit(TimeUnit unit){
        if(unit.equals(TimeUnit.MSEC))
            return ChronoField.MILLI_OF_DAY;
        else if (unit.equals(TimeUnit.MINUTE))
            return ChronoField.MINUTE_OF_DAY;
        else
            return ChronoField.SECOND_OF_DAY;
    }
}
