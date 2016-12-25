package com.vladsch.flexmark.ext.spec.example.internal;

import com.vladsch.flexmark.ext.spec.example.*;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

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
                new NodeRenderingHandler<>(SpecExampleBlock.class, new CustomNodeRenderer<SpecExampleBlock>() {
                    @Override
                    public void render(SpecExampleBlock node, NodeRendererContext context, HtmlWriter html) {
                        SpecExampleNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(SpecExampleOptionsList.class, new CustomNodeRenderer<SpecExampleOptionsList>() {
                    @Override
                    public void render(SpecExampleOptionsList node, NodeRendererContext context, HtmlWriter html) {
                        SpecExampleNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(SpecExampleOption.class, new CustomNodeRenderer<SpecExampleOption>() {
                    @Override
                    public void render(SpecExampleOption node, NodeRendererContext context, HtmlWriter html) {
                        SpecExampleNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(SpecExampleOptionSeparator.class, new CustomNodeRenderer<SpecExampleOptionSeparator>() {
                    @Override
                    public void render(SpecExampleOptionSeparator node, NodeRendererContext context, HtmlWriter html) {
                        SpecExampleNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(SpecExampleSeparator.class, new CustomNodeRenderer<SpecExampleSeparator>() {
                    @Override
                    public void render(SpecExampleSeparator node, NodeRendererContext context, HtmlWriter html) {
                        SpecExampleNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(SpecExampleSource.class, new CustomNodeRenderer<SpecExampleSource>() {
                    @Override
                    public void render(SpecExampleSource node, NodeRendererContext context, HtmlWriter html) {
                        SpecExampleNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(SpecExampleHtml.class, new CustomNodeRenderer<SpecExampleHtml>() {
                    @Override
                    public void render(SpecExampleHtml node, NodeRendererContext context, HtmlWriter html) {
                        SpecExampleNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(SpecExampleAst.class, new CustomNodeRenderer<SpecExampleAst>() {
                    @Override
                    public void render(SpecExampleAst node, NodeRendererContext context, HtmlWriter html) {
                        SpecExampleNodeRenderer.this.render(node, context, html);
                    }
                })
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

        switch (options.renderAs) {
            case DEFINITION_LIST:
                html.tag("dt").text("Source").tag("/dt").line();
                html.tag("dd");
                render(text, "markdown", context, html);
                html.tag("/dd").line();
                break;

            case SECTIONS:
                if (!text.isEmpty()) {
                    html.tagVoidLine("hr");
                    render(text, "markdown", context, html);
                }
                break;

            case FENCED_CODE:
            default:
                break;
        }
    }

    private void render(SpecExampleHtml node, NodeRendererContext context, HtmlWriter html) {
        BasedSequence text = node.getChars();

        switch (options.renderAs) {
            case DEFINITION_LIST:
                html.tag("dt").text("Html").tag("/dt").line();
                html.tag("dd");
                render(text, "html", context, html);
                html.tag("/dd").line();
                if (options.renderHtml) {
                    html.tag("dt").text("Rendered Html").tag("/dt").line();
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
                    render(text, "html", context, html);
                    if (options.renderHtml) {
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

    private void render(SpecExampleAst node, NodeRendererContext context, HtmlWriter html) {
        BasedSequence text = node.getChars();

        switch (options.renderAs) {
            case DEFINITION_LIST:
                html.tag("dt").text("AST").tag("/dt").line();
                html.tag("dd");
                render(text, "text", context, html);
                html.tag("/dd").line();
                break;

            case SECTIONS:
                if (!text.isEmpty()) {
                    html.tagVoidLine("hr");
                    render(text, "text", context, html);
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
                    BasedSequence trimmed = node.getOptions().trim(BasedSequence.WHITESPACE_NBSP_CHARS);
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
                html.attr("class", context.getHtmlOptions().languageClassPrefix + language);
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
}
