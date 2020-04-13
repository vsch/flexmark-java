package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.ExceptionMatcher;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import com.vladsch.flexmark.util.sequence.mappers.SpaceMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.junit.Assert.*;

@SuppressWarnings("PointlessArithmeticExpression")
public class LineAppendableImplTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void test_emptyAppendableIterator() {
        String input = "[simLink spaced](simLink.md)";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendableImpl fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        for (LineInfo info : fa) {
            fail();
        }
    }

    @Test
    public void test_defaultAppendsAll1() {
        LineAppendableImpl fa = new LineAppendableImpl(0);

        fa.append("\n");
        CharSequence sequence1 = fa.toSequence();
        assertEquals("\n", sequence1.toString());
    }

    @Test
    public void test_defaultAppendsAll2() {
        LineAppendableImpl fa = new LineAppendableImpl(0);

        fa.append("    \n");
        CharSequence sequence1 = fa.toSequence();
        assertEquals("    \n", sequence1.toString());
    }

    @Test
    public void test_defaultAppendsOther03() {
        LineAppendableImpl fa = new LineAppendableImpl(0);
        LineAppendableImpl fa2 = new LineAppendableImpl(0);

        fa2.append("line 1\n");
        fa2.append("line 2\n");
        fa2.append("line 3\n");
        fa.append(fa2, 0, 3, true);

        CharSequence sequence1 = fa.toSequence();
        assertEquals("" +
                "line 1\n" +
                "line 2\n" +
                "line 3\n" +
                "", sequence1.toString());
    }

    @Test
    public void test_defaultAppendsOther13() {
        LineAppendableImpl fa = new LineAppendableImpl(0);
        LineAppendableImpl fa2 = new LineAppendableImpl(0);

        fa2.append("line 1\n");
        fa2.append("line 2\n");
        fa2.append("line 3\n");
        fa.append(fa2, 1, 3, true);

        CharSequence sequence1 = fa.toSequence();
        assertEquals("" +
                "line 2\n" +
                "line 3\n" +
                "", sequence1.toString());
    }

    @Test
    public void test_defaultAppendsOther23() {
        LineAppendableImpl fa = new LineAppendableImpl(0);
        LineAppendableImpl fa2 = new LineAppendableImpl(0);

        fa2.append("line 1\n");
        fa2.append("line 2\n");
        fa2.append("line 3\n");
        fa.append(fa2, 2, 3, true);

        CharSequence sequence1 = fa.toSequence();
        assertEquals("" +
                "line 3\n" +
                "", sequence1.toString());
    }

    @Test
    public void test_defaultAppendsOther33() {
        LineAppendableImpl fa = new LineAppendableImpl(0);
        LineAppendableImpl fa2 = new LineAppendableImpl(0);

        fa2.append("line 1\n");
        fa2.append("line 2\n");
        fa2.append("line 3\n");
        fa.append(fa2, 3, 3, true);

        CharSequence sequence1 = fa.toSequence();
        assertEquals("" +
                "", sequence1.toString());
    }

    @Test
    public void test_defaultAppendsOther01() {
        LineAppendableImpl fa = new LineAppendableImpl(0);
        LineAppendableImpl fa2 = new LineAppendableImpl(0);

        fa2.append("line 1\n");
        fa2.append("line 2\n");
        fa2.append("line 3\n");
        fa.append(fa2, 0, 1, true);

        CharSequence sequence1 = fa.toSequence();
        assertEquals("" +
                "line 1\n" +
                "", sequence1.toString());
    }

    @Test
    public void test_defaultAppendsOther02() {
        LineAppendableImpl fa = new LineAppendableImpl(0);
        LineAppendableImpl fa2 = new LineAppendableImpl(0);

        fa2.append("line 1\n");
        fa2.append("line 2\n");
        fa2.append("line 3\n");
        fa.append(fa2, 0, 2, true);

        CharSequence sequence1 = fa.toSequence();
        assertEquals("" +
                "line 1\n" +
                "line 2\n" +
                "", sequence1.toString());
    }

    @Test
    public void test_defaultAppendsOther00() {
        LineAppendableImpl fa = new LineAppendableImpl(0);
        LineAppendableImpl fa2 = new LineAppendableImpl(0);

        fa2.append("line 1\n");
        fa2.append("line 2\n");
        fa2.append("line 3\n");
        fa.append(fa2, 0, 0, true);

        CharSequence sequence1 = fa.toSequence();
        assertEquals("" +
                "", sequence1.toString());
    }

    @Test
    public void test_appendCharSb() throws Exception {
        StringBuilder sb = new StringBuilder();
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);

        fa.append(' ').appendTo(sb);
        assertEquals("", sb.toString());

        sb = new StringBuilder();
        fa.append('a').appendTo(sb);
        assertEquals("a\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append('a').append('b').append('c').line().appendTo(sb);
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append('a').append('b').append('\n').line().appendTo(sb);
        assertEquals("ab\n", sb.toString());
    }

    @Test
    public void test_toString() {
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);

        fa.append(' ');
        assertEquals("", fa.toString());

        fa.append('a');
        assertEquals("a", fa.toString());

        fa.line();
        assertEquals("a\n", fa.toString());
    }

    @Test
    public void test_appendChar() {
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);

        fa.append(' ');
        assertEquals("", fa.toString(0, 0));

        fa.append('a');
        assertEquals("a\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append('a').append('b').append('c').line();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append('a').append('b').append('\n').line();
        assertEquals("ab\n", fa.toString(0, 0));
    }

    @Test
    public void test_leadingEOL() throws Exception {
        StringBuilder sb = new StringBuilder();
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL & ~LineAppendable.F_TRIM_LEADING_EOL);

        fa.line().appendTo(sb);
        assertEquals("", sb.toString());

        fa.blankLine().appendTo(sb, 2, 2);
        assertEquals("\n", sb.toString());
        sb.delete(0, sb.length());

        fa.blankLine().appendTo(sb, 2, 2);
        assertEquals("\n", sb.toString());
        sb.delete(0, sb.length());

        fa.blankLine(1).appendTo(sb, 2, 2);
        assertEquals("\n", sb.toString());
        sb.delete(0, sb.length());

        fa.blankLine(2).appendTo(sb, 2, 2);
        assertEquals("\n\n", sb.toString());
        sb.delete(0, sb.length());

        fa.blankLine(2).appendTo(sb, 2, 2);
        assertEquals("\n\n", sb.toString());
        sb.delete(0, sb.length());

        fa.blankLine(1).appendTo(sb, 2, 2);
        assertEquals("\n\n", sb.toString());
        sb.delete(0, sb.length());

        fa.blankLine().appendTo(sb, 2, 2);
        assertEquals("\n\n", sb.toString());
        sb.delete(0, sb.length());
    }

    @Test
    public void test_noLeadingEOL() throws Exception {
        StringBuilder sb = new StringBuilder();
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);

        fa.line().appendTo(sb);
        assertEquals("", sb.toString());

        fa.blankLine().appendTo(sb, 2, 2);
        assertEquals("", sb.toString());

        fa.blankLine().appendTo(sb, 2, 2);
        assertEquals("", sb.toString());

        fa.blankLine(1).appendTo(sb, 2, 2);
        assertEquals("", sb.toString());

        fa.blankLine(2).appendTo(sb, 2, 2);
        assertEquals("", sb.toString());

        fa.blankLine(2).appendTo(sb, 2, 2);
        assertEquals("", sb.toString());

        fa.blankLine(1).appendTo(sb, 2, 2);
        assertEquals("", sb.toString());

        fa.blankLine().appendTo(sb, 2, 2);
        assertEquals("", sb.toString());
    }

    @Test
    public void test_appendCharsSb() throws Exception {
        StringBuilder sb = new StringBuilder();
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);

        fa.append(" ").appendTo(sb);
        assertEquals("", sb.toString());

        fa.append("     ").appendTo(sb);
        assertEquals("", sb.toString());

        fa.append("a").appendTo(sb);
        assertEquals("a\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").line().appendTo(sb);
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").line().line().appendTo(sb);
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").line().blankLine().appendTo(sb);
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").line().blankLine().appendTo(sb, 1, 1);
        assertEquals("abc\n\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n").line().appendTo(sb);
        assertEquals("ab\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").line().appendTo(sb);
        assertEquals("ab\n", sb.toString());
    }

    @Test
    public void test_appendChars() {
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);

        fa.append(" ");
        assertEquals("", fa.toString(0, 0));

        fa.append("     ");
        assertEquals("", fa.toString(0, 0));

        fa.append("a");
        assertEquals("a\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").line();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").line().line();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").line().blankLine();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").line().blankLine();
        assertEquals("abc\n\n", fa.toString(1, 1));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n").line();
        assertEquals("ab\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").line();
        assertEquals("ab\n", fa.toString(0, 0));
    }

    @Test
    public void test_lineIf() {
        LineAppendable fa;

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab").lineIf(false).append("c").line();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab").lineIf(true).append("c").line();
        assertEquals("ab\nc\n", fa.toString(0, 0));
    }

    @Test
    public void test_BlankLine() {
        LineAppendable fa;

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab").blankLine().append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab").blankLine().blankLine().append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n\n").blankLine().blankLine().append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").blankLine().append("c");
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));
    }

    @Test
    public void test_BlankLineIf() {
        LineAppendable fa;

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab").blankLineIf(false).append("c").line();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab").blankLineIf(true).blankLine().append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));
        assertEquals("ab\nc", fa.toString(0, -1));
    }

    @Test
    public void test_BlankLines() {
        LineAppendable fa;

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab").blankLine(1).append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab").blankLine(1).blankLine(0).append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n\n").blankLine(1).blankLine(2).append("c").line();
        assertEquals("ab\n\n\nc\n", fa.toString(2, 0));
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").blankLine(1).append("c");
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").blankLine(2).append("c");
        assertEquals("ab\n\n\nc\n", fa.toString(2, 0));
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));
    }

    @Test
    public void test_noIndent() {
        String indent = "";
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.setIndentPrefix(indent);

        fa.indent().append(" ").unIndent();
        assertEquals("", fa.toString(0, 0));

        fa.indent().append("     ").unIndent();
        assertEquals("", fa.toString(0, 0));

        fa.indent().append("     ").indent().unIndent().unIndent();
        assertEquals("", fa.toString(0, 0));

        fa.indent().append("a").unIndent();
        assertEquals("a\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").indent().unIndent();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").indent().append("c").unIndent();
        assertEquals("ab\nc\n", fa.toString(0, 0));
    }

    @Test
    public void test_indent() {
        String indent = " ";
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append(" ").unIndent();
        assertEquals("", fa.toString(0, 0));

        fa.indent().append("     ").unIndent();
        assertEquals("", fa.toString(0, 0));

        fa.indent().append("     ").indent().unIndent().unIndent();
        assertEquals("", fa.toString(0, 0));

        fa.indent().append("a").unIndent();
        assertEquals(" a\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.append("abc").indent().unIndent();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab").indent().append("c").unIndent();
        assertEquals("ab\n c\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab\n    \t \n\t   \n\n").indent().append("c").unIndent();
        assertEquals("ab\n c\n", fa.toString(0, 0));

        indent = "  ";
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.indent().append("a").unIndent();
        assertEquals("  a\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.append("abc").indent().unIndent();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab").indent().append("c").unIndent();
        assertEquals("ab\n  c\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab\n    \t \n\t   \n\n").indent().append("c").unIndent();
        assertEquals("ab\n  c\n", fa.toString(0, 0));
    }

    @Test
    public void test_openPreFormatted() {
        String indent = "  ";
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<pre>").openPreFormatted(false).append("abc\ndef \n\n").append("hij\n")
                .append("</pre>").closePreFormatted().unIndent();
        assertEquals("  <pre>abc\ndef \n\nhij\n</pre>\n", fa.toString(1, 0));
        assertEquals("  <pre>abc\ndef \n\nhij\n</pre>\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").openPreFormatted(false).append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().append("</div>").unIndent();
        assertEquals("  <p>this is a paragraph <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").line().openPreFormatted(false).append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().append("</div>").unIndent();
        assertEquals("  <p>this is a paragraph\n  <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").indent().openPreFormatted(false).append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().unIndent().append("</div>").unIndent();
        assertEquals("  <p>this is a paragraph\n    <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0, 0));
    }

    @Test
    public void test_lineCount() {
        String indent = "  ";
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<pre>")
                .openPreFormatted(false)
                .append("abc\ndef \n\n")
                .append("hij\n")
                .append("</pre>")
                .closePreFormatted()
                .unIndent();
        assertEquals("  <pre>abc\ndef \n\nhij\n</pre>\n", fa.toString(0, 0));
        assertEquals("  <pre>abc\ndef \n\nhij\n</pre>\n", fa.toString(1, 0));
        assertEquals(5, fa.getLineCountWithPending());

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab")
                .indent()
                .append("c")
                .unIndent();
        assertEquals("ab\n  c\n", fa.toString(0, 0));
        assertEquals(2, fa.getLineCountWithPending());

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab\n    \t \n\t   \n\n")
                .indent()
                .append("c")
                .unIndent();
        assertEquals("ab\n  c\n", fa.toString(0, 0));
        assertEquals(5, fa.getLineCountWithPending());

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").openPreFormatted(false).append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().append("</div>").unIndent();
        //assertEquals("  <p>this is a paragraph <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0));
        assertEquals(3, fa.getLineCountWithPending());

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").line().openPreFormatted(false).append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().append("</div>").unIndent();
        //assertEquals("  <p>this is a paragraph\n  <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0));
        assertEquals(4, fa.getLineCountWithPending());

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent()
                .append("<p>this is a paragraph ")
                .indent()
                .openPreFormatted(false)
                .append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted()
                .unIndent()
                .append("</div>")
                .unIndent();
        assertEquals("  <p>this is a paragraph\n    <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0, 0));
        assertEquals(4, fa.getLineCountWithPending());
    }

    @Test
    public void test_leadingSpace() {
        String indent = "";
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL & ~(LineAppendable.F_COLLAPSE_WHITESPACE | LineAppendable.F_TRIM_LEADING_WHITESPACE));
        fa.setIndentPrefix(indent);

        fa.append("  abc");
        assertEquals("  abc\n", fa.toString(0, 0));

        fa.append("     def");
        assertEquals("  abc\n     def\n", fa.toString(0, 0));

        fa.append("a");
        assertEquals("  abc\n     def\na\n", fa.toString(0, 0));
    }

    @Test
    public void test_leadingSpaceVaries() {
        String indent = "";
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        fa.setIndentPrefix(indent);

        int saved = fa.getOptions();
        fa.setOptions(saved & ~(LineAppendable.F_COLLAPSE_WHITESPACE | LineAppendable.F_TRIM_LEADING_WHITESPACE));
        fa.append("  abc");
        fa.setOptions(saved);
//        assertEquals("  abc\n", fa.toString(0));

        fa.append("     def\n");
        assertEquals("  abc def\n", fa.toString(0, 0));

        fa.append("a");
        assertEquals("  abc def\na\n", fa.toString(0, 0));
    }

    @Test
    public void test_withBuilder() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49)).line();

        assertEquals("Line: " + 0, sequence.subSequence(0 * 10, 0 * 10 + 10), fa.getLine(0));
        assertEquals("Line: " + 1, sequence.subSequence(1 * 10, 1 * 10 + 10), fa.getLine(1));
        assertEquals("Line: " + 2, sequence.subSequence(2 * 10, 2 * 10 + 10), fa.getLine(2));
        assertEquals("Line: " + 3, sequence.subSequence(3 * 10, 3 * 10 + 10), fa.getLine(3));
        assertEquals("Line: " + 4, sequence.subSequence(4 * 10, 4 * 10 + 10), fa.getLine(4));

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=0, tl=9, l=10, sumPl=0, sumTl=18, sumL=20, bp, '1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=0, tl=9, l=10, sumPl=0, sumTl=27, sumL=30, bp, '2:2343568\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=0, sumTl=36, sumL=40, bp, '3:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=0, sumTl=45, sumL=50, bp, '4:2343568\\n'}", fa.getLineInfo(4).toString());
    }

    @Test
    public void test_prefixAfterEol() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.pushPrefix().addPrefix("  ", true);
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.popPrefix(true);
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49)).line();

        assertEquals("Line: " + 0, sequence.subSequence(0 * 10, 0 * 10 + 10), fa.getLine(0));
        assertEquals("Line: " + 1, sequence.subSequence(1 * 10, 1 * 10 + 10), fa.getLine(1));
        assertEquals("Line: " + 2, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(2 * 10, 2 * 10 + 10)), fa.getLine(2));
        assertEquals("Line: " + 3, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(3 * 10, 3 * 10 + 10)), fa.getLine(3));
        assertEquals("Line: " + 4, sequence.subSequence(4 * 10, 4 * 10 + 10), fa.getLine(4));

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=0, tl=9, l=10, sumPl=0, sumTl=18, sumL=20, bp, '1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=2, sumTl=27, sumL=32, bp, '  2:2343568\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=2, tl=9, l=12, sumPl=4, sumTl=36, sumL=44, bp, '  3:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=4, sumTl=45, sumL=54, bp, '4:2343568\\n'}", fa.getLineInfo(4).toString());

        assertEquals("" +
                "0:2343568\n" +
                "1:2343568\n" +
                "  2:2343568\n" +
                "  3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, true));

        assertEquals("" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, false));
    }

    @Test
    public void test_prefix() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.pushPrefix().addPrefix("  ", false);
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.popPrefix(false);
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49)).line();

        assertEquals("Line: " + 0, sequence.subSequence(0 * 10, 0 * 10 + 10), fa.getLine(0));
        assertEquals("Line: " + 1, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(1 * 10, 1 * 10 + 10)), fa.getLine(1));
        assertEquals("Line: " + 2, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(2 * 10, 2 * 10 + 10)), fa.getLine(2));
        assertEquals("Line: " + 3, sequence.subSequence(3 * 10, 3 * 10 + 10), fa.getLine(3));
        assertEquals("Line: " + 4, sequence.subSequence(4 * 10, 4 * 10 + 10), fa.getLine(4));

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, bp, '  1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=4, sumTl=27, sumL=34, bp, '  2:2343568\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=4, sumTl=36, sumL=44, bp, '3:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=4, sumTl=45, sumL=54, bp, '4:2343568\\n'}", fa.getLineInfo(4).toString());

        assertEquals("" +
                "0:2343568\n" +
                "  1:2343568\n" +
                "  2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, true));

        assertEquals("" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, false));
    }

    @Test
    public void test_setPrefixLength() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.pushPrefix().addPrefix("  ", false);
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.popPrefix(false);
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49)).line();

        assertEquals("Line: " + 0, sequence.subSequence(0 * 10, 0 * 10 + 10), fa.getLine(0));
        assertEquals("Line: " + 1, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(1 * 10, 1 * 10 + 10)), fa.getLine(1));
        assertEquals("Line: " + 2, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(2 * 10, 2 * 10 + 10)), fa.getLine(2));
        assertEquals("Line: " + 3, sequence.subSequence(3 * 10, 3 * 10 + 10), fa.getLine(3));
        assertEquals("Line: " + 4, sequence.subSequence(4 * 10, 4 * 10 + 10), fa.getLine(4));

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, bp, '  1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=4, sumTl=27, sumL=34, bp, '  2:2343568\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=4, sumTl=36, sumL=44, bp, '3:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=4, sumTl=45, sumL=54, bp, '4:2343568\\n'}", fa.getLineInfo(4).toString());

        fa.setPrefixLength(2, 4);

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, bp, '  1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=4, tl=7, l=12, sumPl=6, sumTl=25, sumL=34, '  2:2343568\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=6, sumTl=34, sumL=44, bp, '3:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=6, sumTl=43, sumL=54, bp, '4:2343568\\n'}", fa.getLineInfo(4).toString());

        assertEquals("" +
                "0:2343568\n" +
                "  1:2343568\n" +
                "  2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, true));

        assertEquals("" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, false));
    }

    @Test
    public void test_setLine() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.pushPrefix().addPrefix("  ", false);
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.popPrefix(false);
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49)).line();

        assertEquals("Line: " + 0, sequence.subSequence(0 * 10, 0 * 10 + 10), fa.getLine(0));
        assertEquals("Line: " + 1, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(1 * 10, 1 * 10 + 10)), fa.getLine(1));
        assertEquals("Line: " + 2, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(2 * 10, 2 * 10 + 10)), fa.getLine(2));
        assertEquals("Line: " + 3, sequence.subSequence(3 * 10, 3 * 10 + 10), fa.getLine(3));
        assertEquals("Line: " + 4, sequence.subSequence(4 * 10, 4 * 10 + 10), fa.getLine(4));

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, bp, '  1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=4, sumTl=27, sumL=34, bp, '  2:2343568\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=4, sumTl=36, sumL=44, bp, '3:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=4, sumTl=45, sumL=54, bp, '4:2343568\\n'}", fa.getLineInfo(4).toString());

        fa.setLine(2, "", "0123456");
        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, bp, '  1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=0, tl=7, l=8, sumPl=2, sumTl=25, sumL=30, bp, '0123456\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=2, sumTl=34, sumL=40, bp, '3:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=2, sumTl=43, sumL=50, bp, '4:2343568\\n'}", fa.getLineInfo(4).toString());

        assertEquals("" +
                "0:2343568\n" +
                "  1:2343568\n" +
                "0123456\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, true));

        assertEquals("" +
                "0:2343568\n" +
                "1:2343568\n" +
                "0123456\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, false));

        fa.setLine(4, "  ", "4:01234");
        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, bp, '  1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=0, tl=7, l=8, sumPl=2, sumTl=25, sumL=30, bp, '0123456\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=2, sumTl=34, sumL=40, bp, '3:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=2, tl=7, l=10, sumPl=4, sumTl=41, sumL=50, bp, '  4:01234\\n'}", fa.getLineInfo(4).toString());

        assertEquals("" +
                "0:2343568\n" +
                "  1:2343568\n" +
                "0123456\n" +
                "3:2343568\n" +
                "  4:01234\n" +
                "", fa.toString(2, 2, true));

        assertEquals("" +
                "0:2343568\n" +
                "1:2343568\n" +
                "0123456\n" +
                "3:2343568\n" +
                "4:01234\n" +
                "", fa.toString(2, 2, false));
    }

    @Test
    public void test_setLinePending() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.pushPrefix().addPrefix("  ", false);
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.popPrefix(false);
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49));
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());

        assertEquals("Line: " + 0, sequence.subSequence(0 * 10, 0 * 10 + 10), fa.getLine(0));
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());
        assertEquals("Line: " + 1, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(1 * 10, 1 * 10 + 10)), fa.getLine(1));
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());
        assertEquals("Line: " + 2, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(2 * 10, 2 * 10 + 10)), fa.getLine(2));
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());
        assertEquals("Line: " + 3, sequence.subSequence(3 * 10, 3 * 10 + 10), fa.getLine(3));
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());
        assertEquals("Line: " + 4, sequence.subSequence(4 * 10, 4 * 10 + 10), fa.getLine(4));
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, bp, '  1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=4, sumTl=27, sumL=34, bp, '  2:2343568\\n'}", fa.getLineInfo(2).toString());
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=4, sumTl=36, sumL=44, bp, '3:2343568\\n'}", fa.getLineInfo(3).toString());
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=4, sumTl=45, sumL=54, bp, '4:2343568\\n'}", fa.getLineInfo(4).toString());
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());

        fa.setLine(fa.getLineCount(), "", "0123456");
        assertFalse(fa.getLineCountWithPending() > fa.getLineCount());

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, bp, '  1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=4, sumTl=27, sumL=34, bp, '  2:2343568\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=4, sumTl=36, sumL=44, bp, '3:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=7, l=8, sumPl=4, sumTl=43, sumL=52, bp, '0123456\\n'}", fa.getLineInfo(4).toString());

        assertEquals("" +
                "0:2343568\n" +
                "  1:2343568\n" +
                "  2:2343568\n" +
                "3:2343568\n" +
                "0123456\n" +
                "", fa.toString(2, 2, true));

        assertEquals("" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "0123456\n" +
                "", fa.toString(2, 2, false));
    }

    @Test
    public void test_insertLine() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.pushPrefix().addPrefix("  ", false);
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.popPrefix(false);
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49)).line();

        assertEquals("Line: " + 0, sequence.subSequence(0 * 10, 0 * 10 + 10), fa.getLine(0));
        assertEquals("Line: " + 1, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(1 * 10, 1 * 10 + 10)), fa.getLine(1));
        assertEquals("Line: " + 2, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(2 * 10, 2 * 10 + 10)), fa.getLine(2));
        assertEquals("Line: " + 3, sequence.subSequence(3 * 10, 3 * 10 + 10), fa.getLine(3));
        assertEquals("Line: " + 4, sequence.subSequence(4 * 10, 4 * 10 + 10), fa.getLine(4));

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, bp, '  1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=4, sumTl=27, sumL=34, bp, '  2:2343568\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=4, sumTl=36, sumL=44, bp, '3:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=4, sumTl=45, sumL=54, bp, '4:2343568\\n'}", fa.getLineInfo(4).toString());

        fa.insertLine(2, " ", "1.5:0123456");
        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, bp, '  1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=1, tl=11, l=13, sumPl=3, sumTl=29, sumL=35, bp, ' 1.5:0123456\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=2, tl=9, l=12, sumPl=5, sumTl=38, sumL=47, bp, '  2:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=5, sumTl=47, sumL=57, bp, '3:2343568\\n'}", fa.getLineInfo(4).toString());
        assertEquals("Line: " + 5, "LineInfo{i=5, pl=0, tl=9, l=10, sumPl=5, sumTl=56, sumL=67, bp, '4:2343568\\n'}", fa.getLineInfo(5).toString());

        assertEquals("" +
                "0:2343568\n" +
                "  1:2343568\n" +
                " 1.5:0123456\n" +
                "  2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, true));

        assertEquals("" +
                "0:2343568\n" +
                "1:2343568\n" +
                "1.5:0123456\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, false));

        fa.insertLine(5, "  ", "4.5:01234");
        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, bp, '  1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=1, tl=11, l=13, sumPl=3, sumTl=29, sumL=35, bp, ' 1.5:0123456\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=2, tl=9, l=12, sumPl=5, sumTl=38, sumL=47, bp, '  2:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=5, sumTl=47, sumL=57, bp, '3:2343568\\n'}", fa.getLineInfo(4).toString());
        assertEquals("Line: " + 5, "LineInfo{i=5, pl=2, tl=9, l=12, sumPl=7, sumTl=56, sumL=69, bp, '  4.5:01234\\n'}", fa.getLineInfo(5).toString());
        assertEquals("Line: " + 6, "LineInfo{i=6, pl=0, tl=9, l=10, sumPl=7, sumTl=65, sumL=79, bp, '4:2343568\\n'}", fa.getLineInfo(6).toString());

        assertEquals("" +
                "0:2343568\n" +
                "  1:2343568\n" +
                " 1.5:0123456\n" +
                "  2:2343568\n" +
                "3:2343568\n" +
                "  4.5:01234\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, true));

        assertEquals("" +
                "0:2343568\n" +
                "1:2343568\n" +
                "1.5:0123456\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4.5:01234\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, false));
    }

    @Test
    public void test_insertLinePending() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.pushPrefix().addPrefix("  ", false);
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.popPrefix(false);
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49));

        assertEquals("Line: " + 0, sequence.subSequence(0 * 10, 0 * 10 + 10), fa.getLine(0));
        assertEquals("Line: " + 1, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(1 * 10, 1 * 10 + 10)), fa.getLine(1));
        assertEquals("Line: " + 2, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(2 * 10, 2 * 10 + 10)), fa.getLine(2));
        assertEquals("Line: " + 3, sequence.subSequence(3 * 10, 3 * 10 + 10), fa.getLine(3));
        assertEquals("Line: " + 4, sequence.subSequence(4 * 10, 4 * 10 + 10), fa.getLine(4));

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, bp, '  1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=4, sumTl=27, sumL=34, bp, '  2:2343568\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=4, sumTl=36, sumL=44, bp, '3:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=4, sumTl=45, sumL=54, bp, '4:2343568\\n'}", fa.getLineInfo(4).toString());

        fa.insertLine(fa.getLineCount(), " ", "4.5:0123456");
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());
        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, bp, '  1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=4, sumTl=27, sumL=34, bp, '  2:2343568\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=4, sumTl=36, sumL=44, bp, '3:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=1, tl=11, l=13, sumPl=5, sumTl=47, sumL=57, bp, ' 4.5:0123456\\n'}", fa.getLineInfo(4).toString());
        assertEquals("Line: " + 5, "LineInfo{i=5, pl=0, tl=9, l=10, sumPl=5, sumTl=56, sumL=67, bp, '4:2343568\\n'}", fa.getLineInfo(5).toString());
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());

        assertEquals("" +
                "0:2343568\n" +
                "  1:2343568\n" +
                "  2:2343568\n" +
                "3:2343568\n" +
                " 4.5:0123456\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, true));

        assertEquals("" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4.5:0123456\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, false));
    }

    @Test
    public void test_insertLineFirst() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.pushPrefix().addPrefix("  ", false);
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.popPrefix(false);
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49));

        assertEquals("Line: " + 0, sequence.subSequence(0 * 10, 0 * 10 + 10), fa.getLine(0));
        assertEquals("Line: " + 1, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(1 * 10, 1 * 10 + 10)), fa.getLine(1));
        assertEquals("Line: " + 2, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(2 * 10, 2 * 10 + 10)), fa.getLine(2));
        assertEquals("Line: " + 3, sequence.subSequence(3 * 10, 3 * 10 + 10), fa.getLine(3));
        assertEquals("Line: " + 4, sequence.subSequence(4 * 10, 4 * 10 + 10), fa.getLine(4));

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, bp, '  1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=4, sumTl=27, sumL=34, bp, '  2:2343568\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=4, sumTl=36, sumL=44, bp, '3:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=4, sumTl=45, sumL=54, bp, '4:2343568\\n'}", fa.getLineInfo(4).toString());

        fa.insertLine(0, " ", "0.5:0123456");
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());
        assertEquals("Line: " + 0, "LineInfo{i=0, pl=1, tl=11, l=13, sumPl=1, sumTl=11, sumL=13, bp, ' 0.5:0123456\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=0, tl=9, l=10, sumPl=1, sumTl=20, sumL=23, bp, '0:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=3, sumTl=29, sumL=35, bp, '  1:2343568\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=2, tl=9, l=12, sumPl=5, sumTl=38, sumL=47, bp, '  2:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=5, sumTl=47, sumL=57, bp, '3:2343568\\n'}", fa.getLineInfo(4).toString());
        assertEquals("Line: " + 5, "LineInfo{i=5, pl=0, tl=9, l=10, sumPl=5, sumTl=56, sumL=67, bp, '4:2343568\\n'}", fa.getLineInfo(5).toString());
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());

        assertEquals("" +
                " 0.5:0123456\n" +
                "0:2343568\n" +
                "  1:2343568\n" +
                "  2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, true));

        assertEquals("" +
                "0.5:0123456\n" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, false));
    }

    @Test
    public void test_insertLineFirstBlank() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.pushPrefix().addPrefix("  ", false);
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.popPrefix(false);
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49));

        assertEquals("Line: " + 0, sequence.subSequence(0 * 10, 0 * 10 + 10), fa.getLine(0));
        assertEquals("Line: " + 1, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(1 * 10, 1 * 10 + 10)), fa.getLine(1));
        assertEquals("Line: " + 2, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(2 * 10, 2 * 10 + 10)), fa.getLine(2));
        assertEquals("Line: " + 3, sequence.subSequence(3 * 10, 3 * 10 + 10), fa.getLine(3));
        assertEquals("Line: " + 4, sequence.subSequence(4 * 10, 4 * 10 + 10), fa.getLine(4));

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, bp, '  1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=4, sumTl=27, sumL=34, bp, '  2:2343568\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=4, sumTl=36, sumL=44, bp, '3:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=4, sumTl=45, sumL=54, bp, '4:2343568\\n'}", fa.getLineInfo(4).toString());

        fa.insertLine(0, "", "");
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());
        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=0, l=1, sumPl=0, sumTl=0, sumL=1, bp bt, '\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=11, bp, '0:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=23, bp, '  1:2343568\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=2, tl=9, l=12, sumPl=4, sumTl=27, sumL=35, bp, '  2:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=4, sumTl=36, sumL=45, bp, '3:2343568\\n'}", fa.getLineInfo(4).toString());
        assertEquals("Line: " + 5, "LineInfo{i=5, pl=0, tl=9, l=10, sumPl=4, sumTl=45, sumL=55, bp, '4:2343568\\n'}", fa.getLineInfo(5).toString());
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());

        assertEquals("" +
                "\n" +
                "0:2343568\n" +
                "  1:2343568\n" +
                "  2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, true));

        assertEquals("" +
                "\n" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, false));
    }

    @Test
    public void test_insertLineFirstPrefixedBlank() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.pushPrefix().addPrefix("  ", false);
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.popPrefix(false);
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49));

        assertEquals("Line: " + 0, sequence.subSequence(0 * 10, 0 * 10 + 10), fa.getLine(0));
        assertEquals("Line: " + 1, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(1 * 10, 1 * 10 + 10)), fa.getLine(1));
        assertEquals("Line: " + 2, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(2 * 10, 2 * 10 + 10)), fa.getLine(2));
        assertEquals("Line: " + 3, sequence.subSequence(3 * 10, 3 * 10 + 10), fa.getLine(3));
        assertEquals("Line: " + 4, sequence.subSequence(4 * 10, 4 * 10 + 10), fa.getLine(4));

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, bp, '0:2343568\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, bp, '  1:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=4, sumTl=27, sumL=34, bp, '  2:2343568\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=4, sumTl=36, sumL=44, bp, '3:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=4, sumTl=45, sumL=54, bp, '4:2343568\\n'}", fa.getLineInfo(4).toString());

        fa.insertLine(0, "> ", "");
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());
        assertEquals("Line: " + 0, "LineInfo{i=0, pl=1, tl=0, l=2, sumPl=1, sumTl=0, sumL=2, bt, '>\\n'}", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=0, tl=9, l=10, sumPl=1, sumTl=9, sumL=12, bp, '0:2343568\\n'}", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=3, sumTl=18, sumL=24, bp, '  1:2343568\\n'}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=2, tl=9, l=12, sumPl=5, sumTl=27, sumL=36, bp, '  2:2343568\\n'}", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=5, sumTl=36, sumL=46, bp, '3:2343568\\n'}", fa.getLineInfo(4).toString());
        assertEquals("Line: " + 5, "LineInfo{i=5, pl=0, tl=9, l=10, sumPl=5, sumTl=45, sumL=56, bp, '4:2343568\\n'}", fa.getLineInfo(5).toString());
        assertTrue(fa.getLineCountWithPending() > fa.getLineCount());

        assertEquals("" +
                ">\n" +
                "0:2343568\n" +
                "  1:2343568\n" +
                "  2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, true));

        assertEquals("" +
                "\n" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(2, 2, false));
    }

    @Test
    public void test_maxBlankLines() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.append(sequence.subSequence(10, 19)).blankLine();
        fa.append(sequence.subSequence(20, 29)).blankLine(2);
        fa.append(sequence.subSequence(30, 39)).blankLine(3);
        fa.blankLine();

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(4, 4));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(3, 4));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(2, 4));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(1, 4));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(0, 4));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(4, 3));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n" +
                "", fa.toString(4, 2));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n" +
                "", fa.toString(4, 1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n" +
                "", fa.toString(4, 0));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568" +
                "", fa.toString(4, -1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(3, 3));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(2, 3));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(1, 3));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(0, 3));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(-1, 3));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n" +
                "", fa.toString(2, 2));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n" +
                "3:2343568\n\n\n" +
                "", fa.toString(1, 2));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n\n\n" +
                "", fa.toString(0, 2));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n\n\n" +
                "", fa.toString(-1, 2));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n" +
                "", fa.toString(2, 1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n" +
                "", fa.toString(2, 0));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568" +
                "", fa.toString(2, -1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n" +
                "3:2343568\n\n" +
                "", fa.toString(1, 1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n\n" +
                "", fa.toString(0, 1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n\n" +
                "", fa.toString(-1, 1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n" +
                "3:2343568\n" +
                "", fa.toString(1, 0));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n" +
                "3:2343568" +
                "", fa.toString(1, -1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "", fa.toString(0, 0));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "", fa.toString(-1, 0));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568" +
                "", fa.toString(0, -1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568" +
                "", fa.toString(-1, -1));
    }

    @Test
    public void test_normalize1() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.blankLine(0);
        fa.removeExtraBlankLines(-1, 0);

        assertEquals("0", "0:2343568\n", fa.toString(0, 0));
        assertEquals("1", "0:2343568\n", fa.toString(1, 1));
        assertEquals("2", "0:2343568\n", fa.toString(2, 2));

        fa.blankLine(0);
        fa.removeExtraBlankLines(-1, -1);

        assertEquals("0", "0:2343568\n", fa.toString(0, 0));
        assertEquals("1", "0:2343568\n", fa.toString(1, 1));
        assertEquals("2", "0:2343568\n", fa.toString(2, 2));
    }

    @Test
    public void test_normalize2() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.append(sequence.subSequence(10, 19)).blankLine();
        fa.append(sequence.subSequence(20, 29)).blankLine(2);
        fa.append(sequence.subSequence(30, 39)).blankLine(3);

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(3, 3));

        fa.removeExtraBlankLines(2, 2);
        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n" +
                "", fa.toString(3, 3));

        fa.removeExtraBlankLines(2, 1);
        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n" +
                "", fa.toString(3, 3));

        fa.removeExtraBlankLines(1, 1);
        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n" +
                "3:2343568\n\n" +
                "", fa.toString(3, 3));

        fa.removeExtraBlankLines(1, 0);
        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n" +
                "3:2343568\n" +
                "", fa.toString(3, 3));

        fa.removeExtraBlankLines(0, 0);
        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "", fa.toString(3, 3));

        fa.removeExtraBlankLines(0, -1);
        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "", fa.toString(3, 3));

        fa.removeExtraBlankLines(-1, -1);
        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "", fa.toString(3, 3));
    }

    @Test
    public void test_maxTailBlankLinesBuilder() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.blankLine(0);

        fa.appendToSilently(builder, -1, -1);
        assertEquals("-1", "0:2343568", builder.toString());
    }

    @Test
    public void test_iterateMaxTailBlankLinesInfo() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.append(sequence.subSequence(10, 19)).blankLine();
        fa.append(sequence.subSequence(20, 29)).blankLine(2);
        fa.append(sequence.subSequence(30, 39)).blankLine(3);

        {
            int i = 2;
            StringBuilder out = new StringBuilder();
            for (LineInfo info : fa.getLinesInfo(i)) {
//                System.out.println(info.getLine().toVisibleWhitespaceString());
                out.append(info.lineSeq);
            }
            assertEquals("" + i, fa.toString(4, i), out.toString());
        }

        for (int i = 5; i-- > 0; ) {
            StringBuilder out = new StringBuilder();
            for (LineInfo info : fa.getLinesInfo(i)) {
//                System.out.println(info.getLine().toVisibleWhitespaceString());
                out.append(info.lineSeq);
            }
            assertEquals("" + i, fa.toString(4, i), out.toString());
        }
    }

    @Test
    public void test_iterateMaxTailBlankLinesLines() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        fa.setPrefix("> ");
        fa.append(sequence.subSequence(0, 9)).line();
        fa.append(sequence.subSequence(10, 19)).blankLine();
        fa.append(sequence.subSequence(20, 29)).blankLine(2);
        fa.append(sequence.subSequence(30, 39)).blankLine(3);

        {
            int i = 2;
            StringBuilder out = new StringBuilder();
            for (BasedSequence lineSeq : fa.getLines(i)) {
//                System.out.println(info.getLine().toVisibleWhitespaceString());
                out.append(lineSeq);
            }
            assertEquals("" + i, fa.toString(4, i), out.toString());
        }

        for (int i = 5; i-- > 0; ) {
            StringBuilder out = new StringBuilder();
            for (BasedSequence lineSeq : fa.getLines(i)) {
//                System.out.println(info.getLine().toVisibleWhitespaceString());
                out.append(lineSeq);
            }
            assertEquals("" + i, fa.toString(4, i), out.toString());
        }
    }

    @Test
    public void test_iterateMaxTailBlankLinesLinesNoEOL() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        fa.append(sequence.subSequence(0, 10)).line();
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49)).line();

        ArrayList<BasedSequence> lines = new ArrayList<>();
        Iterable<BasedSequence> faLines = fa.getLines(-1, true);
        for (BasedSequence line : faLines) {
            lines.add(line);
        }

        assertEquals("0", "0:2343568\n", lines.get(0).toString());
        assertEquals("1", "1:2343568\n", lines.get(1).toString());
        assertEquals("2", "2:2343568\n", lines.get(2).toString());
        assertEquals("3", "3:2343568\n", lines.get(3).toString());
        assertEquals("4", "4:2343568", lines.get(4).toString());
    }

    @Test
    public void test_iterateMaxTailBlankLinesLinesNoPrefix() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        fa.setPrefix("> ");
        fa.append(sequence.subSequence(0, 9)).line();
        fa.append(sequence.subSequence(10, 19)).blankLine();
        fa.append(sequence.subSequence(20, 29)).blankLine(2);
        fa.append(sequence.subSequence(30, 39)).blankLine(3);

        {
            int i = 2;
            StringBuilder out = new StringBuilder();
            for (BasedSequence lineSeq : fa.getLines(i, false)) {
//                System.out.println(info.getLine().toVisibleWhitespaceString());
                out.append(lineSeq);
            }
            assertEquals("" + i, fa.toString(4, i, false), out.toString());
        }

        for (int i = 5; i-- > 0; ) {
            StringBuilder out = new StringBuilder();
            for (BasedSequence lineSeq : fa.getLines(i, false)) {
//                System.out.println(info.getLine().toVisibleWhitespaceString());
                out.append(lineSeq);
            }
            assertEquals("" + i, fa.toString(4, i, false), out.toString());
        }
    }

    @Test
    public void test_appendToNoLine() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        assertEquals("", fa.toString());
    }

    @Test
    public void test_prefixAfterEOL() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.setPrefix("* ", false);
        fa.setPrefix("", true);
        assertEquals("* ", fa.getBeforeEolPrefix().toString());
        fa.line();
        assertEquals("* ", fa.getBeforeEolPrefix().toString());
        fa.blankLine();
        assertEquals("* ", fa.getBeforeEolPrefix().toString());
        fa.append("abc");
        assertEquals("* ", fa.getBeforeEolPrefix().toString());
        fa.line();
        assertEquals("", fa.getBeforeEolPrefix().toString());
        assertEquals("* abc\n", fa.toString());
    }

    @Test
    public void test_getOffsetWithPending() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        int iMax = sequence.length();
        for (int i = 0; i < iMax; i++) {
            fa.append(sequence.subSequence(i, i + 1));
            assertEquals("i: " + i, i + 1, fa.offsetWithPending());
        }
    }

    @Test
    public void test_getOffset() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        int iMax = sequence.length();
        int lastEol = 0;
        for (int i = 0; i < iMax; i++) {
            fa.append(sequence.subSequence(i, i + 1));
            if (sequence.charAt(i) == '\n') lastEol = i + 1;
            assertEquals("i: " + i, lastEol, fa.offset());
        }
    }

    @Test
    public void test_appendLineAppendable() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendableImpl fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        LineAppendableImpl fa2 = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();

        fa2.append(fa);
        assertEquals("0", "" +
                "0:2343568\n" +
                "", fa2.toString(2, 2));

        fa2.append(" ").line();

        fa2.pushPrefix().setPrefix("", false);
        fa2.blankLine().popPrefix(false);

        assertEquals("0", "" +
                "0:2343568\n\n" +
                "", fa2.toString(2, 2));

        fa2.blankLine();
        assertEquals("0", "" +
                "0:2343568\n\n" +
                "", fa2.toString(2, 2));
    }

    @Test
    public void test_appendLineAppendablePrefixed() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendableImpl fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        LineAppendableImpl fa2 = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.setPrefix("> ", false);
        fa.append(sequence.subSequence(0, 9)).line();

        fa2.append(fa);
        assertEquals("0", "" +
                "> 0:2343568\n" +
                "", fa2.toString(2, 2));

        fa.append(" ").line();
        fa.blankLine();
        assertEquals("0", "" +
                "> 0:2343568\n" +
                ">\n" +
                "", fa.toString(2, 2));

        fa2 = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        fa2.setPrefix("  ", false);
        fa2.append(fa);

        assertEquals("0", "" +
                "  > 0:2343568\n" +
                "  >\n" +
                "", fa2.toString(2, 2));

        fa2.blankLine();
        assertEquals("0", "" +
                "  > 0:2343568\n" +
                "  >\n" +
                "", fa2.toString(2, 2));
    }

    @Test
    public void test_appendLineAppendablePrefixedUnterminated() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendableImpl fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        LineAppendableImpl fa2 = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.setPrefix("> ", false);
        fa.append(sequence.subSequence(0, 9));
        assertEquals("" +
                "> 0:2343568" +
                "", fa.toString());

        fa2.setPrefix("  ", false);
        fa2.append(fa);
        assertEquals("" +
                "  > 0:2343568" +
                "", fa2.toString());

        assertEquals("0", "" +
                "  > 0:2343568" +
                "", fa2.toString());

        fa2.append("|abc").line();
        assertEquals("0", "" +
                "  > 0:2343568|abc\n" +
                "", fa2.toString());
    }

    @Test
    public void test_appendLineAppendablePrefixedUnterminated2() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendableImpl fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), 0);

        fa.append("> ");
        assertEquals("" +
                "> " +
                "", fa.toString());

        fa.setPrefixLength(0, 2);
        assertEquals("" +
                "> \n" +
                "", fa.toString());

        LineInfo info = fa.getLineInfo(0);
        assertEquals(3, info.length);
        assertEquals(2, info.prefixLength);
        assertEquals(0, info.textLength);
    }

    @Test
    public void test_appendLineAppendablePrefixedUnterminated3() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendableImpl fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), 0);

        fa.append("> ");
        assertEquals("" +
                "> " +
                "", fa.toString());

        thrown.expect(ExceptionMatcher.match(IllegalArgumentException.class, "prefixLength 3 is out of valid range [0, 3) for the line"));
        fa.setPrefixLength(0, 3);
    }

    @Test
    public void test_appendLineAppendablePrefixedNoPrefixes() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendableImpl fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        LineAppendableImpl fa2 = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.setPrefix("> ", false);
        fa.append(sequence.subSequence(0, 9)).line();

        fa2.append(fa, false);
        assertEquals("0", "" +
                "0:2343568\n" +
                "", fa2.toString(2, 2));

        fa.append(" ").line();
        fa.blankLine();
        assertEquals("0", "" +
                "> 0:2343568\n" +
                ">\n" +
                "", fa.toString(2, 2));

        fa2 = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        fa2.setPrefix("  ", false);
        fa2.append(fa, false);

        assertEquals("0", "" +
                "  0:2343568\n" +
                "\n" +
                "", fa2.toString(2, 2));

        fa2.blankLine();
        assertEquals("0", "" +
                "  0:2343568\n" +
                "\n" +
                "", fa2.toString(2, 2));
    }

    @Test
    public void test_appendLineAppendableMapped() {
        String input = "[simLink spaced](simLink.md)";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendableImpl fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.setPrefix("> ", false);
        BasedSequence mapped = sequence.toMapped(SpaceMapper.toNonBreakSpace);
        fa.append(mapped);
        LineInfo info = fa.getLineInfo(0);
        SequenceBuilder lineBuilder = sequence.getBuilder().append(info.getLine());
        assertEquals("⟦⟧> ⟦[simLink⟧ ⟦spaced](simLink.md)⟧\\n⟦⟧", lineBuilder.toStringWithRanges(true));

        SequenceBuilder lineBuilder2 = sequence.getBuilder().append(info.getLineNoEOL());
        assertEquals("⟦⟧> ⟦[simLink⟧ ⟦spaced](simLink.md)⟧", lineBuilder2.toStringWithRanges(true));

        BasedSequence actual = BasedSequence.of(fa.toSequence(0, -1));
        assertEquals("> [simLink\u00A0spaced](simLink.md)", actual.toString());
        SequenceBuilder actualBuilder = sequence.getBuilder().append(actual);
        assertEquals("⟦⟧> ⟦[simLink⟧\u00A0⟦spaced](simLink.md)⟧", actualBuilder.toStringWithRanges(true));

        BasedSequence actualSpc = actual.toMapped(SpaceMapper.fromNonBreakSpace);
        assertEquals("> [simLink spaced](simLink.md)", actualSpc.toString());
        SequenceBuilder actualSpcBuilder = sequence.getBuilder().append(actualSpc);
        assertEquals("⟦⟧> ⟦[simLink spaced](simLink.md)⟧", actualSpcBuilder.toStringWithRanges(true));
    }
}

