package com.vladsch.flexmark.core.test.util.parser;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.test.specs.TestSpecLocator;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterator;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.data.SharedDataKeys;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInHandler;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInStartsWithCharsHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
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

        InputStream input1 = TestSpecLocator.DEFAULT_RESOURCE_LOCATION.getResourceInputStream();
        Node document1;
        InputStreamReader reader = new InputStreamReader(input1, StandardCharsets.UTF_8);
        document1 = parser.parseReader(reader);

        String spec = TestSpecLocator.DEFAULT_RESOURCE_LOCATION.getResourceText();
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

    String escape(String input, Parser parser) {
        BasedSequence baseSeq = BasedSequence.of(input);
        List<SpecialLeadInHandler> handlers = Parser.SPECIAL_LEAD_IN_HANDLERS.get(parser.getOptions());
        StringBuilder sb = new StringBuilder();

        for (SpecialLeadInHandler handler : handlers) {
            if (handler.escape(baseSeq, parser.getOptions(), sb::append)) return sb.toString();
        }
        return input;
    }

    String unEscape(String input, Parser parser) {
        BasedSequence baseSeq = BasedSequence.of(input);
        List<SpecialLeadInHandler> handlers = Parser.SPECIAL_LEAD_IN_HANDLERS.get(parser.getOptions());
        StringBuilder sb = new StringBuilder();

        for (SpecialLeadInHandler handler : handlers) {
            if (handler.unEscape(baseSeq, parser.getOptions(), sb::append)) return sb.toString();
        }
        return input;
    }

    @Test
    public void test_escapeCustom() {
        Parser parser = Parser.builder().specialLeadInHandler(SpecialLeadInStartsWithCharsHandler.create('$')).build();

        assertEquals("abc", escape("abc", parser));
        assertEquals("\\$", escape("$", parser));
        assertEquals("\\$abc", escape("$abc", parser));

        assertEquals("abc", unEscape("abc", parser));
        assertEquals("$", unEscape("\\$", parser));
        assertEquals("$abc", unEscape("\\$abc", parser));
    }

    @Test
    public void test_escapeBlockQuote() {
        Parser parser = Parser.builder().build();

        assertEquals("abc", escape("abc", parser));
        assertEquals("\\>", escape(">", parser));
        assertEquals("\\>abc", escape(">abc", parser));

        assertEquals("abc", unEscape("abc", parser));
        assertEquals(">", unEscape("\\>", parser));
        assertEquals(">abc", unEscape("\\>abc", parser));
    }

    @Test
    public void test_escapeHeading() {
        Parser parser = Parser.builder().build();

        assertEquals("abc", escape("abc", parser));
        assertEquals("\\#", escape("#", parser));
        assertEquals("#abc", escape("#abc", parser));

        assertEquals("abc", unEscape("abc", parser));
        assertEquals("#", unEscape("\\#", parser));
        assertEquals("\\#abc", unEscape("\\#abc", parser));
    }

    @Test
    public void test_escapeHeadingNoAtxSpace() {
        Parser parser = Parser.builder(new MutableDataSet().set(Parser.HEADING_NO_ATX_SPACE, true)).build();

        assertEquals("abc", escape("abc", parser));
        assertEquals("\\#", escape("#", parser));
        assertEquals("\\#abc", escape("#abc", parser));

        assertEquals("abc", unEscape("abc", parser));
        assertEquals("#", unEscape("\\#", parser));
        assertEquals("#abc", unEscape("\\#abc", parser));
    }

    @Test
    public void test_escapeUnorderedList() {
        Parser parser = Parser.builder().build();

        assertEquals("abc", escape("abc", parser));

        assertEquals("\\+", escape("+", parser));
        assertEquals("+abc", escape("+abc", parser));

        assertEquals("\\-", escape("-", parser));
        assertEquals("-abc", escape("-abc", parser));

        assertEquals("\\*", escape("*", parser));
        assertEquals("*abc", escape("*abc", parser));

        assertEquals("+", unEscape("\\+", parser));
        assertEquals("\\+abc", unEscape("\\+abc", parser));

        assertEquals("-", unEscape("\\-", parser));
        assertEquals("\\-abc", unEscape("\\-abc", parser));

        assertEquals("*", unEscape("\\*", parser));
        assertEquals("\\*abc", unEscape("\\*abc", parser));
    }

    @Test
    public void test_escapeUnorderedListNoNumbered() {
        Parser parser = Parser.builder(new MutableDataSet().set(SharedDataKeys.ESCAPE_NUMBERED_LEAD_IN, false)).build();

        assertEquals("abc", escape("abc", parser));

        assertEquals("\\+", escape("+", parser));
        assertEquals("+abc", escape("+abc", parser));

        assertEquals("\\-", escape("-", parser));
        assertEquals("-abc", escape("-abc", parser));

        assertEquals("\\*", escape("*", parser));
        assertEquals("*abc", escape("*abc", parser));

        assertEquals("+", unEscape("\\+", parser));
        assertEquals("\\+abc", unEscape("\\+abc", parser));

        assertEquals("-", unEscape("\\-", parser));
        assertEquals("\\-abc", unEscape("\\-abc", parser));

        assertEquals("*", unEscape("\\*", parser));
        assertEquals("\\*abc", unEscape("\\*abc", parser));
    }

    @Test
    public void test_escapeUnorderedListCustom() {
        Parser parser = Parser.builder(new MutableDataSet().set(Parser.LISTS_ITEM_PREFIX_CHARS, "$")).build();

        assertEquals("abc", escape("abc", parser));

        assertEquals("\\$", escape("$", parser));
        assertEquals("$abc", escape("$abc", parser));

        assertEquals("$", unEscape("\\$", parser));
        assertEquals("\\$abc", unEscape("\\$abc", parser));

        assertEquals("+", escape("+", parser));
        assertEquals("+abc", escape("+abc", parser));

        assertEquals("-", escape("-", parser));
        assertEquals("-abc", escape("-abc", parser));

        assertEquals("*", escape("*", parser));
        assertEquals("*abc", escape("*abc", parser));

        assertEquals("\\+", unEscape("\\+", parser));
        assertEquals("\\+abc", unEscape("\\+abc", parser));

        assertEquals("\\-", unEscape("\\-", parser));
        assertEquals("\\-abc", unEscape("\\-abc", parser));

        assertEquals("\\*", unEscape("\\*", parser));
        assertEquals("\\*abc", unEscape("\\*abc", parser));
    }

    @Test
    public void test_escapeOrderedList() {
        Parser parser = Parser.builder().build();

        assertEquals("1", escape("1", parser));
        assertEquals("2", escape("2", parser));
        assertEquals("3", escape("3", parser));

        assertEquals("", escape("", parser));
        assertEquals(".", escape(".", parser));

        assertEquals("1 ", escape("1 ", parser));
        assertEquals("2 ", escape("2 ", parser));
        assertEquals("3 ", escape("3 ", parser));

        assertEquals("1\\.", escape("1.", parser));
        assertEquals("1.abc", escape("1.abc", parser));

        assertEquals("2\\.", escape("2.", parser));
        assertEquals("2.abc", escape("2.abc", parser));

        assertEquals("1\\)", escape("1)", parser));
        assertEquals("1)abc", escape("1)abc", parser));

        assertEquals("2\\)", escape("2)", parser));
        assertEquals("2)abc", escape("2)abc", parser));

        assertEquals("1\\", unEscape("1\\", parser));
        assertEquals("2\\", unEscape("2\\", parser));
        assertEquals("3\\", unEscape("3\\", parser));

        assertEquals("\\", unEscape("\\", parser));
        assertEquals("\\.", unEscape("\\.", parser));

        assertEquals("1.", unEscape("1\\.", parser));
        assertEquals("1\\.abc", unEscape("1\\.abc", parser));

        assertEquals("2.", unEscape("2\\.", parser));
        assertEquals("2\\.abc", unEscape("2\\.abc", parser));

        assertEquals("1)", unEscape("1\\)", parser));
        assertEquals("1\\)abc", unEscape("1\\)abc", parser));

        assertEquals("2)", unEscape("2\\)", parser));
        assertEquals("2\\)abc", unEscape("2\\)abc", parser));
    }

    @Test
    public void test_escapeOrderedListNoNumbered() {
        Parser parser = Parser.builder(new MutableDataSet().set(SharedDataKeys.ESCAPE_NUMBERED_LEAD_IN, false)).build();

        assertEquals("1", escape("1", parser));
        assertEquals("2", escape("2", parser));
        assertEquals("3", escape("3", parser));

        assertEquals("", escape("", parser));
        assertEquals(".", escape(".", parser));

        assertEquals("1 ", escape("1 ", parser));
        assertEquals("2 ", escape("2 ", parser));
        assertEquals("3 ", escape("3 ", parser));

        assertEquals("1.", escape("1.", parser));
        assertEquals("1.abc", escape("1.abc", parser));

        assertEquals("2.", escape("2.", parser));
        assertEquals("2.abc", escape("2.abc", parser));

        assertEquals("1)", escape("1)", parser));
        assertEquals("1)abc", escape("1)abc", parser));

        assertEquals("2)", escape("2)", parser));
        assertEquals("2)abc", escape("2)abc", parser));

        assertEquals("1\\", unEscape("1\\", parser));
        assertEquals("2\\", unEscape("2\\", parser));
        assertEquals("3\\", unEscape("3\\", parser));

        assertEquals("\\", unEscape("\\", parser));
        assertEquals("\\.", unEscape("\\.", parser));

        assertEquals("1.", unEscape("1\\.", parser));
        assertEquals("1\\.abc", unEscape("1\\.abc", parser));

        assertEquals("2.", unEscape("2\\.", parser));
        assertEquals("2\\.abc", unEscape("2\\.abc", parser));

        assertEquals("1)", unEscape("1\\)", parser));
        assertEquals("1\\)abc", unEscape("1\\)abc", parser));

        assertEquals("2)", unEscape("2\\)", parser));
        assertEquals("2\\)abc", unEscape("2\\)abc", parser));
    }

    @Test
    public void test_escapeOrderedListDotOnly() {
        Parser parser = Parser.builder(new MutableDataSet().set(Parser.LISTS_ORDERED_ITEM_DOT_ONLY, true)).build();

        assertEquals("1", escape("1", parser));
        assertEquals("2", escape("2", parser));
        assertEquals("3", escape("3", parser));

        assertEquals("1\\.", escape("1.", parser));
        assertEquals("1.abc", escape("1.abc", parser));

        assertEquals("2\\.", escape("2.", parser));
        assertEquals("2.abc", escape("2.abc", parser));

        assertEquals("1)", escape("1)", parser));
        assertEquals("1)abc", escape("1)abc", parser));

        assertEquals("2)", escape("2)", parser));
        assertEquals("2)abc", escape("2)abc", parser));

        assertEquals("1\\", unEscape("1\\", parser));
        assertEquals("2\\", unEscape("2\\", parser));
        assertEquals("3\\", unEscape("3\\", parser));

        assertEquals("1.", unEscape("1\\.", parser));
        assertEquals("1\\.abc", unEscape("1\\.abc", parser));

        assertEquals("2.", unEscape("2\\.", parser));
        assertEquals("2\\.abc", unEscape("2\\.abc", parser));

        assertEquals("1\\)", unEscape("1\\)", parser));
        assertEquals("1\\)abc", unEscape("1\\)abc", parser));

        assertEquals("2\\)", unEscape("2\\)", parser));
        assertEquals("2\\)abc", unEscape("2\\)abc", parser));
    }

    @Test
    public void test_escapeOrderedListDotOnlyNoNumbered() {
        Parser parser = Parser.builder(new MutableDataSet().set(Parser.LISTS_ORDERED_ITEM_DOT_ONLY, true).set(SharedDataKeys.ESCAPE_NUMBERED_LEAD_IN, false)).build();

        assertEquals("1", escape("1", parser));
        assertEquals("2", escape("2", parser));
        assertEquals("3", escape("3", parser));

        assertEquals("1.", escape("1.", parser));
        assertEquals("1.abc", escape("1.abc", parser));

        assertEquals("2.", escape("2.", parser));
        assertEquals("2.abc", escape("2.abc", parser));

        assertEquals("1)", escape("1)", parser));
        assertEquals("1)abc", escape("1)abc", parser));

        assertEquals("2)", escape("2)", parser));
        assertEquals("2)abc", escape("2)abc", parser));

        assertEquals("1\\", unEscape("1\\", parser));
        assertEquals("2\\", unEscape("2\\", parser));
        assertEquals("3\\", unEscape("3\\", parser));

        assertEquals("1.", unEscape("1\\.", parser));
        assertEquals("1\\.abc", unEscape("1\\.abc", parser));

        assertEquals("2.", unEscape("2\\.", parser));
        assertEquals("2\\.abc", unEscape("2\\.abc", parser));

        assertEquals("1\\)", unEscape("1\\)", parser));
        assertEquals("1\\)abc", unEscape("1\\)abc", parser));

        assertEquals("2\\)", unEscape("2\\)", parser));
        assertEquals("2\\)abc", unEscape("2\\)abc", parser));
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

        @NotNull
        @Override
        public BasedSequence[] getSegments() {
            return EMPTY_SEGMENTS;
        }
    }

    private static class DashBlockParser extends AbstractBlockParser {
        final private DashBlock dash;

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
        @Nullable
        @Override
        public Set<Class<?>> getAfterDependents() {
            return null;
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @NotNull
        @Override
        public BlockParserFactory apply(@NotNull DataHolder options) {
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
