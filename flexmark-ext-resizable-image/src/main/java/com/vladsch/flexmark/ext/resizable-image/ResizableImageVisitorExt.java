package com.atlassian.plugins.confluence.markdown.ext.DevOpsResizableImage;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class ResizableImageVisitorExt {
    public static <V extends ResizableImageVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(ResizableImage.class, visitor::visit),
        };
    }
}
