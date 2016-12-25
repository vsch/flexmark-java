package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.ast.util.TextCollectingVisitor;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.vladsch.flexmark.util.sequence.BasedSequence.NULL;

/**
 * The node renderer that renders all the core nodes (comes last in the order of node renderers).
 */
public class CoreNodeRenderer implements NodeRenderer {
    public static final AttributablePart LOOSE_LIST_ITEM = new AttributablePart("LOOSE_LIST_ITEM");
    public static final AttributablePart TIGHT_LIST_ITEM = new AttributablePart("TIGHT_LIST_ITEM");
    public static final AttributablePart PARAGRAPH_LINE = new AttributablePart("PARAGRAPH_LINE");
    public static final AttributablePart CODE_CONTENT = new AttributablePart("FENCED_CODE_CONTENT");

    private final ReferenceRepository referenceRepository;
    private final ListOptions listOptions;

    public CoreNodeRenderer(DataHolder options) {
        this.referenceRepository = options.get(Parser.REFERENCES);
        this.listOptions = new ListOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(AutoLink.class, new CustomNodeRenderer<AutoLink>() {
                    @Override
                    public void render(AutoLink node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(BlockQuote.class, new CustomNodeRenderer<BlockQuote>() {
                    @Override
                    public void render(BlockQuote node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(BulletList.class, new CustomNodeRenderer<BulletList>() {
                    @Override
                    public void render(BulletList node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(Code.class, new CustomNodeRenderer<Code>() {
                    @Override
                    public void render(Code node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(Document.class, new CustomNodeRenderer<Document>() {
                    @Override
                    public void render(Document node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(Emphasis.class, new CustomNodeRenderer<Emphasis>() {
                    @Override
                    public void render(Emphasis node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(FencedCodeBlock.class, new CustomNodeRenderer<FencedCodeBlock>() {
                    @Override
                    public void render(FencedCodeBlock node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(HardLineBreak.class, new CustomNodeRenderer<HardLineBreak>() {
                    @Override
                    public void render(HardLineBreak node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(Heading.class, new CustomNodeRenderer<Heading>() {
                    @Override
                    public void render(Heading node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(HtmlBlock.class, new CustomNodeRenderer<HtmlBlock>() {
                    @Override
                    public void render(HtmlBlock node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(HtmlCommentBlock.class, new CustomNodeRenderer<HtmlCommentBlock>() {
                    @Override
                    public void render(HtmlCommentBlock node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(HtmlInnerBlock.class, new CustomNodeRenderer<HtmlInnerBlock>() {
                    @Override
                    public void render(HtmlInnerBlock node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(HtmlInnerBlockComment.class, new CustomNodeRenderer<HtmlInnerBlockComment>() {
                    @Override
                    public void render(HtmlInnerBlockComment node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(HtmlEntity.class, new CustomNodeRenderer<HtmlEntity>() {
                    @Override
                    public void render(HtmlEntity node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(HtmlInline.class, new CustomNodeRenderer<HtmlInline>() {
                    @Override
                    public void render(HtmlInline node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(HtmlInlineComment.class, new CustomNodeRenderer<HtmlInlineComment>() {
                    @Override
                    public void render(HtmlInlineComment node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(Image.class, new CustomNodeRenderer<Image>() {
                    @Override
                    public void render(Image node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(ImageRef.class, new CustomNodeRenderer<ImageRef>() {
                    @Override
                    public void render(ImageRef node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(IndentedCodeBlock.class, new CustomNodeRenderer<IndentedCodeBlock>() {
                    @Override
                    public void render(IndentedCodeBlock node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(Link.class, new CustomNodeRenderer<Link>() {
                    @Override
                    public void render(Link node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(LinkRef.class, new CustomNodeRenderer<LinkRef>() {
                    @Override
                    public void render(LinkRef node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(BulletListItem.class, new CustomNodeRenderer<BulletListItem>() {
                    @Override
                    public void render(BulletListItem node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(OrderedListItem.class, new CustomNodeRenderer<OrderedListItem>() {
                    @Override
                    public void render(OrderedListItem node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(MailLink.class, new CustomNodeRenderer<MailLink>() {
                    @Override
                    public void render(MailLink node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(OrderedList.class, new CustomNodeRenderer<OrderedList>() {
                    @Override
                    public void render(OrderedList node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(Paragraph.class, new CustomNodeRenderer<Paragraph>() {
                    @Override
                    public void render(Paragraph node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(Reference.class, new CustomNodeRenderer<Reference>() {
                    @Override
                    public void render(Reference node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(SoftLineBreak.class, new CustomNodeRenderer<SoftLineBreak>() {
                    @Override
                    public void render(SoftLineBreak node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(StrongEmphasis.class, new CustomNodeRenderer<StrongEmphasis>() {
                    @Override
                    public void render(StrongEmphasis node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(Text.class, new CustomNodeRenderer<Text>() {
                    @Override
                    public void render(Text node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(TextBase.class, new CustomNodeRenderer<TextBase>() {
                    @Override
                    public void render(TextBase node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(ThematicBreak.class, new CustomNodeRenderer<ThematicBreak>() {
                    @Override
                    public void render(ThematicBreak node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                })
        ));
    }

    private void render(Document node, NodeRendererContext context, HtmlWriter html) {
        // No rendering itself
        context.renderChildren(node);
    }

    private void render(final Heading node, final NodeRendererContext context, final HtmlWriter html) {
        if (context.getHtmlOptions().renderHeaderId) {
            String id = context.getNodeId(node);
            if (id != null) {
                html.attr("id", id);
            }
        }

        if (context.getHtmlOptions().sourcePositionParagraphLines) {
            html.srcPos(node.getChars()).withAttr().tagLine("h" + node.getLevel(), new Runnable() {
                @Override
                public void run() {
                    html.srcPos(node.getText()).withAttr().tag("span");
                    context.renderChildren(node);
                    html.tag("/span");
                }
            });
        } else {
            html.srcPos(node.getText()).withAttr().tagLine("h" + node.getLevel(), new Runnable() {
                @Override
                public void run() {
                    context.renderChildren(node);
                }
            });
        }
    }

    private void render(final BlockQuote node, final NodeRendererContext context, final HtmlWriter html) {
        html.withAttr().tagLineIndent("blockquote", new Runnable() {
            @Override
            public void run() {
                context.renderChildren(node);
            }
        });
    }

    private void render(FencedCodeBlock node, NodeRendererContext context, HtmlWriter html) {
        html.line();
        html.srcPosWithTrailingEOL(node.getChars()).withAttr().tag("pre").openPre();

        BasedSequence info = node.getInfo();
        if (info.isNotNull() && !info.isBlank()) {
            int space = info.indexOf(' ');
            BasedSequence language;
            if (space == -1) {
                language = info;
            } else {
                language = info.subSequence(0, space);
            }
            html.attr("class", context.getHtmlOptions().languageClassPrefix + language.unescape());
        }

        html.srcPosWithEOL(node.getContentChars()).withAttr(CODE_CONTENT).tag("code");
        html.text(node.getContentChars().normalizeEOL());
        html.tag("/code");
        html.tag("/pre").closePre();
        html.line();
    }

    private void render(ThematicBreak node, NodeRendererContext context, HtmlWriter html) {
        html.srcPos(node.getChars()).withAttr().tagVoidLine("hr");
    }

    private void render(IndentedCodeBlock node, NodeRendererContext context, HtmlWriter html) {
        html.line();
        html.srcPosWithEOL(node.getChars()).withAttr().tag("pre").openPre();
        html.srcPosWithEOL(node.getContentChars()).withAttr(CODE_CONTENT).tag("code");
        html.text(node.getContentChars().trimTailBlankLines().normalizeEndWithEOL());
        html.tag("/code");
        html.tag("/pre").closePre();
        html.line();
    }

    private void render(final BulletList node, final NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tagIndent("ul", new Runnable() {
            @Override
            public void run() {
                context.renderChildren(node);
            }
        });
    }

    private void render(final OrderedList node, final NodeRendererContext context, HtmlWriter html) {
        int start = node.getStartNumber();
        if (listOptions.isOrderedListManualStart() && start != 1) html.attr("start", String.valueOf(start));
        html.withAttr().tagIndent("ol", new Runnable() {
            @Override
            public void run() {
                context.renderChildren(node);
            }
        });
    }

    private void render(BulletListItem node, NodeRendererContext context, HtmlWriter html) {
        renderListItem(node, context, html);
    }

    private void render(OrderedListItem node, NodeRendererContext context, HtmlWriter html) {
        renderListItem(node, context, html);
    }

    private void renderListItem(final ListItem node, final NodeRendererContext context, HtmlWriter html) {
        if (listOptions.isTightListItem(node)) {
            html.srcPosWithEOL(node.getChars()).withAttr(TIGHT_LIST_ITEM).withCondIndent().tagLine("li", new Runnable() {
                @Override
                public void run() {
                    context.renderChildren(node);
                }
            });
        } else {
            html.srcPosWithEOL(node.getChars()).withAttr(LOOSE_LIST_ITEM).tagIndent("li", new Runnable() {
                @Override
                public void run() {
                    context.renderChildren(node);
                }
            });
        }
    }

    public static void renderTextBlockParagraphLines(Node node, NodeRendererContext context, HtmlWriter html) {
        if (context.getHtmlOptions().sourcePositionParagraphLines) {
            BasedSequence nextLine = getSoftLineBreakSpan(node.getFirstChild());
            if (nextLine.isNotNull()) {
                html.srcPos(nextLine).withAttr(PARAGRAPH_LINE).tag("span");
                context.renderChildren(node);
                html.tag("/span");
                return;
            }
        }
        context.renderChildren(node);
    }

    private void render(final Paragraph node, final NodeRendererContext context, final HtmlWriter html) {
        boolean inTightList = listOptions.isInTightListItem(node);
        if (!inTightList && (!(node.getParent() instanceof ListItem) || !((ListItem) node.getParent()).isParagraphWrappingDisabled())) {
            html.srcPosWithEOL(node.getChars()).withAttr().tagLine("p", new Runnable() {
                @Override
                public void run() {
                    renderTextBlockParagraphLines(node, context, html);
                }
            });
        } else {
            renderTextBlockParagraphLines(node, context, html);
        }
    }

    public static BasedSequence getSoftLineBreakSpan(Node node) {
        if (node == null) return NULL;

        Node lastNode = node;
        Node nextNode = node.getNext();

        while (nextNode != null && !(nextNode instanceof SoftLineBreak)) {
            lastNode = nextNode;
            nextNode = nextNode.getNext();
        }

        return Node.spanningChars(node.getChars(), lastNode.getChars());
    }

    private void render(SoftLineBreak node, NodeRendererContext context, HtmlWriter html) {
        if (context.getHtmlOptions().sourcePositionParagraphLines) {
            BasedSequence nextLine = getSoftLineBreakSpan(node.getNext());
            if (nextLine.isNotNull()) {
                html.tag("/span");
                html.raw(context.getHtmlOptions().softBreak);
                html.srcPos(nextLine).withAttr(PARAGRAPH_LINE).tag("span");
                return;
            }
        }
        html.raw(context.getHtmlOptions().softBreak);
    }

    private void render(HardLineBreak node, NodeRendererContext context, HtmlWriter html) {
        html.raw(context.getHtmlOptions().hardBreak);
    }

    private void render(Emphasis node, NodeRendererContext context, HtmlWriter html) {
        html.srcPos(node.getText()).withAttr().tag("em");
        context.renderChildren(node);
        html.tag("/em");
    }

    private void render(StrongEmphasis node, NodeRendererContext context, HtmlWriter html) {
        html.srcPos(node.getText()).withAttr().tag("strong");
        context.renderChildren(node);
        html.tag("/strong");
    }

    private void render(Text node, NodeRendererContext context, HtmlWriter html) {
        html.text(Escaping.normalizeEOL(node.getChars().unescape()));
    }

    private void render(TextBase node, NodeRendererContext context, HtmlWriter html) {
        context.renderChildren(node);
    }

    private void render(Code node, NodeRendererContext context, HtmlWriter html) {
        html.srcPos(node.getText()).withAttr().tag("code");
        html.text(Escaping.collapseWhitespace(node.getText(), true));
        html.tag("/code");
    }

    private void render(HtmlBlock node, NodeRendererContext context, HtmlWriter html) {
        if (context.getHtmlOptions().sourceWrapHtmlBlocks) {
            html.line().srcPos(node.getChars()).withAttr(AttributablePart.NODE_POSITION).tag("div").indent().line();
        }

        if (node.hasChildren()) {
            // inner blocks handle rendering
            context.renderChildren(node);
        } else {
            renderHtmlBlock(node, context, html, context.getHtmlOptions().suppressHtmlBlocks, context.getHtmlOptions().escapeHtmlBlocks);
        }

        if (context.getHtmlOptions().sourceWrapHtmlBlocks) {
            html.unIndent().tag("/div").line();
        }
    }

    private void render(HtmlCommentBlock node, NodeRendererContext context, HtmlWriter html) {
        renderHtmlBlock(node, context, html, context.getHtmlOptions().suppressHtmlCommentBlocks, context.getHtmlOptions().escapeHtmlCommentBlocks);
    }

    private void render(HtmlInnerBlock node, NodeRendererContext context, HtmlWriter html) {
        renderHtmlBlock(node, context, html, context.getHtmlOptions().suppressHtmlBlocks, context.getHtmlOptions().escapeHtmlBlocks);
    }

    private void render(HtmlInnerBlockComment node, NodeRendererContext context, HtmlWriter html) {
        renderHtmlBlock(node, context, html, context.getHtmlOptions().suppressHtmlCommentBlocks, context.getHtmlOptions().escapeHtmlCommentBlocks);
    }

    public void renderHtmlBlock(HtmlBlockBase node, NodeRendererContext context, HtmlWriter html, boolean suppress, boolean escape) {
        if (suppress) return;

        html.line();
        if (escape) {
            html.text(node.getContentChars().normalizeEOL());
        } else {
            html.rawIndentedPre(node.getContentChars().normalizeEOL());
        }
        html.line();
    }

    private void render(HtmlInline node, NodeRendererContext context, HtmlWriter html) {
        //if (context.getHtmlOptions().sourceWrapInlineHtml) {
        //    html.srcPos(node.getChars()).withAttr(AttributablePart.NODE_POSITION).tag("span");
        //}
        renderInlineHtml(node, context, html, context.getHtmlOptions().suppressInlineHtml, context.getHtmlOptions().escapeInlineHtml);
        //if (context.getHtmlOptions().sourceWrapInlineHtml) {
        //    html.tag("/span");
        //}
    }

    private void render(HtmlInlineComment node, NodeRendererContext context, HtmlWriter html) {
        renderInlineHtml(node, context, html, context.getHtmlOptions().suppressInlineHtmlComments, context.getHtmlOptions().escapeInlineHtmlComments);
    }

    public void renderInlineHtml(HtmlInlineBase node, NodeRendererContext context, HtmlWriter html, boolean suppress, boolean escape) {
        if (suppress) return;

        if (escape) {
            html.text(node.getChars().normalizeEOL());
        } else {
            html.rawPre(node.getChars().normalizeEOL());
        }
    }

    private void render(Reference node, NodeRendererContext context, HtmlWriter html) {

    }

    private void render(HtmlEntity node, NodeRendererContext context, HtmlWriter html) {
        html.text(node.getChars().unescape());
    }

    private void render(AutoLink node, NodeRendererContext context, final HtmlWriter html) {
        final String text = node.getText().toString();
        if (context.isDoNotRenderLinks()) {
            html.text(text);
        } else {
            ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, text, null);
            html.srcPos(node.getText()).attr("href", resolvedLink.getUrl())
                    .withAttr(resolvedLink)
                    .tag("a", false, false, new Runnable() {
                        @Override
                        public void run() {
                            html.text(text);
                        }
                    });
        }
    }

    private void render(MailLink node, NodeRendererContext context, HtmlWriter html) {
        String text = node.getText().unescape();
        if (context.isDoNotRenderLinks()) {
            html.text(text);
        } else {
            ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, text, null);
            html.srcPos(node.getText()).attr("href", "mailto:" + resolvedLink.getUrl())
                    .withAttr(resolvedLink)
                    .tag("a")
                    .text(text)
                    .tag("/a");
        }
    }

    private void render(Image node, NodeRendererContext context, HtmlWriter html) {
        if (!context.isDoNotRenderLinks()) {
            String altText = new TextCollectingVisitor().collectAndGetText(node);

            ResolvedLink resolvedLink = context.resolveLink(LinkType.IMAGE, node.getUrl().unescape(), null);
            String url = resolvedLink.getUrl();

            if (!node.getUrlContent().isEmpty()) {
                // reverse URL encoding of =, &
                String content = Escaping.percentEncodeUrl(node.getUrlContent().toString()).replace("+", "%20").replace("%3D", "=").replace("%26", "&amp;");
                url += content;
            }

            html.attr("src", url);
            html.attr("alt", altText);
            if (node.getTitle().isNotNull()) {
                html.attr("title", node.getTitle().unescape());
            }
            html.srcPos(node.getChars()).withAttr(resolvedLink).tagVoid("img");
        }
    }

    private void render(Link node, NodeRendererContext context, HtmlWriter html) {
        if (context.isDoNotRenderLinks()) {
            context.renderChildren(node);
        } else {
            ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, node.getUrl().unescape(), null);

            html.attr("href", resolvedLink.getUrl());
            if (node.getTitle().isNotNull()) {
                html.attr("title", node.getTitle().unescape());
            }
            html.srcPos(node.getChars()).withAttr(resolvedLink).tag("a");
            context.renderChildren(node);
            html.tag("/a");
        }
    }

    private void render(ImageRef node, NodeRendererContext context, HtmlWriter html) {
        if (!node.isDefined()) {
            // empty ref, we treat it as text
            html.text(node.getChars().unescape());
        } else {
            if (!context.isDoNotRenderLinks()) {
                Reference reference = node.getReferenceNode(referenceRepository);
                assert reference != null;
                String altText = new TextCollectingVisitor().collectAndGetText(node);

                ResolvedLink resolvedLink = context.resolveLink(LinkType.IMAGE, reference.getUrl().unescape(), null);

                html.attr("src", resolvedLink.getUrl());
                html.attr("alt", altText);
                if (reference.getTitle().isNotNull()) {
                    html.attr("title", reference.getTitle().unescape());
                }
                html.srcPos(node.getChars()).withAttr(resolvedLink).tagVoid("img");
            }
        }
    }

    private void render(LinkRef node, NodeRendererContext context, HtmlWriter html) {
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

                ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, reference.getUrl().unescape(), null);

                html.attr("href", resolvedLink.getUrl());
                if (reference.getTitle().isNotNull()) {
                    html.attr("title", reference.getTitle().unescape());
                }
                html.srcPos(node.getChars()).withAttr(resolvedLink).tag("a");
                context.renderChildren(node);
                html.tag("/a");
            }
        }
    }
}
