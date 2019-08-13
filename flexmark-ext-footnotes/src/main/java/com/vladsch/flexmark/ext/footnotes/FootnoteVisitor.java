package com.vladsch.flexmark.ext.footnotes;

public interface FootnoteVisitor {
    void visit(FootnoteBlock node);
    void visit(Footnote node);
}
