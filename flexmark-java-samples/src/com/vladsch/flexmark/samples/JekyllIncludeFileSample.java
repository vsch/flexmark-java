package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTag;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JekyllIncludeFileSample {
    static String commonMark(String markdown, Map<String, String> included) {
        MutableDataHolder options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, Arrays.asList(AutolinkExtension.create(), JekyllTagExtension.create()));

        // change soft break to hard break
        options.set(HtmlRenderer.SOFT_BREAK, "<br/>");

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(markdown);

        // see if document has includes
        if (document instanceof Document) {
            Document doc = (Document) document;
            if (doc.contains(JekyllTagExtension.TAG_LIST)) {
                List<JekyllTag> tagList = JekyllTagExtension.TAG_LIST.getFrom(doc);
                Map<String, String> includeHtmlMap = new HashMap<>();

                for (JekyllTag tag : tagList) {
                    String includeFile = tag.getParameters().toString();
                    if (tag.getTag().equals("include") && !includeFile.isEmpty() && !includeHtmlMap.containsKey(includeFile)) {
                        // see if it exists
                        if (included.containsKey(includeFile)) {
                            // have the file
                            String text = included.get(includeFile);

                            if (includeFile.endsWith(".md")) {
                                Document includeDoc = parser.parse(text);
                                String includeHtml = renderer.render(includeDoc);
                                includeHtmlMap.put(includeFile, includeHtml);

                                if (includeDoc instanceof Document) {
                                    // copy any definition of reference elements from included file to our document
                                    parser.transferReferences(doc, includeDoc, null);
                                }
                            } else {
                                includeHtmlMap.put(includeFile, text);
                            }
                        }
                    }

                    if (!includeHtmlMap.isEmpty()) {
                        doc.set(JekyllTagExtension.INCLUDED_HTML, includeHtmlMap);
                    }
                }
            }
        }

        String html = renderer.render(document);
        return html;
    }

    public static void main(String[] args) {
        Map<String, String> included = new HashMap<>();
        included.put("test.md", "## Included Heading\n" +
                "\n" +
                "Included paragraph\n" +
                "\n" +
                "[ref]: http://example.com\n" +
                "");

        included.put("test.html", "<p>some text</p>\n" +
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
