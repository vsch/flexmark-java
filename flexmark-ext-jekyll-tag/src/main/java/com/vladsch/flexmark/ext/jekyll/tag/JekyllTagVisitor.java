package com.vladsch.flexmark.ext.jekyll.tag;

public interface JekyllTagVisitor {
    void visit(final JekyllTag node);
    void visit(final JekyllTagBlock node);
}
