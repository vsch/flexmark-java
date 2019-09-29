package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class FootnoteVisitorExt {
    public static <V extends FootnoteVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<FootnoteBlock>(FootnoteBlock.class, visitor::visit),
                new VisitHandler<Footnote>(Footnote.class, visitor::visit),
        };
    }
}
