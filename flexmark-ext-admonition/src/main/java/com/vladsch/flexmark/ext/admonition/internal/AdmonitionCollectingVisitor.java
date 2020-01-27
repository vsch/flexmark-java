package com.vladsch.flexmark.ext.admonition.internal;

import com.vladsch.flexmark.ext.admonition.AdmonitionBlock;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;

import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class AdmonitionCollectingVisitor {
    private LinkedHashSet<String> qualifiers;
    final private NodeVisitor myVisitor;

    public AdmonitionCollectingVisitor() {
        myVisitor = new NodeVisitor(
                new VisitHandler<>(AdmonitionBlock.class, this::visit)
        );
    }

    public LinkedHashSet<String> getQualifiers() {
        return qualifiers;
    }

    public void collect(Node node) {
        qualifiers = new LinkedHashSet<>();
        myVisitor.visit(node);
    }

    public Set<String> collectAndGetQualifiers(Node node) {
        collect(node);
        return qualifiers;
    }

    void visit(AdmonitionBlock node) {
        qualifiers.add(node.getInfo().toString());
    }
}
