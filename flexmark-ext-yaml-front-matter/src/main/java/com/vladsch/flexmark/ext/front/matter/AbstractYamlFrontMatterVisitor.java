package com.vladsch.flexmark.ext.front.matter;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeVisitor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AbstractYamlFrontMatterVisitor implements YamlFrontMatterVisitor {
    private Map<String, List<String>> data;

    private final NodeVisitor myVisitor;

    public AbstractYamlFrontMatterVisitor() {
        //noinspection unchecked
        //myVisitor = new NodeVisitor(
        //        new VisitHandler<>(YamlFrontMatterNode.class, AbstractYamlFrontMatterVisitor.this::visit),
        //        new VisitHandler<>(YamlFrontMatterBlock.class, AbstractYamlFrontMatterVisitor.this::visit)
        //);
        myVisitor = new NodeVisitor(YamlFrontMatterVisitorExt.VISIT_HANDLERS(this));
        data = new LinkedHashMap<>();
    }

    public void visit(Node node) {
        myVisitor.visit(node);
    }
    
    @Override
    public void visit(YamlFrontMatterNode node) {
        data.put(node.getKey(), node.getValues());
    }

    @Override
    public void visit(YamlFrontMatterBlock node) {
        myVisitor.visitChildren(node);
    }

    public Map<String, List<String>> getData() {
        return data;
    }
}
