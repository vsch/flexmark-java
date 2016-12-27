package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.util.options.OptionsParser;

public class SimTocOptionsParser extends OptionsParser<TocOptions> {
    public SimTocOptionsParser() {
        super("SimTocOptions", SimTocOptionTypes.OPTIONS, ' ', '=');
    }
}
