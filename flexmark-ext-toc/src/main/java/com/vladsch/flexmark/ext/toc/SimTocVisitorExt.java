package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class SimTocVisitorExt {
    public static <V extends SimTocVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(SimTocBlock.class, visitor::visit),
                new VisitHandler<>(SimTocOptionList.class, visitor::visit),
                new VisitHandler<>(SimTocOption.class, visitor::visit),
                new VisitHandler<>(SimTocContent.class, visitor::visit)
        };
    }
}
