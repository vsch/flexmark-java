package com.vladsch.flexmark.util.html;

import org.junit.Test;

import static org.junit.Assert.*;

public class HtmlFormattingAppendableBaseTest {

    @Test
    public void test_basic() throws Exception {
        StringBuilder sb = new StringBuilder();
        final HtmlFormattingAppendableBase fa = new HtmlFormattingAppendableBase(sb, 2, true);

        fa.tagIndent("ul", new Runnable() {
            @Override
            public void run() {
                fa.withCondIndent().tagLine("li", new Runnable() {
                    @Override
                    public void run() {
                        fa.text("item1");
                    }
                });
            }
        });

        fa.flush();
        assertEquals("<ul>\n  <li>item1</li>\n</ul>\n", sb.toString());

        sb = new StringBuilder();
        final HtmlFormattingAppendableBase fa1 = new HtmlFormattingAppendableBase(sb, 2, true);

        fa1.tagIndent("ul", new Runnable() {
            @Override
            public void run() {
                fa1.withCondIndent().tagLine("li", new Runnable() {
                    @Override
                    public void run() {
                        fa1.text("item1");
                        fa1.tagIndent("ul", new Runnable() {
                            @Override
                            public void run() {
                                fa1.withCondIndent().tagLine("li", new Runnable() {
                                    @Override
                                    public void run() {
                                        fa1.text("item1");
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        fa1.flush();
        assertEquals("<ul>\n  <li>item1\n    <ul>\n      <li>item1</li>\n    </ul>\n  </li>\n</ul>\n", sb.toString());

        sb = new StringBuilder();
        final HtmlFormattingAppendableBase fa2 = new HtmlFormattingAppendableBase(sb, 2, true);

        fa2.withCondLine().tagIndent("tbody", new Runnable() {
            @Override
            public void run() {

            }
        });

        fa2.flush();
        assertEquals("<tbody></tbody>\n", sb.toString());
    }
}
