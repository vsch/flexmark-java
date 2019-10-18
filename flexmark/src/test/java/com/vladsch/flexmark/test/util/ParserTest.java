package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.test.spec.SpecReader;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterator;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

final public class ParserTest {

    @Test
    public void emptyReaderTest() throws IOException {
        Parser parser = Parser.builder().build();
        Node document1 = parser.parseReader(new StringReader(""));
        assertFalse(document1.hasChildren());
    }

    @Test
    public void ioReaderTest() throws IOException {
        Parser parser = Parser.builder().build();

        InputStream input1 = SpecReader.getSpecInputStream(TestUtils.class, TestUtils.DEFAULT_SPEC_RESOURCE);
        Node document1;
        InputStreamReader reader = new InputStreamReader(input1, StandardCharsets.UTF_8);
        document1 = parser.parseReader(reader);

        String spec = SpecReader.readSpec(TestUtils.class, TestUtils.DEFAULT_SPEC_RESOURCE);
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
        Document document = parser.parse(given);

        assertThat(document.getFirstChild(), instanceOf(BulletList.class));
        assertEquals("Document line count", 4, document.getLineCount());

        Node list = document.getFirstChild(); // first level list
        assertEquals("expect one child", list.getFirstChild(), list.getLastChild());
        assertEquals("1 space", firstText(list.getFirstChild()));
        assertEquals("node start line number", 0, list.getStartLineNumber());
        assertEquals("node end line number", 3, list.getEndLineNumber());

        list = list.getFirstChild().getLastChild(); // second level list
        assertEquals("expect one child", list.getFirstChild(), list.getLastChild());
        assertEquals("3 spaces", firstText(list.getFirstChild()));
        assertEquals("node start line number", 1, list.getStartLineNumber());
        assertEquals("node end line number", 3, list.getEndLineNumber());

        list = list.getFirstChild().getLastChild(); // third level list
        assertEquals("5 spaces", firstText(list.getFirstChild()));
        assertEquals("tab + space", firstText(list.getFirstChild().getNext()));
        assertEquals("node start line number", 2, list.getStartLineNumber());
        assertEquals("node end line number", 3, list.getEndLineNumber());
    }

    @Test
    public void indentationWithLines() {
        String given = " - 1 space\n   - 3 spaces\n     - 5 spaces\n\t - tab + space";
        MutableDataHolder options = new MutableDataSet().set(Parser.TRACK_DOCUMENT_LINES, true);
        Parser parser = Parser.builder(options).build();
        Document document = parser.parse(given);

        assertThat(document.getFirstChild(), instanceOf(BulletList.class));
        assertEquals("Document line count", 4, document.getLineCount());

        Node list = document.getFirstChild(); // first level list
        assertEquals("expect one child", list.getFirstChild(), list.getLastChild());
        assertEquals("1 space", firstText(list.getFirstChild()));
        assertEquals("node start line number", 0, list.getStartLineNumber());
        assertEquals("node end line number", 3, list.getEndLineNumber());

        list = list.getFirstChild().getLastChild(); // second level list
        assertEquals("expect one child", list.getFirstChild(), list.getLastChild());
        assertEquals("3 spaces", firstText(list.getFirstChild()));
        assertEquals("node start line number", 1, list.getStartLineNumber());
        assertEquals("node end line number", 3, list.getEndLineNumber());

        list = list.getFirstChild().getLastChild(); // third level list
        assertEquals("5 spaces", firstText(list.getFirstChild()));
        assertEquals("tab + space", firstText(list.getFirstChild().getNext()));
        assertEquals("node start line number", 2, list.getStartLineNumber());
        assertEquals("node end line number", 3, list.getEndLineNumber());
    }

    @Test
    public void blockquotesWithLfLineBreaks() {

        //---------------------- --1------ ---2------ ---3--------
        //--------------01234567 890123456 7890123456 789012345678
        String given = "> line1\n> line2 \n> line3  \n> line4    \n";
        Parser parser = Parser.builder().build();
        Document document = parser.parse(given);

        assertThat(document.getFirstChild(), instanceOf(BlockQuote.class));
        assertThat(document.getFirstChild().getFirstChild(), instanceOf(Paragraph.class));
        ReversiblePeekingIterator<Node> it = document.getFirstChild().getFirstChild().getChildIterator();

        assertTrue(it.hasNext());
        Node node = it.next();
        assertThat(node, instanceOf(Text.class));
        assertEquals("line1", node.getChars().toString());
        assertEquals(2, node.getStartOffset());
        assertEquals(7, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(SoftLineBreak.class));
        assertEquals(7, node.getStartOffset());
        assertEquals(8, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(Text.class));
        assertEquals("line2", node.getChars().toString());
        assertEquals(10, node.getStartOffset());
        assertEquals(15, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(SoftLineBreak.class));
        assertEquals(16, node.getStartOffset());
        assertEquals(17, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(Text.class));
        assertEquals("line3", node.getChars().toString());
        assertEquals(19, node.getStartOffset());
        assertEquals(24, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(HardLineBreak.class));
        assertEquals(24, node.getStartOffset());
        assertEquals(27, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(Text.class));
        assertEquals("line4", node.getChars().toString());
        assertEquals(29, node.getStartOffset());
        assertEquals(34, node.getEndOffset());

        assertFalse(it.hasNext());
    }

    @Test
    public void blockquotesWithCrLineBreaks() {

        //---------------------- --1------ ---2------ ---3--------
        //--------------01234567 890123456 7890123456 789012345678
        String given = "> line1\r> line2 \r> line3  \r> line4    \r";
        Parser parser = Parser.builder().build();
        Document document = parser.parse(given);

        assertThat(document.getFirstChild(), instanceOf(BlockQuote.class));
        assertThat(document.getFirstChild().getFirstChild(), instanceOf(Paragraph.class));
        ReversiblePeekingIterator<Node> it = document.getFirstChild().getFirstChild().getChildIterator();

        assertTrue(it.hasNext());
        Node node = it.next();
        assertThat(node, instanceOf(Text.class));
        assertEquals("line1", node.getChars().toString());
        assertEquals(2, node.getStartOffset());
        assertEquals(7, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(SoftLineBreak.class));
        assertEquals(7, node.getStartOffset());
        assertEquals(8, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(Text.class));
        assertEquals("line2", node.getChars().toString());
        assertEquals(10, node.getStartOffset());
        assertEquals(15, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(SoftLineBreak.class));
        assertEquals(16, node.getStartOffset());
        assertEquals(17, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(Text.class));
        assertEquals("line3", node.getChars().toString());
        assertEquals(19, node.getStartOffset());
        assertEquals(24, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(HardLineBreak.class));
        assertEquals(24, node.getStartOffset());
        assertEquals(27, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(Text.class));
        assertEquals("line4", node.getChars().toString());
        assertEquals(29, node.getStartOffset());
        assertEquals(34, node.getEndOffset());

        assertFalse(it.hasNext());
    }

    @Test
    public void blockquotesWithCrLfLineBreaks() {

        //---------------------- - -1------- - -2-------- - 3---------4- -
        //--------------01234567 8 901234567 8 9012345678 9 012345678901 2
        String given = "> line1\r\n> line2 \r\n> line3  \r\n> line4    \r\n";
        Parser parser = Parser.builder().build();
        Document document = parser.parse(given);

        assertThat(document.getFirstChild(), instanceOf(BlockQuote.class));
        assertThat(document.getFirstChild().getFirstChild(), instanceOf(Paragraph.class));
        ReversiblePeekingIterator<Node> it = document.getFirstChild().getFirstChild().getChildIterator();

        assertTrue(it.hasNext());
        Node node = it.next();
        assertThat(node, instanceOf(Text.class));
        assertEquals("line1", node.getChars().toString());
        assertEquals(2, node.getStartOffset());
        assertEquals(7, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(SoftLineBreak.class));
        assertEquals(7, node.getStartOffset());
        assertEquals(9, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(Text.class));
        assertEquals("line2", node.getChars().toString());
        assertEquals(11, node.getStartOffset());
        assertEquals(16, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(SoftLineBreak.class));
        assertEquals(17, node.getStartOffset());
        assertEquals(19, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(Text.class));
        assertEquals("line3", node.getChars().toString());
        assertEquals(21, node.getStartOffset());
        assertEquals(26, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(HardLineBreak.class));
        assertEquals(26, node.getStartOffset());
        assertEquals(30, node.getEndOffset());

        assertTrue(it.hasNext());
        node = it.next();
        assertThat(node, instanceOf(Text.class));
        assertEquals("line4", node.getChars().toString());
        assertEquals(32, node.getStartOffset());
        assertEquals(37, node.getEndOffset());

        assertFalse(it.hasNext());
    }

    private String firstText(Node n) {
        while (!(n instanceof Text)) {
            assertThat(n, notNullValue());
            n = n.getFirstChild();
        }
        return n.getChars().toString();
    }

    private interface DashBlockVisitor {
        void visit(DashBlock node);
    }

    private static class DashBlock extends Block {
        DashBlock() {
        }

        @Override
        public BasedSequence[] getSegments() {
            return EMPTY_SEGMENTS;
        }
    }

    private static class DashBlockParser extends AbstractBlockParser {
        private final DashBlock dash;

        public DashBlockParser(BasedSequence line) {
            dash = new DashBlock();
            dash.setChars(line);
        }

        @Override
        public Block getBlock() {
            return dash;
        }

        @Override
        public void closeBlock(ParserState state) {
            dash.setCharsFromContent();
        }

        @Override
        public BlockContinue tryContinue(ParserState state) {
            return BlockContinue.none();
        }
    }

    public static class DashBlockParserFactory implements CustomBlockParserFactory {
        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @Override
        public BlockParserFactory apply(DataHolder options) {
            return new BlockFactory(options);
        }
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        BlockFactory(DataHolder options) {
            super(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            if (state.getLine().equals("---")) {
                return BlockStart.of(new DashBlockParser(state.getLine()));
            }
            return BlockStart.none();
        }
    }
}
