package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class WikiImageVisitorExt {
    public static <V extends WikiImageVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(WikiImage.class, visitor::visit)
        };
    }
}
