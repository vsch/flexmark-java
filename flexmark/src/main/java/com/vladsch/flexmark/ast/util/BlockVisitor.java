package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.util.ast.Document;

public interface BlockVisitor {
    void visit(final BlockQuote node);
    void visit(final BulletList node);
    void visit(final Document node);
    void visit(final FencedCodeBlock node);
    void visit(final Heading node);
    void visit(final HtmlBlock node);
    void visit(final HtmlCommentBlock node);
    void visit(final IndentedCodeBlock node);
    void visit(final BulletListItem node);
    void visit(final OrderedListItem node);
    void visit(final OrderedList node);
    void visit(final Paragraph node);
    void visit(final Reference node);
    void visit(final ThematicBreak node);
}
