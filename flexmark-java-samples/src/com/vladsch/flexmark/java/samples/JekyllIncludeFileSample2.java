package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JekyllIncludeFileSample2 {
    static String commonMark(String markdown, Map<String, String> included) {
        MutableDataHolder options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, Arrays.asList(AutolinkExtension.create(), JekyllTagExtension.create()));

        // change soft break to hard break
        options.set(HtmlRenderer.SOFT_BREAK, "<br/>");

        // parser and renderer used to convert markdown included content to HTML
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Map<String, String> includeHtmlMap = new HashMap<>();
        ArrayList<Document> includedDocs = new ArrayList<>();

        for (Map.Entry<String, String> entry : included.entrySet()) {
            String includeFile = entry.getKey();
            String text = entry.getValue();

            if (includeFile.endsWith(".md")) {
                Document includeDoc = parser.parse(text);
                String includeHtml = renderer.render(includeDoc);

                includeHtmlMap.put(includeFile, includeHtml);

                // copy any definition of reference elements from included file to our document
                includedDocs.add(includeDoc);
            } else {
                includeHtmlMap.put(includeFile, text);
            }
        }

        if (!includeHtmlMap.isEmpty()) {
            options.set(JekyllTagExtension.INCLUDED_HTML, includeHtmlMap);
            options.set(JekyllTagExtension.EMBED_INCLUDED_CONTENT, true);

            // use new options for main document parsing/rendering
            parser = Parser.builder(options).build();
            renderer = HtmlRenderer.builder(options).build();
        }

        Document document = parser.parse(markdown);

        // transfer any references defined in the included documents to the main document
        for (Document includedDoc : includedDocs) {
            parser.transferReferences(document, includedDoc, null);
        }

        return renderer.render(document);
    }

    public static void main(String[] args) {
        Map<String, String> included = new HashMap<>();
        included.put("test.md", "## Included Heading\n" +
                "\n" +
                "Included paragraph\n" +
                "\n" +
                "[ref]: http://example.com\n" +
                "");

        included.put("test.html", "<p>test.html: some text</p>\n" +
                "");

        String html = commonMark("http://github.com/vsch/flexmark-java\n" +
                "\n" +
                "[ref]\n" +
                "\n" +
                "{% include test.md %}\n" +
                "\n" +
                "", included);
        System.out.println(html);

        html = commonMark("http://github.com/vsch/flexmark-java\n" +
                "\n" +
                "[ref]\n" +
                "\n" +
                "{% include test.html %}\n" +
                "\n" +
                "", included);
        System.out.println(html);
    }
}
