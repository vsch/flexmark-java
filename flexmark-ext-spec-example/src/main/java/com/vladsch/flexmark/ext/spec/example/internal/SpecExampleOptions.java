package com.vladsch.flexmark.ext.spec.example.internal;

import com.vladsch.flexmark.ext.spec.example.SpecExampleExtension;
import com.vladsch.flexmark.util.options.DataHolder;

class SpecExampleOptions {
    public final boolean renderHtml;
    public final RenderAs renderAs;
    public final String exampleBreak;
    public final String typeBreak;
    public final boolean optionNodes;
    public final String renderedHtmlPrefix;
    public final String renderedHtmlSuffix;

    public SpecExampleOptions(DataHolder options) {
        this.renderHtml = options.get(SpecExampleExtension.SPEC_EXAMPLE_RENDER_RAW_HTML);
        this.renderAs = options.get(SpecExampleExtension.SPEC_EXAMPLE_RENDER_AS);
        this.exampleBreak = options.get(SpecExampleExtension.SPEC_EXAMPLE_BREAK);
        this.typeBreak = options.get(SpecExampleExtension.SPEC_TYPE_BREAK);
        this.optionNodes = options.get(SpecExampleExtension.SPEC_OPTION_NODES);
        this.renderedHtmlPrefix = options.get(SpecExampleExtension.SPEC_EXAMPLE_RENDERED_HTML_PREFIX);
        this.renderedHtmlSuffix = options.get(SpecExampleExtension.SPEC_EXAMPLE_RENDERED_HTML_SUFFIX);
    }
}
