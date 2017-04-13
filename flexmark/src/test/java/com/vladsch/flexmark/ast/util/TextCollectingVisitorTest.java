package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.parser.Parser;
import org.junit.Test;

import static org.junit.Assert.*;

public class TextCollectingVisitorTest {
    @Test
    public void test_basic() {
        Parser parser = Parser.builder().build();
        Node document = parser.parse("Test text");
        TextCollectingVisitor collectingVisitor = new TextCollectingVisitor();
        final String text = collectingVisitor.collectAndGetText(document);
        assertEquals("Test text", text);
    }

    @Test
    public void test_emphasis() {
        Parser parser = Parser.builder().build();
        Node document = parser.parse("Test text *emphasis*");
        TextCollectingVisitor collectingVisitor = new TextCollectingVisitor();
        final String text = collectingVisitor.collectAndGetText(document);
        assertEquals("Test text emphasis", text);
    }

    @Test
    public void test_strong_emphasis() {
        Parser parser = Parser.builder().build();
        Node document = parser.parse("Test text **emphasis**");
        TextCollectingVisitor collectingVisitor = new TextCollectingVisitor();
        final String text = collectingVisitor.collectAndGetText(document);
        assertEquals("Test text emphasis", text);
    }

    @Test
    public void test_inline_code() {
        Parser parser = Parser.builder().build();
        Node document = parser.parse("Test text `inline code`");
        TextCollectingVisitor collectingVisitor = new TextCollectingVisitor();
        final String text = collectingVisitor.collectAndGetText(document);
        assertEquals("Test text inline code", text);
    }

    @Test
    public void test_fenced_code() {
        Parser parser = Parser.builder().build();
        Node document = parser.parse("```info\nfenced code\nlines\n```");
        TextCollectingVisitor collectingVisitor = new TextCollectingVisitor();
        final String text = collectingVisitor.collectAndGetText(document);
        assertEquals("fenced code\nlines\n", text);
    }
}
