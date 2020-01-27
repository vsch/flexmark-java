package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ext.toc.internal.SimTocOptionsParser;
import com.vladsch.flexmark.ext.toc.internal.TocOptions;
import com.vladsch.flexmark.ext.toc.internal.TocOptionsParser;
import com.vladsch.flexmark.formatter.MarkdownWriter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.TextCollectingVisitor;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.misc.DelimitedBuilder;
import com.vladsch.flexmark.util.misc.Extension;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.misc.Paired;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.LineAppendable;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class TocUtils {
    final public static AttributablePart TOC_CONTENT = new AttributablePart("TOC_CONTENT");
    final public static AttributablePart TOC_LIST = new AttributablePart("TOC_LIST");

    public static String getTocPrefix(TocOptions options, TocOptions defaultOptions) {
        DelimitedBuilder out = new DelimitedBuilder(" ");
        out.append("[TOC").mark();

        TocOptionsParser optionsParser = new TocOptionsParser();
        out.append(optionsParser.getOptionText(options, defaultOptions));
        out.unmark().append("]");
        out.append("\n").unmark();
        return out.toString();
    }

    public static String getSimTocPrefix(TocOptions options, TocOptions defaultOptions) {
        DelimitedBuilder out = new DelimitedBuilder(" ");
        out.append("[TOC").mark();

        SimTocOptionsParser optionsParser = new SimTocOptionsParser();
        out.append(optionsParser.getOptionText(options, defaultOptions));
        out.unmark().append("]:").mark().append('#').mark();

        String optionTitleHeading = options.getTitleHeading();
        String optionTitle = options.title;

        if (defaultOptions == null || !optionTitleHeading.equals(defaultOptions.getTitleHeading())) {
            if (!optionTitle.isEmpty()) {
                out.append('"');
                if (defaultOptions == null || options.titleLevel != defaultOptions.titleLevel) {
                    out.append(optionTitleHeading.trim().replace("\\", "\\\\").replace("\"", "\\\""));
                } else {
                    out.append(optionTitle.trim().replace("\\", "\\\\").replace("\"", "\\\""));
                }
                out.append('"').mark();
            } else {
                out.append("\"\"").mark();
            }
        }

        out.unmark().append("\n").unmark();
        return out.toString();
    }

    public static void renderTocContent(MarkdownWriter markdown, TocOptions options, TocOptions defaultOptions, List<Heading> headings, List<String> headingTexts) {
        if (headings.isEmpty()) return;

        Document document = headings.get(0).getDocument();

        if (options.isHtml) {
            MarkdownWriter out = new MarkdownWriter(markdown.getOptions());
            for (Heading heading : headings) {
                out.append(heading.getChars()).line();
            }
            out.append(getTocPrefix(options, defaultOptions));

            MutableDataHolder options1 = new MutableDataSet(document);
            defaultOptions.setIn(options1);
            options.setIn(options1);

            if (!options1.contains(HtmlRenderer.INDENT_SIZE)) options1.set(HtmlRenderer.INDENT_SIZE, 2);
            options1.set(HtmlRenderer.RENDER_HEADER_ID, false);
            options1.set(HtmlRenderer.GENERATE_HEADER_ID, false);

            ArrayList<Extension> extensions = new ArrayList<>(Parser.EXTENSIONS.get(options1));
            extensions.removeIf(extension -> extension instanceof SimTocExtension);
            extensions.add(TocExtension.create());
            options1.set(Parser.EXTENSIONS, extensions);

            Parser parser = Parser.builder(options1).build();
            HtmlRenderer htmlRenderer = HtmlRenderer.builder(options1).build();

            Document tocDocument = parser.parse(out.toString());
            // copy ref ids to make sure they are the same
            int i = 0;
            for (Node node : tocDocument.getChildren()) {
                if (node instanceof Heading) {
                    ((Heading) node).setAnchorRefId(headings.get(i).getAnchorRefId());
                    i++;
                }
            }

            Node toc = tocDocument.getFirstChildAny(TocBlock.class);
            assert toc != null;

            markdown.openPreFormatted(false);
            htmlRenderer.render(toc, markdown);
            markdown.closePreFormatted();
        } else {
            String heading = options.getTitleHeading();
            if (!heading.isEmpty()) {
                markdown.append(heading);
            }

            if (Parser.PARSER_EMULATION_PROFILE.get(document).family == ParserEmulationProfile.FIXED_INDENT) {
                markdown.setIndentPrefix(RepeatedSequence.ofSpaces(4));
            } else {
                markdown.setIndentPrefix(RepeatedSequence.ofSpaces(options.isNumbered ? 3 : 2));
            }

            renderMarkdownToc(markdown, headings.stream().map(Heading::getLevel).collect(Collectors.toList()), headingTexts, options);
        }
    }

    public static void renderHtmlToc(HtmlWriter out, BasedSequence sourceText, List<Integer> headings, List<String> headingTexts, List<String> headingRefIds, TocOptions tocOptions) {
        if (headings.size() > 0 && (sourceText.isNotNull() || !tocOptions.title.trim().isEmpty())) {
            if (sourceText.isNotNull()) out.srcPos(sourceText);
            out.attr(Attribute.CLASS_ATTR, tocOptions.divClass).withAttr(TOC_CONTENT).tag("div").line().indent();
            if (!tocOptions.title.trim().isEmpty()) {
                out.tag("h" + tocOptions.titleLevel).text(tocOptions.title).tag("/h" + tocOptions.titleLevel).line();
            }
        }

        int initLevel = -1;
        int lastLevel = -1;
        String listOpen = tocOptions.isNumbered ? "ol" : "ul";
        String listClose = "/" + listOpen;
        boolean[] openedItems = new boolean[7];
        boolean[] openedList = new boolean[7];
        int[] openedItemAppendCount = new int[7];

        for (int i = 0; i < headings.size(); i++) {
            String headerText = headingTexts.get(i);
            int headerLevel = tocOptions.listType != TocOptions.ListType.HIERARCHY ? 1 : headings.get(i);

            if (initLevel == -1) {
                initLevel = headerLevel;
                lastLevel = headerLevel;
                out.attr(Attribute.CLASS_ATTR, tocOptions.listClass).withAttr(TOC_LIST).line().tag(listOpen).indent().line();
                openedList[0] = true;
            }

            if (lastLevel < headerLevel) {
                for (int lv = lastLevel; lv < headerLevel; lv++) {
                    openedItems[lv + 1] = false;
                    openedList[lv + 1] = false;
                }

                if (!openedList[lastLevel]) {
                    out.withAttr().indent().line().tag(listOpen).indent();
                    openedList[lastLevel] = true;
                }
            } else if (lastLevel == headerLevel) {
                if (openedItems[lastLevel]) {
                    if (openedList[lastLevel]) out.unIndent().tag(listClose).line();
                    out.lineIf(openedItemAppendCount[lastLevel] != out.offsetWithPending()).tag("/li").line();
                }
                openedItems[lastLevel] = false;
                openedList[lastLevel] = false;
            } else {
                // lastLevel > headerLevel
                for (int lv = lastLevel; lv >= headerLevel; lv--) {
                    if (openedItems[lv]) {
                        if (openedList[lv]) out.unIndent().tag(listClose).unIndent().line();
                        out.lineIf(openedItemAppendCount[lastLevel] != out.offsetWithPending()).tag("/li").line();
                    }
                    openedItems[lv] = false;
                    openedList[lv] = false;
                }
            }

            out.line().tag("li");
            openedItems[headerLevel] = true;
            String headerId = headingRefIds.get(i);
            if (headerId == null || headerId.isEmpty()) {
                out.raw(headerText);
            } else {
                out.attr("href", "#" + headerId).withAttr().tag("a");
                out.raw(headerText);
                out.tag("/a");
            }

            lastLevel = headerLevel;
            openedItemAppendCount[headerLevel] = out.offsetWithPending();
        }

        for (int i = lastLevel; i >= 1; i--) {
            if (openedItems[i]) {
                if (openedList[i]) out.unIndent().tag(listClose).unIndent().line();
                out.lineIf(openedItemAppendCount[lastLevel] != out.offsetWithPending()).tag("/li").line();
            }
        }

        // close original list
        if (openedList[0]) out.unIndent().tag(listClose).line();

        if (headings.size() > 0 && (sourceText.isNotNull() || !tocOptions.title.trim().isEmpty())) {
            out.line().unIndent().tag("/div");
        }

        out.line();
    }

    public static List<Heading> filteredHeadings(List<Heading> headings, TocOptions tocOptions) {
        ArrayList<Heading> filteredHeadings = new ArrayList<>(headings.size());

        for (Heading header : headings) {
            if (tocOptions.isLevelIncluded(header.getLevel()) && !(header.getParent() instanceof SimTocContent)) {
                filteredHeadings.add(header);
            }
        }

        return filteredHeadings;
    }

    public static Paired<List<Heading>, List<String>> htmlHeadingTexts(NodeRendererContext context, List<Heading> headings, TocOptions tocOptions) {
        final List<String> headingContents = new ArrayList<>(headings.size());
        final boolean isReversed = tocOptions.listType == TocOptions.ListType.SORTED_REVERSED || tocOptions.listType == TocOptions.ListType.FLAT_REVERSED;
        final boolean isSorted = tocOptions.listType == TocOptions.ListType.SORTED || tocOptions.listType == TocOptions.ListType.SORTED_REVERSED;
        final boolean needText = isReversed || isSorted;
        final HashMap<String, Heading> headingNodes = !needText ? null : new HashMap<>(headings.size());
        final HashMap<String, String> headingTexts = !needText || tocOptions.isTextOnly ? null : new HashMap<>(headings.size());

        for (Heading heading : headings) {
            String headingContent;
            // need to skip anchor links but render emphasis
            if (tocOptions.isTextOnly) {
                headingContent = getHeadingText(heading);
            } else {
                headingContent = getHeadingContent(context, heading);

                if (needText) {
                    headingTexts.put(headingContent, getHeadingText(heading));
                }
            }

            if (needText) {
                headingNodes.put(headingContent, heading);
            }

            headingContents.add(headingContent);
        }

        if (isSorted || isReversed) {
            if (tocOptions.isTextOnly) {
                if (isSorted) {
                    headingContents.sort((heading1, heading2) -> isReversed ? heading2.compareTo(heading1) : heading1.compareTo(heading2));
                } else {
                    Collections.reverse(headingContents);
                }
            } else {
                if (isSorted) {
                    headingContents.sort((heading1, heading2) -> {
                        final String headingText1 = headingTexts.get(heading1);
                        final String headingText2 = headingTexts.get(heading2);
                        return isReversed ? headingText2.compareTo(headingText1) : headingText1.compareTo(headingText2);
                    });
                } else {
                    Collections.reverse(headingContents);
                }
            }

            headings = new ArrayList<>();
            for (String headingContent : headingContents) {
                headings.add(headingNodes.get(headingContent));
            }
        }

        return Pair.of(headings, headingContents);
    }

    private static String getHeadingText(Heading header) {
        return Escaping.escapeHtml(new TextCollectingVisitor().collectAndGetText(header), false);
    }

    private static String getHeadingContent(NodeRendererContext context, Heading header) {
        NodeRendererContext subContext = context.getSubContext(false);
        subContext.doNotRenderLinks();
        subContext.renderChildren(header);
        return ((LineAppendable) subContext.getHtmlWriter()).toString(-1, -1);
    }

    public static Pair<List<Heading>, List<String>> markdownHeaderTexts(List<Heading> headings, TocOptions tocOptions) {
        final List<String> headingContents = new ArrayList<>(headings.size());
        final boolean isReversed = tocOptions.listType == TocOptions.ListType.SORTED_REVERSED || tocOptions.listType == TocOptions.ListType.FLAT_REVERSED;
        final boolean isSorted = tocOptions.listType == TocOptions.ListType.SORTED || tocOptions.listType == TocOptions.ListType.SORTED_REVERSED;
        final boolean needText = isReversed || isSorted;
        final HashMap<String, Heading> headingNodes = !needText ? null : new HashMap<>(headings.size());
        final HashMap<String, String> headingTexts = !needText || tocOptions.isTextOnly ? null : new HashMap<>(headings.size());

        for (Heading heading : headings) {
            String headingContent;
            // need to skip anchor links but render emphasis
            String headingText = tocOptions.isTextOnly || needText ? new TextCollectingVisitor().collectAndGetText(heading) : "";

            if (tocOptions.isTextOnly) {
                headingContent = headingText;
            } else {
                headingContent = heading.getText().toString();
            }

            String headerId = heading.getAnchorRefId();
            String headerLink;
            if (headerId == null || headingContent.isEmpty()) {
                headerLink = headingContent;
            } else {
                headerLink = "[" + headingContent + "](#" + headerId + ")";
            }

            if (needText) {
                if (!tocOptions.isTextOnly) headingTexts.put(headerLink, headingText);
                headingNodes.put(headerLink, heading);
            }

            headingContents.add(headerLink);
        }

        if (isSorted || isReversed) {
            if (tocOptions.isTextOnly) {
                if (isSorted) {
                    headingContents.sort((heading1, heading2) -> isReversed ? heading2.compareTo(heading1) : heading1.compareTo(heading2));
                } else {
                    Collections.reverse(headingContents);
                }
            } else {
                if (isSorted) {
                    headingContents.sort((heading1, heading2) -> {
                        final String headingText1 = headingTexts.get(heading1);
                        final String headingText2 = headingTexts.get(heading2);
                        return isReversed ? headingText2.compareTo(headingText1) : headingText1.compareTo(headingText2);
                    });
                } else {
                    Collections.reverse(headingContents);
                }
            }

            headings = new ArrayList<>();
            for (String headingContent : headingContents) {
                headings.add(headingNodes.get(headingContent));
            }
        }

        return Pair.of(headings, headingContents);
    }

    public static void renderMarkdownToc(MarkdownWriter out, List<Integer> headings, List<String> headingTexts, final TocOptions tocOptions) {
        int initLevel = -1;
        int lastLevel = -1;
        boolean[] openedItems = new boolean[7];
        boolean[] openedList = new boolean[7];
        int[] openedItemAppendCount = new int[7];

        for (int i = 0; i < headings.size(); i++) {
            String headerText = headingTexts.get(i);
            int headerLevel = tocOptions.listType != TocOptions.ListType.HIERARCHY ? 1 : headings.get(i);

            if (initLevel == -1) {
                initLevel = headerLevel;
                lastLevel = headerLevel;
                out.line();
                openedList[0] = true;
            }

            if (lastLevel < headerLevel) {
                for (int lv = lastLevel; lv < headerLevel; lv++) {
                    openedItems[lv + 1] = false;
                    openedList[lv + 1] = false;
                }

                if (!openedList[lastLevel]) {
                    out.indent();
                    openedList[lastLevel] = true;
                }
            } else if (lastLevel == headerLevel) {
                if (openedItems[lastLevel]) {
                    if (openedList[lastLevel]) out.unIndent();
                    out.lineIf(openedItemAppendCount[lastLevel] != out.offsetWithPending()).line();
                }
                openedItems[lastLevel] = false;
                openedList[lastLevel] = false;
            } else {
                // lastLevel > headerLevel
                for (int lv = lastLevel; lv >= headerLevel; lv--) {
                    if (openedItems[lv]) {
                        if (openedList[lv]) out.unIndent();
                        out.lineIf(openedItemAppendCount[lastLevel] != out.offsetWithPending()).line();
                    }
                    openedItems[lv] = false;
                    openedList[lv] = false;
                }
            }

            out.line().append(tocOptions.isNumbered ? "1. " : "- ");
            openedItems[headerLevel] = true;
            out.append(headerText);
            lastLevel = headerLevel;
            openedItemAppendCount[headerLevel] = out.offsetWithPending();
        }

        for (int i = lastLevel; i >= 1; i--) {
            if (openedItems[i]) {
                if (openedList[i]) out.unIndent();
                out.lineIf(openedItemAppendCount[lastLevel] != out.offsetWithPending()).line();
            }
        }

        out.line();
    }
}
