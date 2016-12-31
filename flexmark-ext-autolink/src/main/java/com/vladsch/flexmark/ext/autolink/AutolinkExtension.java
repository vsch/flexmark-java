package com.vladsch.flexmark.ext.autolink;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ext.autolink.internal.AutolinkNodePostProcessor;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for automatically turning plain URLs and email addresses into links.
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed links are turned into normal {@link Link} nodes.
 * </p>
 */
public class AutolinkExtension implements Parser.ParserExtension {
    private AutolinkExtension() {
    }

    public static Extension create() {
        return new AutolinkExtension();
    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessorFactory(new AutolinkNodePostProcessor.Factory());
    }
}
