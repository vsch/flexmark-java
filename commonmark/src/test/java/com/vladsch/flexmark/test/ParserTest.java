package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.spec.SpecReader;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ParserTest {

    @Test
    public void ioReaderTest() throws IOException {
        Parser parser = Parser.builder().build();

        InputStream input1 = SpecReader.getSpecInputStream();
        Node document1;
        try (InputStreamReader reader = new InputStreamReader(input1)) {
            document1 = parser.parseReader(reader);
        }

        String spec = SpecReader.readSpec();
        Node document2 = parser.parse(spec);

        HtmlRenderer renderer = HtmlRenderer.builder().escapeHtml(true).build();
        assertEquals(renderer.render(document2), renderer.render(document1));
    }

    @Test
    public void customBlockParserFactory() {
        Parser parser = Parser.builder().customBlockParserFactory(new DashBlockParserFactory()).build();

        // The dashes would normally be a ThematicBreak
        Node document = parser.parse("hey\n\n---\n");

        assertThat(document.getFirstChild(), instanceOf(Paragraph.class));
        assertEquals("hey", ((Text) document.getFirstChild().getFirstChild()).getChars().toString());
        assertThat(document.getLastChild(), instanceOf(DashBlock.class));
    }

    @Test
    public void indentation() {
        String given = " - 1 space\n   - 3 spaces\n     - 5 spaces\n\t - tab + space";
        Parser parser = Parser.builder().build();
        Node document = parser.parse(given);

        assertThat(document.getFirstChild(), instanceOf(BulletList.class));

        Node list = document.getFirstChild(); // first level list
        assertEquals("expect one child", list.getFirstChild(), list.getLastChild());
        assertEquals("1 space", firstText(list.getFirstChild()));

        list = list.getFirstChild().getLastChild(); // second level list
        assertEquals("expect one child", list.getFirstChild(), list.getLastChild());
        assertEquals("3 spaces", firstText(list.getFirstChild()));

        list = list.getFirstChild().getLastChild(); // third level list
        assertEquals("5 spaces", firstText(list.getFirstChild()));
        assertEquals("tab + space", firstText(list.getFirstChild().getNext()));
    }

    private String firstText(Node n) {
        while (!(n instanceof Text)) {
            assertThat(n, notNullValue());
            n = n.getFirstChild();
        }
        return n.getChars().toString();
    }

    private static class DashBlock extends CustomBlock {
    }

    private static class DashBlockParser extends AbstractBlockParser {

        private DashBlock dash = new DashBlock();

        @Override
        public Block getBlock() {
            return dash;
        }

        @Override
        public BlockContinue tryContinue(ParserState parserState) {
            return BlockContinue.none();
        }
    }

    private static class DashBlockParserFactory extends AbstractBlockParserFactory {

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            if (state.getLine().equals("---")) {
                return BlockStart.of(new DashBlockParser());
            }
            return BlockStart.none();
        }
    }
}
