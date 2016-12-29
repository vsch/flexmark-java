package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class WikiImageVisitorExt {
    public static <V extends WikiImageVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(WikiImage.class, new Visitor<WikiImage>() {
                    @Override
                    public void visit(WikiImage node) {
                        visitor.visit(node);
                    }
                })
        };
    }
}
