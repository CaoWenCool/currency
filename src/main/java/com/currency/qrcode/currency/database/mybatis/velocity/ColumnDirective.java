package com.currency.qrcode.currency.database.mybatis.velocity;


import com.currency.qrcode.currency.database.SqliPrevention;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

/**
 * #column( $column $order )
 *
 * <p>
 * where '$order' is optional.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/19 上午11:08
 */
public class ColumnDirective extends Directive {
    @Override
    public String getName() {
        return "column";
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public void init(RuntimeServices rs, InternalContextAdapter context, Node node) throws TemplateInitException {
        super.init(rs, context, node);

        int argCount = node.jjtGetNumChildren();
        if (argCount < 1 || argCount > 2) {
            throw new TemplateInitException("#" + getName() + "() requires one or two arguments",
                getTemplateName(), getLine(), getColumn());
        }
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node)
        throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        Object value = node.jjtGetChild(0).value(context);
        String column = Objects.toString(value);
        SqliPrevention.checkColumn(column);
        writer.append(column);

        if (node.jjtGetNumChildren() > 1) {
            value = node.jjtGetChild(1).value(context);
            String order = Objects.toString(value);
            SqliPrevention.checkOrder(order);
            writer.append(" ").append(order);
        }

        return true;
    }
}
