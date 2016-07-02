package com.vladsch.flexmark.ext.front.matter;

import com.vladsch.flexmark.internal.util.AbstractVisitor;
import com.vladsch.flexmark.node.CustomNode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class YamlFrontMatterVisitor extends AbstractVisitor {
    private Map<String, List<String>> data;

    public YamlFrontMatterVisitor() {
        data = new LinkedHashMap<>();
    }

    @Override
    public void visit(CustomNode customNode) {
        if (customNode instanceof YamlFrontMatterNode) {
            data.put(((YamlFrontMatterNode) customNode).getKey(), ((YamlFrontMatterNode) customNode).getValues());
        } else {
            super.visit(customNode);
        }
    }

    public Map<String, List<String>> getData() {
        return data;
    }
}
