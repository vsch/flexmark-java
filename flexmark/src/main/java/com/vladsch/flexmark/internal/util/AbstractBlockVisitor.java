package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.node.*;

/**
 * Abstract visitor that visits only children of blocks excluding Paragraphs
 * <p>
 * Can be used to only process block nodes efficiently skipping text. If you override a method and want visiting to descend into children,
 * call {@link #visitChildren}.
 */
public class AbstractBlockVisitor extends AbstractVisitor {
    // @formatter:off
    // blocks
    //@Override public void visit(BlockQuote node) { visitChildren(node); } 
    //@Override public void visit(BulletList node) { visitChildren(node); } 
    //@Override public void visit(BulletListItem node) { visitChildren(node); }
    //@Override public void visit(CustomBlock node) { visitChildren(node); } 
    //@Override public void visit(CustomNode node) { visitChildren(node); } 
    //@Override public void visit(Document node) { visitChildren(node); } 
    //@Override public void visit(FencedCodeBlock node) { visitChildren(node); } 
    //@Override public void visit(Heading node) { visitChildren(node); } 
    //@Override public void visit(HtmlBlock node) { visitChildren(node); } 
    //@Override public void visit(HtmlCommentBlock node) { visitChildren(node); } 
    //@Override public void visit(IndentedCodeBlock node) { visitChildren(node); } 
    //@Override public void visit(OrderedList node) { visitChildren(node); } 
    //@Override public void visit(OrderedListItem node) { visitChildren(node); } 
    @Override public void visit(Paragraph node) {  } 
    //@Override public void visit(ThematicBreak node) { visitChildren(node); }
    
    
    // inlines
    @Override public void visit(AutoLink node) {  } 
    @Override public void visit(Code node) {  } 
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
    @Override public void visit(Text node) {  } 
    
    // @formatter:on
}
