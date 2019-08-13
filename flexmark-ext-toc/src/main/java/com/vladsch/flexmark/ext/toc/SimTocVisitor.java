package com.vladsch.flexmark.ext.toc;

public interface SimTocVisitor {
    void visit(SimTocBlock node);
    void visit(SimTocOptionList node);
    void visit(SimTocOption node);
    void visit(SimTocContent node);
}
