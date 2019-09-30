package com.vladsch.flexmark.ext.zzzzzz;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class ZzzzzzVisitorExt {
    public static <V extends ZzzzzzVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(Zzzzzz.class, visitor::visit),// zzzoptionszzz(CUSTOM_NODE)
                new VisitHandler<>(ZzzzzzBlock.class, visitor::visit),// zzzoptionszzz(CUSTOM_BLOCK_NODE)
        };
    }
}
