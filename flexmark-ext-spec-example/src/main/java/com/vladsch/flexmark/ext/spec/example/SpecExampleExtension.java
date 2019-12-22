package com.vladsch.flexmark.ext.spec.example;

import com.vladsch.flexmark.ext.spec.example.internal.RenderAs;
import com.vladsch.flexmark.ext.spec.example.internal.SpecExampleBlockParser;
import com.vladsch.flexmark.ext.spec.example.internal.SpecExampleNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.test.util.spec.SpecReader;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.format.options.TrailingSpaces;
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
public class SpecExampleExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private static final Map<Integer, String> DEFAULT_LANGUAGE_MAPPING = new HashMap<>();
    static {
        DEFAULT_LANGUAGE_MAPPING.put(1, "markdown");
        DEFAULT_LANGUAGE_MAPPING.put(2, "html");
        DEFAULT_LANGUAGE_MAPPING.put(3, "text");
    }

    private static final Map<Integer, String> DEFAULT_SECTION_MAPPING = new HashMap<>();
    static {
        DEFAULT_SECTION_MAPPING.put(1, "Source");
        DEFAULT_SECTION_MAPPING.put(2, "Html");
        DEFAULT_SECTION_MAPPING.put(3, "AST");
    }

    public static final DataKey<Boolean> SPEC_EXAMPLE_RENDER_RAW_HTML = new DataKey<>("SPEC_EXAMPLE_RENDER_RAW_HTML", true);
    public static final DataKey<String> SPEC_EXAMPLE_RENDERED_HTML_PREFIX = new DataKey<>("SPEC_EXAMPLE_RENDERED_HTML_PREFIX", "<div style=\"border:solid #cccccc 1px;padding:0 20px 10px 20px;\">");
    public static final DataKey<String> SPEC_EXAMPLE_RENDERED_HTML_SUFFIX = new DataKey<>("SPEC_EXAMPLE_RENDERED_HTML_SUFFIX", "</div>");
    public static final DataKey<RenderAs> SPEC_EXAMPLE_RENDER_AS = new DataKey<>("SPEC_EXAMPLE_RENDER_AS", RenderAs.FENCED_CODE);
    public static final DataKey<String> SPEC_EXAMPLE_BREAK = new DataKey<>("SPEC_EXAMPLE_BREAK", SpecReader.EXAMPLE_BREAK);
    public static final DataKey<String> SPEC_TYPE_BREAK = new DataKey<>("SPEC_TYPE_BREAK", SpecReader.TYPE_BREAK);
    public static final DataKey<Boolean> SPEC_OPTION_NODES = new DataKey<>("SPEC_OPTION_NODES", true);
    public static final DataKey<Map<Integer, String>> SPEC_EXAMPLE_SECTION_LANGUAGES = new DataKey<>("SPEC_EXAMPLE_SECTION_LANGUAGES", DEFAULT_LANGUAGE_MAPPING);
    public static final DataKey<Map<Integer, String>> SPEC_EXAMPLE_SECTION_NAMES = new DataKey<>("SPEC_EXAMPLE_SECTION_NAMES", DEFAULT_SECTION_MAPPING);

    // IMPORTANT: implement node formatter and this option
    public static final DataKey<TrailingSpaces> KEEP_TRAILING_SPACES = new DataKey<>("KEEP_TRAILING_SPACES", TrailingSpaces.KEEP_ALL);

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
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new SpecExampleBlockParser.Factory());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder rendererBuilder, @NotNull String rendererType) {
        if (rendererBuilder.isRendererType("HTML")) {
            rendererBuilder.nodeRendererFactory(new SpecExampleNodeRenderer.Factory());
        } else if (rendererBuilder.isRendererType("JIRA")) {
        }
    }
}
