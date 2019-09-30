package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class WikiLinkVisitorExt {
    public static <V extends WikiLinkVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(WikiLink.class, visitor::visit),
        };
    }
}
