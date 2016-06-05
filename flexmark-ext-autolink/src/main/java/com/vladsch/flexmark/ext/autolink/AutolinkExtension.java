package com.vladsch.flexmark.ext.autolink;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.autolink.internal.AutolinkPostProcessor;
import com.vladsch.flexmark.node.Link;
import com.vladsch.flexmark.parser.Parser;

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

    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessor(new AutolinkPostProcessor());
    }

}
