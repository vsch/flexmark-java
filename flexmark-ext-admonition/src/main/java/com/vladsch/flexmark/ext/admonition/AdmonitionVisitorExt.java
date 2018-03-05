package com.vladsch.flexmark.ext.admonition;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class AdmonitionVisitorExt {
    public static <V extends AdmonitionVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<AdmonitionBlock>(AdmonitionBlock.class, new Visitor<AdmonitionBlock>() {
                    @Override
                    public void visit(AdmonitionBlock node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
