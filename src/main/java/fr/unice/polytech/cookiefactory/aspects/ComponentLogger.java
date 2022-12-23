package fr.unice.polytech.cookiefactory.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ComponentLogger {

        private static final Logger LOG = LoggerFactory.getLogger(ComponentLogger.class);
        private static final String PREFIX = "CookieFactory:Component:";

        @Pointcut("execution(public * fr.unice.polytech.cookiefactory.components..*(..))")
        private void allComponentsMethods() {}

        @Before("allComponentsMethods()")
        public void logMethodNameAndParametersAtEntry(JoinPoint joinPoint) {
            LOG.info(PREFIX + joinPoint.getThis() + ":Called {}", joinPoint.getSignature().getName() + " " + joinPoint.getArgs());
        }

        @AfterReturning(pointcut = "allComponentsMethods()", returning = "resultVal")
        public void logMethodReturningProperly(JoinPoint joinPoint, Object resultVal) {
            LOG.info(PREFIX + joinPoint.getThis() + ":Returned {}", joinPoint.getSignature().getName() + " with value " + resultVal);
        }

        @AfterThrowing(pointcut = "allComponentsMethods()", throwing = "exception")
        public void logMethodException(JoinPoint joinPoint, Exception exception) {
            LOG.warn(PREFIX + joinPoint.getThis() + ":Exception from {}", joinPoint.getSignature().getName() + " with exception " + exception);
        }

}
