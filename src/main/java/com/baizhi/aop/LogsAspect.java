package com.baizhi.aop;
import com.baizhi.annotation.AddLog;
import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Log;
import com.baizhi.util.UUIDUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Configuration
@Aspect
public class LogsAspect {

    @Resource
    private HttpServletRequest request;

    @Resource
    private LogMapper logMapper;


    //记录管理员操作的日志
    @Around("@annotation(com.baizhi.annotation.AddLog)")
    public Object adminOperLogs(ProceedingJoinPoint proceedingJoinPoint){
        //使用环绕通知

        //获取管理员name
        Admin admin = (Admin) request.getSession().getAttribute("admin");
      //  String username = admin.getUsername();
        //模拟取出的管理员名字  因为springboot的session默认存活时间为60s
        String username = "admin";

        //获取时间
        Date date = new Date();

        //获取方法名字
        String name = proceedingJoinPoint.getSignature().getName();

        //获取方法的注解
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        AddLog addLog = methodSignature.getMethod().getAnnotation(AddLog.class);
        String value = addLog.value();
        //向下执行

        Object methodResult = null;
        try {
             methodResult = proceedingJoinPoint.proceed();
            String message = "success";
            String uuid = UUIDUtil.getUUID();
            Log log = new Log(uuid, username,date,value+"("+ name+")", message);
            //日志入库
            logMapper.insert(log);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            String message = "error";
            Log log = new Log(UUIDUtil.getUUID(), admin.getUsername(),date,value+"("+ name+")", message);
            //日志入库
            logMapper.insert(log);
        }
        return methodResult;
    }

}
