package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.MutableDataSet;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.FullSpecTestCase;

import java.util.Collections;
import java.util.Set;

public class WikiLinkCreoleFullSpecTest extends FullSpecTestCase {
    final static String SPEC_RESOURCE = "/ext_wikilink_creole_ast_spec.txt";
    final static private MutableDataSet OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(WikiLinkExtension.LINK_FIRST_SYNTAX, true);

    final static private Set<Extension> EXTENSIONS = Collections.singleton(WikiLinkExtension.create());
    final static HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).extensions(EXTENSIONS).build();
    final static Parser PARSER = Parser.builder(OPTIONS).extensions(EXTENSIONS).build();

    @Override
    protected String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    protected Node parse(String source) {
        return PARSER.parse(source);
    }

    @Override
    protected String render(Node node) {
        return RENDERER.render(node);
    }
}
