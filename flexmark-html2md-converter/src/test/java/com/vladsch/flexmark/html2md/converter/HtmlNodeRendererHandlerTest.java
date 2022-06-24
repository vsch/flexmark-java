package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.util.data.DataHolder;

import com.vladsch.flexmark.util.data.MutableDataSet;

import org.junit.Test;

import static com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter.UNWRAPPED_TAGS;
import static org.junit.Assert.assertEquals;

public class HtmlNodeRendererHandlerTest {
    @Test
    public void overrideDefinitionListRendererHandler() {
        String markdown;
        int incorrect = 0;

        DataHolder flexmarkOptions = new MutableDataSet()
                .set(UNWRAPPED_TAGS, new String[]{"article", "address", "frameset", "section", "small", "iframe",
                        "dl", "dt", "dd",})
                .toImmutable();
        FlexmarkHtmlConverter converter = FlexmarkHtmlConverter.builder(flexmarkOptions).build();

        for (int i = 0; i < 10000; i++) {
            String html = "<dl id=\"definition-list\">\n" +
                    "<div>\n" +
                    "<dt></dt>\n" +
                    "<dd>Data 1</dd>\n" +
                    "<span>\n" +
                    "<dd>Data 2</dd>\n" +
                    "</span>\n" +
                    "</div>\n" +
                    "</dl>";

            markdown = converter.convert(html);

            if (!markdown.contains("Data 2")) {
                incorrect++;
            }
        }

        assertEquals(0, incorrect);
    }
}
