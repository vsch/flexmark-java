package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.node.*;

/**
 * Abstract rendering visitor that renders nothing by default
 * <p>
 * Can be used to only render certain nodes and ignore others. If you want to render children then call {@link NodeRendererContext#renderChildren(Node)}.
 */
public class RenderingVisitor implements Visitor {
    private NodeRendererContext myContext;
    private HtmlWriter myHtmlWriter;

    public RenderingVisitor() {
    }

    public void render(NodeRendererContext context, HtmlWriter htmlWriter, Node node) {
        myContext = context;
        myHtmlWriter = htmlWriter;
        node.accept(this);
    }

    // @formatter:off
    protected void render(NodeRendererContext context, HtmlWriter html, AutoLink node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, BlockQuote node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, BulletList node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, Code node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, CustomBlock node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, CustomNode node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, Document node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, Emphasis node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, FencedCodeBlock node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, HardLineBreak node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, Heading node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, HtmlBlock node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, HtmlCommentBlock node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, HtmlEntity node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, HtmlInline node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, HtmlInlineComment node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, Image node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, ImageRef node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, IndentedCodeBlock node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, Link node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, LinkRef node) {  }
    protected void render(NodeRendererContext context, HtmlWriter html, BulletListItem node) { }
    protected void render(NodeRendererContext context, HtmlWriter html, OrderedListItem node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, MailLink node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, OrderedList node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, Paragraph node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, Reference node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, SoftLineBreak node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, StrongEmphasis node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, Text node) {  } 
    protected void render(NodeRendererContext context, HtmlWriter html, ThematicBreak node) {  }
    
    @Override final public void visit(AutoLink node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(BlockQuote node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(BulletList node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(Code node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(CustomBlock node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(CustomNode node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(Document node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(Emphasis node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(FencedCodeBlock node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(HardLineBreak node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(Heading node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(HtmlBlock node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(HtmlCommentBlock node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(HtmlEntity node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(HtmlInline node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(HtmlInlineComment node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(Image node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(ImageRef node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(IndentedCodeBlock node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(Link node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(LinkRef node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(BulletListItem node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(OrderedListItem node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(MailLink node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(OrderedList node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(Paragraph node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(Reference node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(SoftLineBreak node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(StrongEmphasis node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(Text node) { render(myContext, myHtmlWriter, node); }
    @Override final public void visit(ThematicBreak node) { render(myContext, myHtmlWriter, node); }
    // @formatter:on
}
