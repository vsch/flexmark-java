package com.vladsch.flexmark.html2md.converter.internal;

import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.ext.emoji.internal.EmojiReference;
import com.vladsch.flexmark.ext.emoji.internal.EmojiShortcuts;
import com.vladsch.flexmark.html.renderer.LinkType;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.html2md.converter.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.MarkdownTable;
import com.vladsch.flexmark.util.format.RomanNumeral;
import com.vladsch.flexmark.util.format.TableCell;
import com.vladsch.flexmark.util.html.CellAlignment;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.misc.Utils;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.LineAppendable;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.util.misc.Utils.minLimit;

public class HtmlConverterCoreNodeRenderer implements PhasedHtmlNodeRenderer {
    final public static String EMOJI_ALT_PREFIX = "emoji ";

    final public static Pattern NUMERIC_DOT_LIST_PAT = Pattern.compile("^(\\d+)\\.\\s*$");
    final public static Pattern NUMERIC_PAREN_LIST_PAT = Pattern.compile("^(\\d+)\\)\\s*$");
    final public static Pattern NON_NUMERIC_DOT_LIST_PAT = Pattern.compile("^((?:(?:" + RomanNumeral.ROMAN_NUMERAL.pattern() + ")|(?:" + RomanNumeral.LOWERCASE_ROMAN_NUMERAL.pattern() + ")|[a-z]+|[A-Z]+))\\.\\s*$");
    final public static Pattern NON_NUMERIC_PAREN_LIST_PAT = Pattern.compile("^((?:[a-z]+|[A-Z]+))\\)\\s*$");
    final public static Pattern BULLET_LIST_PAT = Pattern.compile("^([·])\\s*$");
    final public static Pattern ALPHA_NUMERAL_PAT = Pattern.compile("^[a-z]+|[A-Z]+$");

    public static HashSet<String> explicitLinkTextTags = new HashSet<>(Arrays.asList(FlexmarkHtmlConverter.EXPLICIT_LINK_TEXT_TAGS));

    final private HashMap<String, String> myAbbreviations;
    final private HashMap<String, String> myMacrosMap;               // macro name to macro content
    final private HtmlConverterOptions myHtmlConverterOptions;
    private MarkdownTable myTable;
    private boolean myTableSuppressColumns = false;

    public HtmlConverterCoreNodeRenderer(DataHolder options) {
        myHtmlConverterOptions = new HtmlConverterOptions(options);

        myAbbreviations = new HashMap<>();
        myMacrosMap = new HashMap<>();
    }

    @Override
    public Set<HtmlConverterPhase> getHtmlConverterPhases() {
        return new HashSet<>(Arrays.asList(
                HtmlConverterPhase.COLLECT,
                HtmlConverterPhase.DOCUMENT_BOTTOM
        ));
    }

    @Override
    public Set<HtmlNodeRendererHandler<?>> getHtmlNodeRendererHandlers() {
        HashSet<HtmlNodeRendererHandler<? extends Node>> result = new HashSet<>(Arrays.asList(
                // Generic unknown node formatter
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.COMMENT_NODE, Comment.class, this::processComment),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.A_NODE, Element.class, this::processA),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.ABBR_NODE, Element.class, this::processAbbr),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.ASIDE_NODE, Element.class, this::processAside),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.B_NODE, Element.class, this::processStrong),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.BLOCKQUOTE_NODE, Element.class, this::processBlockQuote),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.BR_NODE, Element.class, this::processBr),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.CODE_NODE, Element.class, this::processCode),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.DEL_NODE, Element.class, this::processDel),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.DIV_NODE, Element.class, this::processDiv),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.DL_NODE, Element.class, this::processDl),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.EM_NODE, Element.class, this::processEmphasis),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.EMOJI_NODE, Element.class, this::processEmoji),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.H1_NODE, Element.class, this::processHeading),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.H2_NODE, Element.class, this::processHeading),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.H3_NODE, Element.class, this::processHeading),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.H4_NODE, Element.class, this::processHeading),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.H5_NODE, Element.class, this::processHeading),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.H6_NODE, Element.class, this::processHeading),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.HR_NODE, Element.class, this::processHr),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.I_NODE, Element.class, this::processEmphasis),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.IMG_NODE, Element.class, this::processImg),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.INPUT_NODE, Element.class, this::processInput),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.INS_NODE, Element.class, this::processIns),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.LI_NODE, Element.class, this::processLi),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.MATH_NODE, Element.class, this::processMath),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.OL_NODE, Element.class, this::processOl),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.P_NODE, Element.class, this::processP),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.PRE_NODE, Element.class, this::processPre),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.SPAN_NODE, Element.class, this::processSpan),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.STRIKE_NODE, Element.class, this::processDel),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.STRONG_NODE, Element.class, this::processStrong),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.SUB_NODE, Element.class, this::processSub),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.SUP_NODE, Element.class, this::processSup),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.SVG_NODE, Element.class, this::processSvg),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.TABLE_NODE, Element.class, this::processTable),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.U_NODE, Element.class, this::processIns),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.UL_NODE, Element.class, this::processUl),
                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.TEXT_NODE, TextNode.class, this::processText),

                new HtmlNodeRendererHandler<>(FlexmarkHtmlConverter.DEFAULT_NODE, Node.class, this::processDefault)
        ));

        // add wrapped and unwrapped handlers
        if (myHtmlConverterOptions.unwrappedTags.length > 0) {
            for (String tag : myHtmlConverterOptions.unwrappedTags) {
                result.add(new HtmlNodeRendererHandler<>(tag, Node.class, this::processUnwrapped));
            }
        }

        if (myHtmlConverterOptions.wrappedTags.length > 0) {
            for (String tag : myHtmlConverterOptions.wrappedTags) {
                result.add(new HtmlNodeRendererHandler<>(tag, Node.class, this::processWrapped));
            }
        }

        return result;
    }

    @Override
    public void renderDocument(HtmlNodeConverterContext context, LineAppendable out, Document document, HtmlConverterPhase phase) {
        switch (phase) {
            case COLLECT: {
                // initialize reference data
                com.vladsch.flexmark.util.ast.Document myForDocument = context.getForDocument();

                if (myForDocument != null) {
                    ReferenceRepository referenceRepository = Parser.REFERENCES.get(myForDocument);
                    if (referenceRepository != null) {
                        HashMap<String, Reference> referenceUrlToReferenceMap = context.getReferenceUrlToReferenceMap();
                        HashSet<Reference> externalReferences = context.getExternalReferences();

                        for (Reference reference : referenceRepository.getValues()) {
                            referenceUrlToReferenceMap.put(reference.getUrl().toString(), reference);
                            referenceUrlToReferenceMap.put(reference.getReference().toString(), reference);
                            externalReferences.add(reference);
                        }
                    }
                }
            }
            break;

            case DOCUMENT_BOTTOM: {
                // output abbreviations if any
                if (!myAbbreviations.isEmpty()) {
                    out.blankLine();
                    for (Map.Entry<String, String> entry : myAbbreviations.entrySet()) {
                        out.line().append("*[").append(entry.getKey()).append("]: ").append(entry.getValue()).line();
                    }
                    out.blankLine();
                }

                // output references if any
                HashMap<String, Reference> referenceUrlToReferenceMap = context.getReferenceUrlToReferenceMap();
                if (!referenceUrlToReferenceMap.isEmpty()) {
                    boolean first = true;
                    HashSet<Reference> externalReferences = context.getExternalReferences();
                    for (Map.Entry<String, Reference> entry : referenceUrlToReferenceMap.entrySet()) {
                        if (!externalReferences.contains(entry.getValue())) {
                            if (first) {
                                first = false;
                                out.blankLine();
                            }
                            out.line().append(entry.getValue().getChars()).line();
                        }
                    }

                    if (!first) {
                        out.blankLine();
                    }
                }

                // output macros if any
                if (!myMacrosMap.isEmpty()) {
                    for (Map.Entry<String, String> entry : myMacrosMap.entrySet()) {
                        out.blankLine();
                        out.append(">>>").append(entry.getKey()).line();
                        CharSequence charSequence = entry.getValue();
                        BasedSequence value = BasedSequence.of(charSequence);
                        out.append(value.trimEnd()).append("\n");
                        out.append("<<<\n");
                        out.blankLine();
                    }
                }

                //// output blank line if none follows output
                //if (out.getPendingEOL() < 1) {
                //    out.blankLine();
                //}
            }
            break;

            default:
                break;
        }
    }

    public static int getMaxRepeatedChars(CharSequence text, char c, int minCount) {
        BasedSequence chars = BasedSequence.of(text);
        int lastPos = 0;
        while (lastPos < chars.length()) {
            int pos = chars.indexOf(c, lastPos);
            if (pos < 0) break;
            int count = chars.countLeading(value -> value == c, pos);
            if (minCount <= count) minCount = count + 1;
            lastPos = pos + count;
        }
        return minCount;
    }

    public static boolean hasChildrenOfType(Element element, Set<String> nodeNames) {
        for (Node child : element.children()) {
            if (nodeNames.contains(child.nodeName().toLowerCase())) return true;
        }
        return false;
    }

    public static boolean isFirstChild(Element element) {
        for (Node node : element.parent().childNodes()) {
            if (node instanceof Element) {
                return element == node;
            } else if (node.nodeName().equals(FlexmarkHtmlConverter.TEXT_NODE) && !node.outerHtml().trim().isEmpty()) {
                break;
            }
        }
        return false;
    }

    public static boolean isLastChild(Element element) {
        Elements children = element.parent().children();
        int i = children.size();
        while (i-- > 0) {
            Node node = children.get(i);
            if (node instanceof Element) {
                return element == node;
            }
        }
        return false;
    }

    // Node Converters
    private void processDefault(Node node, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        // by default output nothing for unspecified tags
        context.renderDefault(node);
    }

    private boolean isHeading(Element element) {
        if (element != null) {
            String tagName = element.tagName().toLowerCase();
            for (String headingTag : FlexmarkHtmlConverter.HEADING_NODES) {
                if (tagName.equals(headingTag)) return true;
            }
        }
        return false;
    }

    private void processA(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        // see if it is an anchor or a link
        if (element.hasAttr("href")) {
            LinkConversion conv = myHtmlConverterOptions.extInlineLink;
            if (conv.isSuppressed()) return;

            String href = element.attr("href");
            ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, href, false);
            String useHref = resolvedLink.getUrl();

            if (out.isPreFormatted()) {
                // in preformatted text links convert to URLs
                int slashIndex = useHref.lastIndexOf('/');
                if (slashIndex != -1) {
                    int hashIndex = useHref.indexOf('#', slashIndex);
                    if (hashIndex != -1 && slashIndex + 1 == hashIndex) {
                        // remove trailing / from page ref
                        useHref = useHref.substring(0, slashIndex) + useHref.substring(hashIndex);
                    }
                }
                out.append(useHref);
            } else if (conv.isParsed()) {
                context.pushState(element);
                String textNodes = context.processTextNodes(element);
                String text = textNodes.trim();
                String title = element.hasAttr("title") ? element.attr("title") : null;

                if (!text.isEmpty() || !useHref.contains("#")
                        || !isHeading(element.parent()) && !useHref.equals("#") && (context.getState() == null || context.getState().getAttributes().get("id") == null || context.getState().getAttributes().get("id").getValue().isEmpty())) {
                    if (myHtmlConverterOptions.extractAutoLinks && href.equals(text) && (title == null || title.isEmpty())) {
                        if (myHtmlConverterOptions.wrapAutoLinks) out.append('<');
                        out.append(useHref);
                        if (myHtmlConverterOptions.wrapAutoLinks) out.append('>');
                        context.transferIdToParent();
                    } else if (!conv.isTextOnly() && !useHref.startsWith("javascript:")) {
                        boolean handled = false;

                        if (conv.isReference() && !hasChildrenOfType(element, explicitLinkTextTags)) {
                            // need reference
                            Reference reference = context.getOrCreateReference(useHref, text, title);
                            if (reference != null) {
                                handled = true;
                                if (reference.getReference().equals(text)) {
                                    out.append('[').append(text).append("][]");
                                } else {
                                    out.append('[').append(text).append("][").append(reference.getReference()).append(']');
                                }
                            }
                        }

                        if (!handled) {
                            out.append('[');
                            out.append(text);
                            out.append(']');
                            out.append('(').append(useHref);
                            if (title != null)
                                out.append(" \"").append(title.replace("\n", myHtmlConverterOptions.eolInTitleAttribute).replace("\"", "\\\"")).append('"');
                            out.append(")");
                        }
                    } else {
                        if (href.equals(text)) {
                            out.append(useHref);
                        } else {
                            out.append(text);
                        }
                    }

                    context.excludeAttributes("href", "title");
                    context.popState(out);
                } else {
                    context.transferIdToParent();
                    context.popState(null);
                }
            } else if (!conv.isSuppressed()) {
                context.processWrapped(element, null, true);
            }
        } else {
            boolean stripIdAttribute = false;
            if (element.childNodeSize() == 0 && element.parent().tagName().equals("body")) {
                // these are GitHub dummy repeats of heading anchors
                stripIdAttribute = true;
            }

            context.processTextNodes(element, stripIdAttribute);
        }
    }

    private void processAbbr(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        // see if it is an anchor or a link
        if (element.hasAttr("title")) {
            String text = context.processTextNodes(element).trim();
            myAbbreviations.put(text, element.attr("title"));
        }
    }

    private void processAside(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        if (isFirstChild(element)) out.line();
        out.pushPrefix();
        out.addPrefix("| ");
        context.renderChildren(element, true, null);
        out.line();
        out.popPrefix();
    }

    private void processBlockQuote(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        if (isFirstChild(element)) out.line();
        out.pushPrefix();
        out.addPrefix("> ");
        context.renderChildren(element, true, null);
        out.line();
        out.popPrefix();
    }

    private void processBr(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        if (out.isPreFormatted()) {
            out.append('\n');
        } else {
            int options = out.getOptions();
            out.setOptions(options & ~(LineAppendable.F_TRIM_TRAILING_WHITESPACE | LineAppendable.F_COLLAPSE_WHITESPACE));
            if (out.getPendingEOL() == 0) {
                // hard break
                out.append(' ', 2).line();
            } else {
                if (out.getPendingEOL() == 1) {
                    String s = out.toString();
                    if (!s.endsWith("<br />")) {
                        // this is a paragraph break
                        if (myHtmlConverterOptions.brAsParaBreaks) {
                            out.blankLine();
                        }
                    } else {
                        // this is blank line insertion via <br />
                        if (myHtmlConverterOptions.brAsExtraBlankLines) {
                            out.append("<br />").blankLine();
                        }
                    }
                } else {
                    // this is blank line insertion via <br />
                    if (myHtmlConverterOptions.brAsExtraBlankLines) {
                        out.append("<br />").blankLine();
                    }
                }
            }
            out.setOptions(options);
        }
    }

    private void processCode(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        context.processConditional(myHtmlConverterOptions.extInlineCode, element, () -> {
            CharSequence charSequence = element.ownText();
            BasedSequence text = BasedSequence.of(charSequence);
            int backTickCount = getMaxRepeatedChars(text, '`', 1);
            CharSequence backTicks = RepeatedSequence.repeatOf("`", backTickCount);
            context.inlineCode(() -> context.processTextNodes(element, false, myHtmlConverterOptions.extInlineCode.isTextOnly() ? "" : backTicks));
        });
    }

    private void processDel(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        context.processConditional(myHtmlConverterOptions.extInlineDel, element, () -> {
            if (!myHtmlConverterOptions.preCodePreserveEmphasis && out.isPreFormatted()) {
                context.wrapTextNodes(element, "", false);
            } else {
                context.wrapTextNodes(element, myHtmlConverterOptions.extInlineDel.isTextOnly() ? "" : "~~", element.nextElementSibling() != null);
            }
        });
    }

    private void handleDivTable(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        MarkdownTable oldTable = myTable;

        myTable = new MarkdownTable("", myHtmlConverterOptions.tableOptions);
        myTableSuppressColumns = false;

        Node node = element;
        do {
            if (!node.nodeName().toLowerCase().equals(FlexmarkHtmlConverter.DIV_NODE)) {
                if (node instanceof Element) break;
                continue;
            }

            Set<String> classNames = ((Element) node).classNames();
            if (!classNames.contains("wt-data-grid__row")) break;

            handleDivTableRow((Element) node, context, out);
        } while ((node = context.next()) != null);

        myTable.finalizeTable();
        int sepColumns = myTable.getMaxColumns();

        if (sepColumns > 0) {
            out.blankLine();
            myTable.appendTable(out);
            out.tailBlankLine();
        }

        myTable = oldTable;
    }

    private void handleDivTableRow(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        context.pushState(element);

        myTable.setHeader(hasIntersection(element.classNames(), myHtmlConverterOptions.divTableHdrClasses));

        Node node;
        while ((node = context.next()) != null) {
            if (!node.nodeName().toLowerCase().equals(FlexmarkHtmlConverter.DIV_NODE)) {
                if (node instanceof Element) break;
                continue;
            }
            if (!hasIntersection(((Element) node).classNames(), myHtmlConverterOptions.divTableCellClasses)) break;

            handleDivTableCell((Element) node, context, out);
        }

        myTable.nextRow();
        context.popState(out);
    }

    private void handleDivTableCell(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        String cellText = context.processTextNodes(element).trim().replaceAll("\\s*\n\\s*", " ");
        int colSpan = 1;
        int rowSpan = 1;
        CellAlignment alignment = CellAlignment.NONE;

        // skip cells defined by row spans in previous rows
        if (!myTableSuppressColumns) {
            myTable.addCell(new TableCell(null, BasedSequence.NULL, cellText.replace("\n", " "), BasedSequence.NULL, rowSpan, colSpan, alignment));
        }
    }

    private boolean hasIntersection(@NotNull Set<String> stringSet1, String[] stringSet2) {
        for (String item : stringSet2) {
            if (stringSet1.contains(item)) return true;
        }
        return false;
    }

    private void processDiv(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        // unwrap and process content
        if (myHtmlConverterOptions.divTableProcessing) {
            // NOTE: handle class names for header, row and cell
            if (hasIntersection(element.classNames(), myHtmlConverterOptions.divTableRowClasses)) {
                handleDivTable(element, context, out);
                return;
            }
        }

        if (!isFirstChild(element)) {
            if (!myHtmlConverterOptions.divAsParagraph) {
                int pendingEOL = out.getPendingEOL();
                if (pendingEOL == 0) {
                    // no trailing line or spaces, add break spaces and line
                    int pendingSpace = out.getPendingSpace();
                    out.lineWithTrailingSpaces(minLimit(0, 2 - pendingSpace));
                } else if (pendingEOL == 1) {
                    // may need to replace last line to add trailing break spaces
                    int lineCount = out.getLineCountWithPending();
                    if (lineCount > 0) {
                        CharSequence lineContent = out.getLineContent(lineCount - 1);
                        int pendingSpace = BasedSequence.of(lineContent).countTrailing(CharPredicate.SPACE_TAB);
                        if (pendingSpace < 2) {
                            // replace last line
                            out.removeLines(lineCount - 1, lineCount);
                            out.append(lineContent);
                            out.lineWithTrailingSpaces(2 - pendingSpace);
                        }
                    }
                }
            } else {
                out.blankLine();
            }
        }

        context.renderChildren(element, false, null);

        if (!isLastChild(element)) {
            out.line();
            if (myHtmlConverterOptions.divAsParagraph) out.blankLine();
        }
    }

    private void processDl(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        context.pushState(element);

        Node item;
        boolean lastWasDefinition = true;
        boolean firstItem = true;

        while ((item = context.next()) != null) {
            switch (item.nodeName().toLowerCase()) {
                case FlexmarkHtmlConverter.DT_NODE:
                    out.blankLineIf(lastWasDefinition).lineIf(!firstItem);
                    context.processTextNodes(item, false);
                    out.line();
                    lastWasDefinition = false;
                    firstItem = false;
                    break;

                case FlexmarkHtmlConverter.DD_NODE:
                    handleDefinition((Element) item, context, out);
                    lastWasDefinition = true;
                    firstItem = false;
                    break;

                default:
                    //context.processWrapped(item, true, false);
                    break;
            }
        }

        context.popState(out);
    }

    private void handleDefinition(Element item, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        context.pushState(item);
        int options = out.getOptions();
        Elements children = item.children();
        boolean firstIsPara = false;

        if (!children.isEmpty() && children.get(0).tagName().equalsIgnoreCase(FlexmarkHtmlConverter.P_NODE)) {
            // we need a blank line
            out.blankLine();
            firstIsPara = true;
        }

        int count = myHtmlConverterOptions.listContentIndent ? myHtmlConverterOptions.definitionMarkerSpaces + 1 : 4;
        CharSequence childPrefix = RepeatedSequence.repeatOf(" ", count);

        out.line().setOptions(options & ~LineAppendable.F_COLLAPSE_WHITESPACE);
        out.append(':').append(' ', myHtmlConverterOptions.definitionMarkerSpaces);
        out.pushPrefix();
        out.addPrefix(childPrefix, true);
        out.setOptions(options);
        if (firstIsPara) {
            context.renderChildren(item, true, null);
        } else {
            context.processTextNodes(item, false);
        }
        out.line();
        out.popPrefix();
        context.popState(out);
    }

    private void processEmoji(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        if (element.hasAttr("alias")) {
            out.append(':').append(element.attr("alias")).append(':');
            return;
        }

        if (element.hasAttr("fallback-src")) {
            EmojiReference.Emoji emoji = EmojiShortcuts.getEmojiFromURI(element.attr("fallback-src"));
            if (emoji != null) {
                out.append(':').append(emoji.shortcut).append(':');
                return;
            }
        }

        context.renderDefault(element);
    }

    private void processEmphasis(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        context.processConditional(myHtmlConverterOptions.extInlineEmphasis, element, () -> {
            if (!myHtmlConverterOptions.preCodePreserveEmphasis && out.isPreFormatted()) {
                context.wrapTextNodes(element, "", false);
            } else {
                context.wrapTextNodes(element, myHtmlConverterOptions.extInlineEmphasis.isTextOnly() ? "" : "*", element.nextElementSibling() != null);
            }
        });
    }

    private void processHr(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        out.blankLine().append(myHtmlConverterOptions.thematicBreak).blankLine();
    }

    private void processImg(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        // see if it is an anchor or a link
        if (element.hasAttr("src")) {
            String src = element.attr("src");
            EmojiReference.Emoji emoji = EmojiShortcuts.getEmojiFromURI(src);

            // see if this is an emoji from Apple mail from pasted Markdown Navigator HTML mime
            if (emoji == null && element.hasAttr("alt")) {
                String emojiAlt = element.attr("alt");
                if (emojiAlt.startsWith(EMOJI_ALT_PREFIX)) {
                    // see if the full attribute is emoji
                    int pos = emojiAlt.indexOf(":", EMOJI_ALT_PREFIX.length());
                    if (pos > 0) {
                        // NOTE: category is not significant since it can change, so it is ignored.
                        // String category = emojiAlt.substring(EMOJI_ALT_PREFIX.length(), pos);
                        String shortcut = emojiAlt.substring(pos + 1);
                        emoji = EmojiShortcuts.getEmojiFromShortcut(shortcut);
                    }
                }
            }

            if (emoji != null && emoji.shortcut != null) {
                out.append(':').append(emoji.shortcut).append(':');
            } else {
                LinkConversion conv = myHtmlConverterOptions.extInlineImage;
                if (conv.isSuppressed()) return;

                if (conv.isParsed()) {
                    String alt = !element.hasAttr("alt") ? null
                            : element.attr("alt").trim().replace("[", "\\[").replace("]", "\\]");

                    if (alt != null && alt.isEmpty()) alt = null;

                    String title = !element.hasAttr("title") ? null
                            : element.attr("title").replace("\n", myHtmlConverterOptions.eolInTitleAttribute).replace("\"", "\\\"");
                    if (title != null && title.isEmpty()) title = null;

                    if (!conv.isTextOnly()) {
                        ResolvedLink resolvedLink = context.resolveLink(LinkType.IMAGE, src, false);
                        String useSrc = resolvedLink.getUrl();
                        int pos = useSrc.indexOf('?');
                        int eol = pos < 0 ? pos : useSrc.indexOf("%0A", pos);
                        boolean isMultiLineUrl = pos > 0 && eol > 0;
                        boolean handled = false;

                        if (conv.isReference() && !isMultiLineUrl) {
                            Reference reference = context.getOrCreateReference(useSrc, alt == null ? "image" : alt, title);
                            if (reference != null) {
                                handled = true;

                                if (alt == null || reference.getReference().equals(alt)) {
                                    // use reference as is
                                    out.append("![").append(reference.getReference()).append("][]");
                                } else {
                                    out.append("![").append(alt).append("][").append(reference.getReference()).append("]");
                                }
                            }
                        }

                        if (!handled) {
                            out.append("![");
                            if (alt != null) out.append(alt);
                            out.append(']').append('(');

                            if (isMultiLineUrl) {
                                out.append(useSrc, 0, pos + 1);
                                String decoded = Utils.urlDecode(useSrc.substring(pos + 1).replace("+", "%2B"), "UTF8");
                                out.line().append(decoded);
                            } else {
                                out.append(useSrc);
                            }

                            if (title != null) out.append(" \"").append(title).append('"');
                            out.append(")");
                        }
                    } else {
                        if (alt != null) out.append(alt);
                        else if (title != null) out.append(title);
                    }
                } else if (!conv.isSuppressed()) {
                    context.processWrapped(element, null, false);
                }
            }
        }
    }

    private void processInput(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        boolean isItemParagraph = false;

        Element firstElementSibling = element.firstElementSibling();
        if (firstElementSibling == null || element == firstElementSibling) {
            String tagName = element.parent().tagName();
            isItemParagraph = tagName.equalsIgnoreCase("li");
        }

        if (isItemParagraph) {
            if (element.hasAttr("type") && "checkbox".equalsIgnoreCase(element.attr("type"))) {
                if (element.hasAttr("checked")) {
                    out.append("[x] ");
                } else {
                    out.append("[ ] ");
                }
                return;
            }
        }
        context.renderDefault(element);
    }

    private void processIns(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        context.processConditional(myHtmlConverterOptions.extInlineIns, element, () -> {
            if (!myHtmlConverterOptions.preCodePreserveEmphasis && out.isPreFormatted()) {
                context.wrapTextNodes(element, "", false);
            } else {
                context.wrapTextNodes(element, myHtmlConverterOptions.extInlineIns.isTextOnly() ? "" : "++", element.nextElementSibling() != null);
            }
        });
    }

    private void processStrong(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        context.processConditional(myHtmlConverterOptions.extInlineStrong, element, () -> {
            if (!myHtmlConverterOptions.preCodePreserveEmphasis && out.isPreFormatted()) {
                context.wrapTextNodes(element, "", false);
            } else {
                context.wrapTextNodes(element, myHtmlConverterOptions.extInlineStrong.isTextOnly() ? "" : "**", element.nextElementSibling() != null);
            }
        });
    }

    private void processSub(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        context.processConditional(myHtmlConverterOptions.extInlineSub, element, () -> {
            if (myHtmlConverterOptions.extInlineSub.isTextOnly() || !myHtmlConverterOptions.preCodePreserveEmphasis && out.isPreFormatted()) {
                context.wrapTextNodes(element, "", false);
            } else {
                context.wrapTextNodes(element, "~", false);
            }
        });
    }

    private void processSup(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        context.processConditional(myHtmlConverterOptions.extInlineSup, element, () -> {
            if (myHtmlConverterOptions.extInlineSup.isTextOnly() || !myHtmlConverterOptions.preCodePreserveEmphasis && out.isPreFormatted()) {
                context.wrapTextNodes(element, "", false);
            } else {
                context.wrapTextNodes(element, "^", false);
            }
        });
    }

    private void processMath(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        context.processConditional(myHtmlConverterOptions.extMath, element, () -> {
            // TODO: implement math to markdown conversion
            boolean noWraps = myHtmlConverterOptions.extMath.isTextOnly();
            context.processTextNodes(element, false, noWraps ? "" : "$`", noWraps ? "" : "`$");
        });
    }

    // list processing
    private void handleListItem(HtmlNodeConverterContext context, HtmlMarkdownWriter out, Element item, ListState listState) {
        context.pushState(item);

        listState.itemCount++;
        CharSequence itemPrefix = listState.getItemPrefix(this.myHtmlConverterOptions);
        int count = myHtmlConverterOptions.listContentIndent ? itemPrefix.length() : myHtmlConverterOptions.listItemIndent;
        CharSequence childPrefix = RepeatedSequence.repeatOf(" ", count);

        out.line().append(itemPrefix);
        out.pushPrefix();
        out.addPrefix(childPrefix, true);
        int offset = out.offsetWithPending();
        context.renderChildren(item, true, null);
        if (offset == out.offsetWithPending()) {
            // completely empty, add space and make sure it is not suppressed
            int options = out.getOptions();
            out.setOptions(options & ~(LineAppendable.F_TRIM_TRAILING_WHITESPACE | LineAppendable.F_TRIM_LEADING_WHITESPACE));
            //out.append(' ');
            out.line();
            out.setOptions(options);
        } else {
            out.line();
        }
        out.popPrefix();
        context.popState(out);
    }

    private boolean hasListItemParent(Element element) {
        Element parent = element.parent();
        while (parent != null) {
            if (parent.tagName().equalsIgnoreCase("li")) {
                return true;
            }
            parent = parent.parent();
        }
        return false;
    }

    private boolean haveListItemAncestor(Node node) {
        Node parent = node.parent();
        while (parent != null) {
            if (parent.nodeName().toLowerCase().equals(FlexmarkHtmlConverter.LI_NODE)) {
                return true;
            }
            parent = parent.parent();
        }
        return false;
    }

    private void handleList(
            HtmlNodeConverterContext context, HtmlMarkdownWriter out,
            Element element,
            boolean isNumbered,
            boolean isFakeList,
            boolean isNestedList
    ) {
        if (!isFakeList) {
            context.pushState(element);

            if (!isNestedList && !haveListItemAncestor(context.getState().getParent()) && !isFirstChild(element)) {
                out.blankLine();
            }
        }

        Element previousElementSibling = element.previousElementSibling();
        String tag = previousElementSibling == null ? null : previousElementSibling.tagName().toUpperCase();
        if (tag != null && tag.equals(element.tagName().toUpperCase()) && (tag.equals("UL") || tag.equals("OL"))) {
            if (myHtmlConverterOptions.listsEndOnDoubleBlank) {
                out.blankLine(2);
            } else {
                out.line().append("<!-- -->").blankLine();
            }
        }

        ListState listState = new ListState(isNumbered);

        if (listState.isNumbered && element.hasAttr("start")) {
            try {
                int i = Integer.parseInt(element.attr("start"));
                listState.itemCount = i - 1; // it will be pre-incremented before output
            } catch (NumberFormatException ignored) {
            }
        }

        Node item = element;
        boolean hadListItem = false;

        do {
            boolean isNumberedList = false;

            switch (item.nodeName().toLowerCase()) {
                case FlexmarkHtmlConverter.LI_NODE:
                    handleListItem(context, out, (Element) item, listState);
                    hadListItem = true;
                    break;

                case FlexmarkHtmlConverter.P_NODE:
                    if (item.childNodeSize() > 0) {
                        handleListItem(context, out, (Element) item, listState);
                    }
                    break;

                case FlexmarkHtmlConverter.OL_NODE:
                    isNumberedList = true;

                case FlexmarkHtmlConverter.UL_NODE:
                    if (item != element && item.childNodeSize() > 0) {
                        if (hadListItem) {
                            CharSequence itemPrefix = listState.getItemPrefix(this.myHtmlConverterOptions);
                            int count = myHtmlConverterOptions.listContentIndent ? itemPrefix.length() : 4;
                            CharSequence childPrefix = RepeatedSequence.repeatOf(" ", count);
                            //out.line().append(itemPrefix);
                            out.pushPrefix();
                            out.addPrefix(childPrefix, true);
                        }

                        handleList(context, out, (Element) item, isNumberedList, false, true);

                        if (hadListItem) {
                            out.popPrefix();
                        }
                    }
                    break;

                default:
                    //context.processWrapped(item, true, false);
                    context.render(item);
                    break;
            }
        } while ((item = context.next()) != null);

        if (!isNestedList && element.nextElementSibling() != null) {
            out.blankLine();
        }

        if (!isFakeList) {
            context.popState(out);
        }
    }

    private void processLi(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        handleList(context, out, element, false, true, false);
    }

    private void processOl(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        handleList(context, out, element, true, false, false);
    }

    private void processUl(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        handleList(context, out, element, false, false, false);
    }

    private void processSvg(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        if (element.hasClass("octicon")) {
        } else {
            context.renderDefault(element);
        }
    }

    private void processP(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        boolean isItemParagraph = false;
        boolean isDefinitionItemParagraph = false;

        Element firstElementSibling = element.firstElementSibling();
        if (firstElementSibling == null || element == firstElementSibling) {
            String tagName = element.parent().tagName();
            isItemParagraph = tagName.equalsIgnoreCase("li");
            isDefinitionItemParagraph = tagName.equalsIgnoreCase("dd");
        }

        out.blankLineIf(!(isItemParagraph || isDefinitionItemParagraph || isFirstChild(element)));

        if (element.childNodeSize() == 0) {
            if (myHtmlConverterOptions.brAsExtraBlankLines) {
                out.append("<br />").blankLine();
            }
        } else {
            context.processTextNodes(element, false);
        }

        out.line();

        if (isItemParagraph || isDefinitionItemParagraph) {
            out.tailBlankLine();
        }
    }

    private void processHeading(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        int level;
        boolean skipHeading = false;
        switch (element.nodeName().toLowerCase()) {
            case FlexmarkHtmlConverter.H1_NODE:
                level = 1;
                skipHeading = myHtmlConverterOptions.skipHeading1;
                break;
            case FlexmarkHtmlConverter.H2_NODE:
                level = 2;
                skipHeading = myHtmlConverterOptions.skipHeading2;
                break;
            case FlexmarkHtmlConverter.H3_NODE:
                level = 3;
                skipHeading = myHtmlConverterOptions.skipHeading3;
                break;
            case FlexmarkHtmlConverter.H4_NODE:
                level = 4;
                skipHeading = myHtmlConverterOptions.skipHeading4;
                break;
            case FlexmarkHtmlConverter.H5_NODE:
                level = 5;
                skipHeading = myHtmlConverterOptions.skipHeading5;
                break;

            case FlexmarkHtmlConverter.H6_NODE:
            default:
                level = 6;
                skipHeading = myHtmlConverterOptions.skipHeading6;
                break;
        }

        String headingText = context.processTextNodes(element).trim();
        if (!headingText.isEmpty()) {
            out.blankLine();
            if (skipHeading) {
                out.append(headingText);
            } else {
                if (myHtmlConverterOptions.setextHeadings && level <= 2) {
                    out.append(headingText);
                    int extraChars = context.outputAttributes(out, " ");
                    out.line().append(level == 1 ? '=' : '-', minLimit(headingText.length() + extraChars, myHtmlConverterOptions.minSetextHeadingMarkerLength));
                } else {
                    out.append('#', level).append(' ');
                    out.append(headingText);
                    context.outputAttributes(out, " ");
                }
                out.blankLine();
            }
        }
    }

    private void processPre(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        context.pushState(element);

        String text;
        boolean hadCode = false;
        String className = "";

        HtmlNodeConverterContext preText = context.getSubContext();
        preText.getMarkdown().setOptions(out.getOptions() & ~(LineAppendable.F_COLLAPSE_WHITESPACE | LineAppendable.F_TRIM_TRAILING_WHITESPACE));
        preText.getMarkdown().openPreFormatted(false);

        Node next;
        while ((next = context.next()) != null) {
            if (next.nodeName().equalsIgnoreCase("code") || next.nodeName().equalsIgnoreCase("tt")) {
                hadCode = true;
                Element code = (Element) next;
                //text = code.toString();
                preText.inlineCode(() -> preText.renderChildren(code, false, null));
                if (className.isEmpty()) className = Utils.removePrefix(code.className(), "language-");
            } else if (next.nodeName().equalsIgnoreCase("br")) {
                preText.getMarkdown().append("\n");
            } else if (next.nodeName().equalsIgnoreCase("#text")) {
                preText.getMarkdown().append(((TextNode) next).getWholeText());
            } else {
                preText.renderChildren(next, false, null);
            }
        }

        preText.getMarkdown().closePreFormatted();
        text = preText.getMarkdown().toString(Integer.MAX_VALUE, 2);

        //int start = text.indexOf('>');
        //int end = text.lastIndexOf('<');
        //text = text.substring(start + 1, end);
        //text = Escaping.unescapeHtml(text);

        int backTickCount = getMaxRepeatedChars(text, '`', 3);
        CharSequence backTicks = RepeatedSequence.repeatOf("`", backTickCount);

        if (!myHtmlConverterOptions.skipFencedCode && (!className.isEmpty() || text.trim().isEmpty() || !hadCode)) {
            out.blankLine().append(backTicks);
            if (!className.isEmpty()) {
                out.append(className);
            }
            out.line();
            out.openPreFormatted(true);
            out.append(text.isEmpty() ? "\n" : text);
            out.closePreFormatted();
            out.line().append(backTicks).line();
            out.tailBlankLine();
        } else {
            // we indent the whole thing by 4 spaces
            out.blankLine();
            out.pushPrefix();
            out.addPrefix(myHtmlConverterOptions.codeIndent);
            out.openPreFormatted(true);
            out.append(text.isEmpty() ? "\n" : text);
            out.closePreFormatted();
            out.line();
            out.tailBlankLine();
            out.popPrefix();
        }

        context.popState(out);
    }

    private void processTable(Element table, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        MarkdownTable oldTable = myTable;

        context.pushState(table);

        myTable = new MarkdownTable("", myHtmlConverterOptions.tableOptions);
        myTableSuppressColumns = false;

        Node item;
        while ((item = context.next()) != null) {
            String nodeName = item.nodeName().toLowerCase();
            switch (nodeName) {
                case FlexmarkHtmlConverter.CAPTION_NODE:
                    handleTableCaption((Element) item, context, out);
                    break;
                case FlexmarkHtmlConverter.TBODY_NODE:
                    myTable.setHeader(false);
                    handleTableSection(context, out, (Element) item);
                    break;
                case FlexmarkHtmlConverter.THEAD_NODE:
                    myTable.setHeader(true);
                    handleTableSection(context, out, (Element) item);
                    break;
                case FlexmarkHtmlConverter.TR_NODE:
                    Element tableRow = (Element) item;
                    Elements children = tableRow.children();
                    myTable.setHeader(!children.isEmpty() && children.get(0).tagName().equalsIgnoreCase("th"));
                    handleTableRow(context, out, (Element) item);
                    break;
            }
        }

        myTable.finalizeTable();
        int sepColumns = myTable.getMaxColumns();

        if (sepColumns > 0) {
            out.blankLine();
            myTable.appendTable(out);
            out.tailBlankLine();
        }

        myTable = oldTable;
        context.popState(out);
    }

    private void handleTableSection(HtmlNodeConverterContext context, HtmlMarkdownWriter out, Element element) {
        context.pushState(element);

        Node node;
        while ((node = context.next()) != null) {
            if (node.nodeName().equalsIgnoreCase(FlexmarkHtmlConverter.TR_NODE)) {
                Element tableRow = (Element) node;
                Elements children = tableRow.children();
                boolean wasHeading = myTable.getHeader();
                if (!children.isEmpty()) {
                    if (children.get(0).tagName().equalsIgnoreCase(FlexmarkHtmlConverter.TH_NODE)) {
                        myTable.setHeader(true);
                    }
                }
                if (myTable.getHeader() && myTable.body.rows.size() > 0) {
                    if (myHtmlConverterOptions.ignoreTableHeadingAfterRows) {
                        // ignore it
                        myTableSuppressColumns = true;
                    } else {
                        myTable.setHeader(false);
                    }
                }
                handleTableRow(context, out, tableRow);
                myTableSuppressColumns = false;
                myTable.setHeader(wasHeading);
            }
        }

        context.popState(out);
    }

    private void handleTableRow(HtmlNodeConverterContext context, HtmlMarkdownWriter out, Element element) {
        context.pushState(element);

        Node node;
        while ((node = context.next()) != null) {
            switch (node.nodeName().toLowerCase()) {
                case FlexmarkHtmlConverter.TH_NODE:
                case FlexmarkHtmlConverter.TD_NODE:
                    handleTableCell((Element) node, context, out);
                    break;

                default:
                    //context.processWrapped(element, true, false);
                    break;
            }
        }

        myTable.nextRow();
        context.popState(out);
    }

    private void handleTableCaption(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        myTable.setCaption(context.processTextNodes(element).trim());
    }

    private void handleTableCell(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        String cellText = replaceMultipleBlankSpace(context.processTextNodes(element).trim());
        int colSpan = 1;
        int rowSpan = 1;
        CellAlignment alignment = null;

        if (element.hasAttr("colSpan")) {
            try {
                colSpan = Integer.parseInt(element.attr("colSpan"));
            } catch (NumberFormatException ignored) {

            }
        }

        if (element.hasAttr("rowSpan")) {
            try {
                rowSpan = Integer.parseInt(element.attr("rowSpan"));
            } catch (NumberFormatException ignored) {

            }
        }

        if (element.hasAttr("align")) {
            alignment = CellAlignment.getAlignment(element.attr("align"));
        } else {
            // see if has class that matches
            Set<String> classNames = element.classNames();
            if (!classNames.isEmpty()) {
                for (String clazz : classNames) {
                    CellAlignment cellAlignment = myHtmlConverterOptions.tableCellAlignmentMap.get(clazz);
                    if (cellAlignment != null) {
                        alignment = cellAlignment;
                        break;
                    }
                }

                if (alignment == null) {
                    // see if we have matching patterns
                    for (Object key : myHtmlConverterOptions.tableCellAlignmentMap.keySet()) {
                        if (key instanceof Pattern) {
                            Pattern pattern = (Pattern) key;
                            for (String clazz : classNames) {
                                if (pattern.matcher(clazz).find()) {
                                    // have a match
                                    alignment = myHtmlConverterOptions.tableCellAlignmentMap.get(key);
                                    break;
                                }
                            }

                            if (alignment != null) break;
                        }
                    }
                }
            }
        }

        // skip cells defined by row spans in previous rows
        if (!myTableSuppressColumns) {
            myTable.addCell(new TableCell(null, BasedSequence.NULL, cellText.replace("\n", " "), BasedSequence.NULL, rowSpan, colSpan, alignment));
        }
    }

    private String replaceMultipleBlankSpace(String cellText) {
        StringBuilder result = new StringBuilder();
        boolean wasSpace = false;

        for (char c : cellText.toCharArray()) {
            if (Character.isWhitespace(c)) {
                if (!wasSpace) {
                    result.append(' ');
                    wasSpace = true;
                }
            } else {
                result.append(c);
                wasSpace = false;
            }
        }

        return result.toString();
    }

    private boolean matchingText(Pattern pattern, String text, String[] match) {
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            if (matcher.groupCount() > 0) {
                match[0] = matcher.group(1);
            } else {
                match[0] = matcher.group();
            }
            return true;
        }
        return false;
    }

    private String convertNumeric(String text) {
        text = text.trim();

        if (RomanNumeral.LIMITED_ROMAN_NUMERAL.matcher(text).matches() || RomanNumeral.LIMITED_LOWERCASE_ROMAN_NUMERAL.matcher(text).matches()) {
            RomanNumeral numeral = new RomanNumeral(text);
            return String.valueOf(numeral.toInt());
        } else if (ALPHA_NUMERAL_PAT.matcher(text).matches()) {
            int value = 0;
            text = text.toUpperCase();
            int iMax = text.length();
            for (int i = 0; i < iMax; i++) {
                char c = text.charAt(i);
                value *= 'Z' - 'A' + 1;
                value += c - 'A' + 1;
            }
            return String.valueOf(value);
        }
        return "1";
    }

    private void processUnwrapped(Node node, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        context.processUnwrapped(node);
    }

    private void processWrapped(Node node, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        context.processWrapped(node, false, false);
    }

    private void processSpan(Element element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        // unwrap and process content
        if (element.hasAttr("style")) {
            String style = element.attr("style");
            if (style.equals("mso-list:Ignore")) {
                String[] match = new String[] { "1" };
                String text = context.processTextNodes(element);
                if (matchingText(NUMERIC_DOT_LIST_PAT, text, match)) {
                    out.append(text).append(' ');
                } else if (matchingText(NUMERIC_PAREN_LIST_PAT, text, match)) {
                    if (myHtmlConverterOptions.dotOnlyNumericLists) {
                        out.append(match[0]).append(". ");
                    } else {
                        out.append(match[0]).append(") ");
                    }
                } else if (matchingText(NON_NUMERIC_DOT_LIST_PAT, text, match)) {
                    out.append(convertNumeric(match[0])).append(". ");
                    if (myHtmlConverterOptions.commentOriginalNonNumericListItem) out.append(" <!-- ").append(match[0]).append(" -->");
                } else if (matchingText(NON_NUMERIC_PAREN_LIST_PAT, text, match)) {
                    if (myHtmlConverterOptions.dotOnlyNumericLists) {
                        out.append(convertNumeric(match[0])).append(". ");
                        if (myHtmlConverterOptions.commentOriginalNonNumericListItem) out.append(" <!-- ").append(match[0]).append(" -->");
                    } else {
                        out.append(convertNumeric(match[0])).append(") ");
                        if (myHtmlConverterOptions.commentOriginalNonNumericListItem) out.append(" <!-- ").append(match[0]).append(" -->");
                    }
                } else if (BULLET_LIST_PAT.matcher(text).matches()) {
                    out.append("* ");
                } else {
                    out.append("* ").append(text);
                }
                context.transferIdToParent();
                return;
            }
        }

        context.renderChildren(element, true, context::transferIdToParent);
    }

    private void processComment(Comment element, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        if (myHtmlConverterOptions.renderComments) {
            out.append("<!--").append(element.getData()).append("-->");
        }
    }

    private void processText(TextNode node, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
        if (out.isPreFormatted()) {
            out.append(context.prepareText(node.getWholeText(), true));
        } else {
            String text = context.prepareText(node.text());
            if (out.offsetWithPending() != 0 || !text.trim().isEmpty()) {
                out.append(text);
            }
        }
    }
}
