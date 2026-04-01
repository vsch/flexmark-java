package com.vladsch.flexmark.ext.docusaurus.admonition;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class DocusaurusAdmonitionBlockHtmlRendererTest {

    @Test
    public void checkParsing() {
        Parser parser = Parser.builder().extensions(Collections.singleton(DocusaurusAdmonitionExtension.create())).build();

        Document document = parser.parse(":::info\nThis is info\n:::");
        Assert.assertTrue(document.getFirstChild() instanceof DocusaurusAdmonitionBlock);
    }
}
