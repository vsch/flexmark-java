package com.vladsch.flexmark.node;

/**
 * Abstract visitor that visits all children by default.
 * <p>
 * Can be used to only process certain nodes. If you override a method and want visiting to descend into children,
 * call {@link #visitChildren}.
 */
public class AbstractVisitor implements Visitor {
    public static boolean isNodeOfType(Node node, Class... classes) {
        for (Class nodeType : classes) {
            if (nodeType.isInstance(node)) return true;
        }
        return false;
    }

    public boolean isVisiting(Node node, Class... classes) {
        while (node != null) {
            if (isNodeOfType(node, classes)) return true;
            node = node.getParent();
        }

        return false;
    }

    // @formatter:off
    @Override public void visit(AutoLink node) { visitChildren(node); } 
    @Override public void visit(BlockQuote node) { visitChildren(node); } 
    @Override public void visit(BulletList node) { visitChildren(node); } 
    @Override public void visit(Code node) { visitChildren(node); } 
    @Override public void visit(CustomBlock node) { visitChildren(node); } 
    @Override public void visit(CustomNode node) { visitChildren(node); } 
    @Override public void visit(Document node) { visitChildren(node); } 
    @Override public void visit(Emphasis node) { visitChildren(node); } 
    @Override public void visit(FencedCodeBlock node) { visitChildren(node); } 
    @Override public void visit(HardLineBreak node) { visitChildren(node); } 
    @Override public void visit(Heading node) { visitChildren(node); } 
    @Override public void visit(HtmlBlock node) { visitChildren(node); } 
    @Override public void visit(HtmlEntity node) { visitChildren(node); } 
    @Override public void visit(HtmlInline node) { visitChildren(node); } 
    @Override public void visit(Image node) { visitChildren(node); } 
    @Override public void visit(ImageRef node) { visitChildren(node); } 
    @Override public void visit(IndentedCodeBlock node) { visitChildren(node); } 
    @Override public void visit(Link node) { visitChildren(node); } 
    @Override public void visit(LinkRef node) { visitChildren(node); } 
    @Override public void visit(ListItem node) { visitChildren(node); } 
    @Override public void visit(MailLink node) { visitChildren(node); } 
    @Override public void visit(OrderedList node) { visitChildren(node); } 
    @Override public void visit(Paragraph node) { visitChildren(node); } 
    @Override public void visit(Reference node) { visitChildren(node); } 
    @Override public void visit(SoftLineBreak node) { visitChildren(node); } 
    @Override public void visit(StrongEmphasis node) { visitChildren(node); } 
    @Override public void visit(Text node) { visitChildren(node); } 
    @Override public void visit(ThematicBreak node) { visitChildren(node); }
    // @formatter:on

    /**
     * Visit the child nodes.
     *
     * @param parent the parent node whose children should be visited
     */
    protected void visitChildren(Node parent) {
        Node node = parent.getFirstChild();
        while (node != null) {
            // A subclass of this visitor might modify the node, resulting in getNext returning a different node or no
            // node after visiting it. So get the next node before visiting.
            Node next = node.getNext();
            node.accept(this);
            node = next;
        }
    }
}
