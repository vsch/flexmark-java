package com.vladsch.flexmark.html.parser;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.parser.internal.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;

public class FlexmarkHtmlParser {
    // public static final DataKey<FlexmarkHtmlParserRepository> FLEXMARK_HTML_PARSERS = new DataKey<>("FLEXMARK_HTML_PARSERS", new DataValueFactory<FlexmarkHtmlParserRepository>() { @Override public FlexmarkHtmlParserRepository create(DataHolder options) { return new FlexmarkHtmlParserRepository(options); } });
    // public static final DataKey<KeepType> FLEXMARK_HTML_PARSERS_KEEP = new DataKey<>("FLEXMARK_HTML_PARSERS_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates
    // public static final DataKey<Boolean> FLEXMARK_HTML_PARSER_OPTION1 = new DataKey<>("FLEXMARK_HTML_PARSER_OPTION1", false);
    // public static final DataKey<String> FLEXMARK_HTML_PARSER_OPTION2 = new DataKey<>("FLEXMARK_HTML_PARSER_OPTION2", "default");
    // public static final DataKey<Integer> FLEXMARK_HTML_PARSER_OPTION3 = new DataKey<>("FLEXMARK_HTML_PARSER_OPTION3", Integer.MAX_VALUE);
    // public static final DataKey<String> LOCAL_ONLY_TARGET_CLASS = new DataKey<>("LOCAL_ONLY_TARGET_CLASS", "local-only");
    // public static final DataKey<String> MISSING_TARGET_CLASS = new DataKey<>("MISSING_TARGET_CLASS", "absent");
    //public static final LinkStatus LOCAL_ONLY = new LinkStatus("LOCAL_ONLY");

    private FlexmarkHtmlParser() {
    }

}
