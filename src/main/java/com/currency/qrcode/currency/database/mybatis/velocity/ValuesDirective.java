package com.currency.qrcode.currency.database.mybatis.velocity;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.*;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.directive.StopCommand;
import org.apache.velocity.runtime.parser.node.ASTReference;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.ParserTreeConstants;
import org.apache.velocity.util.introspection.Info;
import org.mybatis.scripting.velocity.InDirective;
import org.mybatis.scripting.velocity.ParameterMappingCollector;
import org.mybatis.scripting.velocity.RepeatDirective;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

/**
 * #values( $collection $item )
 *     ...
 * #end
 *
 * <p>
 * Unlike {@link RepeatDirective}, this directive does not have a limit for items (1000 in {@link RepeatDirective}).
 *
 * <p>
 * Copied and modified from {@link InDirective}.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/19 上午10:56
 */
public class ValuesDirective extends RepeatDirective {
    private static final String MAPPING_COLLECTOR_KEY = "_pmc";

    private static final String VALUES = "VALUES\n";
    private static final String OPEN = "(\n";
    private static final String CLOSE = ")\n";
    private static final String SEPARATOR = ",\n";

    private String var;

    @Override
    public String getName() {
        return "values";
    }

    @Override
    public void init(RuntimeServices rs, InternalContextAdapter context, Node node) throws TemplateInitException {
        super.init(rs, context, node);

        if (node.jjtGetNumChildren() < 2) {
            throw new TemplateInitException("Syntax error", getTemplateName(), getLine(), getColumn());
        }

        Node child = node.jjtGetChild(1);
        if (child.getType() == ParserTreeConstants.JJTREFERENCE) {
            var = ((ASTReference) child).getRootString();
        } else {
            throw new TemplateInitException("Syntax error", getTemplateName(), getLine(), getColumn());
        }

        uberInfo = new Info(getTemplateName(), getLine(), getColumn());
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node)
            throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        Object listObject = node.jjtGetChild(0).value(context);
        if (listObject == null) {
            return false;
        }

        Iterator<?> iterator = null;

        try {
            iterator = rsvc.getUberspect().getIterator(listObject, uberInfo);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            String msg = "Error getting iterator for #" + getName() + " at " + uberInfo;
            rsvc.getLog().error(msg, e);
            throw new VelocityException(msg, e);
        }

        if (iterator == null) {
            throw new VelocityException("Invalid collection");
        }

        Object o = context.get(var);

        ParameterMappingCollector collector = (ParameterMappingCollector) context.get(MAPPING_COLLECTOR_KEY);
        String savedItemKey = collector.getItemKey();
        collector.setItemKey(var);
        CustomRepeatScope scope = new CustomRepeatScope(this, context.get(getName()), this.var);
        context.put(getName(), scope);

        writer.append(VALUES);

        CustomNullHolderContext nullHolderContext = null;
        Object value = null;
        while (iterator.hasNext()) {
            writer.append(OPEN);

            value = iterator.next();
            put(context, var, value);
            scope.index++;
            scope.hasNext = iterator.hasNext();

            try {
                if (value == null) {
                    if (nullHolderContext == null) {
                        nullHolderContext = new CustomNullHolderContext(var, context);
                    }
                    node.jjtGetChild(node.jjtGetNumChildren() - 1).render(nullHolderContext, writer);
                } else {
                    node.jjtGetChild(node.jjtGetNumChildren() - 1).render(context, writer);
                }
            } catch (StopCommand stop) {
                if (stop.isFor(this)) {
                    break;
                } else {
                    clean(context, o, collector, savedItemKey);
                    throw stop;
                }
            }

            writer.append(CLOSE);

            if (iterator.hasNext()) {
                writer.append(SEPARATOR);
            }
        }

        clean(context, o, collector, savedItemKey);
        return true;
    }
}
