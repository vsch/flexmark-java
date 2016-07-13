package com.vladsch.flexmark.ext.spec.example.internal;

import com.vladsch.flexmark.ext.spec.example.SpecExampleExtension;
import com.vladsch.flexmark.internal.util.options.DataHolder;

class SpecExampleOptions {
    final public boolean renderHtml;
    final public RenderAs renderAs;
    final public String exampleBreak;
    final public String typeBreak;
    final public boolean optionNodes;
    final public String renderedHtmlPrefix;
    final public String renderedHtmlSuffix;

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
