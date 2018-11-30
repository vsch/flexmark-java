package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.Utils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HtmlFormattingAppendableBaseTest {

    @Test
    public void test_basic() throws Exception {
        StringBuilder sb = new StringBuilder();
        final HtmlFormattingAppendableBase fa = new HtmlFormattingAppendableBase(sb, 2, FormattingAppendable.FORMAT_ALL);

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
        final HtmlFormattingAppendableBase fa1 = new HtmlFormattingAppendableBase(sb, 2, FormattingAppendable.FORMAT_ALL);

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
        final HtmlFormattingAppendableBase fa2 = new HtmlFormattingAppendableBase(sb, 2, FormattingAppendable.FORMAT_ALL);

        fa2.withCondLine().tagIndent("tbody", new Runnable() {
            @Override
            public void run() {

            }
        });

        fa2.flush();
        assertEquals("<tbody></tbody>\n", sb.toString());
    }

    // test tag tracking

    @Test
    public void test_tagList() throws Exception {
        final StringBuilder sb = new StringBuilder();
        final HtmlFormattingAppendableBase fa = new HtmlFormattingAppendableBase(sb, 2, FormattingAppendable.FORMAT_ALL);

        fa.tag("span", false);
        fa.tagIndent("ul", new Runnable() {
            @Override
            public void run() {
                fa.withCondIndent().tagLine("li", new Runnable() {
                    @Override
                    public void run() {
                        final List<String> tagsAfterLast = fa.getOpenTagsAfterLast("span");
                        String tags = Utils.splice(tagsAfterLast.toArray(new String[tagsAfterLast.size()]), ", ");
                        assertEquals("ul, li", tags);
                        fa.text("item1");
                    }
                });
            }
        });
        fa.closeTag("span");

        fa.flush();
        assertEquals("<span>\n<ul>\n  <li>item1</li>\n</ul>\n</span>", sb.toString());

        final StringBuilder sb1 = new StringBuilder();
        final HtmlFormattingAppendableBase fa1 = new HtmlFormattingAppendableBase(sb1, 2, FormattingAppendable.FORMAT_ALL);

        fa1.tagIndent("ul", new Runnable() {
            @Override
            public void run() {
                fa1.withCondIndent().tagLine("li", new Runnable() {
                    @Override
                    public void run() {
                        final List<String> tagsAfterLast = fa.getOpenTagsAfterLast("span");
                        String tags = Utils.splice(tagsAfterLast.toArray(new String[tagsAfterLast.size()]), ", ");
                        assertEquals("", tags);
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
        assertEquals("<ul>\n  <li>item1\n    <ul>\n      <li>item1</li>\n    </ul>\n  </li>\n</ul>\n", sb1.toString());
    }
}
