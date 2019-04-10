package com.vladsch.flexmark.util;

import com.vladsch.flexmark.util.html.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LineFormattingAppendableImplTest {
    @Test
    public void test_appendCharSb() throws Exception {
        StringBuilder sb = new StringBuilder();
        LineFormattingAppendable fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);

        fa.append(' ').appendTo(sb);
        assertEquals("", sb.toString());

        sb = new StringBuilder();
        fa.append('a').appendTo(sb);
        assertEquals("a\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append('a').append('b').append('c').line().appendTo(sb);
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append('a').append('b').append('\n').line().appendTo(sb);
        assertEquals("ab\n", sb.toString());
    }

    @Test
    public void test_appendChar() throws Exception {
        LineFormattingAppendable fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);

        fa.append(' ');
        assertEquals("", fa.toString(0));

        fa.append('a');
        assertEquals("a\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append('a').append('b').append('c').line();
        assertEquals("abc\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append('a').append('b').append('\n').line();
        assertEquals("ab\n", fa.toString(0));
    }

    @Test
    public void test_appendCharsSb() throws Exception {
        StringBuilder sb = new StringBuilder();
        LineFormattingAppendable fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);

        fa.append(" ").appendTo(sb);
        assertEquals("", sb.toString());

        fa.append("     ").appendTo(sb);
        assertEquals("", sb.toString());

        fa.append("a").appendTo(sb);
        assertEquals("a\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("abc").line().appendTo(sb);
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("abc").line().line().appendTo(sb);
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("abc").line().blankLine().appendTo(sb);
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("abc").line().blankLine().appendTo(sb,1);
        assertEquals("abc\n\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab\n").line().appendTo(sb);
        assertEquals("ab\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").line().appendTo(sb);
        assertEquals("ab\n", sb.toString());
    }

    @Test
    public void test_appendChars() throws Exception {
        LineFormattingAppendable fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);

        fa.append(" ");
        assertEquals("", fa.toString(0));

        fa.append("     ");
        assertEquals("", fa.toString(0));

        fa.append("a");
        assertEquals("a\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("abc").line();
        assertEquals("abc\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("abc").line().line();
        assertEquals("abc\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("abc").line().blankLine();
        assertEquals("abc\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("abc").line().blankLine();
        assertEquals("abc\n\n", fa.toString(1));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab\n").line();
        assertEquals("ab\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").line();
        assertEquals("ab\n", fa.toString(0));
    }

    @Test
    public void test_lineIf() throws Exception {
        LineFormattingAppendable fa;

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab").lineIf(false).append("c").line();
        assertEquals("abc\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab").lineIf(true).append("c").line();
        assertEquals("ab\nc\n", fa.toString(0));
    }

    @Test
    public void test_BlankLine() throws Exception {
        LineFormattingAppendable fa;

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab").blankLine().append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab").blankLine().blankLine().append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab\n\n").blankLine().blankLine().append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").blankLine().append("c");
        assertEquals("ab\n\nc\n", fa.toString(0));
    }

    @Test
    public void test_BlankLineIf() throws Exception {
        LineFormattingAppendable fa;

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab").blankLineIf(false).append("c").line();
        assertEquals("abc\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab").blankLineIf(true).blankLine().append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(0));
    }

    @Test
    public void test_BlankLines() throws Exception {
        LineFormattingAppendable fa;

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab").blankLine(1).append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab").blankLine(1).blankLine(0).append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab\n\n").blankLine(1).blankLine(2).append("c").line();
        assertEquals("ab\n\n\nc\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").blankLine(1).append("c");
        assertEquals("ab\n\nc\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").blankLine(2).append("c");
        assertEquals("ab\n\n\nc\n", fa.toString(0));
    }

    @Test
    public void test_noIndent() throws Exception {
        String indent = "";
        LineFormattingAppendable fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.setIndentPrefix(indent);

        fa.indent().append(" ").unIndent();
        assertEquals("", fa.toString(0));

        fa.indent().append("     ").unIndent();
        assertEquals("", fa.toString(0));

        fa.indent().append("     ").indent().unIndent().unIndent();
        assertEquals("", fa.toString(0));

        fa.indent().append("a").unIndent();
        assertEquals("a\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("abc").indent().unIndent();
        assertEquals("abc\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").indent().append("c").unIndent();
        assertEquals("ab\nc\n", fa.toString(0));
    }

    @Test
    public void test_indent() throws Exception {
        String indent = " ";
        LineFormattingAppendable fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append(" ").unIndent();
        assertEquals("", fa.toString(0));

        fa.indent().append("     ").unIndent();
        assertEquals("", fa.toString(0));

        fa.indent().append("     ").indent().unIndent().unIndent();
        assertEquals("", fa.toString(0));

        fa.indent().append("a").unIndent();
        assertEquals(" a\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);
        fa.append("abc").indent().unIndent();
        assertEquals("abc\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab").indent().append("c").unIndent();
        assertEquals("ab\n c\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab\n    \t \n\t   \n\n").indent().append("c").unIndent();
        assertEquals("ab\n c\n", fa.toString(0));

        indent = "  ";
        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);
        fa.indent().append("a").unIndent();
        assertEquals("  a\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);
        fa.append("abc").indent().unIndent();
        assertEquals("abc\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab").indent().append("c").unIndent();
        assertEquals("ab\n  c\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab\n    \t \n\t   \n\n").indent().append("c").unIndent();
        assertEquals("ab\n  c\n", fa.toString(0));
    }

    @Test
    public void test_openPreFormatted() throws Exception {
        String indent = "  ";
        LineFormattingAppendable fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<pre>").openPreFormatted().append("abc\ndef \n\n").append("hij\n")
                .append("</pre>").closePreFormatted().unIndent();
        assertEquals("  <pre>abc\ndef \n\nhij\n</pre>\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").openPreFormatted().append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().append("</div>").unIndent();
        assertEquals("  <p>this is a paragraph <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").line().openPreFormatted().append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().append("</div>").unIndent();
        assertEquals("  <p>this is a paragraph\n  <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0));

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").indent().openPreFormatted().append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().unIndent().append("</div>").unIndent();
        assertEquals("  <p>this is a paragraph\n    <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0));
    }

    @Test
    public void test_lineCount() throws Exception {
        String indent = "  ";
        LineFormattingAppendable fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<pre>")
                .openPreFormatted()
                .append("abc\ndef \n\n")
                .append("hij\n")
                .append("</pre>")
                .closePreFormatted()
                .unIndent();
        assertEquals("  <pre>abc\ndef \n\nhij\n</pre>\n", fa.toString(0));
        assertEquals(5, fa.getLineCount());

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab")
                .indent()
                .append("c")
                .unIndent();
        assertEquals("ab\n  c\n", fa.toString(0));
        assertEquals(2, fa.getLineCount());

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab\n    \t \n\t   \n\n")
                .indent()
                .append("c")
                .unIndent();
        assertEquals("ab\n  c\n", fa.toString(0));
        assertEquals(2, fa.getLineCount());

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").openPreFormatted().append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().append("</div>").unIndent();
        //assertEquals("  <p>this is a paragraph <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0));
        assertEquals(3, fa.getLineCount());

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").line().openPreFormatted().append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().append("</div>").unIndent();
        //assertEquals("  <p>this is a paragraph\n  <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0));
        assertEquals(4, fa.getLineCount());

        fa = new LineFormattingAppendableImpl(LineFormattingAppendable.FORMAT_ALL).setIndentPrefix(indent);

        fa.indent()
                .append("<p>this is a paragraph ")
                .indent()
                .openPreFormatted()
                .append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted()
                .unIndent()
                .append("</div>")
                .unIndent();
        assertEquals("  <p>this is a paragraph\n    <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0));
        assertEquals(4, fa.getLineCount());
    }
}
