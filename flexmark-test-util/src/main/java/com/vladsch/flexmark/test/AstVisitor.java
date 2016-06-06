package com.vladsch.flexmark.test;

import com.vladsch.flexmark.node.*;

class AstVisitor extends AbstractVisitor {
    public static final String EOL = "\n";
    protected StringBuilder output = new StringBuilder();
    protected int indent = 0;
    protected boolean eolPending = false;

    public String getAst() {
        return output.toString();
    }

    protected void appendIndent() {
        for (int i = 0; i < indent * 2; i++) {
            output.append(' ');
        }
        eolPending = true;
    }

    protected void appendEOL() {
        output.append(EOL);
        eolPending = false;
    }

    protected void appendPendingEOL() {
        if (eolPending) appendEOL();
    }

    public void visitNode(Node node) {
        visitNode(node, "");
    }

    public void visitNode(Node node, String appendNodeData) {
        appendIndent();
        output.append(node.getClass().getName().substring(node.getClass().getPackage().getName().length() + 1))
                .append("[").append(node.getStartOffset()).append(", ").append(node.getEndOffset()).append("]")
                .append(appendNodeData)
                .append(EOL);

        indent++;
        try {
            super.visitChildren(node);
        } finally {
            indent--;
        }
    }

    @Override
    public void visit(BlockQuote node) {
        String extra = " marker:[" + node.getOpeningMarker().getStartOffset() + ", " + node.getOpeningMarker().getEndOffset() + "]";
        visitNode(node, extra);
    }

    @Override
    public void visit(BulletList node) {
        visitNode(node);
    }

    @Override
    public void visit(Code node) {
        String extra = " open:[" + node.getOpeningMarker().getStartOffset() + ", " + node.getOpeningMarker().getEndOffset() + "]";
         extra += " close:[" + node.getClosingMarker().getStartOffset() + ", " + node.getClosingMarker().getEndOffset() + "]";
        visitNode(node, extra);
    }

    @Override
    public void visit(Document node) {
        visitNode(node);
    }

    @Override
    public void visit(Emphasis node) {
        String extra = " open:[" + node.getOpeningMarker().getStartOffset() + ", " + node.getOpeningMarker().getEndOffset() + "]";
        extra += " close:[" + node.getClosingMarker().getStartOffset() + ", " + node.getClosingMarker().getEndOffset() + "]";
        visitNode(node, extra);
    }

    @Override
    public void visit(FencedCodeBlock node) {
        String extra = " open:[" + node.getOpeningMarker().getStartOffset() + ", " + node.getOpeningMarker().getEndOffset() + "]";
        extra += " info:[" + node.getInfo().getStartOffset() + ", " + node.getInfo().getEndOffset() + "]";
        extra += " close:[" + node.getClosingMarker().getStartOffset() + ", " + node.getClosingMarker().getEndOffset() + "]";
        visitNode(node, extra);
    }

    @Override
    public void visit(HardLineBreak node) {
        visitNode(node);
    }

    @Override
    public void visit(Heading node) {
        String extra;

        if (node.isAtxHeading()) {
            extra = " marker:[" + node.getOpeningMarker().getStartOffset() + ", " + node.getOpeningMarker().getEndOffset() + "]";
        } else {
            extra = " marker:[" + node.getClosingMarker().getStartOffset() + ", " + node.getClosingMarker().getEndOffset() + "]";
        }
        visitNode(node, extra);
    }

    @Override
    public void visit(ThematicBreak node) {
        visitNode(node);
    }

    @Override
    public void visit(HtmlInline node) {
        visitNode(node);
    }

    @Override
    public void visit(HtmlBlock node) {
        visitNode(node);
    }

    @Override
    public void visit(Image node) {
        String extra = " open:[" + node.getTextOpeningMarker().getStartOffset() + ", " + node.getTextOpeningMarker().getEndOffset() + "]";
        extra += " close:[" + node.getLinkClosingMarker().getStartOffset() + ", " + node.getLinkClosingMarker().getEndOffset() + "]";
        visitNode(node, extra);
    }

    @Override
    public void visit(ImageRef node) {
        String extra = " open:[" + node.getTextOpeningMarker().getStartOffset() + ", " + node.getTextOpeningMarker().getEndOffset() + "]";
        extra += " close:[" + node.getReferenceClosingMarker().getStartOffset() + ", " + node.getReferenceClosingMarker().getEndOffset() + "]";
        visitNode(node, extra);
    }

    @Override
    public void visit(IndentedCodeBlock node) {
        visitNode(node);
    }

    @Override
    public void visit(Link node) {
        String extra = " open:[" + node.getTextOpeningMarker().getStartOffset() + ", " + node.getTextOpeningMarker().getEndOffset() + "]";
        extra += " close:[" + node.getLinkClosingMarker().getStartOffset() + ", " + node.getLinkClosingMarker().getEndOffset() + "]";
        visitNode(node, extra);
    }

    @Override
    public void visit(LinkRef node) {
        String extra = " open:[" + node.getTextOpeningMarker().getStartOffset() + ", " + node.getTextOpeningMarker().getEndOffset() + "]";
        extra += " close:[" + node.getReferenceClosingMarker().getStartOffset() + ", " + node.getReferenceClosingMarker().getEndOffset() + "]";
        visitNode(node, extra);
    }

    @Override
    public void visit(ListItem node) {
        String extra = " open:[" + node.getOpeningMarker().getStartOffset() + ", " + node.getOpeningMarker().getEndOffset() + "]";
        visitNode(node, extra);
    }

    @Override
    public void visit(OrderedList node) {
        visitNode(node);
    }

    @Override
    public void visit(Paragraph node) {
        visitNode(node);
    }

    @Override
    public void visit(Reference node) {
        String extra = " open:[" + node.getOpeningMarker().getStartOffset() + ", " + node.getOpeningMarker().getEndOffset() + "]";
        extra += " close:[" + node.getClosingMarker().getStartOffset() + ", " + node.getClosingMarker().getEndOffset() + "]";
        extra += " url:[" + node.getUrl().getStartOffset() + ", " + node.getUrl().getEndOffset() + "]";
        extra += " title:[" + node.getTitleOpeningMarker().getStartOffset() + ", " + node.getTitleClosingMarker().getEndOffset() + "]";
        visitNode(node, extra);
    }

    @Override
    public void visit(SoftLineBreak node) {
        visitNode(node);
    }

    @Override
    public void visit(StrongEmphasis node) {
        String extra = " open:[" + node.getOpeningMarker().getStartOffset() + ", " + node.getOpeningMarker().getEndOffset() + "]";
        extra += " close:[" + node.getClosingMarker().getStartOffset() + ", " + node.getClosingMarker().getEndOffset() + "]";
        visitNode(node, extra);
    }

    @Override
    public void visit(Text node) {
        visitNode(node);
    }

    @Override
    public void visit(CustomBlock node) {
        visitNode(node);
    }

    @Override
    public void visit(CustomNode node) {
        visitNode(node);
    }

    @Override
    public void visit(MailLink node) {
        String extra = " open:[" + node.getOpeningMarker().getStartOffset() + ", " + node.getOpeningMarker().getEndOffset() + "]";
        extra += " close:[" + node.getClosingMarker().getStartOffset() + ", " + node.getClosingMarker().getEndOffset() + "]";
        visitNode(node, extra);
    }

    @Override
    public void visit(HtmlEntity node) {
        visitNode(node);
    }

    @Override
    public void visit(AutoLink node) {
        String extra = " open:[" + node.getOpeningMarker().getStartOffset() + ", " + node.getOpeningMarker().getEndOffset() + "]";
        extra += " close:[" + node.getClosingMarker().getStartOffset() + ", " + node.getClosingMarker().getEndOffset() + "]";
        visitNode(node, extra);
    }

    @Override
    protected void visitChildren(Node node) {
        super.visitChildren(node);
    }
}
