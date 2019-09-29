package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class SimTocVisitorExt {
    public static <V extends SimTocVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<SimTocBlock>(SimTocBlock.class, visitor::visit),
                new VisitHandler<SimTocOptionList>(SimTocOptionList.class, visitor::visit),
                new VisitHandler<SimTocOption>(SimTocOption.class, visitor::visit),
                new VisitHandler<SimTocContent>(SimTocContent.class, visitor::visit)
        };
    }
}
