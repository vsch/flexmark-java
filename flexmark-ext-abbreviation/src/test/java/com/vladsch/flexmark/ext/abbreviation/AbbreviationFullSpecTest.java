package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.internal.util.MutableDataSet;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.FullSpecTestCase;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AbbreviationFullSpecTest extends FullSpecTestCase {
    static final String SPEC_RESOURCE = "/ext_abbreviation_ast_spec.txt";
    private static final Set<Extension> EXTENSIONS = Collections.singleton(AbbreviationExtension.create());
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(AbbreviationExtension.USE_LINKS, false);

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("links", new MutableDataSet()
                .set(AbbreviationExtension.USE_LINKS, true)
        );
    }

    static final Parser PARSER = Parser.builder(OPTIONS).extensions(EXTENSIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).extensions(EXTENSIONS).build();

    static DataHolder optionsSet(String optionSet) {
        return optionsMap.get(optionSet);
    }

    @Override
    protected DataHolder options(String optionSet) {
        return optionsSet(optionSet);
    }

    @Override
    protected String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    protected Parser parser() {
        return PARSER;
    }

    @Override
    protected HtmlRenderer renderer() {
        return RENDERER;
    }
}
