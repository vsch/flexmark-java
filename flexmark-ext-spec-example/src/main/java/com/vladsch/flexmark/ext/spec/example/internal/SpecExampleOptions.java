package com.vladsch.flexmark.ext.spec.example.internal;

import com.vladsch.flexmark.ext.spec.example.SpecExampleExtension;
import com.vladsch.flexmark.util.data.DataHolder;

import java.util.Map;

class SpecExampleOptions {
    public final boolean renderHtml;
    public final RenderAs renderAs;
    public final String exampleBreak;
    public final String sectionBreak;
    public final boolean optionNodes;
    public final String renderedHtmlPrefix;
    public final String renderedHtmlSuffix;
    public final Map<Integer, String> sectionLanguages;
    public final Map<Integer, String> sectionNames;

    public SpecExampleOptions(DataHolder options) {
        this.renderHtml = SpecExampleExtension.SPEC_EXAMPLE_RENDER_RAW_HTML.get(options);
        this.renderAs = SpecExampleExtension.SPEC_EXAMPLE_RENDER_AS.get(options);
        this.exampleBreak = SpecExampleExtension.SPEC_EXAMPLE_BREAK.get(options);
        this.sectionBreak = SpecExampleExtension.SPEC_SECTION_BREAK.get(options);
        this.optionNodes = SpecExampleExtension.SPEC_OPTION_NODES.get(options);
        this.renderedHtmlPrefix = SpecExampleExtension.SPEC_EXAMPLE_RENDERED_HTML_PREFIX.get(options);
        this.renderedHtmlSuffix = SpecExampleExtension.SPEC_EXAMPLE_RENDERED_HTML_SUFFIX.get(options);
        this.sectionLanguages = SpecExampleExtension.SPEC_EXAMPLE_SECTION_LANGUAGES.get(options);
        this.sectionNames = SpecExampleExtension.SPEC_EXAMPLE_SECTION_NAMES.get(options);
    }
}
