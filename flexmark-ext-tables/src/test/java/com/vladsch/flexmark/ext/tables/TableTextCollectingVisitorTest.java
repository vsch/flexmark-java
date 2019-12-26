package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.TextCollectingVisitor;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TableTextCollectingVisitorTest {
    @Test
    public void test_basic() {
        DataHolder options = new MutableDataSet()
                .set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()))
                .toImmutable();

        Parser parser = Parser.builder(options).build();
        String markdown =
                "| First Header  | Second Header |\n" +
                        "| ------------- | ------------- |\n" +
                        "| Content Cell  | Content Cell  |\n" +
                        "\n" +
                        "| Left-aligned | Center-aligned | Right-aligned |\n" +
                        "| :---         |     :---:      |          ---: |\n" +
                        "| git status   | git status     | git status    |\n" +
                        "| git diff     | git diff       | git diff      |\n" +
                        "";
        Node document = parser.parse(markdown);
        TextCollectingVisitor collectingVisitor = new TextCollectingVisitor();
        final String text = collectingVisitor.collectAndGetText(document);
        //System.out.println(text);

        //final String astText = new AstCollectingVisitor().collectAndGetAstText(document);
        //System.out.println(astText);
        assertEquals("" +
                "First Header Second Header\n" +
                "Content Cell Content Cell\n" +
                "\n" +
                "Left-aligned Center-aligned Right-aligned\n" +
                "git status git status git status\n" +
                "git diff git diff git diff\n" +
                "\n" +
                "", text);
    }
}

