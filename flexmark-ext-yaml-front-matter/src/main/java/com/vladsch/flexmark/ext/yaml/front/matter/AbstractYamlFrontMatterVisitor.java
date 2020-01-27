package com.vladsch.flexmark.ext.yaml.front.matter;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AbstractYamlFrontMatterVisitor implements YamlFrontMatterVisitor {
    final private Map<String, List<String>> data;
    final private NodeVisitor myVisitor;

    public AbstractYamlFrontMatterVisitor() {
        //myVisitor = new NodeVisitor(
        //        new VisitHandler<>(YamlFrontMatterNode.class, this::visit),
        //        new VisitHandler<>(YamlFrontMatterBlock.class, this::visit)
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
