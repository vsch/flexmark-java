package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ext.resizable.image.ResizableImageExtension;
import com.vladsch.flexmark.ext.resizable.image.internal.ResizableImageInlineParserExtension;
import com.vladsch.flexmark.ext.resizable.image.internal.ResizableImageNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.util.Arrays;
import java.util.Collections;

public class ResizableImageSample {
    public static void main(String[] args) {
        MutableDataSet options = new MutableDataSet();

        // uncomment to set optional extensions
        options.set(Parser.EXTENSIONS, Arrays.asList(ResizableImageExtension.create()));

        // uncomment to convert soft-breaks to hard breaks
        // options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse("![Image_30x30](https://raw.githubusercontent.com/sparksparrow/Intelligent-mobile-apps/master/Relativepath/test.jpg =30x30)");
        String html = renderer.render(document);
        System.out.println(html);
    }
}
