package com.vladsch.flexmark.ext.toc;

public interface TocVisitor {
    void visit(final TocBlock node);
}
