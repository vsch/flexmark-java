package com.vladsch.flexmark.pdf.converter;

import com.openhtmltopdf.DOMBuilder;
import com.openhtmltopdf.bidi.support.ICUBidiReorderer;
import com.openhtmltopdf.bidi.support.ICUBidiSplitter;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
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

    public static void exportToPdf(String out, String html, String url, DataHolder options) {
        exportToPdf(out, html, url, options.get(DEFAULT_TEXT_DIRECTION));
    }

    public static void exportToPdf(String out, String html, String url, final PdfRendererBuilder.TextDirection defaultTextDirection) {
        try {
            OutputStream os = new FileOutputStream(out);
            exportToPdf(os, html, url, defaultTextDirection);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void exportToPdf(final OutputStream os, final String html, final String url, final DataHolder options) {
        exportToPdf(os, html, url, options.get(DEFAULT_TEXT_DIRECTION));
    }

    public static void exportToPdf(final OutputStream os, final String html, final String url, final PdfRendererBuilder.TextDirection defaultTextDirection) {
        try {
            // There are more options on the builder than shown below.
            PdfRendererBuilder builder = new PdfRendererBuilder();

            if (defaultTextDirection != null) {
                builder.useUnicodeBidiSplitter(new ICUBidiSplitter.ICUBidiSplitterFactory());
                builder.useUnicodeBidiReorderer(new ICUBidiReorderer());
                builder.defaultTextDirection(defaultTextDirection); // OR RTL
            }

            org.jsoup.nodes.Document doc;
            doc = Jsoup.parse(html);

            Document dom = DOMBuilder.jsoup2DOM(doc);
            builder.withW3cDocument(dom, url);
            builder.toStream(os);
            builder.run();
        } catch (Exception e) {
            e.printStackTrace();
            // LOG exception
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                // swallow
            }
        }
    }
}
