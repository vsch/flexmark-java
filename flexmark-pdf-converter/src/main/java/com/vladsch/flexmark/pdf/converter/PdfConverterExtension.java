package com.vladsch.flexmark.pdf.converter;

import com.openhtmltopdf.DOMBuilder;
import com.openhtmltopdf.bidi.support.ICUBidiReorderer;
import com.openhtmltopdf.bidi.support.ICUBidiSplitter;
import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfBoxRenderer;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.collection.DynamicDefaultKey;
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
 * or {@link #exportToPdf(String, String, String, PdfRendererBuilder.TextDirection, ProtectionPolicy protectionPolicy)}
 * or {@link #exportToPdf(OutputStream, String, String, PdfRendererBuilder.TextDirection)}
 * or {@link #exportToPdf(OutputStream, String, String, PdfRendererBuilder.TextDirection, ProtectionPolicy protectionPolicy)}
 * <p>
 * The parsed Markdown text is rendered to HTML then converted to PDF
 * </p>
 */
public class PdfConverterExtension {
    public static final DataKey<PdfRendererBuilder.TextDirection> DEFAULT_TEXT_DIRECTION = new DataKey<PdfRendererBuilder.TextDirection>("DEFAULT_TEXT_DIRECTION", (PdfRendererBuilder.TextDirection) null);
    public static final DataKey<ProtectionPolicy> PROTECTION_POLICY = new DataKey<>("PROTECTION_POLICY", (ProtectionPolicy) null);
    public static final String DEFAULT_CSS_RESOURCE_PATH = "/default.css";
    public static final String DEFAULT_TOC_LIST_CLASS = "toc";

    public static final DataKey<String> DEFAULT_CSS = new DynamicDefaultKey<String>("DEFAULT_CSS", value -> Utils.getResourceAsString(DEFAULT_CSS_RESOURCE_PATH, PdfConverterExtension.class));

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

            if (pos != -1) {
                StringBuilder sb = new StringBuilder();
                sb.append(html.subSequence(0, pos)).append(prefix).append(css).append(suffix).append(html.subSequence(pos, html.length())).append(tail);
                return sb.toString();
            }
        }
        return html;
    }

    public static void exportToPdf(String out, String html, String url, DataHolder options) {
        String css = DEFAULT_CSS.getFrom(options);
        html = embedCss(html, css);
        exportToPdf(out, html, url, options.get(DEFAULT_TEXT_DIRECTION), options.get(PROTECTION_POLICY));
    }

    public static void exportToPdf(String out, String html, String url, final PdfRendererBuilder.TextDirection defaultTextDirection) {
        exportToPdf(out, html, url, defaultTextDirection, null);
    }

    public static void exportToPdf(String out, String html, String url, final PdfRendererBuilder.TextDirection defaultTextDirection, ProtectionPolicy protectionPolicy) {
        try {
            OutputStream os = new FileOutputStream(out);
            exportToPdf(os, html, url, defaultTextDirection, protectionPolicy);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void exportToPdf(final OutputStream os, final String html, final String url, final DataHolder options) {
        exportToPdf(os, html, url, options.get(DEFAULT_TEXT_DIRECTION), options.get(PROTECTION_POLICY));
    }

    public static void exportToPdf(final OutputStream os, final String html, final String url, final PdfRendererBuilder.TextDirection defaultTextDirection) {
        exportToPdf(os, html, url, defaultTextDirection, null);
    }

    public static void exportToPdf(final OutputStream os, final String html, final String url, final PdfRendererBuilder.TextDirection defaultTextDirection, final ProtectionPolicy protectionPolicy) {
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
