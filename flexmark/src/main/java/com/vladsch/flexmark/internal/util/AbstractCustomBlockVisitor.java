package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.node.*;

import java.util.Collection;

/**
 * Abstract visitor that visits all children by default.
 * <p>
 * Can be used to only process certain nodes. If you override a method and want visiting to descend into children,
 * call {@link #visitChildren}.
 */
public class AbstractCustomBlockVisitor extends AbstractCustomVisitorBase {
    public AbstractCustomBlockVisitor(NodeVisitHandler<?>... visitors) {
        super(visitors);
    }

    public AbstractCustomBlockVisitor(Collection<NodeVisitHandler<?>> visitors) {
        super(visitors);
    }

    public AbstractCustomBlockVisitor(Computable<NodeVisitHandler<?>[], Object>... visitors) {
        super(visitors);
    }

    // inlines
    @Override public void visit(AutoLink node) {  }
    @Override public void visit(Code node) {  }
    @Override public void visit(CustomNode node) {  }
    @Override public void visit(Emphasis node) {  }
    @Override public void visit(HardLineBreak node) {  }
    @Override public void visit(HtmlEntity node) {  }
    @Override public void visit(HtmlInline node) {  }
    @Override public void visit(HtmlInlineComment node) {  }
    @Override public void visit(Image node) {  }
    @Override public void visit(ImageRef node) {  }
    @Override public void visit(Link node) {  }
    @Override public void visit(LinkRef node) {  }
    @Override public void visit(MailLink node) {  }
    @Override public void visit(Reference node) {  }
    @Override public void visit(SoftLineBreak node) {  }
    @Override public void visit(StrongEmphasis node) {  }
    // @formatter:on
}
