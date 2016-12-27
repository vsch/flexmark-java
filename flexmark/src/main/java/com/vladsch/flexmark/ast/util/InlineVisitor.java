package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;

public interface InlineVisitor {
    void visit(final AutoLink node);
    void visit(final Code node);
    void visit(final Emphasis node);
    void visit(final HardLineBreak node);
    void visit(final HtmlEntity node);
    void visit(final HtmlInline node);
    void visit(final HtmlInlineComment node);
    void visit(final Image node);
    void visit(final ImageRef node);
    void visit(final Link node);
    void visit(final LinkRef node);
    void visit(final MailLink node);
    void visit(final SoftLineBreak node);
    void visit(final StrongEmphasis node);
    void visit(final Text node);
}
