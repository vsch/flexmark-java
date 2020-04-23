package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTag;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.util.*;

/**
 * This sample uses the parsed document to compute the embedded content. 
 * 
 * This means that the main document is parsed twice. 
 * 
 * For a sample which can compute the embedded content without needing to examine
 * the main document AST and therefore, computes the content map before parsing 
 * the main document, see: {@link JekyllIncludeFileSample2}
 */
public class JekyllIncludeFileSample {
    static String commonMark(String markdown, Map<String, String> included) {
        MutableDataHolder options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, Arrays.asList(AutolinkExtension.create(), JekyllTagExtension.create()));

        // change soft break to hard break
        options.set(HtmlRenderer.SOFT_BREAK, "<br/>");

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Document document = parser.parse(markdown);

        // see if document has includes
        Document doc = document;
        if (doc.contains(JekyllTagExtension.TAG_LIST)) {
            List<JekyllTag> tagList = JekyllTagExtension.TAG_LIST.get(doc);
            Map<String, String> includeHtmlMap = new HashMap<>();
            ArrayList<Document> includedDocs = new ArrayList<>();

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

                            // copy any definition of reference elements from included file to our document
                            includedDocs.add(includeDoc);
                        } else {
                            includeHtmlMap.put(includeFile, text);
                        }
                    }
                }

                // Need to reparse document because embedding is done by a NodePostProcessor at the end of the parsing processing
                // 
                // NOTE: an alternative is to append to JekyllTagBlock nodes which will render the embedded content using custom nodes and custom node renderer  
                if (!includeHtmlMap.isEmpty()) {
                    options.set(JekyllTagExtension.INCLUDED_HTML, includeHtmlMap);
                    options.set(JekyllTagExtension.EMBED_INCLUDED_CONTENT, true);

                    parser = Parser.builder(options).build();
                    renderer = HtmlRenderer.builder(options).build();

                    document = parser.parse(markdown);

                    for (Document includedDoc : includedDocs) {
                        parser.transferReferences(document, includedDoc, null);
                    }
                }
            }
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
