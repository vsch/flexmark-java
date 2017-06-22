package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.ast.util.TextCollectingVisitor;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlRendererOptions;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.vladsch.flexmark.html.renderer.LinkStatus.UNKNOWN;
import static com.vladsch.flexmark.util.sequence.BasedSequence.NULL;

/**
 * The node renderer that renders all the core nodes (comes last in the order of node renderers).
 */
@SuppressWarnings("WeakerAccess")
public class CoreNodeRenderer implements NodeRenderer {
    public static final AttributablePart LOOSE_LIST_ITEM = new AttributablePart("LOOSE_LIST_ITEM");
    public static final AttributablePart TIGHT_LIST_ITEM = new AttributablePart("TIGHT_LIST_ITEM");
    public static final AttributablePart PARAGRAPH_LINE = new AttributablePart("PARAGRAPH_LINE");
    public static final AttributablePart CODE_CONTENT = new AttributablePart("FENCED_CODE_CONTENT");

    private final ListOptions listOptions;
    private final boolean obfuscateEmail;
    private final boolean obfuscateEmailRandom;
    private ReferenceRepository referenceRepository;
    private boolean recheckUndefinedReferences;
    private boolean codeContentBlock;

    public CoreNodeRenderer(DataHolder options) {
        this.referenceRepository = options.get(Parser.REFERENCES);
        this.listOptions = ListOptions.getFrom(options);
        this.recheckUndefinedReferences = HtmlRenderer.RECHECK_UNDEFINED_REFERENCES.getFrom(options);
        this.obfuscateEmail = HtmlRenderer.OBFUSCATE_EMAIL.getFrom(options);
        this.obfuscateEmailRandom = HtmlRenderer.OBFUSCATE_EMAIL_RANDOM.getFrom(options);
        this.codeContentBlock = Parser.FENCED_CODE_CONTENT_BLOCK.getFrom(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<NodeRenderingHandler<? extends Node>>(Arrays.asList(
                new NodeRenderingHandler<AutoLink>(AutoLink.class, new CustomNodeRenderer<AutoLink>() {
                    @Override
                    public void render(AutoLink node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<BlockQuote>(BlockQuote.class, new CustomNodeRenderer<BlockQuote>() {
                    @Override
                    public void render(BlockQuote node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<BulletList>(BulletList.class, new CustomNodeRenderer<BulletList>() {
                    @Override
                    public void render(BulletList node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<Code>(Code.class, new CustomNodeRenderer<Code>() {
                    @Override
                    public void render(Code node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<CodeBlock>(CodeBlock.class, new CustomNodeRenderer<CodeBlock>() {
                    @Override
                    public void render(CodeBlock node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<Document>(Document.class, new CustomNodeRenderer<Document>() {
                    @Override
                    public void render(Document node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<Emphasis>(Emphasis.class, new CustomNodeRenderer<Emphasis>() {
                    @Override
                    public void render(Emphasis node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<FencedCodeBlock>(FencedCodeBlock.class, new CustomNodeRenderer<FencedCodeBlock>() {
                    @Override
                    public void render(FencedCodeBlock node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<HardLineBreak>(HardLineBreak.class, new CustomNodeRenderer<HardLineBreak>() {
                    @Override
                    public void render(HardLineBreak node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<Heading>(Heading.class, new CustomNodeRenderer<Heading>() {
                    @Override
                    public void render(Heading node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<HtmlBlock>(HtmlBlock.class, new CustomNodeRenderer<HtmlBlock>() {
                    @Override
                    public void render(HtmlBlock node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<HtmlCommentBlock>(HtmlCommentBlock.class, new CustomNodeRenderer<HtmlCommentBlock>() {
                    @Override
                    public void render(HtmlCommentBlock node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<HtmlInnerBlock>(HtmlInnerBlock.class, new CustomNodeRenderer<HtmlInnerBlock>() {
                    @Override
                    public void render(HtmlInnerBlock node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<HtmlInnerBlockComment>(HtmlInnerBlockComment.class, new CustomNodeRenderer<HtmlInnerBlockComment>() {
                    @Override
                    public void render(HtmlInnerBlockComment node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<HtmlEntity>(HtmlEntity.class, new CustomNodeRenderer<HtmlEntity>() {
                    @Override
                    public void render(HtmlEntity node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<HtmlInline>(HtmlInline.class, new CustomNodeRenderer<HtmlInline>() {
                    @Override
                    public void render(HtmlInline node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<HtmlInlineComment>(HtmlInlineComment.class, new CustomNodeRenderer<HtmlInlineComment>() {
                    @Override
                    public void render(HtmlInlineComment node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<Image>(Image.class, new CustomNodeRenderer<Image>() {
                    @Override
                    public void render(Image node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<ImageRef>(ImageRef.class, new CustomNodeRenderer<ImageRef>() {
                    @Override
                    public void render(ImageRef node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<IndentedCodeBlock>(IndentedCodeBlock.class, new CustomNodeRenderer<IndentedCodeBlock>() {
                    @Override
                    public void render(IndentedCodeBlock node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<Link>(Link.class, new CustomNodeRenderer<Link>() {
                    @Override
                    public void render(Link node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<LinkRef>(LinkRef.class, new CustomNodeRenderer<LinkRef>() {
                    @Override
                    public void render(LinkRef node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<BulletListItem>(BulletListItem.class, new CustomNodeRenderer<BulletListItem>() {
                    @Override
                    public void render(BulletListItem node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<OrderedListItem>(OrderedListItem.class, new CustomNodeRenderer<OrderedListItem>() {
                    @Override
                    public void render(OrderedListItem node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<MailLink>(MailLink.class, new CustomNodeRenderer<MailLink>() {
                    @Override
                    public void render(MailLink node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<OrderedList>(OrderedList.class, new CustomNodeRenderer<OrderedList>() {
                    @Override
                    public void render(OrderedList node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<Paragraph>(Paragraph.class, new CustomNodeRenderer<Paragraph>() {
                    @Override
                    public void render(Paragraph node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<Reference>(Reference.class, new CustomNodeRenderer<Reference>() {
                    @Override
                    public void render(Reference node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<SoftLineBreak>(SoftLineBreak.class, new CustomNodeRenderer<SoftLineBreak>() {
                    @Override
                    public void render(SoftLineBreak node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<StrongEmphasis>(StrongEmphasis.class, new CustomNodeRenderer<StrongEmphasis>() {
                    @Override
                    public void render(StrongEmphasis node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<Text>(Text.class, new CustomNodeRenderer<Text>() {
                    @Override
                    public void render(Text node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<TextBase>(TextBase.class, new CustomNodeRenderer<TextBase>() {
                    @Override
                    public void render(TextBase node, NodeRendererContext context, HtmlWriter html) {
                        CoreNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<ThematicBreak>(ThematicBreak.class, new CustomNodeRenderer<ThematicBreak>() {
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
        } else {
            String noLanguageClass = context.getHtmlOptions().noLanguageClass.trim();
            if (!noLanguageClass.isEmpty()) {
                html.attr("class", noLanguageClass);
            }
        }

        html.srcPosWithEOL(node.getContentChars()).withAttr(CODE_CONTENT).tag("code");
        if (codeContentBlock) {
            context.renderChildren(node);
        } else {
            html.text(node.getContentChars().normalizeEOL());
        }
        html.tag("/code");
        html.tag("/pre").closePre();
        html.lineIf(context.getHtmlOptions().htmlBlockCloseTagEol);
    }

    private void render(ThematicBreak node, NodeRendererContext context, HtmlWriter html) {
        html.srcPos(node.getChars()).withAttr().tagVoidLine("hr");
    }

    private void render(IndentedCodeBlock node, NodeRendererContext context, HtmlWriter html) {
        html.line();
        html.srcPosWithEOL(node.getChars()).withAttr().tag("pre").openPre();

        String noLanguageClass = context.getHtmlOptions().noLanguageClass.trim();
        if (!noLanguageClass.isEmpty()) {
            html.attr("class", noLanguageClass);
        }

        html.srcPosWithEOL(node.getContentChars()).withAttr(CODE_CONTENT).tag("code");
        if (codeContentBlock) {
            context.renderChildren(node);
        } else {
            html.text(node.getContentChars().trimTailBlankLines().normalizeEndWithEOL());
        }
        html.tag("/code");
        html.tag("/pre").closePre();
        html.lineIf(context.getHtmlOptions().htmlBlockCloseTagEol);
    }

    private void render(CodeBlock node, NodeRendererContext context, HtmlWriter html) {
        if (node.getParent() instanceof IndentedCodeBlock) {
            html.text(node.getContentChars().trimTailBlankLines().normalizeEndWithEOL());
        } else {
            html.text(node.getContentChars().normalizeEOL());
        }
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

    private void renderListItem(final ListItem node, final NodeRendererContext context, final HtmlWriter html) {
        if (listOptions.isTightListItem(node)) {
            html.srcPosWithEOL(node.getChars()).withAttr(TIGHT_LIST_ITEM).withCondIndent().tagLine("li", new Runnable() {
                @Override
                public void run() {
                    html.text(node.getMarkerSuffix().unescape());
                    context.renderChildren(node);
                }
            });
        } else {
            html.srcPosWithEOL(node.getChars()).withAttr(LOOSE_LIST_ITEM).tagIndent("li", new Runnable() {
                @Override
                public void run() {
                    html.text(node.getMarkerSuffix().unescape());
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

    private void renderLooseParagraph(final Paragraph node, final NodeRendererContext context, final HtmlWriter html) {
        html.srcPosWithEOL(node.getChars()).withAttr().tagLine("p", new Runnable() {
            @Override
            public void run() {
                renderTextBlockParagraphLines(node, context, html);
            }
        });
    }

    private void render(final Paragraph node, final NodeRendererContext context, final HtmlWriter html) {
        if (!(node.getParent() instanceof ParagraphItemContainer)
                || !((ParagraphItemContainer) node.getParent()).isParagraphWrappingDisabled(node, listOptions, context.getOptions())) {
            renderLooseParagraph(node, context, html);
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
        HtmlRendererOptions htmlOptions = context.getHtmlOptions();
        if (htmlOptions.emphasisStyleHtmlOpen == null || htmlOptions.emphasisStyleHtmlClose == null) {
            html.srcPos(node.getText()).withAttr().tag("em");
            context.renderChildren(node);
            html.tag("/em");
        } else {
            html.raw(htmlOptions.emphasisStyleHtmlOpen);
            context.renderChildren(node);
            html.raw(htmlOptions.emphasisStyleHtmlClose);
        }
    }

    private void render(StrongEmphasis node, NodeRendererContext context, HtmlWriter html) {
        HtmlRendererOptions htmlOptions = context.getHtmlOptions();
        if (htmlOptions.strongEmphasisStyleHtmlOpen == null || htmlOptions.strongEmphasisStyleHtmlClose == null) {
            html.srcPos(node.getText()).withAttr().tag("strong");
            context.renderChildren(node);
            html.tag("/strong");
        } else {
            html.raw(htmlOptions.strongEmphasisStyleHtmlOpen);
            context.renderChildren(node);
            html.raw(htmlOptions.strongEmphasisStyleHtmlClose);
        }
    }

    private void render(Text node, NodeRendererContext context, HtmlWriter html) {
        html.text(Escaping.normalizeEOL(node.getChars().unescape()));
    }

    private void render(TextBase node, NodeRendererContext context, HtmlWriter html) {
        context.renderChildren(node);
    }

    private void render(Code node, NodeRendererContext context, HtmlWriter html) {
        HtmlRendererOptions htmlOptions = context.getHtmlOptions();
        if (htmlOptions.codeStyleHtmlOpen == null || htmlOptions.codeStyleHtmlClose == null) {
            html.srcPos(node.getText()).withAttr().tag("code");
            html.text(Escaping.collapseWhitespace(node.getText(), true));
            html.tag("/code");
        } else {
            html.raw(htmlOptions.codeStyleHtmlOpen);
            html.text(Escaping.collapseWhitespace(node.getText(), true));
            html.raw(htmlOptions.codeStyleHtmlClose);
        }
    }

    private void render(HtmlBlock node, NodeRendererContext context, HtmlWriter html) {
        html.line();

        if (context.getHtmlOptions().sourceWrapHtmlBlocks) {
            html.srcPos(node.getChars()).withAttr(AttributablePart.NODE_POSITION).tag("div").indent().line();
        }

        if (node.hasChildren()) {
            // inner blocks handle rendering
            context.renderChildren(node);
        } else {
            renderHtmlBlock(node, context, html, context.getHtmlOptions().suppressHtmlBlocks, context.getHtmlOptions().escapeHtmlBlocks);
        }

        if (context.getHtmlOptions().sourceWrapHtmlBlocks) {
            html.unIndent().tag("/div");
        }

        html.lineIf(context.getHtmlOptions().htmlBlockCloseTagEol);
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

        if (node instanceof HtmlBlock)
            html.line();

        if (escape) {
            html.text(node instanceof HtmlBlock ? node.getContentChars().normalizeEOL() : node.getChars().normalizeEOL());
        } else {
            html.rawPre((node instanceof HtmlBlock ? node.getContentChars().normalizeEOL() : node.getChars().normalizeEOL()));
        }

        if (node instanceof HtmlBlock)
            html.lineIf(context.getHtmlOptions().htmlBlockCloseTagEol);
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
        if (context.getHtmlOptions().unescapeHtmlEntities) {
            html.text(node.getChars().unescape());
        } else {
            html.raw(node.getChars().unescapeNoEntities());
        }
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
            if (obfuscateEmail) {
                String url = Escaping.obfuscate("mailto:" + resolvedLink.getUrl(), obfuscateEmailRandom);
                text = Escaping.obfuscate(text, true);

                html.srcPos(node.getText()).attr("href", url)
                        .withAttr(resolvedLink)
                        .tag("a")
                        .raw(text)
                        .tag("/a");
            } else {
                String url = resolvedLink.getUrl();
                html.srcPos(node.getText()).attr("href", "mailto:" + url)
                        .withAttr(resolvedLink)
                        .tag("a")
                        .text(text)
                        .tag("/a");
            }
        }
    }

    private void render(Image node, NodeRendererContext context, HtmlWriter html) {
        if (!context.isDoNotRenderLinks()) {
            String altText = new TextCollectingVisitor().collectAndGetText(node);
            ResolvedLink resolvedLink = context.resolveLink(LinkType.IMAGE, node.getUrl().unescape(), null, null);
            String url = resolvedLink.getUrl();

            if (!node.getUrlContent().isEmpty()) {
                // reverse URL encoding of =, &
                String content = Escaping.percentEncodeUrl(node.getUrlContent()).replace("+", "%2B").replace("%3D", "=").replace("%26", "&amp;");
                url += content;
            }

            html.attr("src", url);
            html.attr("alt", altText);

            // we have a title part, use that
            if (node.getTitle().isNotNull()) {
                resolvedLink.getNonNullAttributes().replaceValue(Attribute.TITLE_ATTR, node.getTitle().unescape());
            } else {
                resolvedLink.getNonNullAttributes().remove(Attribute.TITLE_ATTR);
            }

            html.attr(resolvedLink.getAttributes());
            html.srcPos(node.getChars()).withAttr(resolvedLink).tagVoid("img");
        }
    }

    private void render(Link node, NodeRendererContext context, HtmlWriter html) {
        if (context.isDoNotRenderLinks()) {
            context.renderChildren(node);
        } else {
            ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, node.getUrl().unescape(), null, null);

            html.attr("href", resolvedLink.getUrl());

            // we have a title part, use that
            if (node.getTitle().isNotNull()) {
                resolvedLink.getNonNullAttributes().replaceValue(Attribute.TITLE_ATTR, node.getTitle().unescape());
            } else {
                resolvedLink.getNonNullAttributes().remove(Attribute.TITLE_ATTR);
            }

            html.attr(resolvedLink.getAttributes());
            html.srcPos(node.getChars()).withAttr(resolvedLink).tag("a");
            context.renderChildren(node);
            html.tag("/a");
        }
    }

    private void render(ImageRef node, NodeRendererContext context, HtmlWriter html) {
        ResolvedLink resolvedLink = null;

        if (!node.isDefined() && recheckUndefinedReferences) {
            if (node.getReferenceNode(referenceRepository) != null) {
                node.setDefined(true);
            }
        }

        if (node.isDefined()) {
            Reference reference = node.getReferenceNode(referenceRepository);
            String url = reference.getUrl().unescape();

            resolvedLink = context.resolveLink(LinkType.IMAGE, url, null, null);
            if (reference.getTitle().isNotNull()) {
                resolvedLink.getNonNullAttributes().replaceValue(Attribute.TITLE_ATTR, reference.getTitle().unescape());
            } else {
                resolvedLink.getNonNullAttributes().remove(Attribute.TITLE_ATTR);
            }
        } else {
            // see if have reference resolver and this is resolved
            String normalizeRef = referenceRepository.normalizeKey(node.getReference());
            resolvedLink = context.resolveLink(LinkType.IMAGE_REF, normalizeRef, null, null);
            if (resolvedLink.getStatus() == UNKNOWN) {
                resolvedLink = null;
            }
        }

        if (resolvedLink == null) {
            // empty ref, we treat it as text
            html.text(node.getChars().unescape());
        } else {
            if (!context.isDoNotRenderLinks()) {
                String altText = new TextCollectingVisitor().collectAndGetText(node);

                html.attr("src", resolvedLink.getUrl());
                html.attr("alt", altText);
                html.attr(resolvedLink.getAttributes());
                html.srcPos(node.getChars()).withAttr(resolvedLink).tagVoid("img");
            }
        }
    }

    private void render(LinkRef node, NodeRendererContext context, HtmlWriter html) {
        ResolvedLink resolvedLink = null;

        if (!node.isDefined() && recheckUndefinedReferences) {
            if (node.getReferenceNode(referenceRepository) != null) {
                node.setDefined(true);
            }
        }

        if (node.isDefined()) {
            Reference reference = node.getReferenceNode(referenceRepository);
            String url = reference.getUrl().unescape();

            resolvedLink = context.resolveLink(LinkType.LINK, url, null, null);
            if (reference.getTitle().isNotNull()) {
                resolvedLink.getNonNullAttributes().replaceValue(Attribute.TITLE_ATTR, reference.getTitle().unescape());
            } else {
                resolvedLink.getNonNullAttributes().remove(Attribute.TITLE_ATTR);
            }
        } else {
            // see if have reference resolver and this is resolved
            String normalizeRef = node.getReference().unescape();
            resolvedLink = context.resolveLink(LinkType.LINK_REF, normalizeRef, null, null);
            if (resolvedLink.getStatus() == UNKNOWN) {
                resolvedLink = null;
            }
        }

        if (resolvedLink == null) {
            // empty ref, we treat it as text
            assert !node.isDefined();
            if (!node.hasChildren()) {
                html.text(node.getChars().unescape());
            } else {
                html.text(node.getChars().prefixOf(node.getChildChars()).unescape());
                context.renderChildren(node);
                html.text(node.getChars().suffixOf(node.getChildChars()).unescape());
            }
        } else {
            if (context.isDoNotRenderLinks()) {
                context.renderChildren(node);
            } else {
                html.attr("href", resolvedLink.getUrl());
                html.attr(resolvedLink.getAttributes());
                html.srcPos(node.getChars()).withAttr(resolvedLink).tag("a");
                context.renderChildren(node);
                html.tag("/a");
            }
        }
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new CoreNodeRenderer(options);
        }
    }
}
