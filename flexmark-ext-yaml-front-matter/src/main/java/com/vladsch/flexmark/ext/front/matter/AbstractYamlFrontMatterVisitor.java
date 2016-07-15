package com.vladsch.flexmark.ext.front.matter;

import com.vladsch.flexmark.internal.util.AbstractCustomVisitor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AbstractYamlFrontMatterVisitor extends AbstractCustomVisitor implements YamlFrontMatterVisitor {
    private Map<String, List<String>> data;

    public AbstractYamlFrontMatterVisitor() {
        //noinspection unchecked
        super(YamlFrontMatterVisitor.VISIT_HANDLERS);
        data = new LinkedHashMap<>();
    }

    @Override
    public void visit(YamlFrontMatterNode node) {
        data.put(node.getKey(), node.getValues());
    }

    @Override
    public void visit(YamlFrontMatterBlock node) {
        visitChildren(node);
    }

    public Map<String, List<String>> getData() {
        return data;
    }
}
