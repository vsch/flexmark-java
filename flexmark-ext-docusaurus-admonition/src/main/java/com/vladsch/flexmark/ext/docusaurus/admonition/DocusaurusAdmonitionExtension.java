package com.vladsch.flexmark.ext.docusaurus.admonition;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

public class DocusaurusAdmonitionExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {

    public static DocusaurusAdmonitionExtension create() {
        return new DocusaurusAdmonitionExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new DocusaurusAdmonitionBlockParser.Factory());
    }

    @Override
    public void extend(HtmlRenderer.@NotNull Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(new DocusaurusAdmonitionNodeRenderer.Factory());
        }
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {
    }

    @Override
    public void parserOptions(MutableDataHolder options) {
    }
}
