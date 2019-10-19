package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profile.pegdown.Extensions;
import com.vladsch.flexmark.profile.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.data.DataHolder;

public class PegdownOptions {
    static final DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(
            Extensions.ALL
    ).toMutable()
            // set additional options here:
            //.set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX,"")
            ;

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    // use the PARSER to parse and RENDERER to render with pegdown compatibility
}
