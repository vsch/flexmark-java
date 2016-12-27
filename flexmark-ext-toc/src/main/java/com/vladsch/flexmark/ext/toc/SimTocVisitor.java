package com.vladsch.flexmark.ext.toc;

public interface SimTocVisitor {
    void visit(final SimTocBlock node);
    void visit(final SimTocOptionList node);
    void visit(final SimTocOption node);
    void visit(final SimTocContent node);
}
