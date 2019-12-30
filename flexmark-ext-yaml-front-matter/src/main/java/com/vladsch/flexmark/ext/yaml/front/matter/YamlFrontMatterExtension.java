package com.vladsch.flexmark.ext.yaml.front.matter;

import com.vladsch.flexmark.ext.yaml.front.matter.internal.YamlFrontMatterBlockParser;
import com.vladsch.flexmark.ext.yaml.front.matter.internal.YamlFrontMatterNodeFormatter;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataHolder;

/**
 * Extension for YAML-like metadata.
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed metadata is turned into {@link YamlFrontMatterNode}. You can access the metadata using {@link AbstractYamlFrontMatterVisitor}.
 */
public class YamlFrontMatterExtension implements Parser.ParserExtension, Formatter.FormatterExtension {
    private YamlFrontMatterExtension() {
    }

    @Override
    public void rendererOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Formatter.Builder formatterBuilder) {
        formatterBuilder.nodeFormatterFactory(new YamlFrontMatterNodeFormatter.Factory());
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new YamlFrontMatterBlockParser.Factory());
    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    public static YamlFrontMatterExtension create() {
        return new YamlFrontMatterExtension();
    }
}
