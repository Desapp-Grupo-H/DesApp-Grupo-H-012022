package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice.aspects;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;


@Aspect
@Component
@Order(0)
public class LogExecutionTimeAspectAnnotation  {
    private Logger logger = LoggerFactory.getLogger(LogExecutionTimeAspectAnnotation.class);

    @Around("@annotation(LogExecutionTime)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("/////// Log Execution Time");
        String signature = joinPoint.getSignature().toShortString();
        logger.info("/////// Method: " + signature);
        logger.info("/////// Args: " + joinPoint.getArgs());
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        logger.info("/////// " + signature + " executed in " + executionTime + "ms");
    return  proceed;
    }
}


