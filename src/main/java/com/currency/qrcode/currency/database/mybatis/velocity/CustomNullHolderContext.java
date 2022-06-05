package com.currency.qrcode.currency.database.mybatis.velocity;

import org.apache.velocity.context.ChainedInternalContextAdapter;
import org.apache.velocity.context.InternalContextAdapter;

/**
 * Copied from {@link org.mybatis.scripting.velocity.RepeatDirective}.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/19 上午11:01
 */
public class CustomNullHolderContext extends ChainedInternalContextAdapter {
    private String loopVariableKey = "";
    private boolean active = true;

    protected CustomNullHolderContext(String key, InternalContextAdapter context) {
        super(context);
        if (key != null) {
            this.loopVariableKey = key;
        }

    }

    public Object get(String key) {
        return this.active && this.loopVariableKey.equals(key) ? null : super.get(key);
    }

    public Object put(String key, Object value) {
        if (this.loopVariableKey.equals(key) && value == null) {
            this.active = true;
        }

        return super.put(key, value);
    }

    public Object remove(String key) {
        if (this.loopVariableKey.equals(key)) {
            this.active = false;
        }

        return super.remove(key);
    }
}
