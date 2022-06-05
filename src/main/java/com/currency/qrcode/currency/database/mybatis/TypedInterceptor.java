package com.currency.qrcode.currency.database.mybatis;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import java.util.Properties;

/**
 * Type safe version of {@link Interceptor}.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/18 下午3:33
 */
public abstract class TypedInterceptor<T> implements Interceptor {

    private final Class<T> type;

    public TypedInterceptor(Class<T> type) {
        this.type = type;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // This method should not be invoked.
        return invocation.proceed();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object plugin(Object target) {
        if (type.isInstance(target)) {
            return wrap((T) target);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        // Properties are not needed.
    }

    protected abstract T wrap(T target);

}
