package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.docx.converter.DocxRenderer;
import com.vladsch.flexmark.ext.aside.AsideExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.ext.superscript.SuperscriptExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.apache.commons.io.IOUtils;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Creates a docx document from DocxRenderer provided empty.md and empty.xml
 */
public class DocxConverterEmpty {
    // don't need to use pegdown options adapter. You can setup the options as you like. I find this is a quick way to add all the fixings
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Arrays.asList(
                    AsideExtension.create(),
                    DefinitionExtension.create(),
                    EmojiExtension.create(),
                    FootnoteExtension.create(),
                    StrikethroughSubscriptExtension.create(),
                    InsExtension.create(),
                    SuperscriptExtension.create(),
                    TablesExtension.create(),
                    TocExtension.create(),
                    SimTocExtension.create(),
                    WikiLinkExtension.create()
            ))
            .set(DocxRenderer.SUPPRESS_HTML, true)
            // the following two are needed to allow doc relative and site relative address resolution
            //.set(DocxRenderer.DOC_RELATIVE_URL, "file:///Users/vlad/src/pdf") // this will be used for URLs like 'images/...' or './' or '../'
            //.set(DocxRenderer.DOC_ROOT_URL, "file:///Users/vlad/src/pdf") // this will be used for URLs like: '/...'
            ;

    static String getResourceFileContent(String resourcePath) {
        StringWriter writer = new StringWriter();
        getResourceFileContent(writer, resourcePath);
        return writer.toString();
    }

    private static void getResourceFileContent(StringWriter writer, String resourcePath) {
        InputStream inputStream = DocxConverterEmpty.class.getResourceAsStream(resourcePath);
        try {
            IOUtils.copy(inputStream, writer, "UTF-8");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String markdown = DocxRenderer.getResourceString("/empty.md");
        System.out.println("markdown\n");
        System.out.println(markdown);

        Parser PARSER = Parser.builder(OPTIONS).build();
        DocxRenderer RENDERER = DocxRenderer.builder(OPTIONS).build();

        Node document = PARSER.parse(markdown);

        // to get XML
        String xml = RENDERER.render(document);

        // or to control the package
        WordprocessingMLPackage template = DocxRenderer.getDefaultTemplate();
        RENDERER.render(document, template);

        File file = new File("flexmark-empty-template.docx");
        try {
            template.save(file, Docx4J.FLAG_SAVE_ZIP_FILE);
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
    }
}
