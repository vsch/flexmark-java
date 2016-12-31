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
        this.renderHtml = SpecExampleExtension.SPEC_EXAMPLE_RENDER_RAW_HTML.getFrom(options);
        this.renderAs = SpecExampleExtension.SPEC_EXAMPLE_RENDER_AS.getFrom(options);
        this.exampleBreak = SpecExampleExtension.SPEC_EXAMPLE_BREAK.getFrom(options);
        this.typeBreak = SpecExampleExtension.SPEC_TYPE_BREAK.getFrom(options);
        this.optionNodes = SpecExampleExtension.SPEC_OPTION_NODES.getFrom(options);
        this.renderedHtmlPrefix = SpecExampleExtension.SPEC_EXAMPLE_RENDERED_HTML_PREFIX.getFrom(options);
        this.renderedHtmlSuffix = SpecExampleExtension.SPEC_EXAMPLE_RENDERED_HTML_SUFFIX.getFrom(options);
    }
}
