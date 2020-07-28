package org.Lunaros.framework.engine.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.Lunaros.framework.api.model.auth.User;
import org.Lunaros.framework.engine.AuthUtil;
import org.Lunaros.global.CurrentThreadInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by xupeng on 16-4-5.
 */
@Aspect
@Component("userIdAspect")
public class UserIdAspect {

    private static Logger logger = LoggerFactory.getLogger(UserIdAspect.class);

//    @Pointcut("execution(* org.Lunaros.framework.api.service..*.*(..))")
    @Pointcut("execution(* org.Lunaros.framework.api.controller..*.*(..))")
    private void service() {}

    @Before("service()")
    public void setUserId(JoinPoint jp) {
        if (logger.isDebugEnabled()) {
            logger.debug("before point cut:{}, join point:{}", jp.getTarget(), jp.toLongString());
        }
        try {
            User user = AuthUtil.getUser();
            CurrentThreadInfo.setUser(user);
        } catch (Exception ignore) {
            CurrentThreadInfo.setUser(null);
            //ignore exception for anonymous controller
        }
    }
}
