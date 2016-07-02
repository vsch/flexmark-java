package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.node.Heading;

import java.util.ArrayList;

public class HeadingCollectingVisitor extends AbstractBlockVisitor {
    final private int level;
    private ArrayList<Heading> headings;

    public HeadingCollectingVisitor(int level) {
        this.level = level;
        this.headings = null;
    }

    public ArrayList<Heading> getHeadings() {
        return headings;
    }

    @Override
    public void visit(Heading node) {
        if (node.getLevel() <= level) {
            if (headings == null) {
                headings = new ArrayList<>();
            }
            headings.add(node);
        }
    }
}
