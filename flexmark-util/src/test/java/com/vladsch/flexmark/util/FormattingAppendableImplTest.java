package com.vladsch.flexmark.util;

import com.vladsch.flexmark.util.html.ConditionalFormatter;
import com.vladsch.flexmark.util.html.FormattingAppendable;
import com.vladsch.flexmark.util.html.FormattingAppendableImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormattingAppendableImplTest {
    @Test
    public void test_appendChar() throws Exception {
        StringBuilder sb = new StringBuilder();
        FormattingAppendable fa = new FormattingAppendableImpl(sb, true);

        fa.append(' ').flush();
        assertEquals("", sb.toString());

        fa.append('a').flush();
        assertEquals("a\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append('a').append('b').append('c').line().flush();
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append('a').append('b').append('\n').line().flush();
        assertEquals("ab\n", sb.toString());
    }

    @Test
    public void test_charOffset() throws Exception {
        StringBuilder sb = new StringBuilder();
        FormattingAppendable fa = new FormattingAppendableImpl(sb, true);

        fa.append(' ');
        assertEquals(0, fa.getOffsetBefore());
        assertEquals(0, fa.getOffsetAfter());
        fa.flush();
        assertEquals("", sb.toString());
        assertEquals(0, fa.getOffsetBefore());
        assertEquals(0, fa.getOffsetAfter());

        fa.append('a');
        assertEquals(0, fa.getOffsetBefore());
        assertEquals(1, fa.getOffsetAfter());
        fa.flush();
        assertEquals("a\n", sb.toString());
        assertEquals(1, fa.getOffsetBefore());
        assertEquals(2, fa.getOffsetAfter());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append('a').append('b').append('c').line();
        assertEquals(2, fa.getOffsetBefore());
        assertEquals(3, fa.getOffsetAfter());
        fa.flush();
        assertEquals("abc\n", sb.toString());
        assertEquals(3, fa.getOffsetBefore());
        assertEquals(4, fa.getOffsetAfter());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append('a').append('b').append('\n');
        assertEquals(1, fa.getOffsetBefore());
        assertEquals(2, fa.getOffsetAfter());
        fa.line().flush();
        assertEquals("ab\n", sb.toString());
        assertEquals(2, fa.getOffsetBefore());
        assertEquals(3, fa.getOffsetAfter());
    }

    @Test
    public void test_appendChars() throws Exception {
        StringBuilder sb = new StringBuilder();
        FormattingAppendable fa = new FormattingAppendableImpl(sb, true);

        fa.append(" ").flush();
        assertEquals("", sb.toString());

        fa.append("     ").flush();
        assertEquals("", sb.toString());

        fa.append("a").flush();
        assertEquals("a\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("abc").line().flush();
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("abc").line().line().flush();
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("abc").line().blankLine().flush();
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("abc").line().blankLine().flush(1);
        assertEquals("abc\n\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab\n").line().flush();
        assertEquals("ab\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab\n    \t \n\t   \n\n").line().flush();
        assertEquals("ab\n", sb.toString());
    }

    @Test
    public void test_charsOffset() throws Exception {
        StringBuilder sb = new StringBuilder();
        FormattingAppendable fa = new FormattingAppendableImpl(sb, true);

        fa.append(" ");
        assertEquals(0, fa.getOffsetBefore());
        assertEquals(0, fa.getOffsetAfter());
        fa.flush();
        assertEquals("", sb.toString());
        assertEquals(0, fa.getOffsetBefore());
        assertEquals(0, fa.getOffsetAfter());

        fa.append("     ");
        assertEquals(0, fa.getOffsetBefore());
        assertEquals(0, fa.getOffsetAfter());
        fa.flush();
        assertEquals("", sb.toString());
        assertEquals(0, fa.getOffsetBefore());
        assertEquals(0, fa.getOffsetAfter());

        fa.append("a");
        assertEquals(0, fa.getOffsetBefore());
        assertEquals(1, fa.getOffsetAfter());
        fa.flush();
        assertEquals("a\n", sb.toString());
        assertEquals(1, fa.getOffsetBefore());
        assertEquals(2, fa.getOffsetAfter());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("abc").line();
        assertEquals(0, fa.getOffsetBefore());
        assertEquals(3, fa.getOffsetAfter());
        fa.flush();
        assertEquals("abc\n", sb.toString());
        assertEquals(3, fa.getOffsetBefore());
        assertEquals(4, fa.getOffsetAfter());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("abc").line().line();
        assertEquals(0, fa.getOffsetBefore());
        assertEquals(3, fa.getOffsetAfter());
        fa.flush();
        assertEquals("abc\n", sb.toString());
        assertEquals(3, fa.getOffsetBefore());
        assertEquals(4, fa.getOffsetAfter());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("abc").line().blankLine();
        assertEquals(0, fa.getOffsetBefore());
        assertEquals(3, fa.getOffsetAfter());
        fa.flush();
        assertEquals("abc\n", sb.toString());
        assertEquals(3, fa.getOffsetBefore());
        assertEquals(4, fa.getOffsetAfter());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("abc").line().blankLine();
        assertEquals(0, fa.getOffsetBefore());
        assertEquals(3, fa.getOffsetAfter());
        fa.flush(1);
        assertEquals("abc\n\n", sb.toString());
        assertEquals(3, fa.getOffsetBefore());
        assertEquals(5, fa.getOffsetAfter());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab\n").line();
        assertEquals(0, fa.getOffsetBefore());
        assertEquals(2, fa.getOffsetAfter());
        fa.flush();
        assertEquals("ab\n", sb.toString());
        assertEquals(2, fa.getOffsetBefore());
        assertEquals(3, fa.getOffsetAfter());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab\n    \t \n\t   \n\n").line();
        assertEquals(0, fa.getOffsetBefore());
        assertEquals(2, fa.getOffsetAfter());
        fa.flush();
        assertEquals("ab\n", sb.toString());
        assertEquals(2, fa.getOffsetBefore());
        assertEquals(3, fa.getOffsetAfter());
    }

    @Test
    public void test_lineIf() throws Exception {
        StringBuilder sb = new StringBuilder();
        FormattingAppendable fa = new FormattingAppendableImpl(sb, true);

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab").lineIf(false).append("c").line().flush();
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab").lineIf(true).append("c").line().flush();
        assertEquals("ab\nc\n", sb.toString());
    }

    @Test
    public void test_BlankLine() throws Exception {
        StringBuilder sb = new StringBuilder();
        FormattingAppendable fa = new FormattingAppendableImpl(sb, true);

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab").blankLine().append("c").line().flush();
        assertEquals("ab\n\nc\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab").blankLine().blankLine().append("c").line().flush();
        assertEquals("ab\n\nc\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab\n\n").blankLine().blankLine().append("c").line().flush();
        assertEquals("ab\n\nc\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab\n    \t \n\t   \n\n").blankLine().append("c").flush();
        assertEquals("ab\n\nc\n", sb.toString());
    }

    @Test
    public void test_BlankLineIf() throws Exception {
        StringBuilder sb = new StringBuilder();
        FormattingAppendable fa = new FormattingAppendableImpl(sb, true);

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab").blankLineIf(false).append("c").line().flush();
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab").blankLineIf(true).blankLine().append("c").line().flush();
        assertEquals("ab\n\nc\n", sb.toString());
    }

    @Test
    public void test_BlankLines() throws Exception {
        StringBuilder sb = new StringBuilder();
        FormattingAppendable fa = new FormattingAppendableImpl(sb, true);

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab").blankLine(1).append("c").line().flush();
        assertEquals("ab\n\nc\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab").blankLine(1).blankLine(0).append("c").line().flush();
        assertEquals("ab\n\nc\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab\n\n").blankLine(1).blankLine(2).append("c").line().flush();
        assertEquals("ab\n\n\nc\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab\n    \t \n\t   \n\n").blankLine(1).append("c").flush();
        assertEquals("ab\n\nc\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab\n    \t \n\t   \n\n").blankLine(2).append("c").flush();
        assertEquals("ab\n\n\nc\n", sb.toString());
    }

    @Test
    public void test_noIndent() throws Exception {
        String indent = "";
        StringBuilder sb = new StringBuilder();
        FormattingAppendable fa = new FormattingAppendableImpl(sb, true);
        fa.setIndentPrefix(indent);

        fa.indent().append(" ").unIndent().flush();
        assertEquals("", sb.toString());

        fa.indent().append("     ").unIndent().flush();
        assertEquals("", sb.toString());

        fa.indent().append("     ").indent().unIndent().unIndent().flush();
        assertEquals("", sb.toString());

        fa.indent().append("a").unIndent().flush();
        assertEquals("a\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("abc").indent().flush().unIndent();
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true);
        fa.append("ab\n    \t \n\t   \n\n").indent().append("c").unIndent().flush();
        assertEquals("ab\nc\n", sb.toString());
    }

    @Test
    public void test_indent() throws Exception {
        String indent = " ";
        StringBuilder sb = new StringBuilder();
        FormattingAppendable fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);

        fa.indent().append(" ").unIndent().flush();
        assertEquals("", sb.toString());

        fa.indent().append("     ").unIndent().flush();
        assertEquals("", sb.toString());

        fa.indent().append("     ").indent().unIndent().unIndent().flush();
        assertEquals("", sb.toString());

        fa.indent().append("a").unIndent().flush();
        assertEquals(" a\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);
        fa.append("abc").indent().flush().unIndent();
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);
        fa.append("ab").indent().append("c").unIndent().flush();
        assertEquals("ab\n c\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);
        fa.append("ab\n    \t \n\t   \n\n").indent().append("c").unIndent().flush();
        assertEquals("ab\n c\n", sb.toString());

        indent = "  ";
        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);
        fa.indent().append("a").unIndent().flush();
        assertEquals("  a\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);
        fa.append("abc").indent().flush().unIndent();
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);
        fa.append("ab").indent().append("c").unIndent().flush();
        assertEquals("ab\n  c\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);
        fa.append("ab\n    \t \n\t   \n\n").indent().append("c").unIndent().flush();
        assertEquals("ab\n  c\n", sb.toString());
    }

    @Test
    public void test_openConditional() throws Exception {
        String indent = "  ";
        StringBuilder sb = new StringBuilder();
        FormattingAppendable fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);

        final FormattingAppendable fa1 = fa;
        fa.append("<li>").openConditional(new ConditionalFormatter() {
            @Override
            public void apply(final boolean firstAppend, boolean onIndent, boolean onLine, boolean onText) {
                if (onIndent) fa1.indent();
                fa1.append("item text");
                if (onIndent) fa1.line();
            }
        });
        fa.closeConditional(new ConditionalFormatter() {
            @Override
            public void apply(final boolean firstAppend, boolean onIndent, boolean onLine, boolean onText) {
                if (!onText) {
                    fa1.append("item text");
                } else if (onIndent) {
                    fa1.unIndent();
                }
                fa1.append("</li>").line();
            }
        }).flush();
        assertEquals("<li>item text</li>\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);

        final FormattingAppendable fa2 = fa;
        fa.append("<li>").openConditional(new ConditionalFormatter() {
            @Override
            public void apply(final boolean firstAppend, boolean onIndent, boolean onLine, boolean onText) {
                if (onIndent) fa2.indent();
                fa2.append("item text");
                if (onIndent) fa2.line();
            }
        });
        Ref<Boolean> paraLine = new Ref<>(false);
        fa.line(paraLine).append("<p>para</p>").lineIf(paraLine);
        fa.closeConditional(new ConditionalFormatter() {
            @Override
            public void apply(final boolean firstAppend, boolean onIndent, boolean onLine, boolean onText) {
                if (!onText) {
                    fa2.append("item text");
                } else if (onIndent) {
                    fa2.unIndent();
                }
                fa2.append("</li>").line();
            }
        }).flush();
        assertEquals("<li>item text<p>para</p></li>\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);

        final FormattingAppendable fa3 = fa;
        fa.append("<li>").openConditional(new ConditionalFormatter() {
            @Override
            public void apply(final boolean firstAppend, boolean onIndent, boolean onLine, boolean onText) {
                fa3.indent().append("item text").line();
            }
        });
        fa.line().append("<p>para</p>").line();
        fa.closeConditional(new ConditionalFormatter() {
            @Override
            public void apply(final boolean firstAppend, boolean onIndent, boolean onLine, boolean onText) {
                if (onText) {
                    fa3.unIndent();
                } else {
                    fa3.append("item text");
                }
                fa3.append("</li>").line();
            }
        }).flush();
        assertEquals("<li>\n  item text\n  <p>para</p>\n</li>\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);

        final FormattingAppendable fa4 = fa;
        fa.append("<li>").openConditional(new ConditionalFormatter() {
            @Override
            public void apply(final boolean firstAppend, boolean onIndent, boolean onLine, boolean onText) {
                if (onIndent) fa4.indent();
                fa4.append("item text");
                if (onIndent) fa4.blankLine().unIndent();
            }
        });
        fa.indent().append("<p>para</p>").unIndent();
        fa.closeConditional(new ConditionalFormatter() {
            @Override
            public void apply(final boolean firstAppend, boolean onIndent, boolean onLine, boolean onText) {
                if (!onText) {
                    fa4.append("item text");
                }
                fa4.append("</li>").line();
            }
        }).flush();
        assertEquals("<li>\n  item text\n\n  <p>para</p>\n</li>\n", sb.toString());
    }

    @Test
    public void test_openPreFormatted() throws Exception {
        String indent = "  ";
        StringBuilder sb = new StringBuilder();
        FormattingAppendable fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);

        fa.indent().append("<pre>").openPreFormatted(true).append("abc\ndef \n\n").append("hij\n")
                .append("</pre>").closePreFormatted().unIndent().flush();
        assertEquals("  <pre>abc\ndef \n\nhij\n</pre>\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").openPreFormatted(true).append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().append("</div>").unIndent().flush();
        assertEquals("  <p>this is a paragraph <div style=''>    some text\n    some more text\n  </div>\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").line().openPreFormatted(true).append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().append("</div>").unIndent().flush();
        assertEquals("  <p>this is a paragraph\n  <div style=''>    some text\n    some more text\n  </div>\n", sb.toString());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").indent().openPreFormatted(true).append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().unIndent().append("</div>").unIndent().flush();
        assertEquals("  <p>this is a paragraph\n    <div style=''>    some text\n    some more text\n  </div>\n", sb.toString());
    }

    @Test
    public void test_lineCount() throws Exception {
        String indent = "  ";
        StringBuilder sb = new StringBuilder();
        FormattingAppendable fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);

        fa.indent().append("<pre>").openPreFormatted(true).append("abc\ndef \n\n").append("hij\n")
                .append("</pre>").closePreFormatted().unIndent().flush();
        assertEquals("  <pre>abc\ndef \n\nhij\n</pre>\n", sb.toString());
        assertEquals(5, fa.getLineCount());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);
        fa.append("ab").indent().append("c").unIndent().flush();
        //assertEquals("ab\n c\n", sb.toString());
        assertEquals(2, fa.getLineCount());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);
        fa.append("ab\n    \t \n\t   \n\n").indent().append("c").unIndent().flush();
        assertEquals("ab\n  c\n", sb.toString());
        assertEquals(2, fa.getLineCount());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").openPreFormatted(true).append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().append("</div>").unIndent().flush();
        //assertEquals("  <p>this is a paragraph <div style=''>    some text\n    some more text\n  </div>\n", sb.toString());
        assertEquals(3, fa.getLineCount());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").line().openPreFormatted(true).append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().append("</div>").unIndent().flush();
        //assertEquals("  <p>this is a paragraph\n  <div style=''>    some text\n    some more text\n  </div>\n", sb.toString());
        assertEquals(4, fa.getLineCount());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").indent().openPreFormatted(true).append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().unIndent().append("</div>").unIndent().flush();
        assertEquals("  <p>this is a paragraph\n    <div style=''>    some text\n    some more text\n  </div>\n", sb.toString());
        assertEquals(4, fa.getLineCount());
    }

    @Test
    public void test_preOffset() throws Exception {
        String indent = "  ";
        StringBuilder sb = new StringBuilder();
        FormattingAppendable fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);

        fa.indent().append("<pre>");
        assertEquals(2, fa.getOffsetBefore());
        assertEquals(7, fa.getOffsetAfter());
        fa.openPreFormatted(true);
        assertEquals(7, fa.getOffsetBefore());
        assertEquals(7, fa.getOffsetAfter());
        fa.append("abc\ndef \n\n");
        assertEquals(7, fa.getOffsetBefore());
        assertEquals(17, fa.getOffsetAfter());
        fa.append("hij\n");
        assertEquals(17, fa.getOffsetBefore());
        assertEquals(21, fa.getOffsetAfter());
        fa.append("</pre>");
        assertEquals(21, fa.getOffsetBefore());
        assertEquals(27, fa.getOffsetAfter());
        fa.closePreFormatted();
        assertEquals(21, fa.getOffsetBefore());
        assertEquals(27, fa.getOffsetAfter());
        fa.unIndent();
        assertEquals(21, fa.getOffsetBefore());
        assertEquals(27, fa.getOffsetAfter());
        fa.flush();
        assertEquals("  <pre>abc\ndef \n\nhij\n</pre>\n", sb.toString());
        assertEquals(27, fa.getOffsetBefore());
        assertEquals(28, fa.getOffsetAfter());

        sb = new StringBuilder();
        fa = new FormattingAppendableImpl(sb, true).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ");
        assertEquals(2, fa.getOffsetBefore());
        assertEquals(24, fa.getOffsetAfter());
        fa.openPreFormatted(true);
        assertEquals(24, fa.getOffsetBefore());
        assertEquals(25, fa.getOffsetAfter());
        fa.append("<div style=''>    some text\n    some more text\n");
        assertEquals(25, fa.getOffsetBefore());
        assertEquals(72, fa.getOffsetAfter());
        fa.closePreFormatted();
        assertEquals(25, fa.getOffsetBefore());
        assertEquals(72, fa.getOffsetAfter());
        fa.append("</div>");
        assertEquals(74, fa.getOffsetBefore());
        assertEquals(80, fa.getOffsetAfter());
        fa.unIndent();
        assertEquals(74, fa.getOffsetBefore());
        assertEquals(80, fa.getOffsetAfter());
        fa.flush();
        assertEquals("  <p>this is a paragraph <div style=''>    some text\n    some more text\n  </div>\n", sb.toString());
        assertEquals(80, fa.getOffsetBefore());
        assertEquals(81, fa.getOffsetAfter());
    }
}
