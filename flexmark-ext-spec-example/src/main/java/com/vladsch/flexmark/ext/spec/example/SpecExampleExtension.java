package com.vladsch.flexmark.ext.spec.example;

import com.vladsch.flexmark.ext.spec.example.internal.RenderAs;
import com.vladsch.flexmark.ext.spec.example.internal.SpecExampleBlockParser;
import com.vladsch.flexmark.ext.spec.example.internal.SpecExampleNodeFormatter;
import com.vladsch.flexmark.ext.spec.example.internal.SpecExampleNodeRenderer;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.test.util.spec.SpecReader;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Extension for spec_examples
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed spec_example text is turned into {@link SpecExample} nodes.
 */
public class SpecExampleExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, Formatter.FormatterExtension {
    final private static Map<Integer, String> DEFAULT_LANGUAGE_MAPPING = new HashMap<>();
    static {
        DEFAULT_LANGUAGE_MAPPING.put(1, "markdown");
        DEFAULT_LANGUAGE_MAPPING.put(2, "html");
        DEFAULT_LANGUAGE_MAPPING.put(3, "text");
    }

    final private static Map<Integer, String> DEFAULT_SECTION_MAPPING = new HashMap<>();
    static {
        DEFAULT_SECTION_MAPPING.put(1, "Source");
        DEFAULT_SECTION_MAPPING.put(2, "Html");
        DEFAULT_SECTION_MAPPING.put(3, "AST");
    }

    final public static DataKey<Boolean> SPEC_EXAMPLE_RENDER_RAW_HTML = new DataKey<>("SPEC_EXAMPLE_RENDER_RAW_HTML", true);
    final public static DataKey<String> SPEC_EXAMPLE_RENDERED_HTML_PREFIX = new DataKey<>("SPEC_EXAMPLE_RENDERED_HTML_PREFIX", "<div style=\"border:solid #cccccc 1px;padding:0 20px 10px 20px;\">");
    final public static DataKey<String> SPEC_EXAMPLE_RENDERED_HTML_SUFFIX = new DataKey<>("SPEC_EXAMPLE_RENDERED_HTML_SUFFIX", "</div>");
    final public static DataKey<RenderAs> SPEC_EXAMPLE_RENDER_AS = new DataKey<>("SPEC_EXAMPLE_RENDER_AS", RenderAs.FENCED_CODE);
    final public static DataKey<String> SPEC_EXAMPLE_BREAK = new DataKey<>("SPEC_EXAMPLE_BREAK", SpecReader.EXAMPLE_BREAK);
    final public static DataKey<String> SPEC_SECTION_BREAK = new DataKey<>("SPEC_SECTION_BREAK", SpecReader.SECTION_BREAK);
    final public static DataKey<Boolean> SPEC_OPTION_NODES = new DataKey<>("SPEC_OPTION_NODES", true);
    final public static DataKey<Map<Integer, String>> SPEC_EXAMPLE_SECTION_LANGUAGES = new DataKey<>("SPEC_EXAMPLE_SECTION_LANGUAGES", DEFAULT_LANGUAGE_MAPPING);
    final public static DataKey<Map<Integer, String>> SPEC_EXAMPLE_SECTION_NAMES = new DataKey<>("SPEC_EXAMPLE_SECTION_NAMES", DEFAULT_SECTION_MAPPING);

    @Deprecated final public static DataKey<String> SPEC_TYPE_BREAK = SPEC_SECTION_BREAK;

    private SpecExampleExtension() {
    }

    public static SpecExampleExtension create() {
        return new SpecExampleExtension();
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Formatter.Builder formatterBuilder) {
        formatterBuilder.nodeFormatterFactory(new SpecExampleNodeFormatter.Factory());
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new SpecExampleBlockParser.Factory());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new SpecExampleNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
        }
    }
}
