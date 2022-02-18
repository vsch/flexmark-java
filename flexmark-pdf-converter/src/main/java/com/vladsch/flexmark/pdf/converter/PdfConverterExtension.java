package com.vladsch.flexmark.pdf.converter;

import com.openhtmltopdf.bidi.support.ICUBidiReorderer;
import com.openhtmltopdf.bidi.support.ICUBidiSplitter;
import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfBoxRenderer;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.NullableDataKey;
import com.vladsch.flexmark.util.misc.Utils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.ProtectionPolicy;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.w3c.dom.Document;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Extension for converting Markdown to PDF
 * <p>
 * After document is rendered pass the HTML result to
 * {@link PdfConverterExtension#exportToPdf(OutputStream, String, String, DataHolder)}
 * or {@link PdfConverterExtension#exportToPdf(String, String, String, DataHolder)}
 * or {@link PdfConverterExtension#exportToPdf(String, String, String, PdfRendererBuilder.TextDirection)}
 * or {@link PdfConverterExtension#exportToPdf(String, String, String, PdfRendererBuilder.TextDirection, ProtectionPolicy protectionPolicy)}
 * or {@link PdfConverterExtension#exportToPdf(OutputStream, String, String, PdfRendererBuilder.TextDirection)}
 * or {@link PdfConverterExtension#exportToPdf(OutputStream, String, String, PdfRendererBuilder.TextDirection, ProtectionPolicy protectionPolicy)}
 * <p>
 * The parsed Markdown text is rendered to HTML then converted to PDF
 * </p>
 */
public class PdfConverterExtension {
    final public static NullableDataKey<PdfRendererBuilder.TextDirection> DEFAULT_TEXT_DIRECTION = new NullableDataKey<>("DEFAULT_TEXT_DIRECTION");
    final public static NullableDataKey<ProtectionPolicy> PROTECTION_POLICY = new NullableDataKey<>("PROTECTION_POLICY");
    final public static String DEFAULT_CSS_RESOURCE_PATH = "/default.css";
    final public static String DEFAULT_TOC_LIST_CLASS = "toc";

    final public static DataKey<String> DEFAULT_CSS = new DataKey<>("DEFAULT_CSS", () -> Utils.getResourceAsString(PdfConverterExtension.class, DEFAULT_CSS_RESOURCE_PATH));

    public static String embedCss(String html, String css) {
        if (css != null && !css.isEmpty()) {
            int pos = html.indexOf("</head>");
            String prefix = "<style>\n";
            String suffix = "\n</style>";
            String tail = "";
            if (pos == -1) {
                pos = html.indexOf("<html>");
                if (pos != -1) {
                    pos += 6;
                    prefix = "<head>" + prefix;
                    suffix = suffix + "</head>";
                } else {
                    pos = html.indexOf("<body>");
                    if (pos != -1) {
                        prefix = "<html><head>" + prefix;
                        suffix = suffix + "</head>";
                        tail = "</html>\n";
                    } else {
                        pos = 0;
                        prefix = "<html><head>" + prefix;
                        suffix = suffix + "</head><body>\n";
                        tail = "</body></html>\n";
                    }
                }
            }

            return html.subSequence(0, pos) + prefix + css + suffix + html.subSequence(pos, html.length()) + tail;
        }
        return html;
    }

    public static void exportToPdf(String out, String html, String url, DataHolder options) {
        String css = DEFAULT_CSS.get(options);
        html = embedCss(html, css);
        exportToPdf(out, html, url, DEFAULT_TEXT_DIRECTION.get(options), PROTECTION_POLICY.get(options));
    }

    public static void exportToPdf(String out, String html, String url, PdfRendererBuilder.TextDirection defaultTextDirection) {
        exportToPdf(out, html, url, defaultTextDirection, null);
    }

    public static void exportToPdf(String out, String html, String url, PdfRendererBuilder.TextDirection defaultTextDirection, ProtectionPolicy protectionPolicy) {
        try {
            OutputStream os = new FileOutputStream(out);
            exportToPdf(os, html, url, defaultTextDirection, protectionPolicy);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void exportToPdf(OutputStream os, String html, String url, DataHolder options) {
        exportToPdf(os, html, url, DEFAULT_TEXT_DIRECTION.get(options), PROTECTION_POLICY.get(options));
    }

    public static void exportToPdf(OutputStream os, String html, String url, PdfRendererBuilder.TextDirection defaultTextDirection) {
        exportToPdf(os, html, url, defaultTextDirection, null);
    }

    public static void exportToPdf(OutputStream os, String html, String url, PdfRendererBuilder.TextDirection defaultTextDirection, ProtectionPolicy protectionPolicy) {
        PdfBoxRenderer renderer = null;
        try {
            // There are more options on the builder than shown below.
            PdfRendererBuilder builder = new PdfRendererBuilder();

            handleTextDirection(defaultTextDirection, builder);
            handleW3cDocument(html, url, builder);

            builder.toStream(os);
            renderer = builder.buildPdfRenderer();
            PDDocument document = renderer.getPdfDocument();

            if (protectionPolicy != null)
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

    private static void handleW3cDocument(String html, String url, PdfRendererBuilder builder) {
        org.jsoup.nodes.Document doc;
        doc = Jsoup.parse(html);

        Document dom = new W3CDom().fromJsoup(doc);
        builder.withW3cDocument(dom, url);
    }

    private static void handleTextDirection(BaseRendererBuilder.TextDirection defaultTextDirection, PdfRendererBuilder builder) {
        if (defaultTextDirection != null) {
            builder.useUnicodeBidiSplitter(new ICUBidiSplitter.ICUBidiSplitterFactory());
            builder.useUnicodeBidiReorderer(new ICUBidiReorderer());
            builder.defaultTextDirection(defaultTextDirection); // OR RTL
        }
    }
}
