package com.vladsch.flexmark.ext.autolink;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ext.autolink.internal.AutolinkNodePostProcessor;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;

/**
 * Extension for automatically turning plain URLs and email addresses into links.
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed links are turned into normal {@link Link} nodes.
 */
public class AutolinkExtension implements Parser.ParserExtension {
    //  regex to match all link texts which should be ignored for auto-linking
    public static final DataKey<String> IGNORE_LINKS = new DataKey<>("IGNORE_LINKS", "");
    //public static final DataKey<Boolean> ALLOW_PROTOCOL_PREFIX_ONLY = new DataKey<>("ALLOW_PROTOCOL_PREFIX_ONLY", false);

    private AutolinkExtension() {
    }

    public static Extension create() {
        return new AutolinkExtension();
    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessorFactory(new AutolinkNodePostProcessor.Factory());
    }
}
