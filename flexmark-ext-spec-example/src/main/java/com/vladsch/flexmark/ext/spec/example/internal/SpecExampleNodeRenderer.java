package com.vladsch.flexmark.ext.spec.example.internal;

import com.vladsch.flexmark.ext.spec.example.*;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.test.util.spec.SpecReader;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.misc.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SpecExampleNodeRenderer implements NodeRenderer {
    final private SpecExampleOptions options;

    public SpecExampleNodeRenderer(DataHolder options) {
        this.options = new SpecExampleOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(SpecExampleBlock.class, SpecExampleNodeRenderer.this::render),
                new NodeRenderingHandler<>(SpecExampleOptionsList.class, SpecExampleNodeRenderer.this::render),
                new NodeRenderingHandler<>(SpecExampleOption.class, SpecExampleNodeRenderer.this::render),
                new NodeRenderingHandler<>(SpecExampleOptionSeparator.class, SpecExampleNodeRenderer.this::render),
                new NodeRenderingHandler<>(SpecExampleSeparator.class, SpecExampleNodeRenderer.this::render),
                new NodeRenderingHandler<>(SpecExampleSource.class, SpecExampleNodeRenderer.this::render),
                new NodeRenderingHandler<>(SpecExampleHtml.class, SpecExampleNodeRenderer.this::render),
                new NodeRenderingHandler<>(SpecExampleAst.class, SpecExampleNodeRenderer.this::render)
        ));
    }

    private void render(SpecExampleOptionsList node, NodeRendererContext context, HtmlWriter html) { }

    private void render(SpecExampleOption node, NodeRendererContext context, HtmlWriter html) { }

    private void render(SpecExampleOptionSeparator node, NodeRendererContext context, HtmlWriter html) { }

    private void render(SpecExampleSeparator node, NodeRendererContext context, HtmlWriter html) {
        switch (options.renderAs) {
            case DEFINITION_LIST:
                break;

            case SECTIONS:
                break;

            case FENCED_CODE:
            default:
                break;
        }
    }

    private void render(SpecExampleSource node, NodeRendererContext context, HtmlWriter html) {
        BasedSequence text = node.getChars();

        String sectionLanguage = options.sectionLanguages.getOrDefault(1, "text");
        String sectionName = options.sectionNames.getOrDefault(1, sectionLanguage.substring(0, 1).toUpperCase() + sectionLanguage.substring(1));

        renderSpecExampleSection(text, sectionLanguage, sectionName, context, html);
    }

    private void render(SpecExampleHtml node, NodeRendererContext context, HtmlWriter html) {
        BasedSequence text = node.getChars();

        String sectionLanguage = options.sectionLanguages.getOrDefault(2, "text");
        String sectionName = options.sectionNames.getOrDefault(2, sectionLanguage.substring(0, 1).toUpperCase() + sectionLanguage.substring(1));

        renderSpecExampleSection(text, sectionLanguage, sectionName, context, html);
    }

    private void render(SpecExampleAst node, NodeRendererContext context, HtmlWriter html) {
        BasedSequence text = node.getChars();

        String sectionLanguage = options.sectionLanguages.getOrDefault(3, "text");
        String sectionName = options.sectionNames.getOrDefault(3, sectionLanguage.substring(0, 1).toUpperCase() + sectionLanguage.substring(1));

        renderSpecExampleSection(text, sectionLanguage, sectionName, context, html);
    }

    private void renderSpecExampleSection(BasedSequence text, String sectionLanguage, String sectionName, NodeRendererContext context, HtmlWriter html) {
        switch (options.renderAs) {
            case DEFINITION_LIST:
                html.tag("dt").text(sectionName).tag("/dt").line();
                html.tag("dd");
                render(text, sectionLanguage, context, html);
                html.tag("/dd").line();
                if (options.renderHtml && sectionLanguage.equals("html")) {
                    html.tag("dt").text("Rendered " + sectionName).tag("/dt").line();
                    html.tag("dd");
                    html.raw(options.renderedHtmlPrefix)
                            .rawIndentedPre(text.normalizeEOL())
                            .raw(options.renderedHtmlSuffix)
                            .line();
                    html.tag("/dd").line();
                }
                break;

            case SECTIONS:
                if (!text.isEmpty()) {
                    html.tagVoidLine("hr");
                    render(text, sectionLanguage, context, html);
                    if (options.renderHtml && sectionLanguage.equals("html")) {
                        html.tagVoidLine("hr");
                        html.raw(options.renderedHtmlPrefix)
                                .rawIndentedPre(text.normalizeEOL())
                                .raw(options.renderedHtmlSuffix)
                                .line();
                    }
                }
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
                    BasedSequence trimmed = node.getOptions().trim(CharPredicate.WHITESPACE_NBSP);
                    if (!trimmed.isEmpty()) {
                        BasedSequence[] optionsList = trimmed.split(",", 0, BasedSequence.SPLIT_TRIM_SKIP_EMPTY);
                        DelimitedBuilder out = new DelimitedBuilder(", ");
                        optionsText = out.appendAll(optionsList).getAndClear();
                    }
                    html.text(" options(").text(optionsText).text(")");
                }
                html.tag("/dt").line();

                context.renderChildren(node);

                html.unIndent().tag("/dl").line();
                break;

            case SECTIONS:
                html.tagVoidLine("hr");
                if (node.getSection().isNotNull() || node.getNumberSeparator().isNotNull() || node.getNumber().isNotNull()) {
                    html.tag("h5").text(node.getSection().toString()).text(": ").text(node.getNumber().toString()).tag("/h5").line();
                }
                context.renderChildren(node);
                break;

            case FENCED_CODE:
            default:
                render(node.getContentChars(), "text", context, html);
                break;
        }
    }

    private void render(BasedSequence contentChars, String language, NodeRendererContext context, HtmlWriter html) {
        String text = contentChars.normalizeEOL();

        if (!text.isEmpty()) {
            if (!language.isEmpty()) {
                html.attr("class", context.getHtmlOptions().languageClassMap.getOrDefault(language, context.getHtmlOptions().languageClassPrefix + language));
            }

            html.line();
            html.tag("pre").openPre();
            html.srcPos(contentChars).withAttr().tag("code");
            html.text(text);
            html.tag("/code");
            html.tag("/pre").closePre();
            html.line();
        }
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new SpecExampleNodeRenderer(options);
        }
    }
}
