package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.AttributeProvider;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.MutableDataHolder;
import com.vladsch.flexmark.internal.util.MutableDataSet;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.FullSpecTestCase;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TablesFullSpecTest extends FullSpecTestCase {
    static final String SPEC_RESOURCE = "/ext_tables_ast_spec.txt";

    static final MutableDataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2);

    private static final Set<Extension> EXTENSIONS = Collections.singleton(TablesExtension.create(OPTIONS));
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).extensions(EXTENSIONS).build();
    static final Parser PARSER = Parser.builder(OPTIONS).extensions(EXTENSIONS).build();

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

    @Test
    public void attributeProviderIsApplied() {
        AttributeProvider attributeProvider = new AttributeProvider() {
            @Override
            public void setAttributes(Node node, String tag, Map<String, String> attributes) {
                if (node instanceof TableBlock) {
                    attributes.put("test", "block");
                } else if (node instanceof TableHead) {
                    attributes.put("test", "head");
                } else if (node instanceof TableBody) {
                    attributes.put("test", "body");
                } else if (node instanceof TableRow) {
                    attributes.put("test", "row");
                } else if (node instanceof TableCell) {
                    attributes.put("test", "cell");
                }
            }
        };

        HtmlRenderer renderer = HtmlRenderer.builder()
                .attributeProvider(attributeProvider)
                .extensions(EXTENSIONS)
                .build();

        String rendered = renderer.render(PARSER.parse("Abc|Def\n---|---\n1|2"));
        assertThat(rendered, is("<table test=\"block\">\n" +
                "<thead test=\"head\">\n" +
                "<tr test=\"row\"><th test=\"cell\">Abc</th><th test=\"cell\">Def</th></tr>\n" +
                "</thead>\n" +
                "<tbody test=\"body\">\n" +
                "<tr test=\"row\"><td test=\"cell\">1</td><td test=\"cell\">2</td></tr>\n" +
                "</tbody>\n" +
                "</table>\n"));
    }
}
