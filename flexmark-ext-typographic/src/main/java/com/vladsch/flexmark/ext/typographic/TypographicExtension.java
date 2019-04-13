package com.vladsch.flexmark.ext.typographic;

import com.vladsch.flexmark.ext.typographic.internal.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for typographics
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed typographic text is turned into {@link TypographicQuotes} and {@link TypographicSmarts} nodes.
 */
public class TypographicExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    public static final DataKey<Boolean> ENABLE_QUOTES = new DataKey<>("ENABLE_QUOTES", true);
    public static final DataKey<Boolean> ENABLE_SMARTS = new DataKey<>("ENABLE_SMARTS", true);
    /**
     * @deprecated use {@link #ENABLE_QUOTES}
     */
    public static final DataKey<Boolean> TYPOGRAPHIC_QUOTES = ENABLE_QUOTES;
    /**
     * @deprecated use {@link #ENABLE_SMARTS}
     */
    public static final DataKey<Boolean> TYPOGRAPHIC_SMARTS = ENABLE_SMARTS;

    public static final DataKey<String> ANGLE_QUOTE_CLOSE = new DataKey<>("ANGLE_QUOTE_CLOSE", "&raquo;");
    public static final DataKey<String> ANGLE_QUOTE_OPEN = new DataKey<>("ANGLE_QUOTE_OPEN", "&laquo;");
    public static final DataKey<String> ANGLE_QUOTE_UNMATCHED = new DataKey<>("ANGLE_QUOTE_UNMATCHED", (String) null);
    public static final DataKey<String> DOUBLE_QUOTE_CLOSE = new DataKey<>("DOUBLE_QUOTE_CLOSE", "&rdquo;");
    public static final DataKey<String> DOUBLE_QUOTE_OPEN = new DataKey<>("DOUBLE_QUOTE_OPEN", "&ldquo;");
    public static final DataKey<String> DOUBLE_QUOTE_UNMATCHED = new DataKey<>("DOUBLE_QUOTE_UNMATCHED", (String) null);
    public static final DataKey<String> ELLIPSIS = new DataKey<>("ELLIPSIS", "&hellip;");
    public static final DataKey<String> ELLIPSIS_SPACED = new DataKey<>("ELLIPSIS_SPACED", "&hellip;");
    public static final DataKey<String> EM_DASH = new DataKey<>("EM_DASH", "&mdash;");
    public static final DataKey<String> EN_DASH = new DataKey<>("EN_DASH", "&ndash;");
    public static final DataKey<String> SINGLE_QUOTE_CLOSE = new DataKey<>("SINGLE_QUOTE_CLOSE", "&rsquo;");
    public static final DataKey<String> SINGLE_QUOTE_OPEN = new DataKey<>("SINGLE_QUOTE_OPEN", "&lsquo;");
    public static final DataKey<String> SINGLE_QUOTE_UNMATCHED = new DataKey<>("SINGLE_QUOTE_UNMATCHED", "&rsquo;");

    private TypographicExtension() {
    }

    public static Extension create() {
        return new TypographicExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        if (ENABLE_QUOTES.getFrom(parserBuilder)) {
            TypographicOptions options = new TypographicOptions(parserBuilder);
            parserBuilder.customDelimiterProcessor(new AngleQuoteDelimiterProcessor(options));
            parserBuilder.customDelimiterProcessor(new SingleQuoteDelimiterProcessor(options));
            parserBuilder.customDelimiterProcessor(new DoubleQuoteDelimiterProcessor(options));
        }
        if (ENABLE_SMARTS.getFrom(parserBuilder)) {
            parserBuilder.customInlineParserExtensionFactory(new SmartsInlineParser.Factory());
        }
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererBuilder.isRendererType("HTML") || rendererBuilder.isRendererType("JIRA")) {
            rendererBuilder.nodeRendererFactory(new TypographicNodeRenderer.Factory());
        }
    }
}
