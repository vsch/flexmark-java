package com.vladsch.flexmark.ext.jekyll.tag;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class JekyllTagVisitorExt {
    public static <V extends JekyllTagVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                // @formatter:off
                new VisitHandler<JekyllTag>(JekyllTag.class, new Visitor<JekyllTag>() { @Override public void visit(JekyllTag node) { visitor.visit(node); } }),
                new VisitHandler<JekyllTagBlock>(JekyllTagBlock.class, new Visitor<JekyllTagBlock>() { @Override public void visit(JekyllTagBlock node) { visitor.visit(node); } }),
                // @formatter:on
        };
    }
}
