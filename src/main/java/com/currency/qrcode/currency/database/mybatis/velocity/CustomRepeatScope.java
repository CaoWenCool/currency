package com.currency.qrcode.currency.database.mybatis.velocity;

import org.apache.velocity.runtime.directive.Scope;

/**
 * Copied from {@link org.mybatis.scripting.velocity.RepeatDirective}.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/19 上午10:59
 */
public class CustomRepeatScope extends Scope {
    protected int index = -1;
    protected boolean hasNext = false;
    protected final String var;

    CustomRepeatScope(Object newOwner, Object replaces, String newVar) {
        super(newOwner, replaces);
        this.var = newVar;
    }

    public int getIndex() {
        return this.index;
    }

    public int getCount() {
        return this.index + 1;
    }

    public boolean hasNext() {
        return getHasNext();
    }

    public boolean getHasNext() {
        return this.hasNext;
    }

    public boolean isFirst() {
        return this.index < 1;
    }

    public boolean getFirst() {
        return isFirst();
    }

    public boolean isLast() {
        return !this.hasNext;
    }

    public boolean getLast() {
        return isLast();
    }

    public String getVar() {
        return this.var;
    }
}
