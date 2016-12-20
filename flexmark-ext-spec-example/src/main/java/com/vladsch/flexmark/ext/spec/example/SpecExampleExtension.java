package com.vladsch.flexmark.ext.spec.example;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.spec.example.internal.RenderAs;
import com.vladsch.flexmark.ext.spec.example.internal.SpecExampleBlockParser;
import com.vladsch.flexmark.ext.spec.example.internal.SpecExampleNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

/**
 * Extension for spec_examples
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed spec_example text is turned into {@link com.vladsch.flexmark.spec.SpecExample} nodes.
 * </p>
 */
public class SpecExampleExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    // public static final DataKey<SpecExampleRepository> SPEC_EXAMPLES = new DataKey<>("SPEC_EXAMPLES", SpecExampleRepository::new);
    // public static final DataKey<KeepType> SPEC_EXAMPLES_KEEP = new DataKey<>("SPEC_EXAMPLES_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates
    public static final DataKey<Boolean> SPEC_EXAMPLE_RENDER_RAW_HTML = new DataKey<>("SPEC_EXAMPLE_RENDER_RAW_HTML", true);
    public static final DataKey<String> SPEC_EXAMPLE_RENDERED_HTML_PREFIX = new DataKey<>("SPEC_EXAMPLE_RENDERED_HTML_PREFIX", "<div style=\"border:solid #cccccc 1px;padding:0 20px 10px 20px;\">");
    public static final DataKey<String> SPEC_EXAMPLE_RENDERED_HTML_SUFFIX = new DataKey<>("SPEC_EXAMPLE_RENDERED_HTML_SUFFIX", "</div>");
    public static final DataKey<RenderAs> SPEC_EXAMPLE_RENDER_AS = new DataKey<>("SPEC_EXAMPLE_RENDER_AS", RenderAs.FENCED_CODE);
    public static final DataKey<String> SPEC_EXAMPLE_BREAK = new DataKey<>("SPEC_EXAMPLE_BREAK", SpecReader.EXAMPLE_BREAK);
    public static final DataKey<String> SPEC_TYPE_BREAK = new DataKey<>("SPEC_TYPE_BREAK", SpecReader.TYPE_BREAK);
    public static final DataKey<Boolean> SPEC_OPTION_NODES = new DataKey<>("SPEC_OPTION_NODES", true);

    private SpecExampleExtension() {
    }

    public static Extension create() {
        return new SpecExampleExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new SpecExampleBlockParser.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("JIRA") || rendererType.equals("YOUTRACK")) {
        } else if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
                @Override
                public NodeRenderer create(DataHolder options) {
                    return new SpecExampleNodeRenderer(options);
                }
            });
        }
    }
}
