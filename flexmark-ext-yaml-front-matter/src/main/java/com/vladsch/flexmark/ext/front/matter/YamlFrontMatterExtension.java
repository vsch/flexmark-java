package com.vladsch.flexmark.ext.front.matter;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.front.matter.internal.YamlFrontMatterBlockParser;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for YAML-like metadata.
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed metadata is turned into {@link YamlFrontMatterNode}. You can access the metadata using {@link AbstractYamlFrontMatterVisitor}.
 * </p>
 */
public class YamlFrontMatterExtension implements Parser.ParserExtension {
    private YamlFrontMatterExtension() {
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new YamlFrontMatterBlockParser.Factory());
    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    public static Extension create() {
        return new YamlFrontMatterExtension();
    }
}
