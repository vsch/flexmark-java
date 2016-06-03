package org.commonmark.html.renderer;

import org.commonmark.html.HtmlWriter;
import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.Escaping;
import org.commonmark.internal.util.SubSequence;
import org.commonmark.node.*;

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
    public void visit(Document document) {
        // No rendering itself
        visitChildren(document);
    }

    @Override
    public void visit(Heading heading) {
        String htag = "h" + heading.getLevel();
        html.line();
        html.tag(htag, getAttrs(heading));
        visitChildren(heading);
        html.tag('/' + htag);
        html.line();
    }

    @Override
    public void visit(Paragraph paragraph) {
        boolean inTightList = isInTightList(paragraph);
        if (!inTightList) {
            html.line();
            html.tag("p", getAttrs(paragraph));
        }
        visitChildren(paragraph);
        if (!inTightList) {
            html.tag("/p");
            html.line();
        }
    }

    @Override
    public void visit(BlockQuote blockQuote) {
        html.line();
        html.tag("blockquote", getAttrs(blockQuote));
        html.line();
        visitChildren(blockQuote);
        html.line();
        html.tag("/blockquote");
        html.line();
    }

    @Override
    public void visit(BulletList bulletList) {
        renderListBlock(bulletList, "ul", getAttrs(bulletList));
    }

    @Override
    public void visit(FencedCodeBlock fencedCodeBlock) {
        String literal = fencedCodeBlock.getContentChars().toString();
        Map<String, String> attributes = new LinkedHashMap<>();
        BasedSequence info = fencedCodeBlock.getInfo();
        if (info != null && !info.isBlank()) {
            int space = info.countLeadingNot(" ");
            String language;
            if (space == -1) {
                language = info.toString();
            } else {
                language = info.subSequence(0, space).toString();
            }
            attributes.put("class", "language-" + language);
        }
        renderCodeBlock(literal, getAttrs(fencedCodeBlock, attributes));
    }

    @Override
    public void visit(HtmlBlock htmlBlock) {
        html.line();
        if (context.shouldEscapeHtml()) {
            html.text(htmlBlock.getContentChars().toString());
        } else {
            html.raw(htmlBlock.getChars().toString());
        }
        html.line();
    }

    @Override
    public void visit(ThematicBreak thematicBreak) {
        html.line();
        html.tag("hr", getAttrs(thematicBreak), true);
        html.line();
    }

    @Override
    public void visit(IndentedCodeBlock indentedCodeBlock) {
        String content = indentedCodeBlock.getContentChars().toString();
        if (!content.endsWith("\n")) {
            content += "\n";
        }
        renderCodeBlock(content, getAttrs(indentedCodeBlock));
    }

    @Override
    public void visit(Link link) {
        Map<String, String> attrs = new LinkedHashMap<>();
        String url = context.encodeUrl(link.getUrl().toString());
        attrs.put("href", url);
        if (link.getTitle() != SubSequence.EMPTY) {
            attrs.put("title", link.getTitle().toString());
        }
        html.tag("a", getAttrs(link, attrs));
        visitChildren(link);
        html.tag("/a");
    }

    @Override
    public void visit(ListItem listItem) {
        html.tag("li", getAttrs(listItem));
        visitChildren(listItem);
        html.tag("/li");
        html.line();
    }

    @Override
    public void visit(OrderedList orderedList) {
        int start = orderedList.getStartNumber();
        Map<String, String> attrs = new LinkedHashMap<>();
        if (start != 1) {
            attrs.put("start", String.valueOf(start));
        }
        renderListBlock(orderedList, "ol", getAttrs(orderedList, attrs));
    }

    @Override
    public void visit(Image image) {
        String url = context.encodeUrl(image.getUrl().toString());

        AltTextVisitor altTextVisitor = new AltTextVisitor();
        image.accept(altTextVisitor);
        String altText = altTextVisitor.getAltText();

        Map<String, String> attrs = new LinkedHashMap<>();
        attrs.put("src", url);
        attrs.put("alt", altText);
        if (image.getTitle() != SubSequence.EMPTY) {
            attrs.put("title", image.getTitle().toString());
        }

        html.tag("img", getAttrs(image, attrs), true);
    }

    @Override
    public void visit(Emphasis emphasis) {
        html.tag("em");
        visitChildren(emphasis);
        html.tag("/em");
    }

    @Override
    public void visit(StrongEmphasis strongEmphasis) {
        html.tag("strong");
        visitChildren(strongEmphasis);
        html.tag("/strong");
    }

    @Override
    public void visit(Text text) {
        html.text(text.getChars().toString());
    }

    @Override
    public void visit(Code code) {
        html.tag("code");
        html.text(code.getContent().toString());
        html.tag("/code");
    }

    @Override
    public void visit(HtmlInline htmlInline) {
        if (context.shouldEscapeHtml()) {
            html.text(htmlInline.getChars().toString());
        } else {
            html.raw(htmlInline.getChars().toString());
        }
    }

    @Override
    public void visit(SoftLineBreak softLineBreak) {
        html.raw(context.getSoftbreak());
    }

    @Override
    public void visit(HardLineBreak hardLineBreak) {
        html.tag("br", null, true);
        html.line();
    }

    @Override
    public void visit(Reference reference) {

    }

    @Override
    public void visit(MailLink mailLink) {
        Map<String, String> attrs = new LinkedHashMap<>();
        String url = context.encodeUrl(mailLink.getContent().toString());
        attrs.put("href", "mailto:" + url);
        html.tag("a", getAttrs(mailLink, attrs));
        visitChildren(mailLink);
        html.tag("/a");
    }

    @Override
    public void visit(HtmlEntity htmlEntity) {
        html.raw(Escaping.unescapeString(htmlEntity.getChars().toString()));
    }

    @Override
    public void visit(AutoLink autoLink) {
        Map<String, String> attrs = new LinkedHashMap<>();
        String url = context.encodeUrl(autoLink.getContent().toString());
        attrs.put("href", url);
        html.tag("a", getAttrs(autoLink, attrs));
        visitChildren(autoLink);
        html.tag("/a");
    }

    @Override
    public void visit(ImageRef image) {
        String url = context.encodeUrl(image.getLinkUrl());

        Map<String, String> attrs = new LinkedHashMap<>();
        attrs.put("src", url);
        attrs.put("alt", image.getLinkText());
        if (image.getLinkTitle() != null) {
            attrs.put("title", image.getLinkTitle());
        }

        html.tag("img", getAttrs(image, attrs), true);
    }

    @Override
    public void visit(LinkRef link) {
        Map<String, String> attrs = new LinkedHashMap<>();
        String url = context.encodeUrl(link.getLinkUrl());
        attrs.put("href", url);
        if (link.getLinkTitle() != null) {
            attrs.put("title", link.getLinkTitle());
        }
        html.tag("a", getAttrs(link, attrs));
        visitChildren(link);
        html.tag("/a");
    }

    @Override
    public void visit(CustomBlock customBlock) {

    }

    @Override
    public void visit(CustomNode customNode) {

    }

    @Override
    protected void visitChildren(Node parent) {
        Node node = parent.getFirstChild();
        while (node != null) {
            Node next = node.getNext();
            context.render(node);
            node = next;
        }
    }

    private void renderCodeBlock(String literal, Map<String, String> attributes) {
        html.line();
        html.tag("pre");
        html.tag("code", attributes);
        html.text(literal);
        html.tag("/code");
        html.tag("/pre");
        html.line();
    }

    private void renderListBlock(ListBlock listBlock, String tagName, Map<String, String> attributes) {
        html.line();
        html.tag(tagName, attributes);
        html.line();
        visitChildren(listBlock);
        html.line();
        html.tag('/' + tagName);
        html.line();
    }

    private boolean isInTightList(Paragraph paragraph) {
        Node parent = paragraph.getParent();
        if (parent != null) {
            Node gramps = parent.getParent();
            if (gramps != null && gramps instanceof ListBlock) {
                ListBlock list = (ListBlock) gramps;
                return list.isTight();
            }
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
        public void visit(Text text) {
            sb.append(text.getChars());
        }

        @Override
        public void visit(SoftLineBreak softLineBreak) {
            sb.append('\n');
        }

        @Override
        public void visit(HardLineBreak hardLineBreak) {
            sb.append('\n');
        }
    }
}
