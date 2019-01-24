package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.pdf.converter.PdfConverterExtension;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class PdfConverter {
    static final MutableDataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(
            Extensions.ALL & ~(Extensions.ANCHORLINKS | Extensions.EXTANCHORLINKS_WRAP)
    ).toMutable();

    static String getResourceFileContent(String resourcePath) {
        StringWriter writer = new StringWriter();
        getResourceFileContent(writer, resourcePath);
        return writer.toString();
    }

    private static void getResourceFileContent(final StringWriter writer, final String resourcePath) {
        InputStream inputStream = PdfConverter.class.getResourceAsStream(resourcePath);
        try {
            IOUtils.copy(inputStream, writer, "UTF-8");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final String pegdown = "#Heading\n" +
                "-----\n" +
                "paragraph text \n" +
                "lazy continuation\n" +
                "\n" +
                "* list item\n" +
                "    > block quote\n" +
                "    lazy continuation\n" +
                "\n" +
                "~~~info\n" +
                "   with uneven indent\n" +
                "      with uneven indent\n" +
                "indented code\n" +
                "~~~ \n" +
                "\n" +
                "       with uneven indent\n" +
                "          with uneven indent\n" +
                "    indented code\n" +
                "1. numbered item 1   \n" +
                "1. numbered item 2   \n" +
                "1. numbered item 3   \n" +
                "    - bullet item 1   \n" +
                "    - bullet item 2   \n" +
                "    - bullet item 3   \n" +
                "        1. numbered sub-item 1   \n" +
                "        1. numbered sub-item 2   \n" +
                "        1. numbered sub-item 3   \n" +
                "    \n" +
                "    ~~~info\n" +
                "       with uneven indent\n" +
                "          with uneven indent\n" +
                "    indented code\n" +
                "    ~~~ \n" +
                "    \n" +
                "           with uneven indent\n" +
                "              with uneven indent\n" +
                "        indented code\n" +
                "";
        System.out.println("pegdown\n");
        System.out.println(pegdown);

        final Parser PARSER = Parser.builder(OPTIONS).build();
        final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

        Node document = PARSER.parse(pegdown);
        String html = RENDERER.render(document);
        
        // add embedded fonts for non-latin character set rendering
        // change file:///usr/local/fonts/ to your system's path for font installation Unix path 
        // On windows the path should start with `file:/X:/...` where `X:/...` is the drive 
        // letter followed by the full installation path.
        String nonLatinFonts = "" +
                "<style>\n" +
                "@font-face {\n" +
                "  font-family: 'noto-cjk';\n" +
                "  src: url('file:///usr/local/fonts/arialuni.ttf');\n" +
                "  font-weight: normal;\n" +
                "  font-style: normal;\n" +
                "}\n" +
                "\n" +
                "@font-face {\n" +
                "  font-family: 'noto-serif';\n" +
                "  src: url('file:///usr/local/fonts/NotoSerif-Regular.ttf');\n" +
                "  font-weight: normal;\n" +
                "  font-style: normal;\n" +
                "}\n" +
                "\n" +
                "@font-face {\n" +
                "  font-family: 'noto-sans';\n" +
                "  src: url('file:///usr/local/fonts/NotoSans-Regular.ttf');\n" +
                "  font-weight: normal;\n" +
                "  font-style: normal;\n" +
                "}\n" +
                "\n" +
                "@font-face {\n" +
                "  font-family: 'noto-mono';\n" +
                "  src: url('file:///usr/local/fonts/NotoMono-Regular.ttf');\n" +
                "  font-weight: normal;\n" +
                "  font-style: normal;\n" +
                "}\n" +
                "\n" +
                "body {\n" +
                "    font-family: 'noto-sans', 'noto-cjk', sans-serif;\n" +
                "    overflow: hidden;\n" +
                "    word-wrap: break-word;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                "var,\n" +
                "code,\n" +
                "kbd,\n" +
                "pre {\n" +
                "    font: 0.9em 'noto-mono', Consolas, \"Liberation Mono\", Menlo, Courier, monospace;\n" +
                "}\n" +
                "</style>\n" +
                "";

        html = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\">\n" +
                "" +  // add your stylesheets, scripts styles etc.
                // uncomment line below for adding style for custom embedded fonts
                // nonLatinFonts +
                "</head><body>" + html + "\n" +
                "</body></html>";

        PdfConverterExtension.exportToPdf("/Users/vlad/src/pdf/flexmark-java.pdf", html,"", OPTIONS);
    }
}
