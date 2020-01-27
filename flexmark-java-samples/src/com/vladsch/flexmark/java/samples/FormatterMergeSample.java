package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.util.Arrays;

public class FormatterMergeSample {
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Arrays.asList(
                    AttributesExtension.create(),
                    FootnoteExtension.create(),
                    AbbreviationExtension.create()
            ));

    static final MutableDataSet FORMAT_OPTIONS = new MutableDataSet();
    static {
        // copy extensions from Pegdown compatible to Formatting
        FORMAT_OPTIONS
                .set(Parser.EXTENSIONS, Parser.EXTENSIONS.get(OPTIONS))
                .set(Formatter.DEFAULT_LINK_RESOLVER, true)
        ;
    }

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final Formatter RENDERER = Formatter.builder(FORMAT_OPTIONS).build();

    // use the PARSER to parse and RENDERER to parse pegdown indentation rules and render CommonMark
    public static void main(String[] args) {
        String markdown = "" +
                "![Fig](http://example.com/test.png){#fig:test}\n" +
                "\n" +
                "[Figure](#fig:test).\n" +
                "\n" +
                "# Atx Heading\n" +
                "\n" +
                "[link](#atx-heading)\n" +
                "\n" +
                "Setext Heading\n" +
                "==============\n" +
                "\n" +
                "[link](#setext-heading)\n" +
                "\n" +
                "[img](img/image.png)\n" +
                "\n" +
                "![img](img/image.png)\n" +
                "\n" +
                "";

        Document document1 = PARSER.parse(markdown);
        document1.set(Formatter.DOC_RELATIVE_URL, "doc1/");
        Document document2 = PARSER.parse(markdown);
        document2.set(Formatter.DOC_RELATIVE_URL, "doc2/");

        Document[] documents = new Document[] { document1, document2 };

        String mergedMarkdown = RENDERER.mergeRender(documents, 1);

        System.out.println("\n\nMerged:\n");
        System.out.println(mergedMarkdown);
    }
}
