package com.currency.qrcode.currency.database.mybatis.xml;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.Configuration;
import org.w3c.dom.NodeList;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Copied and modified from {@link org.apache.ibatis.scripting.xmltags.XMLScriptBuilder} in mybatis 3.5.5.
 *
 * <p>
 * Changes:
 * <ul>
 * <li>Pass in {@code injectionFilter} via constructor.</li>
 * <li>Pass {@code injectionFilter} to {@link TextSqlNode}.</li>
 * </ul>
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/18 下午2:15
 */
public class InjectionFilteringXmlScriptBuilder extends BaseBuilder {

    private final XNode context;
    private boolean isDynamic;
    private final Class<?> parameterType;
    private final Map<String, NodeHandler> nodeHandlerMap;

    private Pattern injectionFilter;

    public InjectionFilteringXmlScriptBuilder(Configuration configuration, XNode context) {
        this(configuration, context, (Class) null, null);
    }

    public InjectionFilteringXmlScriptBuilder(Configuration configuration, XNode context,
                                              Class<?> parameterType, Pattern injectionFilter) {
        super(configuration);
        this.nodeHandlerMap = new HashMap();
        this.context = context;
        this.parameterType = parameterType;
        this.injectionFilter = injectionFilter;
        this.initNodeHandlerMap();
    }

    private void initNodeHandlerMap() {
        this.nodeHandlerMap.put("trim", new TrimHandler());
        this.nodeHandlerMap.put("where", new WhereHandler());
        this.nodeHandlerMap.put("set", new SetHandler());
        this.nodeHandlerMap.put("foreach", new ForEachHandler());
        this.nodeHandlerMap.put("if", new IfHandler());
        this.nodeHandlerMap.put("choose", new ChooseHandler());
        this.nodeHandlerMap.put("when", new IfHandler());
        this.nodeHandlerMap.put("otherwise", new OtherwiseHandler());
        this.nodeHandlerMap.put("bind", new BindHandler());
    }

    public SqlSource parseScriptNode() {
        MixedSqlNode rootSqlNode = this.parseDynamicTags(this.context);
        Object sqlSource;
        if (this.isDynamic) {
            sqlSource = new DynamicSqlSource(this.configuration, rootSqlNode);
        } else {
            sqlSource = new RawSqlSource(this.configuration, rootSqlNode, this.parameterType);
        }

        return (SqlSource) sqlSource;
    }

    protected MixedSqlNode parseDynamicTags(XNode node) {
        List<SqlNode> contents = new ArrayList();
        NodeList children = node.getNode().getChildNodes();

        for (int i = 0; i < children.getLength(); ++i) {
            XNode child = node.newXNode(children.item(i));
            String nodeName;
            if (child.getNode().getNodeType() != 4 && child.getNode().getNodeType() != 3) {
                if (child.getNode().getNodeType() == 1) {
                    nodeName = child.getNode().getNodeName();
                    NodeHandler handler = (NodeHandler) this.nodeHandlerMap.get(nodeName);
                    if (handler == null) {
                        throw new BuilderException("Unknown element <" + nodeName + "> in SQL statement.");
                    }

                    handler.handleNode(child, contents);
                    this.isDynamic = true;
                }
            } else {
                nodeName = child.getStringBody("");
                TextSqlNode textSqlNode = new TextSqlNode(nodeName, injectionFilter);
                if (textSqlNode.isDynamic()) {
                    contents.add(textSqlNode);
                    this.isDynamic = true;
                } else {
                    contents.add(new StaticTextSqlNode(nodeName));
                }
            }
        }

        return new MixedSqlNode(contents);
    }

    private class ChooseHandler implements NodeHandler {
        public ChooseHandler() {
        }

        public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
            List<SqlNode> whenSqlNodes = new ArrayList();
            List<SqlNode> otherwiseSqlNodes = new ArrayList();
            this.handleWhenOtherwiseNodes(nodeToHandle, whenSqlNodes, otherwiseSqlNodes);
            SqlNode defaultSqlNode = this.getDefaultSqlNode(otherwiseSqlNodes);
            ChooseSqlNode chooseSqlNode = new ChooseSqlNode(whenSqlNodes, defaultSqlNode);
            targetContents.add(chooseSqlNode);
        }

        private void handleWhenOtherwiseNodes(XNode chooseSqlNode,
                                              List<SqlNode> ifSqlNodes, List<SqlNode> defaultSqlNodes) {
            List<XNode> children = chooseSqlNode.getChildren();
            Iterator var5 = children.iterator();

            while (var5.hasNext()) {
                XNode child = (XNode) var5.next();
                String nodeName = child.getNode().getNodeName();
                NodeHandler handler = (NodeHandler) nodeHandlerMap.get(nodeName);
                if (handler instanceof IfHandler) {
                    handler.handleNode(child, ifSqlNodes);
                } else if (handler instanceof OtherwiseHandler) {
                    handler.handleNode(child, defaultSqlNodes);
                }
            }

        }

        private SqlNode getDefaultSqlNode(List<SqlNode> defaultSqlNodes) {
            SqlNode defaultSqlNode = null;
            if (defaultSqlNodes.size() == 1) {
                defaultSqlNode = (SqlNode) defaultSqlNodes.get(0);
            } else if (defaultSqlNodes.size() > 1) {
                throw new BuilderException("Too many default (otherwise) elements in choose statement.");
            }

            return defaultSqlNode;
        }
    }

    private class OtherwiseHandler implements NodeHandler {
        public OtherwiseHandler() {
        }

        public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
            MixedSqlNode mixedSqlNode = parseDynamicTags(nodeToHandle);
            targetContents.add(mixedSqlNode);
        }
    }

    private class IfHandler implements NodeHandler {
        public IfHandler() {
        }

        public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
            MixedSqlNode mixedSqlNode = parseDynamicTags(nodeToHandle);
            String test = nodeToHandle.getStringAttribute("test");
            IfSqlNode ifSqlNode = new IfSqlNode(mixedSqlNode, test);
            targetContents.add(ifSqlNode);
        }
    }

    private class ForEachHandler implements NodeHandler {
        public ForEachHandler() {
        }

        public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
            MixedSqlNode mixedSqlNode = parseDynamicTags(nodeToHandle);
            String collection = nodeToHandle.getStringAttribute("collection");
            String item = nodeToHandle.getStringAttribute("item");
            String index = nodeToHandle.getStringAttribute("index");
            String open = nodeToHandle.getStringAttribute("open");
            String close = nodeToHandle.getStringAttribute("close");
            String separator = nodeToHandle.getStringAttribute("separator");
            ForEachSqlNode
                forEachSqlNode = new ForEachSqlNode(configuration, mixedSqlNode, collection, index, item, open,
                close, separator);
            targetContents.add(forEachSqlNode);
        }
    }

    private class SetHandler implements NodeHandler {
        public SetHandler() {
        }

        public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
            MixedSqlNode mixedSqlNode = parseDynamicTags(nodeToHandle);
            SetSqlNode set = new SetSqlNode(configuration, mixedSqlNode);
            targetContents.add(set);
        }
    }

    private class WhereHandler implements NodeHandler {
        public WhereHandler() {
        }

        public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
            MixedSqlNode mixedSqlNode = parseDynamicTags(nodeToHandle);
            WhereSqlNode where = new WhereSqlNode(configuration, mixedSqlNode);
            targetContents.add(where);
        }
    }

    private class TrimHandler implements NodeHandler {
        public TrimHandler() {
        }

        public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
            MixedSqlNode mixedSqlNode = parseDynamicTags(nodeToHandle);
            String prefix = nodeToHandle.getStringAttribute("prefix");
            String prefixOverrides = nodeToHandle.getStringAttribute("prefixOverrides");
            String suffix = nodeToHandle.getStringAttribute("suffix");
            String suffixOverrides = nodeToHandle.getStringAttribute("suffixOverrides");
            TrimSqlNode
                trim = new TrimSqlNode(configuration, mixedSqlNode, prefix, prefixOverrides, suffix, suffixOverrides);
            targetContents.add(trim);
        }
    }

    private class BindHandler implements NodeHandler {
        public BindHandler() {
        }

        public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
            String name = nodeToHandle.getStringAttribute("name");
            String expression = nodeToHandle.getStringAttribute("value");
            VarDeclSqlNode node = new VarDeclSqlNode(name, expression);
            targetContents.add(node);
        }
    }

    private interface NodeHandler {
        void handleNode(XNode var1, List<SqlNode> var2);
    }
}
