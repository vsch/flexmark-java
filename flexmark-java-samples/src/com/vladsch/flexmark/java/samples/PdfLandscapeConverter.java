package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.pdf.converter.PdfConverterExtension;
import com.vladsch.flexmark.profile.pegdown.Extensions;
import com.vladsch.flexmark.profile.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;

public class PdfLandscapeConverter {
    static final DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(
            Extensions.ALL & ~(Extensions.ANCHORLINKS | Extensions.EXTANCHORLINKS_WRAP)
            , TocExtension.create()).toMutable()
            .set(TocExtension.LIST_CLASS, PdfConverterExtension.DEFAULT_TOC_LIST_CLASS)
            .toImmutable();

    public static void main(String[] args) {
        String markdown = "" +
                "Heading\n" +
                "=======\n" +
                "\n" +
                "*** ** * ** ***\n" +
                "\n" +
                "paragraph text lazy continuation\n" +
                "\n" +
                "* list itemblock quote lazy continuation\n" +
                "\n" +
                "\\~\\~\\~info with uneven indent with uneven indent indented code \\~\\~\\~\n" +
                "\n" +
                "       with uneven indent\n" +
                "          with uneven indent\n" +
                "    indented code\n" +
                "\n" +
                "1. numbered item 1\n" +
                "2. numbered item 2\n" +
                "3. numbered item 3\n" +
                "   * bullet item 1\n" +
                "   * bullet item 2\n" +
                "   * bullet item 3\n" +
                "     1. numbered sub-item 1\n" +
                "     2. numbered sub-item 2\n" +
                "     3. numbered sub-item 3\n" +
                "\n" +
                "   \\~\\~\\~info with uneven indent with uneven indent indented code \\~\\~\\~\n" +
                "\n" +
                "          with uneven indent\n" +
                "             with uneven indent\n" +
                "       indented code" +
                "";
        System.out.println("pegdown\n");
        System.out.println(markdown);

        Parser PARSER = Parser.builder(OPTIONS).build();
        HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

        Node document = PARSER.parse(markdown);
        String html = RENDERER.render(document);

        // add embedded fonts for non-latin character set rendering
        // change file:///usr/local/fonts/ to your system's path for font installation Unix path or
        // on windows the path should start with `file:/X:/...` where `X:/...` is the drive
        // letter followed by the full installation path.
        //
        // Google Noto fonts can be downloaded from https://www.google.com/get/noto/
        // `arialuni.ttf` from https://www.wfonts.com/font/arial-unicode-ms
        String nonLatinFonts = "" +
                "";

        html = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\">\n" +
                "" +  // add your stylesheets, scripts styles etc.
                // uncomment line below for adding style for custom embedded fonts
                // nonLatinFonts +
                "<style>\n" +
                "@page landscapeA4 {\n" +
                "    size: A4 landscape;\n" +
                "} \n" +
                ".landscape {\n" +
                "    page:landscapeA4;\n" +
                "    height:100%;\n" +
                "    width:100%;\n" +
                "}\n" +
                "</style>" +
                "</head><body class='landscape'>" + html + "\n" +
                "</body></html>";

        // add PDF protection policy
        //OPTIONS.set(PdfConverterExtension.PROTECTION_POLICY, new StandardProtectionPolicy("opassword", "upassword", new AccessPermission()));
        PdfConverterExtension.exportToPdf("/Users/vlad/src/pdf/flexmark-java-landscape.pdf", html, "", OPTIONS);
        System.out.println("Output PDF to /Users/vlad/src/pdf/flexmark-java-landscape.pdf");
    }
}
