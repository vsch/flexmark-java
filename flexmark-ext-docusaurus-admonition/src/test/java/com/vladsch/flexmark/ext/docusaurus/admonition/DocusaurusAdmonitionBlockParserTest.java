package com.vladsch.flexmark.ext.docusaurus.admonition;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DocusaurusAdmonitionBlockParserTest {

    @Test
    public void checkParsing() {
        MutableDataSet options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, List.of(
                DocusaurusAdmonitionExtension.create()
        ));

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        String markdownInput = ":::info\nThis is info\n:::";

        // Act
        String result = renderer.render(parser.parse(markdownInput));

        // Assert
        String expectedHtml = "<div class=\"docusaurus-admonition docusaurus-admonition-info\">\n<p>This is info</p>\n</div>\n";
        Assert.assertEquals(expectedHtml, result);
    }
}
