package com.vladsch.flexmark.ext.jekyll.front.matter;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class JekyllFrontMatterVisitorExt {
    public static <V extends JekyllFrontMatterVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<JekyllFrontMatterBlock>(JekyllFrontMatterBlock.class, new Visitor<JekyllFrontMatterBlock>() {
                    @Override
                    public void visit(JekyllFrontMatterBlock node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
