package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.LineCollectingVisitor;
import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlRendererOptions;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NonRenderingInline;
import com.vladsch.flexmark.util.ast.TextCollectingVisitor;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.html.renderer.LinkStatus.UNKNOWN;

/**
 * The node renderer that renders all the core nodes (comes last in the order of node renderers).
 */
@SuppressWarnings({ "WeakerAccess", "OverlyCoupledClass" })
public class CoreNodeRenderer implements NodeRenderer {
    final public static AttributablePart LOOSE_LIST_ITEM = new AttributablePart("LOOSE_LIST_ITEM");
    final public static AttributablePart TIGHT_LIST_ITEM = new AttributablePart("TIGHT_LIST_ITEM");
    final public static AttributablePart PARAGRAPH_LINE = new AttributablePart("PARAGRAPH_LINE");
    final public static AttributablePart CODE_CONTENT = new AttributablePart("FENCED_CODE_CONTENT");

    final private ListOptions listOptions;
    final private boolean obfuscateEmail;
    final private boolean obfuscateEmailRandom;
    final private ReferenceRepository referenceRepository;
    final private boolean recheckUndefinedReferences;
    final private boolean codeContentBlock;
    final private boolean codeSoftLineBreaks;

    private List<Range> myLines;
    private List<Integer> myEOLs;
    private int myNextLine;
    private int nextLineStartOffset;

    public CoreNodeRenderer(DataHolder options) {
        referenceRepository = Parser.REFERENCES.get(options);
        recheckUndefinedReferences = HtmlRenderer.RECHECK_UNDEFINED_REFERENCES.get(options);
        listOptions = ListOptions.get(options);
        obfuscateEmail = HtmlRenderer.OBFUSCATE_EMAIL.get(options);
        obfuscateEmailRandom = HtmlRenderer.OBFUSCATE_EMAIL_RANDOM.get(options);
        codeContentBlock = Parser.FENCED_CODE_CONTENT_BLOCK.get(options);
        codeSoftLineBreaks = Parser.CODE_SOFT_LINE_BREAKS.get(options);
        myLines = null;
        myEOLs = null;
        myNextLine = 0;
        nextLineStartOffset = 0;
    }

    @SuppressWarnings("OverlyCoupledMethod")
    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(AutoLink.class, this::render),
                new NodeRenderingHandler<>(BlockQuote.class, this::render),
                new NodeRenderingHandler<>(BulletList.class, this::render),
                new NodeRenderingHandler<>(Code.class, this::render),
                new NodeRenderingHandler<>(CodeBlock.class, this::render),
                new NodeRenderingHandler<>(Document.class, this::render),
                new NodeRenderingHandler<>(Emphasis.class, this::render),
                new NodeRenderingHandler<>(FencedCodeBlock.class, this::render),
                new NodeRenderingHandler<>(HardLineBreak.class, this::render),
                new NodeRenderingHandler<>(Heading.class, this::render),
                new NodeRenderingHandler<>(HtmlBlock.class, this::render),
                new NodeRenderingHandler<>(HtmlCommentBlock.class, this::render),
                new NodeRenderingHandler<>(HtmlInnerBlock.class, this::render),
                new NodeRenderingHandler<>(HtmlInnerBlockComment.class, this::render),
                new NodeRenderingHandler<>(HtmlEntity.class, this::render),
                new NodeRenderingHandler<>(HtmlInline.class, this::render),
                new NodeRenderingHandler<>(HtmlInlineComment.class, this::render),
                new NodeRenderingHandler<>(Image.class, this::render),
                new NodeRenderingHandler<>(ImageRef.class, this::render),
                new NodeRenderingHandler<>(IndentedCodeBlock.class, this::render),
                new NodeRenderingHandler<>(Link.class, this::render),
                new NodeRenderingHandler<>(LinkRef.class, this::render),
                new NodeRenderingHandler<>(BulletListItem.class, this::render),
                new NodeRenderingHandler<>(OrderedListItem.class, this::render),
                new NodeRenderingHandler<>(MailLink.class, this::render),
                new NodeRenderingHandler<>(OrderedList.class, this::render),
                new NodeRenderingHandler<>(Paragraph.class, this::render),
                new NodeRenderingHandler<>(Reference.class, this::render),
                new NodeRenderingHandler<>(SoftLineBreak.class, this::render),
                new NodeRenderingHandler<>(StrongEmphasis.class, this::render),
                new NodeRenderingHandler<>(Text.class, this::render),
                new NodeRenderingHandler<>(TextBase.class, this::render),
                new NodeRenderingHandler<>(ThematicBreak.class, this::render)
        ));
    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(Document node, NodeRendererContext context, HtmlWriter html) {
        // No rendering itself
        context.renderChildren(node);
    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(Heading node, NodeRendererContext context, HtmlWriter html) {
        if (node.isExplicitAnchorRefId() || context.getHtmlOptions().renderHeaderId) {
            String id = context.getNodeId(node);
            if (id != null) {
                html.attr("id", id);
            }
        }

        if (context.getHtmlOptions().sourcePositionParagraphLines) {
            html.srcPos(node.getChars()).withAttr().tagLine("h" + node.getLevel(), () -> {
                html.srcPos(node.getText()).withAttr().tag("span");
                context.renderChildren(node);
                html.tag("/span");
            });
        } else {
            html.srcPos(node.getText()).withAttr().tagLine("h" + node.getLevel(), () -> context.renderChildren(node));
        }
    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(BlockQuote node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tagLineIndent("blockquote", () -> context.renderChildren(node));
    }

    void render(FencedCodeBlock node, NodeRendererContext context, HtmlWriter html) {
        html.line();
        html.srcPosWithTrailingEOL(node.getChars()).withAttr().tag("pre").openPre();

        BasedSequence info = node.getInfo();
        HtmlRendererOptions htmlOptions = context.getHtmlOptions();
        if (info.isNotNull() && !info.isBlank()) {
            String language = node.getInfoDelimitedByAny(htmlOptions.languageDelimiterSet).unescape();
            String languageClass = htmlOptions.languageClassMap.getOrDefault(language, htmlOptions.languageClassPrefix + language);
            html.attr("class", languageClass);
        } else {
            String noLanguageClass = htmlOptions.noLanguageClass.trim();
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
        html.lineIf(htmlOptions.htmlBlockCloseTagEol);
    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(ThematicBreak node, NodeRendererContext context, HtmlWriter html) {
        html.srcPos(node.getChars()).withAttr().tagVoidLine("hr");
    }

    void render(IndentedCodeBlock node, NodeRendererContext context, HtmlWriter html) {
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

    @SuppressWarnings("MethodMayBeStatic")
    void render(CodeBlock node, NodeRendererContext context, HtmlWriter html) {
        if (node.getParent() instanceof IndentedCodeBlock) {
            html.text(node.getContentChars().trimTailBlankLines().normalizeEndWithEOL());
        } else {
            html.text(node.getContentChars().normalizeEOL());
        }
    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(BulletList node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tagIndent("ul", () -> context.renderChildren(node));
    }

    void render(OrderedList node, NodeRendererContext context, HtmlWriter html) {
        int start = node.getStartNumber();
        if (listOptions.isOrderedListManualStart() && start != 1) html.attr("start", String.valueOf(start));
        html.withAttr().tagIndent("ol", () -> context.renderChildren(node));
    }

    void render(BulletListItem node, NodeRendererContext context, HtmlWriter html) {
        renderListItem(node, context, html);
    }

    void render(OrderedListItem node, NodeRendererContext context, HtmlWriter html) {
        renderListItem(node, context, html);
    }

    private void renderListItem(ListItem node, NodeRendererContext context, HtmlWriter html) {
        if (listOptions.isTightListItem(node)) {
            html.srcPosWithEOL(node.getChars()).withAttr(TIGHT_LIST_ITEM).withCondIndent().tagLine("li", () -> {
                html.text(node.getMarkerSuffix().unescape());
                context.renderChildren(node);
            });
        } else {
            html.srcPosWithEOL(node.getChars()).withAttr(LOOSE_LIST_ITEM).withCondIndent().tagLine("li", () -> {
                html.text(node.getMarkerSuffix().unescape());
                context.renderChildren(node);
            });
        }
    }

    public void renderTextBlockParagraphLines(Paragraph node, NodeRendererContext context, HtmlWriter html, boolean wrapTextInSpan) {
        if (context.getHtmlOptions().sourcePositionParagraphLines) {
            if (node.hasChildren()) {
                LineCollectingVisitor breakCollectingVisitor = new LineCollectingVisitor();
                myLines = breakCollectingVisitor.collectAndGetRanges(node);
                myEOLs = breakCollectingVisitor.getEOLs();
                myNextLine = 0;

                if (node.getFirstChild() != null) {
                    outputSourceLineSpan(node, node.getFirstChild(), node, html);
                }
                context.renderChildren(node);
                html.tag("/span");
                return;
            }
        }

        if (wrapTextInSpan) {
            html.withAttr().tag("span", false, false, () -> context.renderChildren(node));
        } else {
            context.renderChildren(node);
        }
    }

    private void outputSourceLineSpan(Node parentNode, Node startNode, Node endNode, HtmlWriter html) {
        int startOffset = startNode.getStartOffset();
        Range range = myLines.get(myNextLine);
        int eolLength = myEOLs.get(myNextLine);

        // remove trailing spaces from text
        int endOffset = endNode.getEndOffset();
        if (range.getEnd() <= endOffset) {
            endOffset = range.getEnd();
            endOffset -= eolLength;
            endOffset -= parentNode.baseSubSequence(startOffset, endOffset).countTrailing(CharPredicate.SPACE_TAB);
            myNextLine++;
            nextLineStartOffset = range.getEnd();
            nextLineStartOffset += parentNode.baseSubSequence(nextLineStartOffset, parentNode.getEndOffset()).countLeading(CharPredicate.SPACE_TAB);
        }

        if (range.getStart() > startOffset) {
            startOffset = range.getStart();
        }

        html.srcPos(startOffset, endOffset).withAttr(PARAGRAPH_LINE).tag("span");
    }

    private void outputNextLineBreakSpan(Node node, HtmlWriter html, boolean outputBreakText) {
        Range range = myLines.get(myNextLine);
        int eolLength = myEOLs.get(myNextLine);
        myNextLine++;

        // remove trailing spaces from text
        int countTrailing = node.baseSubSequence(nextLineStartOffset, range.getEnd() - eolLength).countTrailing(CharPredicate.SPACE_TAB);
        if (!outputBreakText && countTrailing > 0) {
            countTrailing--;
        }
        eolLength += countTrailing;

        html.srcPos(nextLineStartOffset, range.getEnd() - eolLength).withAttr(PARAGRAPH_LINE).tag("span");
        nextLineStartOffset = range.getEnd();

        // remove leading spaces
        nextLineStartOffset += node.baseSubSequence(nextLineStartOffset, node.getChars().getBaseSequence().length()).countLeading(CharPredicate.SPACE_TAB);
    }

    private void renderLooseParagraph(Paragraph node, NodeRendererContext context, HtmlWriter html) {
        if (context.getHtmlOptions().noPTagsUseBr) {
            renderTextBlockParagraphLines(node, context, html, false);
            html.tagVoid("br").tagVoid("br").line();
        } else {
            html.srcPosWithEOL(node.getChars()).withAttr().tagLine("p", () -> renderTextBlockParagraphLines(node, context, html, false));
        }
    }

    void render(Paragraph node, NodeRendererContext context, HtmlWriter html) {
        if (node.getFirstChildAnyNot(NonRenderingInline.class) != null) {
            if (!(node.getParent() instanceof ParagraphItemContainer)
                    || !((ParagraphItemContainer) node.getParent()).isParagraphWrappingDisabled(node, listOptions, context.getOptions())) {
                renderLooseParagraph(node, context, html);
            } else {
                renderTextBlockParagraphLines(node, context, html, false); //context.getHtmlOptions().wrapTightItemParagraphInSpan);
            }
        }
    }

    private boolean renderLineBreak(String breakText, String suppressInTag, Node node, NodeRendererContext context, HtmlWriter html) {
        if (myLines != null && myNextLine < myLines.size()) {
            // here we may need to close tags opened since the span tag
            List<String> openTags = html.getOpenTagsAfterLast("span");
            int iMax = openTags.size();
            boolean outputBreakText = iMax == 0 || suppressInTag == null || !suppressInTag.equalsIgnoreCase(openTags.get(iMax - 1));

            if (!outputBreakText && !html.isPendingSpace()) {
                // we add a space for EOL
                html.raw(" ");
            }

            for (int i = iMax; i-- > 0; ) {
                html.closeTag(openTags.get(i));
            }

            html.tag("/span");

            if (outputBreakText) {
                html.raw(breakText);
            }

            outputNextLineBreakSpan(node, html, outputBreakText);

            for (String tag : openTags) {
                if (!outputBreakText && context.getHtmlOptions().inlineCodeSpliceClass != null && !context.getHtmlOptions().inlineCodeSpliceClass.isEmpty()) {
                    html.attr(Attribute.CLASS_ATTR, context.getHtmlOptions().inlineCodeSpliceClass).withAttr().tag(tag);
                } else {
                    html.tag(tag);
                }
            }
            return true;
        }
        return false;
    }

    void render(SoftLineBreak node, NodeRendererContext context, HtmlWriter html) {
        String softBreak = context.getHtmlOptions().softBreak;
        if (context.getHtmlOptions().sourcePositionParagraphLines) {
            if (renderLineBreak(softBreak, softBreak.equals("\n") || softBreak.equals("\r\n") || softBreak.equals("\r") ? "code" : null, node, context, html)) {
                return;
            }
        }
        html.raw(softBreak);
    }

    void render(HardLineBreak node, NodeRendererContext context, HtmlWriter html) {
        if (context.getHtmlOptions().sourcePositionParagraphLines) {
            if (renderLineBreak(context.getHtmlOptions().hardBreak, null, node, context, html)) {
                return;
            }
        }
        html.raw(context.getHtmlOptions().hardBreak);
    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(Emphasis node, NodeRendererContext context, HtmlWriter html) {
        HtmlRendererOptions htmlOptions = context.getHtmlOptions();
        if (htmlOptions.emphasisStyleHtmlOpen == null || htmlOptions.emphasisStyleHtmlClose == null) {
            if (context.getHtmlOptions().sourcePositionParagraphLines) {
                html.withAttr().tag("em");
            } else {
                html.srcPos(node.getText()).withAttr().tag("em");
            }
            context.renderChildren(node);
            html.tag("/em");
        } else {
            html.raw(htmlOptions.emphasisStyleHtmlOpen);
            context.renderChildren(node);
            html.raw(htmlOptions.emphasisStyleHtmlClose);
        }
    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(StrongEmphasis node, NodeRendererContext context, HtmlWriter html) {
        HtmlRendererOptions htmlOptions = context.getHtmlOptions();
        if (htmlOptions.strongEmphasisStyleHtmlOpen == null || htmlOptions.strongEmphasisStyleHtmlClose == null) {
            if (context.getHtmlOptions().sourcePositionParagraphLines) {
                html.withAttr().tag("strong");
            } else {
                html.srcPos(node.getText()).withAttr().tag("strong");
            }
            context.renderChildren(node);
            html.tag("/strong");
        } else {
            html.raw(htmlOptions.strongEmphasisStyleHtmlOpen);
            context.renderChildren(node);
            html.raw(htmlOptions.strongEmphasisStyleHtmlClose);
        }
    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(Text node, NodeRendererContext context, HtmlWriter html) {
        html.text(Escaping.normalizeEOL(node.getChars().unescape()));
    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(TextBase node, NodeRendererContext context, HtmlWriter html) {
        context.renderChildren(node);
    }

    void render(Code node, NodeRendererContext context, HtmlWriter html) {
        HtmlRendererOptions htmlOptions = context.getHtmlOptions();
        if (htmlOptions.codeStyleHtmlOpen == null || htmlOptions.codeStyleHtmlClose == null) {
            if (context.getHtmlOptions().sourcePositionParagraphLines) {
                html.withAttr().tag("code");
            } else {
                html.srcPos(node.getText()).withAttr().tag("code");
            }
            if (codeSoftLineBreaks && !htmlOptions.isSoftBreakAllSpaces) {
                for (Node child : node.getChildren()) {
                    if (child instanceof Text) {
                        html.text(Escaping.collapseWhitespace(child.getChars(), true));
                    } else {
                        context.render(child);
                    }
                }
            } else {
                html.text(Escaping.collapseWhitespace(node.getText(), true));
            }
            html.tag("/code");
        } else {
            html.raw(htmlOptions.codeStyleHtmlOpen);
            if (codeSoftLineBreaks && !htmlOptions.isSoftBreakAllSpaces) {
                for (Node child : node.getChildren()) {
                    if (child instanceof Text) {
                        html.text(Escaping.collapseWhitespace(child.getChars(), true));
                    } else {
                        context.render(child);
                    }
                }
            } else {
                html.text(Escaping.collapseWhitespace(node.getText(), true));
            }
            html.raw(htmlOptions.codeStyleHtmlClose);
        }
    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(HtmlBlock node, NodeRendererContext context, HtmlWriter html) {
        html.line();
        HtmlRendererOptions htmlOptions = context.getHtmlOptions();

        if (htmlOptions.sourceWrapHtmlBlocks) {
            html.srcPos(node.getChars()).withAttr(AttributablePart.NODE_POSITION).tag("div").indent().line();
        }

        if (node.hasChildren()) {
            // inner blocks handle rendering
            context.renderChildren(node);
        } else {
            renderHtmlBlock(node, context, html, htmlOptions.suppressHtmlBlocks, htmlOptions.escapeHtmlBlocks, false);
        }

        if (htmlOptions.sourceWrapHtmlBlocks) {
            html.unIndent().tag("/div");
        }

        html.lineIf(htmlOptions.htmlBlockCloseTagEol);
    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(HtmlCommentBlock node, NodeRendererContext context, HtmlWriter html) {
        renderHtmlBlock(node, context, html, context.getHtmlOptions().suppressHtmlCommentBlocks, context.getHtmlOptions().escapeHtmlCommentBlocks, false);
    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(HtmlInnerBlock node, NodeRendererContext context, HtmlWriter html) {
        renderHtmlBlock(node, context, html, context.getHtmlOptions().suppressHtmlBlocks, context.getHtmlOptions().escapeHtmlBlocks, false);
    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(HtmlInnerBlockComment node, NodeRendererContext context, HtmlWriter html) {
        renderHtmlBlock(node, context, html, context.getHtmlOptions().suppressHtmlCommentBlocks, context.getHtmlOptions().escapeHtmlCommentBlocks, false);
    }

    public static void renderHtmlBlock(HtmlBlockBase node, NodeRendererContext context, HtmlWriter html, boolean suppress, boolean escape, boolean trimSpaces) {
        if (suppress) return;

        if (node instanceof HtmlBlock)
            html.line();

        String normalizeEOL = node instanceof HtmlBlock ? node.getContentChars().normalizeEOL() : node.getChars().normalizeEOL();

        if (trimSpaces) {
            normalizeEOL = normalizeEOL.trim();
        }

        if (escape) {
            if (node instanceof HtmlBlock) {
                if (normalizeEOL.length() > 0 && normalizeEOL.charAt(normalizeEOL.length() - 1) == '\n') {
                    // leave off the trailing EOL
                    normalizeEOL = normalizeEOL.substring(0, normalizeEOL.length() - 1);
                }
                html.raw("<p>").text(normalizeEOL).raw("</p>");
            } else {
                html.text(normalizeEOL);
            }
        } else {
            html.rawPre(normalizeEOL);
        }

        if (node instanceof HtmlBlock) {
            html.lineIf(context.getHtmlOptions().htmlBlockCloseTagEol);
        }
    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(HtmlInline node, NodeRendererContext context, HtmlWriter html) {
        //if (context.getHtmlOptions().sourceWrapInlineHtml) {
        //    html.srcPos(node.getChars()).withAttr(AttributablePart.NODE_POSITION).tag("span");
        //}
        renderInlineHtml(node, context, html, context.getHtmlOptions().suppressInlineHtml, context.getHtmlOptions().escapeInlineHtml);
        //if (context.getHtmlOptions().sourceWrapInlineHtml) {
        //    html.tag("/span");
        //}
    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(HtmlInlineComment node, NodeRendererContext context, HtmlWriter html) {
        renderInlineHtml(node, context, html, context.getHtmlOptions().suppressInlineHtmlComments, context.getHtmlOptions().escapeInlineHtmlComments);
    }

    public static void renderInlineHtml(HtmlInlineBase node, NodeRendererContext context, HtmlWriter html, boolean suppress, boolean escape) {
        if (suppress) return;

        if (escape) {
            html.text(node.getChars().normalizeEOL());
        } else {
            html.rawPre(node.getChars().normalizeEOL());
        }
    }

    void render(Reference node, NodeRendererContext context, HtmlWriter html) {

    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(HtmlEntity node, NodeRendererContext context, HtmlWriter html) {
        if (context.getHtmlOptions().unescapeHtmlEntities) {
            html.text(node.getChars().unescape());
        } else {
            html.raw(node.getChars().unescapeNoEntities());
        }
    }

    public static boolean isSuppressedLinkPrefix(CharSequence url, NodeRendererContext context) {
        Pattern suppressedLinks = context.getHtmlOptions().suppressedLinks;
        if (suppressedLinks != null) {
            Matcher matcher = suppressedLinks.matcher(url);
            return matcher.matches();
        }
        return false;
    }

    @SuppressWarnings("MethodMayBeStatic")
    void render(AutoLink node, NodeRendererContext context, HtmlWriter html) {
        String text = node.getText().toString();
        if (context.isDoNotRenderLinks() || isSuppressedLinkPrefix(node.getUrl(), context)) {
            html.text(text);
        } else {
            ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, text, null);
            html.srcPos(node.getText()).attr("href", resolvedLink.getUrl().startsWith("www.") ? context.getHtmlOptions().autolinkWwwPrefix + resolvedLink.getUrl() : resolvedLink.getUrl())
                    .withAttr(resolvedLink)
                    .tag("a", false, false, () -> html.text(text));
        }
    }

    void render(MailLink node, NodeRendererContext context, HtmlWriter html) {
        String text = node.getText().unescape();
        if (context.isDoNotRenderLinks() || isSuppressedLinkPrefix(node.getUrl(), context)) {
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

    @SuppressWarnings("MethodMayBeStatic")
    void render(Image node, NodeRendererContext context, HtmlWriter html) {
        if (!(context.isDoNotRenderLinks() || isSuppressedLinkPrefix(node.getUrl(), context))) {
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
                resolvedLink = resolvedLink.withTitle(node.getTitle().unescape());
            }

            html.attr(resolvedLink.getNonNullAttributes());
            html.srcPos(node.getChars()).withAttr(resolvedLink).tagVoid("img");
        }
    }

    void render(Link node, NodeRendererContext context, HtmlWriter html) {
        if (context.isDoNotRenderLinks() || isSuppressedLinkPrefix(node.getUrl(), context)) {
            context.renderChildren(node);
        } else {
            ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, node.getUrl().unescape(), null, null);

            html.attr("href", resolvedLink.getUrl());

            // we have a title part, use that
            if (node.getTitle().isNotNull()) {
                resolvedLink = resolvedLink.withTitle(node.getTitle().unescape());
            }

            html.attr(resolvedLink.getNonNullAttributes());
            html.srcPos(node.getChars()).withAttr(resolvedLink).tag("a");
            renderChildrenSourceLineWrapped(node, node.getText(), context, html);
            html.tag("/a");
        }
    }

    private void renderChildrenSourceLineWrapped(
            Node node,
            BasedSequence nodeChildText,
            NodeRendererContext context,
            HtmlWriter html
    ) {
        // if have SOFT BREAK or HARD BREAK as child then we open our own span
        if (context.getHtmlOptions().sourcePositionParagraphLines && nodeChildText.indexOfAny(CharPredicate.ANY_EOL) >= 0) {
            if (myNextLine > 0) {
                myNextLine--;
            }

            outputSourceLineSpan(node, node, node, html);
            context.renderChildren(node);
            html.tag("/span");
        } else {
            context.renderChildren(node);
        }
    }

    void render(ImageRef node, NodeRendererContext context, HtmlWriter html) {
        ResolvedLink resolvedLink;
        boolean isSuppressed = false;

        if (!node.isDefined() && recheckUndefinedReferences) {
            if (node.getReferenceNode(referenceRepository) != null) {
                node.setDefined(true);
            }
        }

        Reference reference = null;

        if (node.isDefined()) {
            reference = node.getReferenceNode(referenceRepository);
            String url = reference.getUrl().unescape();
            isSuppressed = isSuppressedLinkPrefix(url, context);

            resolvedLink = context.resolveLink(LinkType.IMAGE, url, null, null);
            if (reference.getTitle().isNotNull()) {
                resolvedLink = resolvedLink.withTitle(reference.getTitle().unescape());
            }
        } else {
            // see if have reference resolver and this is resolved
            String normalizeRef = referenceRepository.normalizeKey(node.getReference());
            resolvedLink = context.resolveLink(LinkType.IMAGE_REF, normalizeRef, null, null);
            if (resolvedLink.getStatus() == UNKNOWN || resolvedLink.getUrl().isEmpty()) {
                resolvedLink = null;
            }
        }

        if (resolvedLink == null) {
            // empty ref, we treat it as text
            html.text(node.getChars().unescape());
        } else {
            if (!(context.isDoNotRenderLinks() || isSuppressed)) {
                String altText = new TextCollectingVisitor().collectAndGetText(node);
                Attributes attributes = resolvedLink.getNonNullAttributes();

                html.attr("src", resolvedLink.getUrl());
                html.attr("alt", altText);

                // need to take attributes for reference definition, then overlay them with ours
                if (reference != null) {
                    attributes = context.extendRenderingNodeAttributes(reference, AttributablePart.NODE, attributes);
                }

                html.attr(attributes);
                html.srcPos(node.getChars()).withAttr(resolvedLink).tagVoid("img");
            }
        }
    }

    void render(LinkRef node, NodeRendererContext context, HtmlWriter html) {
        ResolvedLink resolvedLink;
        boolean isSuppressed = false;

        if (!node.isDefined() && recheckUndefinedReferences) {
            if (node.getReferenceNode(referenceRepository) != null) {
                node.setDefined(true);
            }
        }

        Reference reference = null;
        if (node.isDefined()) {
            reference = node.getReferenceNode(referenceRepository);
            String url = reference.getUrl().unescape();
            isSuppressed = isSuppressedLinkPrefix(url, context);

            resolvedLink = context.resolveLink(LinkType.LINK, url, null, null);
            if (reference.getTitle().isNotNull()) {
                resolvedLink = resolvedLink.withTitle(reference.getTitle().unescape());
            }
        } else {
            // see if have reference resolver and this is resolved
            String normalizeRef = node.getReference().unescape();
            resolvedLink = context.resolveLink(LinkType.LINK_REF, normalizeRef, null, null);
            if (resolvedLink.getStatus() == UNKNOWN || resolvedLink.getUrl().isEmpty()) {
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
                renderChildrenSourceLineWrapped(node, node.getText(), context, html);
                html.text(node.getChars().suffixOf(node.getChildChars()).unescape());
            }
        } else {
            if (context.isDoNotRenderLinks() || isSuppressed) {
                context.renderChildren(node);
            } else {
                Attributes attributes = resolvedLink.getNonNullAttributes();

                html.attr("href", resolvedLink.getUrl());

                if (reference != null) {
                    attributes = context.extendRenderingNodeAttributes(reference, AttributablePart.NODE, attributes);
                }

                html.attr(attributes);
                html.srcPos(node.getChars()).withAttr(resolvedLink).tag("a");
                renderChildrenSourceLineWrapped(node, node.getText(), context, html);
                html.tag("/a");
            }
        }
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new CoreNodeRenderer(options);
        }
    }
}
