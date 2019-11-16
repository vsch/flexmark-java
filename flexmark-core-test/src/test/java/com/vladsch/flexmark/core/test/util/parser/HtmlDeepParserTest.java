package com.vladsch.flexmark.core.test.util.parser;

import com.vladsch.flexmark.parser.internal.HtmlDeepParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HtmlDeepParserTest {

    private HtmlDeepParser parseHtml(String html, boolean blockTagsOnly, boolean parseNonBlock, boolean openOnOneLine) {
        HtmlDeepParser deepParser = new HtmlDeepParser();
        String[] htmlLines = html.split("\n");
        for (String htmlLine : htmlLines) {
            deepParser.parseHtmlChunk(htmlLine, blockTagsOnly, parseNonBlock, openOnOneLine);
        }
        return deepParser;
    }

    @Test
    public void test_openBlock() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div>", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(false, deepParser.isHtmlClosed());
        assertEquals(false, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_closedBlock() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div>\n</div>", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_openPartialBlock() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(false, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_closedPartialBlock() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div\n>\n</div>", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_openBlock1() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div><p>asdfasdfsadf\nasdfsadfdsaf</p>", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(false, deepParser.isHtmlClosed());
        assertEquals(false, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_closedBlock1() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div><p>asdfasdfsadf\nasdfsadfdsaf\n</p>\n</div>", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_openBlock2() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div><div attr\n", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(false, deepParser.isHtmlClosed());
        assertEquals(false, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_closedBlock2() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div attr\n>\n<p>asdfasdfsadf\nasdfsadfdsaf\n</p>\n</div>", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_openBlockOneLine() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div", true, true, true);
        assertEquals(false, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_openBlock2OneLine() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div attr\n", true, true, true);
        assertEquals(false, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_void() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<hr>", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_void1() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<br>", false, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_selfClosed() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div />", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_selfClosed0() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div/>", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_selfClosed1() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<img />", false, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_openComment() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div><!-- comment with blank line", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(false, deepParser.isHtmlClosed());
        assertEquals(false, deepParser.isBlankLineInterruptible());
        assertEquals(true, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_closedComment() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div><!-- comment with blank line\n\n-->", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(false, deepParser.isHtmlClosed());
        assertEquals(false, deepParser.isBlankLineInterruptible());
    }

    @Test
    public void test_closedCommentBlock() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div><!-- comment with blank line\n\n-->\n</div>", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_openCDATA() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div><![CDATA[", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(false, deepParser.isHtmlClosed());
        assertEquals(false, deepParser.isBlankLineInterruptible());
        assertEquals(true, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_closedCDATA() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div><![CDATA[\n]]>", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(false, deepParser.isHtmlClosed());
        assertEquals(false, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_closedCDATABlock() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div><![CDATA[\n]]>\n</div>", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_ignoreNonBlock() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<strong>", true, true, false);
        assertEquals(false, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_ignoreNonBlock1() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<strong>", true, false, false);
        assertEquals(false, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_ignoreNonBlock2() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<strong>", false, false, false);
        assertEquals(false, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_ignoreNonBlock3() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<strong><div>", true, true, false);
        assertEquals(false, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_ignoreNonBlock4() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<strong><div>", true, false, false);
        assertEquals(false, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_ignoreNonBlock5() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<strong><!--", false, false, false);
        assertEquals(false, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_ignoreNonBlock6() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<strong><!--", false, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(false, deepParser.isHtmlClosed());
        assertEquals(false, deepParser.isBlankLineInterruptible());
        assertEquals(true, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_openNonBlock() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<strong>", false, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(false, deepParser.isHtmlClosed());
        assertEquals(false, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_closedNonBlock() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<strong></strong>", false, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_openNonBlock2() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div><strong>\n</strong>", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(false, deepParser.isHtmlClosed());
        assertEquals(false, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_closedNonBlock2() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div><strong>\n</strong>\n</div>", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_closedNonBlock3() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<div><strong>\n</div>", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_openPartial() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<p class=\"test\"\n", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(false, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_optialOpen() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<p>par 1\n<p>par 2\n<p>par 3", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(false, deepParser.isHtmlClosed());
        assertEquals(false, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }

    @Test
    public void test_optialClosed() throws Exception {
        HtmlDeepParser deepParser = parseHtml("<p>par 1\n<p>par 2\n<p>par 3</p>\n", true, true, false);
        assertEquals(true, deepParser.hadHtml());
        assertEquals(true, deepParser.isHtmlClosed());
        assertEquals(true, deepParser.isBlankLineInterruptible());
        assertEquals(false, deepParser.haveOpenRawTag());
    }
}
