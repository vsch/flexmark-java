package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class WikiLinkVisitorExt {
    static <V extends WikiLinkVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(WikiLink.class, new Visitor<WikiLink>() {
                    @Override
                    public void visit(WikiLink node) {
                        visitor.visit(node);
                    }
                })
        };
    }
}
