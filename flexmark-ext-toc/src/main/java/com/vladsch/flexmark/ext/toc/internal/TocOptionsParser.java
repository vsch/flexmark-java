package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.util.options.OptionsParser;

public class TocOptionsParser extends OptionsParser<TocOptions> {
    public TocOptionsParser() {
        super("TocOptions", TocOptionTypes.OPTIONS, ' ', '=');
    }
}
