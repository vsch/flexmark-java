package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ext.toc.SimTocContent;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.TextCollectingAppendable;
import com.vladsch.flexmark.internal.util.Computable;
import com.vladsch.flexmark.internal.util.Escaping;
import com.vladsch.flexmark.internal.util.TextCollectingVisitor;
import com.vladsch.flexmark.internal.util.ValueRunnable;
import com.vladsch.flexmark.internal.util.options.DelimitedBuilder;
import com.vladsch.flexmark.node.Heading;

import java.util.ArrayList;
import java.util.List;

public class TocUtils {
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
                    out.append(optionTitleHeading);
                } else {
                    out.append(optionTitle);
                }
                out.append('"').mark();
            } else {
                out.append("\"\"").mark();
            }
        }

        out.unmark().append("\n").unmark();
        return out.toString();
    }

    public static void renderTocContent(HtmlWriter out, String tocContents, TocOptions options, List<Heading> headings, List<String> headingTexts) {
        if (headings.isEmpty()) return;

        if (options.isHtml) {
            renderHtmlToc(out, headings, headingTexts, options);
        } else {
            renderMarkdownToc(out, headings, headingTexts, options);
        }
    }

    public static void renderHtmlToc(HtmlWriter html, List<Heading> headings, List<String> headingTexts, TocOptions tocOptions) {
        if (headings.size() > 0 && !tocOptions.title.isEmpty()) {
            html.tag("div");
            html.tag("h" + tocOptions.titleLevel).text(tocOptions.title).tag("/h" + tocOptions.titleLevel).line().indent();
        }

        int initLevel = -1;
        int lastLevel = -1;
        String listOpen = tocOptions.isNumbered ? "ol" : "ul";
        String listClose = "/" + listOpen;
        boolean[] openedItems = new boolean[7];

        for (int i = 0; i < headings.size(); i++) {
            Heading header = headings.get(i);
            String headerText = headingTexts.get(i);
            int headerLevel = header.getLevel();

            if (initLevel == -1) {
                initLevel = headerLevel;
                lastLevel = headerLevel;
                html.withAttr().line().tag(listOpen).indent();
            }

            if (lastLevel < headerLevel) {
                for (int lv = lastLevel; lv < headerLevel; lv++) {
                    openedItems[lv + 1] = false;
                }
                html.withAttr().line().tag(listOpen).indent();
            } else if (lastLevel == headerLevel) {
                if (openedItems[lastLevel]) html.tag("/li").line();
                openedItems[lastLevel] = false;
            } else {
                for (int lv = lastLevel; lv >= headerLevel + 1; lv--) {
                    if (openedItems[lv]) html.unIndent().tag(listClose).tag("/li").line();
                    openedItems[lv] = false;
                }
            }

            html.line().tag("li");
            openedItems[headerLevel] = true;
            String headerId = header.getAnchorRefId();
            if (headerId == null || headerId.isEmpty()) {
                html.raw(headerText);
            } else {
                html.attr("href", "#" + headerId).withAttr().tag("a");
                html.raw(headerText);
                html.tag("/a");
            }
            lastLevel = headerLevel;
        }

        for (int i = lastLevel; i >= 1; i--) {
            if (openedItems[i]) {
                html.tag("/li");
                html.unIndent().tag(listClose);
            }
        }

        if (headings.size() > 0 && !tocOptions.title.isEmpty()) {
            html.line().unIndent().tag("/div");
        }

        html.line();
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

    public static List<String> htmlHeaderTexts(NodeRendererContext context, List<Heading> headings, TocOptions tocOptions) {
        ArrayList<String> headerTexts = new ArrayList<>(headings.size());

        for (Heading header : headings) {
            String headerText;
            boolean isRaw;
            // need to skip anchor links but render emphasis
            if (tocOptions.isTextOnly) {
                headerText = Escaping.escapeHtml(new TextCollectingVisitor().visitAndGetText(header), false);
                isRaw = false;
            } else {
                TextCollectingAppendable out = new TextCollectingAppendable();
                NodeRendererContext subContext = context.getSubContext(out, false);
                subContext.doNotRenderLinks();
                subContext.renderChildren(header);
                headerText = out.getHtml();
            }
            headerTexts.add(headerText);
        }

        return headerTexts;
    }

    public static List<String> markdownHeaderTexts(List<Heading> headings, TocOptions tocOptions) {
        ArrayList<String> headingTexts = new ArrayList<String>(headings.size());
        for (Heading header : headings) {
            String headerText;
            // need to skip anchor links but render emphasis
            if (tocOptions.isTextOnly) {
                headerText = new TextCollectingVisitor().visitAndGetText(header);
            } else {
                headerText = header.getChars().toString();
            }

            String headerId = header.getAnchorRefId();
            String headerLink;
            if (headerId == null || headerText.isEmpty()) {
                headerLink = headerText;
            } else {
                headerLink = "[" + headerText + "](#" + headerId + ")";
            }

            headingTexts.add(headerLink);
        }

        return headingTexts;
    }

    public static void renderMarkdownToc(HtmlWriter html, List<Heading> headings, List<String> headingTexts, TocOptions tocOptions) {
        int initLevel = -1;
        int lastLevel = -1;
        int[] headingNumbers = new int[7];
        boolean[] openedItems = new boolean[7];

        Computable<String, Integer> listOpen = level -> {
            openedItems[level] = true;
            if (tocOptions.isNumbered) {
                int v = ++headingNumbers[level];
                return v + ". ";
            } else {
                return "- ";
            }
        };

        ValueRunnable<Integer> listClose = level -> {
            if (tocOptions.isNumbered) {
                headingNumbers[level] = 0;
            }
        };

        if (headings.size() > 0 && !tocOptions.title.isEmpty()) {
            html.raw("######".substring(0, tocOptions.titleLevel)).raw(" ").raw(tocOptions.title).line();
        }

        for (int i = 0; i < headings.size(); i++) {
            Heading header = headings.get(i);
            String headerText = headingTexts.get(i);
            int headerLevel = header.getLevel();

            if (initLevel == -1) {
                initLevel = headerLevel;
                lastLevel = headerLevel;
            }

            if (lastLevel < headerLevel) {
                for (int lv = lastLevel; lv <= headerLevel - 1; lv++) {
                    openedItems[lv + 1] = false;
                }
                html.indent();
            } else if (lastLevel == headerLevel) {
                if (i != 0) {
                    html.line();
                }
            } else {
                for (int lv = lastLevel; lv >= headerLevel + 1; lv++) {
                    if (openedItems[lv]) {
                        html.unIndent();
                        listClose.run(lv);
                    }
                }
                html.line();
            }

            html.line().raw(listOpen.compute(headerLevel));
            html.raw(headerText);

            lastLevel = headerLevel;
        }

        html.line();
    }
}
