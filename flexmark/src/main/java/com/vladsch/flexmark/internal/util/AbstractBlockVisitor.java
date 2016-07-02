package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.node.*;

/**
 * Abstract visitor that visits all children by default.
 * <p>
 * Can be used to only process certain nodes. If you override a method and want visiting to descend into children,
 * call {@link #visitChildren}.
 */
public class AbstractBlockVisitor extends AbstractVisitor {
    // @formatter:off
    //@Override public void visit(BlockQuote node) { visitChildren(node); } 
    //@Override public void visit(BulletListItem node) { visitChildren(node); }
    //@Override public void visit(CustomBlock node) { visitChildren(node); } 
    //@Override public void visit(CustomNode node) { visitChildren(node); } 
    //@Override public void visit(Document node) { visitChildren(node); } 
    //@Override public void visit(Heading node) { visitChildren(node); } 
    //@Override public void visit(HtmlBlock node) { visitChildren(node); } 
    //@Override public void visit(HtmlCommentBlock node) { visitChildren(node); } 
    //@Override public void visit(OrderedList node) { visitChildren(node); } 
    //@Override public void visit(OrderedListItem node) { visitChildren(node); } 
    @Override public void visit(AutoLink node) {  } 
    @Override public void visit(BulletList node) {  } 
    @Override public void visit(Code node) {  } 
    @Override public void visit(Emphasis node) {  } 
    @Override public void visit(FencedCodeBlock node) {  } 
    @Override public void visit(HardLineBreak node) {  } 
    @Override public void visit(HtmlEntity node) {  } 
    @Override public void visit(HtmlInline node) {  } 
    @Override public void visit(HtmlInlineComment node) {  } 
    @Override public void visit(Image node) {  } 
    @Override public void visit(ImageRef node) {  } 
    @Override public void visit(IndentedCodeBlock node) {  } 
    @Override public void visit(Link node) {  } 
    @Override public void visit(LinkRef node) {  }
    @Override public void visit(MailLink node) {  } 
    @Override public void visit(Paragraph node) {  } 
    @Override public void visit(Reference node) {  } 
    @Override public void visit(SoftLineBreak node) {  } 
    @Override public void visit(StrongEmphasis node) {  } 
    @Override public void visit(Text node) {  } 
    @Override public void visit(ThematicBreak node) {  }
    // @formatter:on
}
