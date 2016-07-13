package com.vladsch.flexmark.ext.spec.example.internal;

import com.vladsch.flexmark.ext.spec.example.SpecExampleExtension;
import com.vladsch.flexmark.internal.util.options.DataHolder;

class SpecExampleOptions {
    final public boolean renderHtml;
    final public String exampleBreak;
    final public String typeBreak;

    public SpecExampleOptions(DataHolder options) {
        this.renderHtml = options.get(SpecExampleExtension.SPEC_EXAMPLE_RENDER_HTML);
        this.exampleBreak = options.get(SpecExampleExtension.SPEC_EXAMPLE_BREAK);
        this.typeBreak = options.get(SpecExampleExtension.SPEC_TYPE_BREAK);
    }
}
