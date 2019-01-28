package com.vladsch.flexmark.pdf.converter;

import com.openhtmltopdf.DOMBuilder;
import com.openhtmltopdf.bidi.support.ICUBidiReorderer;
import com.openhtmltopdf.bidi.support.ICUBidiSplitter;
import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfBoxRenderer;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.ProtectionPolicy;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Extension for converting Markdown to PDF
 * <p>
 * After document is rendered pass the HTML result to
 * {@link #exportToPdf(OutputStream, String, String, DataHolder)}
 * or {@link #exportToPdf(String, String, String, DataHolder)}
 * or {@link #exportToPdf(String, String, String, PdfRendererBuilder.TextDirection)}
 * or {@link #exportToPdf(OutputStream, String, String, PdfRendererBuilder.TextDirection)}
 * <p>
 * The parsed Markdown text is rendered to HTML then converted to PDF
 * </p>
 */
public class PdfConverterExtension {
    public static final DataKey<PdfRendererBuilder.TextDirection> DEFAULT_TEXT_DIRECTION = new DataKey<PdfRendererBuilder.TextDirection>("DEFAULT_TEXT_DIRECTION", (PdfRendererBuilder.TextDirection) null);

    public static void exportToPdf(String out, String html, String url, DataHolder options, ProtectionPolicy protectionPolicy) {
        exportToPdf(out, html, url, options.get(DEFAULT_TEXT_DIRECTION), protectionPolicy);
    }

    public static void exportToPdf(String out, String html, String url, final PdfRendererBuilder.TextDirection defaultTextDirection, ProtectionPolicy protectionPolicy) {
        try {
            OutputStream os = new FileOutputStream(out);
            exportToPdf(os, html, url, defaultTextDirection, protectionPolicy);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void exportToPdf(final OutputStream os, final String html, final String url, final DataHolder options,ProtectionPolicy protectionPolicy) {
        exportToPdf(os, html, url, options.get(DEFAULT_TEXT_DIRECTION), protectionPolicy);
    }

    public static void exportToPdf(final OutputStream os, final String html, final String url, final PdfRendererBuilder.TextDirection defaultTextDirection, final ProtectionPolicy protectionPolicy) {
        PdfBoxRenderer renderer = null;
        try {
            // There are more options on the builder than shown below.
            PdfRendererBuilder builder = new PdfRendererBuilder();

            handleTextDirection(defaultTextDirection, builder);
            handleW3cDocument(html, url, builder);

            builder.toStream(os);
            //builder.run();
            renderer = builder.buildPdfRenderer();
            PDDocument document = renderer.getPdfDocument();
            if (protectionPolicy != null)
                //ProtectionPolicy password = new StandardProtectionPolicy("ownerPassword", "userpassword", new AccessPermission());
                document.protect(protectionPolicy);

            renderer.layout();
            renderer.createPDF();
        } catch (Exception e) {
            e.printStackTrace();
            // LOG exception
        } finally {
            try {
                if (renderer != null) {
                    renderer.close();
                }
                os.close();
            } catch (IOException e) {
                // swallow
            }
        }
    }

    private static void handleW3cDocument(final String html, final String url, final PdfRendererBuilder builder) {
        org.jsoup.nodes.Document doc;
        doc = Jsoup.parse(html);

        Document dom = DOMBuilder.jsoup2DOM(doc);
        builder.withW3cDocument(dom, url);
    }

    private static void handleTextDirection(final BaseRendererBuilder.TextDirection defaultTextDirection, final PdfRendererBuilder builder) {
        if (defaultTextDirection != null) {
            builder.useUnicodeBidiSplitter(new ICUBidiSplitter.ICUBidiSplitterFactory());
            builder.useUnicodeBidiReorderer(new ICUBidiReorderer());
            builder.defaultTextDirection(defaultTextDirection); // OR RTL
        }
    }
}
