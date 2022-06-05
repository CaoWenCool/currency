package com.currency.qrcode.currency.database.mybatis;

import java.lang.annotation.*;

/**
 * Indicates the bean can be referenced as variables ($_vars.xxx) in mybatis velocity SQLs.
 *
 * <p>
 * Example:
 *
 * <pre>
 * &#064;Component
 * &#064;MybatisVariable("status")
 * public class StatusVar {
 *     public Object get(String name) {
 *         return Status.valueOf(name).ordinal();
 *     }
 * }
 *
 * public enum Status {
 *     INACTIVE, ACTIVE
 * }
 * </pre>
 *
 * <p>
 * Then you can use the Status enum in SQL:
 *
 * <pre>
 * SELECT id FROM my_table WHERE status = $_vars.status.ACTIVE
 * </pre>
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/14 下午6:08
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MybatisVariable {
    /**
     * @return The variable name, using the bean name by default.
     */
    String value() default "";
}
