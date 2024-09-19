package com.vladsch.flexmark.core.test.util.parser;

import static org.junit.Assert.assertEquals;

import com.vladsch.flexmark.parser.internal.HtmlDeepParser;
import org.junit.Test;

public class HtmlDeepParserTest {
  private static HtmlDeepParser parseHtml(
      String html, boolean blockTagsOnly, boolean parseNonBlock, boolean openOnOneLine) {
    HtmlDeepParser deepParser = new HtmlDeepParser();
    String[] htmlLines = html.split("\n");
    for (String htmlLine : htmlLines) {
      deepParser.parseHtmlChunk(htmlLine, blockTagsOnly, parseNonBlock, openOnOneLine);
    }
    return deepParser;
  }

  @Test
  public void test_openBlock() {
    HtmlDeepParser deepParser = parseHtml("<div>", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(false, deepParser.isHtmlClosed());
    assertEquals(false, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_closedBlock() {
    HtmlDeepParser deepParser = parseHtml("<div>\n</div>", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_openPartialBlock() {
    HtmlDeepParser deepParser = parseHtml("<div", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(false, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_closedPartialBlock() {
    HtmlDeepParser deepParser = parseHtml("<div\n>\n</div>", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_openBlock1() {
    HtmlDeepParser deepParser =
        parseHtml("<div><p>asdfasdfsadf\nasdfsadfdsaf</p>", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(false, deepParser.isHtmlClosed());
    assertEquals(false, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_closedBlock1() {
    HtmlDeepParser deepParser =
        parseHtml("<div><p>asdfasdfsadf\nasdfsadfdsaf\n</p>\n</div>", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_openBlock2() {
    HtmlDeepParser deepParser = parseHtml("<div><div attr\n", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(false, deepParser.isHtmlClosed());
    assertEquals(false, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_closedBlock2() {
    HtmlDeepParser deepParser =
        parseHtml("<div attr\n>\n<p>asdfasdfsadf\nasdfsadfdsaf\n</p>\n</div>", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_openBlockOneLine() {
    HtmlDeepParser deepParser = parseHtml("<div", true, true, true);
    assertEquals(false, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_openBlock2OneLine() {
    HtmlDeepParser deepParser = parseHtml("<div attr\n", true, true, true);
    assertEquals(false, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_void() {
    HtmlDeepParser deepParser = parseHtml("<hr>", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_void1() {
    HtmlDeepParser deepParser = parseHtml("<br>", false, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_selfClosed() {
    HtmlDeepParser deepParser = parseHtml("<div />", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_selfClosed0() {
    HtmlDeepParser deepParser = parseHtml("<div/>", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_selfClosed1() {
    HtmlDeepParser deepParser = parseHtml("<img />", false, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_openComment() {
    HtmlDeepParser deepParser = parseHtml("<div><!-- comment with blank line", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(false, deepParser.isHtmlClosed());
    assertEquals(false, deepParser.isBlankLineInterruptible());
    assertEquals(true, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_closedComment() {
    HtmlDeepParser deepParser =
        parseHtml("<div><!-- comment with blank line\n\n-->", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(false, deepParser.isHtmlClosed());
    assertEquals(false, deepParser.isBlankLineInterruptible());
  }

  @Test
  public void test_closedCommentBlock() {
    HtmlDeepParser deepParser =
        parseHtml("<div><!-- comment with blank line\n\n-->\n</div>", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_openCDATA() {
    HtmlDeepParser deepParser = parseHtml("<div><![CDATA[", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(false, deepParser.isHtmlClosed());
    assertEquals(false, deepParser.isBlankLineInterruptible());
    assertEquals(true, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_closedCDATA() {
    HtmlDeepParser deepParser = parseHtml("<div><![CDATA[\n]]>", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(false, deepParser.isHtmlClosed());
    assertEquals(false, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_closedCDATABlock() {
    HtmlDeepParser deepParser = parseHtml("<div><![CDATA[\n]]>\n</div>", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_ignoreNonBlock() {
    HtmlDeepParser deepParser = parseHtml("<strong>", true, true, false);
    assertEquals(false, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_ignoreNonBlock1() {
    HtmlDeepParser deepParser = parseHtml("<strong>", true, false, false);
    assertEquals(false, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_ignoreNonBlock2() {
    HtmlDeepParser deepParser = parseHtml("<strong>", false, false, false);
    assertEquals(false, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_ignoreNonBlock3() {
    HtmlDeepParser deepParser = parseHtml("<strong><div>", true, true, false);
    assertEquals(false, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_ignoreNonBlock4() {
    HtmlDeepParser deepParser = parseHtml("<strong><div>", true, false, false);
    assertEquals(false, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_ignoreNonBlock5() {
    HtmlDeepParser deepParser = parseHtml("<strong><!--", false, false, false);
    assertEquals(false, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_ignoreNonBlock6() {
    HtmlDeepParser deepParser = parseHtml("<strong><!--", false, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(false, deepParser.isHtmlClosed());
    assertEquals(false, deepParser.isBlankLineInterruptible());
    assertEquals(true, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_openNonBlock() {
    HtmlDeepParser deepParser = parseHtml("<strong>", false, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(false, deepParser.isHtmlClosed());
    assertEquals(false, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_closedNonBlock() {
    HtmlDeepParser deepParser = parseHtml("<strong></strong>", false, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_openNonBlock2() {
    HtmlDeepParser deepParser = parseHtml("<div><strong>\n</strong>", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(false, deepParser.isHtmlClosed());
    assertEquals(false, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_closedNonBlock2() {
    HtmlDeepParser deepParser = parseHtml("<div><strong>\n</strong>\n</div>", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_closedNonBlock3() {
    HtmlDeepParser deepParser = parseHtml("<div><strong>\n</div>", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_openPartial() {
    HtmlDeepParser deepParser = parseHtml("<p class=\"test\"\n", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(false, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_optialOpen() {
    HtmlDeepParser deepParser = parseHtml("<p>par 1\n<p>par 2\n<p>par 3", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(false, deepParser.isHtmlClosed());
    assertEquals(false, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }

  @Test
  public void test_optialClosed() {
    HtmlDeepParser deepParser = parseHtml("<p>par 1\n<p>par 2\n<p>par 3</p>\n", true, true, false);
    assertEquals(true, deepParser.hadHtml());
    assertEquals(true, deepParser.isHtmlClosed());
    assertEquals(true, deepParser.isBlankLineInterruptible());
    assertEquals(false, deepParser.haveOpenRawTag());
  }
}
