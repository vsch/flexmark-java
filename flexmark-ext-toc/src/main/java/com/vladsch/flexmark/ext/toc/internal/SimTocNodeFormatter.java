package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.util.HeadingCollectingVisitor;
import com.vladsch.flexmark.ext.toc.SimTocBlock;
import com.vladsch.flexmark.ext.toc.SimTocContent;
import com.vladsch.flexmark.ext.toc.TocUtils;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.MarkdownTable;
import com.vladsch.flexmark.util.misc.Paired;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimTocNodeFormatter implements NodeFormatter {
    final private TocOptions options;
    final private TocFormatOptions formatOptions;

    private MarkdownTable myTable;

    public SimTocNodeFormatter(DataHolder options) {
        this.options = new TocOptions(options, true);
        this.formatOptions = new TocFormatOptions(options);
    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeFormattingHandler<>(SimTocBlock.class, SimTocNodeFormatter.this::render),
                new NodeFormattingHandler<>(SimTocContent.class, SimTocNodeFormatter.this::render)
        ));
    }

    private void render(SimTocBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        switch (formatOptions.updateOnFormat) {
            case REMOVE: {
                String simTocPrefix = TocUtils.getSimTocPrefix(options, this.options);
                markdown.append(simTocPrefix);
                if (options.isBlankLineSpacer) markdown.blankLine();
                break;
            }

            case UPDATE: {
                HeadingCollectingVisitor visitor = new HeadingCollectingVisitor();
                List<Heading> headings = visitor.collectAndGetHeadings(context.getDocument());
                if (headings != null) {
                    SimTocOptionsParser optionsParser = new SimTocOptionsParser();
                    TocOptions options = optionsParser.parseOption(node.getStyle(), this.options, null).getFirst();

                    if (node.getTitle().isNotNull()) {
                        options = options.withTitle(node.getTitle().unescape());
                    }

                    String simTocPrefix = TocUtils.getSimTocPrefix(options, this.options);
                    markdown.append(simTocPrefix);
                    if (options.isBlankLineSpacer) markdown.blankLine();

                    renderTocHeaders(markdown, headings, options);
                }
                break;
            }

            case AS_IS:
                markdown.openPreFormatted(false).append(node.getChars()).closePreFormatted();
                break;

            default:
                throw new IllegalStateException(formatOptions.updateOnFormat.toString() + " is not implemented");
        }
    }

    private void renderTocHeaders(MarkdownWriter markdown, List<Heading> headings, TocOptions options) {
        List<Heading> filteredHeadings = TocUtils.filteredHeadings(headings, options);
        Paired<List<Heading>, List<String>> paired = TocUtils.markdownHeaderTexts(filteredHeadings, options);
        TocUtils.renderTocContent(markdown, options, this.options, paired.getFirst(), paired.getSecond());
    }

    private void render(SimTocContent node, NodeFormatterContext context, MarkdownWriter markdown) {

    }

    public static class Factory implements NodeFormatterFactory {
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new SimTocNodeFormatter(options);
        }
    }
}
