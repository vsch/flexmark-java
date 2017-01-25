package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.formatter.CustomNodeFormatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.CoreNodeRenderer;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.vladsch.flexmark.util.sequence.BasedSequence.NULL;

public class CoreNodeFormatter implements NodeFormatter, PhasedNodeFormatter {
    private final FormatterOptions options;
    private final ListOptions listOptions;
    private ReferenceRepository referenceRepository;
    private boolean recheckUndefinedReferences;

    public CoreNodeFormatter(DataHolder options) {
        this.options = new FormatterOptions(options);
        this.referenceRepository = options.get(Parser.REFERENCES);
        this.listOptions = ListOptions.getFrom(options);
        this.recheckUndefinedReferences = HtmlRenderer.RECHECK_UNDEFINED_REFERENCES.getFrom(options);
    }

    @Override
    public Set<FormattingPhase> getFormattingPhases() {
        HashSet<FormattingPhase> set = new HashSet<>();
        set.add(FormattingPhase.COLLECT);
        return null;
    }

    @Override
    public void renderDocument(final NodeFormatterContext context, final MarkdownWriter markdown, final Document document, final FormattingPhase phase) {
        // here non-rendered elements can be collected so that they are rendered in another part of the document
    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Arrays.asList(
                // Generic unknown node formatter
                new NodeFormattingHandler<>(Node.class, new CustomNodeFormatter<Node>() {
                    @Override
                    public void render(Node node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),

                new NodeFormattingHandler<>(AutoLink.class, new CustomNodeFormatter<AutoLink>() {
                    @Override
                    public void render(AutoLink node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(BlockQuote.class, new CustomNodeFormatter<BlockQuote>() {
                    @Override
                    public void render(BlockQuote node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(BulletList.class, new CustomNodeFormatter<BulletList>() {
                    @Override
                    public void render(BulletList node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(Code.class, new CustomNodeFormatter<Code>() {
                    @Override
                    public void render(Code node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(Document.class, new CustomNodeFormatter<Document>() {
                    @Override
                    public void render(Document node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(Emphasis.class, new CustomNodeFormatter<Emphasis>() {
                    @Override
                    public void render(Emphasis node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(FencedCodeBlock.class, new CustomNodeFormatter<FencedCodeBlock>() {
                    @Override
                    public void render(FencedCodeBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(HardLineBreak.class, new CustomNodeFormatter<HardLineBreak>() {
                    @Override
                    public void render(HardLineBreak node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(Heading.class, new CustomNodeFormatter<Heading>() {
                    @Override
                    public void render(Heading node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(HtmlBlock.class, new CustomNodeFormatter<HtmlBlock>() {
                    @Override
                    public void render(HtmlBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(HtmlCommentBlock.class, new CustomNodeFormatter<HtmlCommentBlock>() {
                    @Override
                    public void render(HtmlCommentBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(HtmlInnerBlock.class, new CustomNodeFormatter<HtmlInnerBlock>() {
                    @Override
                    public void render(HtmlInnerBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(HtmlInnerBlockComment.class, new CustomNodeFormatter<HtmlInnerBlockComment>() {
                    @Override
                    public void render(HtmlInnerBlockComment node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(HtmlEntity.class, new CustomNodeFormatter<HtmlEntity>() {
                    @Override
                    public void render(HtmlEntity node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(HtmlInline.class, new CustomNodeFormatter<HtmlInline>() {
                    @Override
                    public void render(HtmlInline node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(HtmlInlineComment.class, new CustomNodeFormatter<HtmlInlineComment>() {
                    @Override
                    public void render(HtmlInlineComment node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(Image.class, new CustomNodeFormatter<Image>() {
                    @Override
                    public void render(Image node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(ImageRef.class, new CustomNodeFormatter<ImageRef>() {
                    @Override
                    public void render(ImageRef node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(IndentedCodeBlock.class, new CustomNodeFormatter<IndentedCodeBlock>() {
                    @Override
                    public void render(IndentedCodeBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(Link.class, new CustomNodeFormatter<Link>() {
                    @Override
                    public void render(Link node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(LinkRef.class, new CustomNodeFormatter<LinkRef>() {
                    @Override
                    public void render(LinkRef node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(BulletListItem.class, new CustomNodeFormatter<BulletListItem>() {
                    @Override
                    public void render(BulletListItem node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(OrderedListItem.class, new CustomNodeFormatter<OrderedListItem>() {
                    @Override
                    public void render(OrderedListItem node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(MailLink.class, new CustomNodeFormatter<MailLink>() {
                    @Override
                    public void render(MailLink node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(OrderedList.class, new CustomNodeFormatter<OrderedList>() {
                    @Override
                    public void render(OrderedList node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(Paragraph.class, new CustomNodeFormatter<Paragraph>() {
                    @Override
                    public void render(Paragraph node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(Reference.class, new CustomNodeFormatter<Reference>() {
                    @Override
                    public void render(Reference node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(SoftLineBreak.class, new CustomNodeFormatter<SoftLineBreak>() {
                    @Override
                    public void render(SoftLineBreak node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(StrongEmphasis.class, new CustomNodeFormatter<StrongEmphasis>() {
                    @Override
                    public void render(StrongEmphasis node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(Text.class, new CustomNodeFormatter<Text>() {
                    @Override
                    public void render(Text node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(TextBase.class, new CustomNodeFormatter<TextBase>() {
                    @Override
                    public void render(TextBase node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(ThematicBreak.class, new CustomNodeFormatter<ThematicBreak>() {
                    @Override
                    public void render(ThematicBreak node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                })
        ));
    }

    private void render(Node node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(Document node, NodeFormatterContext context, MarkdownWriter markdown) {
        // No rendering itself
        context.renderChildren(node);
    }

    private void render(final Heading node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        if (node.isAtxHeading()) {
            markdown.raw(node.getOpeningMarker());
        }
        // TODO: add ability to add marker and find the # of chars from marker
        context.renderChildren(node);
        if (node.isSetextHeading()) {
            // TODO: equalize length if needed
            markdown.raw(node.getClosingMarker());
        }
    }

    private void render(final BlockQuote node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        markdown.withAttr().tagLineIndent("blockquote", new Runnable() {
            @Override
            public void run() {
                context.renderChildren(node);
            }
        });
    }

    private void render(FencedCodeBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(ThematicBreak node, NodeFormatterContext context, MarkdownWriter markdown) {
        // TODO: add format option for string to use as thematic break
        markdown.raw(node.getChars());
    }

    private void render(IndentedCodeBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(final BulletList node, final NodeFormatterContext context, MarkdownWriter markdown) {
        context.renderChildren(node);
    }

    private void render(final OrderedList node, final NodeFormatterContext context, MarkdownWriter markdown) {
        int start = node.getStartNumber();
        if (listOptions.isOrderedListManualStart() && start != 1) markdown.attr("start", String.valueOf(start));
        context.renderChildren(node);
    }

    private void render(BulletListItem node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderListItem(node, context, markdown);
    }

    private void render(OrderedListItem node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderListItem(node, context, markdown);
    }

    private void renderListItem(final ListItem node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        if (listOptions.isTightListItem(node)) {
            markdown.srcPosWithEOL(node.getChars()).withAttr(CoreNodeRenderer.TIGHT_LIST_ITEM).withCondIndent().tagLine("li", new Runnable() {
                @Override
                public void run() {
                    markdown.raw(node.getChars());
                    context.renderChildren(node);
                }
            });
        } else {
            markdown.srcPosWithEOL(node.getChars()).withAttr(CoreNodeRenderer.LOOSE_LIST_ITEM).tagIndent("li", new Runnable() {
                @Override
                public void run() {
                    markdown.text(node.getMarkerSuffix().unescape());
                    context.renderChildren(node);
                }
            });
        }
    }

    public static void renderTextBlockParagraphLines(Node node, NodeFormatterContext context, MarkdownWriter markdown) {
        context.renderChildren(node);
    }

    private void renderLooseParagraph(final Paragraph node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        markdown.srcPosWithEOL(node.getChars()).withAttr().tagLine("p", new Runnable() {
            @Override
            public void run() {
                renderTextBlockParagraphLines(node, context, markdown);
            }
        });
    }

    private void render(final Paragraph node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        if (!(node.getParent() instanceof ParagraphItemContainer)
                || !((ParagraphItemContainer) node.getParent()).isParagraphWrappingDisabled(node, listOptions, context.getOptions())) {
            renderLooseParagraph(node, context, markdown);
        } else {
            renderTextBlockParagraphLines(node, context, markdown);
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

    private void render(SoftLineBreak node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(HardLineBreak node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(Emphasis node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getOpeningMarker());
        context.renderChildren(node);
        markdown.raw(node.getOpeningMarker());
    }

    private void render(StrongEmphasis node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getOpeningMarker());
        context.renderChildren(node);
        markdown.raw(node.getOpeningMarker());
    }

    private void render(Text node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(TextBase node, NodeFormatterContext context, MarkdownWriter markdown) {
        context.renderChildren(node);
    }

    private void render(Code node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getOpeningMarker());
        markdown.raw(node.getText());
        markdown.raw(node.getOpeningMarker());
    }

    private void render(HtmlBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (node.hasChildren()) {
            // inner blocks handle rendering
            context.renderChildren(node);
        } else {
            markdown.raw(node.getChars());
        }
    }

    private void render(HtmlCommentBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(HtmlInnerBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(HtmlInnerBlockComment node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(HtmlInline node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(HtmlInlineComment node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(Reference node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(HtmlEntity node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(AutoLink node, NodeFormatterContext context, final MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(MailLink node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(Image node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(Link node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(ImageRef node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }

    private void render(LinkRef node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.raw(node.getChars());
    }
}
