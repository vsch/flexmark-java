package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.util.ast.*;

import java.util.ArrayList;

public class HeadingCollectingVisitor {
    private final ArrayList<Heading> headings = new ArrayList<Heading>();
    private final NodeVisitor myVisitor;

    public HeadingCollectingVisitor() {
        myVisitor = new BlockNodeVisitor(
                new VisitHandler<Heading>(Heading.class, new Visitor<Heading>() {
                    @Override
                    public void visit(Heading e) {headings.add(e);}
                })
        );
    }

    public void collect(Node node) {
        myVisitor.visit(node);
    }

    public ArrayList<Heading> collectAndGetHeadings(Node node) {
        myVisitor.visit(node);
        return headings;
    }

    public ArrayList<Heading> getHeadings() {
        return headings;
    }
}
