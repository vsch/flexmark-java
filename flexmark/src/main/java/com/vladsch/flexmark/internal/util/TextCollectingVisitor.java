package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.node.*;

public class TextCollectingVisitor extends AbstractVisitor {
    private final StringBuilder out = new StringBuilder();

    public TextCollectingVisitor() {
    }

    public String getText() {
        return out.toString();
    }
    
    public String visitAndGetText(Node node) {
        node.accept(this);
        return out.toString();
    }

    @Override
    public void visit(HtmlEntity node) {
        out.append(node.getChars().unescape());
    }

    @Override
    public void visit(SoftLineBreak node) {
        out.append('\n');
    }

    @Override
    public void visit(HardLineBreak node) {
        out.append('\n');
    }
    
    @Override
    public void visit(TextBase node) {
        out.append(node.getChars());
    }
}
