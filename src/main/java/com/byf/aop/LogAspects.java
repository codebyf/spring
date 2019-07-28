package com.byf.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.lang.annotation.Retention;
import java.util.Arrays;

@Aspect
public class LogAspects {
    // 抽取公共的切入点表达式
    // 1、本类引用
    // 2、其他的却面引用com.byf.aop.MathCalculator.div
    @Pointcut("execution(public int com.byf.aop.MathCalculator.*(..))")
    public void pointCut(){};

    // @Before：在目标方法之前切入：切入点表达式（指定在哪个方法切入）
    //@Before("public int com.byf.aop.MathCalculator.*(..)")
    @Before("com.byf.aop.LogAspects.pointCut()")
    public void logStart(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.println(""+ joinPoint.getSignature().getName() +"运行：参数列表是{"+ Arrays.asList(args)+"}");
    }
    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint){
        System.out.println(""+ joinPoint.getSignature().getName() +"结束！");
    }
    @AfterReturning(value = "pointCut()",returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result){
        System.out.println(""+ joinPoint.getSignature().getName() +"正常返回：运行结果是：" + result);
    }
    @AfterThrowing(value = "pointCut()",throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception){
        System.out.println(""+ joinPoint.getSignature().getName() +"异常：异常结果：" + exception);
    }
}
