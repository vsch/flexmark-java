package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.Escaping;
import com.vladsch.flexmark.node.*;

import java.util.*;

/**
 * The node renderer that renders all the core nodes (comes last in the order of node renderers).
 */
public class CoreNodeRenderer extends AbstractVisitor implements NodeRenderer {

    protected final NodeRendererContext context;
    private final HtmlWriter html;

    public CoreNodeRenderer(NodeRendererContext context) {
        this.context = context;
        this.html = context.getHtmlWriter();
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<Class<? extends Node>>(Arrays.asList(
                AutoLink.class,
                BlockQuote.class,
                BulletList.class,
                Code.class,
                CustomBlock.class,
                //CustomNode.class,
                Document.class,
                Emphasis.class,
                FencedCodeBlock.class,
                HardLineBreak.class,
                Heading.class,
                HtmlBlock.class,
                HtmlEntity.class,
                HtmlInline.class,
                Image.class,
                ImageRef.class,
                IndentedCodeBlock.class,
                Link.class,
                LinkRef.class,
                ListItem.class,
                MailLink.class,
                OrderedList.class,
                Paragraph.class,
                Reference.class,
                SoftLineBreak.class,
                StrongEmphasis.class,
                Text.class,
                ThematicBreak.class
        ));
    }

    @Override
    public void render(Node node) {
        node.accept(this);
    }

    @Override
    public void visit(Document node) {
        // No rendering itself
        visitChildren(node);
    }

    @Override
    public void visit(Heading node) {
        html.withAttr().tagLine("h" + node.getLevel(), () -> {
            visitChildren(node);
        });
    }

    @Override
    public void visit(Paragraph node) {
        boolean inTightList = isInTightList(node);
        if (!inTightList) {
            html.tagLine("p", () -> {
                visitChildren(node);
            });
        } else {
            visitChildren(node);
        }
    }

    @Override
    public void visit(BlockQuote node) {
        html.withAttr().tagIndent("blockquote", () -> {
            visitChildren(node);
        });
    }

    @Override
    public void visit(BulletList node) {
        html.withAttr().tagIndent("ul", () -> {
            visitChildren(node);
        });
    }

    @Override
    public void visit(FencedCodeBlock node) {
        String literal = node.getContentChars().toString();
        BasedSequence info = node.getInfo();
        if (info.isNotNull() && !info.isBlank()) {
            int space = info.indexOf(' ');
            BasedSequence language;
            if (space == -1) {
                language = info;
            } else {
                language = info.subSequence(0, space);
            }
            html.attr("class", "language-" + language.unescaped());
        }

        html.line();
        html.tag("pre");
        html.withAttr().tag("code");
        html.text(Escaping.normalizeEOL(literal));
        html.tag("/code");
        html.tag("/pre");
        html.line();
    }

    @Override
    public void visit(HtmlBlock node) {
        html.line();
        if (context.shouldEscapeHtml()) {
            html.text(Escaping.normalizeEOL(node.getContentChars()));
        } else {
            html.raw(Escaping.normalizeEOL(node.getContentChars()));
        }
        html.line();
    }

    @Override
    public void visit(ThematicBreak node) {
        //html.line();
        //html.tag("hr", getAttrs(node), true);
        //html.line();
        html.withAttr().tagVoidLine("hr");
    }

    @Override
    public void visit(IndentedCodeBlock node) {
        BasedSequence chars = node.getContentChars();
        String content = chars.trimTailBlankLines().toString();
        if (!content.endsWith("\n")) {
            content += "\n";
        }
        html.line();
        html.tag("pre");
        html.tag("code");
        html.text(Escaping.normalizeEOL(content));
        html.tag("/code");
        html.tag("/pre");
        html.line();
    }

    @Override
    public void visit(ListItem node) {
        if (node.getFirstChild() == null || isTightList(node.getParent())) {
            html.withCondIndent().tagLine("li", () -> {
                visitChildren(node);
            });
        } else {
            html.tagIndent("li", () -> {
                visitChildren(node);
            });
        }
    }

    @Override
    public void visit(OrderedList node) {
        //int start = node.getStartNumber();
        //Map<String, String> attrs = new LinkedHashMap<>();
        //if (start != 1) {
        //    attrs.put("start", String.valueOf(start));
        //}
        //renderListBlock(node, "ol", getAttrs(node, attrs));
        int start = node.getStartNumber();
        if (start != 1) html.attr("start", String.valueOf(start));
        html.withAttr().tagIndent("ol", () -> {
            visitChildren(node);
        });
    }

    @Override
    public void visit(Emphasis node) {
        html.tag("em");
        visitChildren(node);
        html.tag("/em");
    }

    @Override
    public void visit(StrongEmphasis node) {
        html.tag("strong");
        visitChildren(node);
        html.tag("/strong");
    }

    @Override
    public void visit(Text node) {
        html.text(Escaping.normalizeEOL(node.getChars().unescaped()));
    }

    @Override
    public void visit(Code node) {
        html.withAttr().tag("code");
        html.text(Escaping.collapseWhitespace(node.getText(), true));
        html.tag("/code");
    }

    @Override
    public void visit(HtmlInline node) {
        if (context.shouldEscapeHtml()) {
            html.text(Escaping.normalizeEOL(node.getChars()));
        } else {
            html.raw(Escaping.normalizeEOL(node.getChars()));
        }
    }

    @Override
    public void visit(SoftLineBreak node) {
        html.raw(context.getSoftbreak());
    }

    @Override
    public void visit(HardLineBreak node) {
        html.tagVoid("br").line();
    }

    @Override
    public void visit(Reference node) {

    }

    @Override
    public void visit(HtmlEntity node) {
        html.text(node.getChars().unescaped());
    }

    @Override
    public void visit(AutoLink node) {
        //Map<String, String> attrs = new LinkedHashMap<>();
        //String text = node.getText().toString();
        //String url = context.encodeUrl(text);
        //attrs.put("href", url);
        //html.tag("a", getAttrs(node, attrs));
        //html.text(text);
        //html.tag("/a");
        String text = node.getText().toString();
        html.attr("href", context.encodeUrl(text))
                .withAttr()
                .tag("a", () -> {
                    html.text(text);
                });
    }

    @Override
    public void visit(MailLink node) {
        //Map<String, String> attrs = new LinkedHashMap<>();
        //String url = context.encodeUrl(node.getText().unescaped());
        //attrs.put("href", "mailto:" + url);
        //html.tag("a", getAttrs(node, attrs));
        //html.text(url);
        //html.tag("/a");
        String url = context.encodeUrl(node.getText().unescaped());
        html.attr("href", "mailto:" + url)
                .withAttr()
                .tag("a")
                .text(url)
                .tag("/a");
    }

    @Override
    public void visit(Image node) {
        String url = context.encodeUrl(node.getUrl().unescaped());

        AltTextVisitor altTextVisitor = new AltTextVisitor();
        node.accept(altTextVisitor);
        String altText = altTextVisitor.getAltText();

        html.attr("src", url);
        html.attr("alt", altText);
        if (node.getTitle().isNotNull()) {
            html.attr("title", Escaping.unescapeString(node.getTitle().toString()));
        }
        html.withAttr().tagVoid("img");
    }

    @Override
    public void visit(Link node) {
        html.attr("href", context.encodeUrl(node.getUrl().unescaped()));
        if (node.getTitle().isNotNull()) {
            html.attr("title", Escaping.unescapeString(node.getTitle().toString()));
        }
        html.withAttr().tag("a");
        visitChildren(node);
        html.tag("/a");
    }

    @Override
    public void visit(ImageRef node) {
        Reference reference = node.getReferenceNode();
        if (reference == null) {
            // empty ref, we treat it as text
            html.text(node.getChars().unescaped());
        } else {
            AltTextVisitor altTextVisitor = new AltTextVisitor();
            node.accept(altTextVisitor);
            String altText = altTextVisitor.getAltText();

            html.attr("src", context.encodeUrl(reference.getUrl().unescaped()));
            html.attr("alt", altText);

            BasedSequence title = reference.getTitle();
            if (title.isNotNull()) {
                html.attr("title", title.unescaped());
            }

            html.withAttr().tagVoid("img");
        }
    }

    @Override
    public void visit(LinkRef node) {
        Reference reference = node.getReferenceNode();
        if (reference == null) {
            // empty ref, we treat it as text
            html.raw("[");
            visitChildren(node);
            html.raw("]");

            if (!node.isReferenceTextCombined()) {
                html.raw("[");
                html.raw(node.getReference().unescaped());
                html.raw("]");
            }
        } else {
            String url = context.encodeUrl(reference.getUrl().unescaped());
            html.attr("href", url);
            BasedSequence title = reference.getTitle();
            if (title.isNotNull()) {
                html.attr("title", title.unescaped());
            }
            html.withAttr().tag("a");
            visitChildren(node);
            html.tag("/a");
        }
    }

    @Override
    public void visit(CustomBlock node) {

    }

    @Override
    public void visit(CustomNode node) {

    }

    @Override
    protected void visitChildren(Node parent) {
        context.renderChildren(parent);
        //Node node = parent.getFirstChild();
        //while (node != null) {
        //    Node next = node.getNext();
        //    context.render(node);
        //    node = next;
        //}
    }

    //private void renderListBlock(ListBlock listBlock, String tagName) {
    //    //html.line();
    //    //html.tag(tagName, attributes);
    //    //html.line();
    //    //visitChildren(listBlock);
    //    //html.line();
    //    //html.tag('/' + tagName);
    //    //html.line();
    //}

    private boolean isInTightList(Paragraph node) {
        Node parent = node.getParent();
        if (parent != null) {
            Node gramps = parent.getParent();
            return isTightList(gramps);
        }
        return false;
    }

    private Boolean isTightList(Node node) {
        if (node != null && node instanceof ListBlock) {
            ListBlock list = (ListBlock) node;
            return list.isTight();
        }
        return false;
    }

    private Map<String, String> getAttrs(Node node) {
        return context.extendAttributes(node, Collections.<String, String>emptyMap());
    }

    private Map<String, String> getAttrs(Node node, Map<String, String> defaultAttributes) {
        return context.extendAttributes(node, defaultAttributes);
    }

    private static class AltTextVisitor extends AbstractVisitor {

        private final StringBuilder sb = new StringBuilder();

        String getAltText() {
            return sb.toString();
        }

        @Override
        public void visit(Text node) {
            sb.append(node.getChars());
        }

        @Override
        public void visit(HtmlEntity node) {
            sb.append(Escaping.unescapeString(node.getChars().toString()));
        }

        @Override
        public void visit(SoftLineBreak node) {
            sb.append('\n');
        }

        @Override
        public void visit(HardLineBreak node) {
            sb.append('\n');
        }
    }
}
