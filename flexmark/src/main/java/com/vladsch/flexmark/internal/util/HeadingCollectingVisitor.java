package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.node.Heading;

import java.util.ArrayList;

public class HeadingCollectingVisitor extends AbstractBlockVisitor {
    private ArrayList<Heading> headings;

    public HeadingCollectingVisitor() {
        this.headings = null;
    }

    public ArrayList<Heading> getHeadings() {
        return headings;
    }

    @Override
    public void visit(Heading node) {
        if (headings == null) {
            headings = new ArrayList<>();
        }
        headings.add(node);
    }
}
