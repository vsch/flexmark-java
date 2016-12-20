package com.vladsch.flexmark.android.test;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class AndroidSupportTest {

    private String spec;

    @Before
    public void setUp() throws Exception {
        spec = SpecReader.readSpec();
    }

    @Test
    public void parseTest() throws Exception {
        Parser parser = new Parser.Builder().build();

        Node document = parser.parse(spec);

        assertNotNull(document);
    }

    @Test
    public void autolinkExtensionTest() throws Exception {
        parseWithExtensionsTest(AutolinkExtension.create());
    }

    @Test
    public void strikethroughExtensionTest() throws Exception {
        parseWithExtensionsTest(StrikethroughExtension.create());
    }

    @Test
    public void tablesExtensionTest() throws Exception {
        parseWithExtensionsTest(TablesExtension.create());
    }

    @Test
    public void yamlFrontMatterExtensionTest() throws Exception {
        parseWithExtensionsTest(YamlFrontMatterExtension.create());
    }

    @Test
    public void htmlRendererTest() throws Exception {
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        String renderedString = renderer.render(parser.parse(spec));

        assertNotNull(renderedString);
    }

    private void parseWithExtensionsTest(Extension extension) throws Exception {
        Parser parser = Parser.builder()
                .extensions(Collections.singletonList(extension))
                .build();

        Node document = parser.parse(spec);
        assertNotNull(document);

        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(Collections.singletonList(extension))
                .build();

        String renderedString = renderer.render(document);
        assertNotNull(renderedString);
    }
}
