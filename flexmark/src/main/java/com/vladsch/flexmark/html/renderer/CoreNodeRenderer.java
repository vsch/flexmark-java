package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.RenderingVisitor;
import com.vladsch.flexmark.internal.ListOptions;
import com.vladsch.flexmark.internal.util.Escaping;
import com.vladsch.flexmark.internal.util.ReferenceRepository;
import com.vladsch.flexmark.internal.util.TextCollectingVisitor;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.Parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The node renderer that renders all the core nodes (comes last in the order of node renderers).
 */
public class CoreNodeRenderer extends RenderingVisitor implements NodeRenderer {
    private final ReferenceRepository referenceRepository;
    private final ListOptions listOptions;

    public CoreNodeRenderer(DataHolder options) {
        this.referenceRepository = options.get(Parser.REFERENCES);
        this.listOptions = new ListOptions(options);
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
                HtmlCommentBlock.class,
                HtmlEntity.class,
                HtmlInline.class,
                HtmlInlineComment.class,
                Image.class,
                ImageRef.class,
                IndentedCodeBlock.class,
                Link.class,
                LinkRef.class,
                BulletListItem.class,
                OrderedListItem.class,
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
    protected void render(NodeRendererContext context, HtmlWriter html, Document node) {
        // No rendering itself
        context.renderChildren(node);
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, Heading node) {
        if (context.getHtmlOptions().renderHeaderId) {
            String id = context.getNodeId(node);
            if (id != null) {
                html.attr("id", id);
            }
        }

        html.withAttr().tagLine("h" + node.getLevel(), () -> {
            context.renderChildren(node);
        });
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, BlockQuote node) {
        html.withAttr().tagIndent("blockquote", () -> {
            context.renderChildren(node);
        });
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, FencedCodeBlock node) {
        BasedSequence info = node.getInfo();
        if (info.isNotNull() && !info.isBlank()) {
            int space = info.indexOf(' ');
            BasedSequence language;
            if (space == -1) {
                language = info;
            } else {
                language = info.subSequence(0, space);
            }
            html.attr("class", "language-" + language.unescape());
        }

        html.line();
        html.tag("pre");
        html.withAttr().tag("code");
        html.text(node.getContentChars().normalizeEOL());
        html.tag("/code");
        html.tag("/pre");
        html.line();
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, ThematicBreak node) {
        html.withAttr().tagVoidLine("hr");
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, IndentedCodeBlock node) {
        html.line();
        html.tag("pre");
        html.tag("code");
        html.text(node.getContentChars().trimTailBlankLines().normalizeEndWithEOL());
        html.tag("/code");
        html.tag("/pre");
        html.line();
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, BulletList node) {
        html.withAttr().tagIndent("ul", () -> {
            context.renderChildren(node);
        });
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, OrderedList node) {
        int start = node.getStartNumber();
        if (listOptions.orderedStart && start != 1) html.attr("start", String.valueOf(start));
        html.withAttr().tagIndent("ol", () -> {
            context.renderChildren(node);
        });
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, BulletListItem node) {
        render(context, html, (ListItem) node);
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, OrderedListItem node) {
        render(context, html, (ListItem) node);
    }

    protected void render(NodeRendererContext context, HtmlWriter html, ListItem node) {
        if (node.getFirstChild() == null || listOptions.isTightListItem(node)) {
            html.withAttr().withCondIndent().tagLine("li", () -> {
                context.renderChildren(node);
            });
        } else {
            html.withAttr().tagIndent("li", () -> {
                context.renderChildren(node);
            });
        }
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, Paragraph node) {
        boolean inTightList = listOptions.isInTightListItem(node);
        if (!inTightList) {
            html.tagLine("p", () -> {
                context.renderChildren(node);
            });
        } else {
            context.renderChildren(node);
        }
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, Emphasis node) {
        html.tag("em");
        context.renderChildren(node);
        html.tag("/em");
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, StrongEmphasis node) {
        html.tag("strong");
        context.renderChildren(node);
        html.tag("/strong");
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, Text node) {
        html.text(Escaping.normalizeEOL(node.getChars().unescape()));
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, Code node) {
        html.withAttr().tag("code");
        html.text(Escaping.collapseWhitespace(node.getText(), true));
        html.tag("/code");
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, HtmlBlock node) {
        renderHtmlBlock(context, html, node, context.getHtmlOptions().suppressHtmlBlocks, context.getHtmlOptions().escapeHtmlBlocks);
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, HtmlCommentBlock node) {
        renderHtmlBlock(context, html, node, context.getHtmlOptions().suppressHtmlCommentBlocks, context.getHtmlOptions().escapeHtmlCommentBlocks);
    }

    public void renderHtmlBlock(NodeRendererContext context, HtmlWriter html, HtmlBlock node, boolean suppress, boolean escape) {
        if (suppress) return;

        html.line();
        if (escape) {
            html.text(node.getContentChars().normalizeEOL());
        } else {
            html.raw(node.getContentChars().normalizeEOL());
        }
        html.line();
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, HtmlInline node) {
        renderInlineHtml(context, html, node, context.getHtmlOptions().suppressInlineHtml, context.getHtmlOptions().escapeInlineHtml);
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, HtmlInlineComment node) {
        renderInlineHtml(context, html, node, context.getHtmlOptions().suppressInlineHtmlComments, context.getHtmlOptions().escapeInlineHtmlComments);
    }

    public void renderInlineHtml(NodeRendererContext context, HtmlWriter html, HtmlInline node, boolean suppress, boolean escape) {
        if (suppress) return;

        if (escape) {
            html.text(node.getChars().normalizeEOL());
        } else {
            html.raw(node.getChars().normalizeEOL());
        }
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, SoftLineBreak node) {
        html.raw(context.getHtmlOptions().softBreak);
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, HardLineBreak node) {
        html.tagVoid("br").line();
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, Reference node) {

    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, HtmlEntity node) {
        html.text(node.getChars().unescape());
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, AutoLink node) {
        String text = node.getText().toString();
        if (context.isDoNotRenderLinks()) {
            html.text(text);
        } else {
            html.attr("href", context.encodeUrl(text))
                    .withAttr()
                    .tag("a", () -> html.text(text));
        }
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, MailLink node) {
        String text = node.getText().unescape();
        if (context.isDoNotRenderLinks()) {
            html.text(text);
        } else {
            String url = context.encodeUrl(text);
            html.attr("href", "mailto:" + url)
                    .withAttr()
                    .tag("a")
                    .text(url)
                    .tag("/a");
        }
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, Image node) {
        if (!context.isDoNotRenderLinks()) {
            String url = context.encodeUrl(node.getUrl().unescape());

            TextCollectingVisitor altTextVisitor = new TextCollectingVisitor();
            node.accept(altTextVisitor);
            String altText = altTextVisitor.getText();

            html.attr("src", url);
            html.attr("alt", altText);
            if (node.getTitle().isNotNull()) {
                html.attr("title", node.getTitle().unescape());
            }
            html.withAttr().tagVoid("img");
        }
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, Link node) {
        if (context.isDoNotRenderLinks()) {
            context.renderChildren(node);
        } else {
            html.attr("href", context.encodeUrl(node.getUrl().unescape()));
            if (node.getTitle().isNotNull()) {
                html.attr("title", node.getTitle().unescape());
            }
            html.withAttr().tag("a");
            context.renderChildren(node);
            html.tag("/a");
        }
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, ImageRef node) {
        if (!node.isDefined()) {
            // empty ref, we treat it as text
            assert !node.isDefined();
            html.text(node.getChars().unescape());
        } else {
            if (!context.isDoNotRenderLinks()) {
                Reference reference = node.getReferenceNode(referenceRepository);
                assert reference != null;
                TextCollectingVisitor altTextVisitor = new TextCollectingVisitor();
                node.accept(altTextVisitor);
                String altText = altTextVisitor.getText();

                html.attr("src", context.encodeUrl(reference.getUrl().unescape()));
                html.attr("alt", altText);

                BasedSequence title = reference.getTitle();
                if (title.isNotNull()) {
                    html.attr("title", title.unescape());
                }

                html.withAttr().tagVoid("img");
            }
        }
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, LinkRef node) {
        if (!node.isDefined()) {
            // empty ref, we treat it as text
            assert !node.isDefined();
            html.raw("[");
            context.renderChildren(node);
            html.raw("]");

            if (!node.isReferenceTextCombined()) {
                html.raw("[");
                html.raw(node.getReference().unescape());
                html.raw("]");
            }
        } else {
            if (context.isDoNotRenderLinks()) {
                context.renderChildren(node);
            } else {
                Reference reference = node.getReferenceNode(referenceRepository);
                assert reference != null;
                String url = context.encodeUrl(reference.getUrl().unescape());
                html.attr("href", url);
                BasedSequence title = reference.getTitle();
                if (title.isNotNull()) {
                    html.attr("title", title.unescape());
                }
                html.withAttr().tag("a");
                context.renderChildren(node);
                html.tag("/a");
            }
        }
    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, CustomBlock node) {

    }

    @Override
    protected void render(NodeRendererContext context, HtmlWriter html, CustomNode node) {

    }
}
