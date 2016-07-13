package com.vladsch.flexmark.ext.spec.example.internal;

import com.vladsch.flexmark.ext.spec.example.*;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.internal.util.options.DelimitedBuilder;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.internal.util.sequence.BasedSequenceImpl;
import com.vladsch.flexmark.spec.SpecReader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpecExampleNodeRenderer implements NodeRenderer
        // , PhasedNodeRenderer 
{
    private static String fromChars = " +/<>";
    private static String toChars = "-----";

    private final SpecExampleOptions options;

    public SpecExampleNodeRenderer(DataHolder options) {
        this.options = new SpecExampleOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(SpecExampleBlock.class, this::render),
                new NodeRenderingHandler<>(SpecExampleOptionsList.class, this::render),
                new NodeRenderingHandler<>(SpecExampleOption.class, this::render),
                new NodeRenderingHandler<>(SpecExampleOptionSeparator.class, this::render),
                new NodeRenderingHandler<>(SpecExampleSeparator.class, this::render),
                new NodeRenderingHandler<>(SpecExampleSource.class, this::render),
                new NodeRenderingHandler<>(SpecExampleHtml.class, this::render),
                new NodeRenderingHandler<>(SpecExampleAst.class, this::render)
        ));
    }

    private void render(SpecExampleOptionsList node, NodeRendererContext context, HtmlWriter html) { }

    private void render(SpecExampleOption node, NodeRendererContext context, HtmlWriter html) { }

    private void render(SpecExampleOptionSeparator node, NodeRendererContext context, HtmlWriter html) { }

    private void render(SpecExampleSeparator node, NodeRendererContext context, HtmlWriter html) {
        switch (options.renderAs) {
            case DEFINITION_LIST:
                break;

            case SIMPLE:
                html.tagVoidLine("hr");
                break;

            case FENCED_CODE:
            default:
                break;
        }
    }

    private void render(SpecExampleSource node, NodeRendererContext context, HtmlWriter html) {
        String text = node.getChars().normalizeEOL();
        switch (options.renderAs) {
            case DEFINITION_LIST:
                html.tag("dt").text("Source").tag("/dt").line();
                html.tag("dd");
                render(text, "markdown", context, html);
                html.tag("/dd").line();
                break;

            case SIMPLE:
                render(text, "markdown", context, html);
                break;

            case FENCED_CODE:
            default:
                break;
        }
    }

    private void render(SpecExampleHtml node, NodeRendererContext context, HtmlWriter html) {
        String text = node.getChars().normalizeEOL();
        switch (options.renderAs) {
            case DEFINITION_LIST:
                html.tag("dt").text("Html").tag("/dt").line();
                html.tag("dd");
                render(text, "html", context, html);
                html.tag("/dd").line();
                if (options.renderHtml) {
                    html.tag("dt").text("Rendered Html").tag("/dt").line();
                    html.tag("dd");
                    html.raw(options.renderedHtmlPrefix).raw(text).raw(options.renderedHtmlSuffix).line();
                    html.tag("/dd").line();
                }
                break;

            case SIMPLE:
                render(text, "html", context, html);
                if (options.renderHtml && !text.isEmpty()) {
                    html.tagVoidLine("hr");
                    html.raw(options.renderedHtmlPrefix).raw(text).raw(options.renderedHtmlSuffix).line();
                }
                break;

            case FENCED_CODE:
            default:
                break;
        }
    }

    private void render(SpecExampleAst node, NodeRendererContext context, HtmlWriter html) {
        String text = node.getChars().normalizeEOL();

        switch (options.renderAs) {
            case DEFINITION_LIST:
                html.tag("dt").text("AST").tag("/dt").line();
                html.tag("dd");
                render(text, "text", context, html);
                html.tag("/dd").line();
                break;

            case SIMPLE:
                render(text, "text", context, html);
                break;

            case FENCED_CODE:
            default:
                break;
        }
    }

    private void render(SpecExampleBlock node, NodeRendererContext context, HtmlWriter html) {
        // here we should probably prettify and display section, number and options
        switch (options.renderAs) {
            case DEFINITION_LIST:
                html.tagVoidLine("hr");
                html.tag("dl").indent();

                html.tag("dt").text(SpecReader.EXAMPLE_KEYWORD);
                if (node.getSection().isNotNull() || node.getNumberSeparator().isNotNull() || node.getNumber().isNotNull()) {
                    html.text(" ").text(node.getSection().toString()).text(": ").text(node.getNumber().toString());
                }
                if (node.getOptionsKeyword().isNotNull() || node.getOptionsOpeningMarker().isNotNull() || node.getOptions().isNotNull() || node.getOptionsClosingMarker().isNotNull()) {
                    String optionsText = "";
                    BasedSequence trimmed = node.getOptions().trim(BasedSequenceImpl.WHITESPACE_NBSP_CHARS);
                    if (!trimmed.isEmpty()) {
                        List<BasedSequence> optionsList = trimmed.split(',', 0, BasedSequence.SPLIT_TRIM_SKIP_EMPTY);
                        DelimitedBuilder out = new DelimitedBuilder(", ");
                        optionsText = out.appendAll(optionsList).getAndClear();
                    }
                    html.text(" options(").text(optionsText).text(")");
                }
                html.tag("/dt").line();

                context.renderChildren(node);

                html.unIndent().tag("/dl").line();
                break;

            case SIMPLE:
                context.renderChildren(node);
                break;

            case FENCED_CODE:
            default:
                render(node.getContentChars().toString(), "text", context, html);
                break;
        }
    }

    private void render(String text, String language, NodeRendererContext context, HtmlWriter html) {
        if (!text.isEmpty()) {
            if (!language.isEmpty()) {
                html.attr("class", context.getHtmlOptions().languageClassPrefix + language);
            }

            html.line();
            html.tag("pre").openPre();
            html.withAttr().tag("code");
            html.text(text);
            html.tag("/code");
            html.tag("/pre").closePre();
            html.line();
        }
    }
}
