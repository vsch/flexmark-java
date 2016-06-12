package com.vladsch.flexmark.node;

/**
 * Node visitor.
 * <p>
 * See {@link AbstractVisitor} for a base class that can be extended.
 */
public interface Visitor {
    void visit(AutoLink node);
    void visit(BlockQuote node);
    void visit(BulletList node);
    void visit(Code node);
    void visit(CustomBlock node);
    void visit(CustomNode node);
    void visit(Document node);
    void visit(Emphasis node);
    void visit(FencedCodeBlock node);
    void visit(HardLineBreak node);
    void visit(Heading node);
    void visit(HtmlBlock node);
    void visit(HtmlEntity node);
    void visit(HtmlInline node);
    void visit(Image node);
    void visit(ImageRef node);
    void visit(IndentedCodeBlock node);
    void visit(Link node);
    void visit(LinkRef node);
    void visit(ListItem node);
    void visit(MailLink node);
    void visit(OrderedList node);
    void visit(Paragraph node);
    void visit(Reference node);
    void visit(SoftLineBreak node);
    void visit(StrongEmphasis node);
    void visit(Text node);
    void visit(ThematicBreak node);
    void visit(WikiLink node);
}
