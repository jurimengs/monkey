package com.monkey.netty.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;

import com.monkey.exceptions.BusinessInvokeException;
import com.monkey.exceptions.TellUserMessageException;
import com.monkey.netty.client.RpcRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * 做netty渠道反射用的
 * @author A37
 *
 */
@Slf4j
public class BusinessExecutor {
    private BeanFactory beanFactory;
    private Map<String, Method> methodCache = new ConcurrentHashMap<>();

    public Object execute(RpcRequest request) {
        Class<?> toExecuteClass = request.getBeanClass();
        String beanName = request.getBeanName();
        if(toExecuteClass == null && org.apache.commons.lang3.StringUtils.isEmpty(beanName)) {
            throw new TellUserMessageException("要执行哪个 Class 呢？");
        }
        String toExecuteMethodName = request.getMethod();
        if(StringUtils.isEmpty(toExecuteMethodName)) {
            throw new TellUserMessageException("要执行哪个方法呢？");
        }
        try {
            Object[] toExecuteMethodArgs = request.getArgs();
            
            Object bean = beanFactory.getBean(request.getBeanName());
            
            Class<?> [] parameterTypes = request.getParameterTypes();
            
            // 涉及到反射的地方， 可以考虑是否需要将 Method 缓存起来
            Method method = getMethod(toExecuteMethodName, bean, parameterTypes);
            return method.invoke(bean, toExecuteMethodArgs);
        } catch (NoSuchMethodException | SecurityException e) {
            log.error("反射获取 Class {} 的方法 {} 异常", beanName, toExecuteMethodName, e);
            throw new BusinessInvokeException(e.getMessage());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            log.error("执行 Class {} 的方法 {} 异常", beanName, toExecuteMethodName, e);
            throw new BusinessInvokeException(e.getMessage());
        } catch (Exception e) {
            log.error("执行 Class {} 的方法 {} 异常", beanName, toExecuteMethodName, e);
            throw new BusinessInvokeException(e.getMessage());
        }
    }

    private Method getMethod(String toExecuteMethodName, Object bean, Class<?>[] parameterTypes)
            throws NoSuchMethodException {
        String key = bean.getClass().getName() + toExecuteMethodName;
        Method cachedMethod = methodCache.get(key);
        if(cachedMethod != null) {
            return cachedMethod;
        }
        Method method = bean.getClass().getDeclaredMethod(toExecuteMethodName, parameterTypes);
        methodCache.put(key, method);
        return method;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
    
}
