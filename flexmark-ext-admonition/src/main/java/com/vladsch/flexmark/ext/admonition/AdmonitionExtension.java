package com.vladsch.flexmark.ext.admonition;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.admonition.internal.AdmonitionBlockParser;
import com.vladsch.flexmark.ext.admonition.internal.AdmonitionNodeFormatter;
import com.vladsch.flexmark.ext.admonition.internal.AdmonitionNodeRenderer;
import com.vladsch.flexmark.formatter.internal.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Extension for admonitions
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed admonition text is turned into {@link AdmonitionBlock} nodes.
 * </p>
 */
public class AdmonitionExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, Formatter.FormatterExtension
        // , Parser.ReferenceHoldingExtension
{
    public static final DataKey<Integer> CONTENT_INDENT = new DataKey<>("ADMONITION.CONTENT_INDENT", 4);
    public static final DataKey<Boolean> ALLOW_LEADING_SPACE = new DataKey<>("ADMONITION.ALLOW_LEADING_SPACE", true);
    public static final DataKey<Boolean> INTERRUPTS_PARAGRAPH = new DataKey<>("ADMONITION.INTERRUPTS_PARAGRAPH", true);
    public static final DataKey<Boolean> INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("ADMONITION.INTERRUPTS_ITEM_PARAGRAPH", true);
    public static final DataKey<Boolean> WITH_SPACES_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("ADMONITION.WITH_SPACES_INTERRUPTS_ITEM_PARAGRAPH", true);
    public static final DataKey<Boolean> ALLOW_LAZY_CONTINUATION = new DataKey<>("ADMONITION.ALLOW_LAZY_CONTINUATION", true);
    public static final DataKey<String> UNRESOLVED_QUALIFIER = new DataKey<>("ADMONITION.UNRESOLVED_QUALIFIER", "note");
    public static final DataKey<Map<String, String>> QUALIFIER_TYPE_MAP = new DataKey<Map<String, String>>("ADMONITION.QUALIFIER_TYPE_MAP", getQualifierTypeValueFactory());
    public static final DataKey<Map<String, String>> QUALIFIER_TITLE_MAP = new DataKey<Map<String, String>>("ADMONITION.QUALIFIER_TITLE_MAP", getQualifierTitleValueFactory());
    public static final DataKey<Map<String, String>> TYPE_SVG_MAP = new DataKey<Map<String, String>>("ADMONITION.TYPE_SVG_MAP", getQualifierSvgValueFactory());

    private static DataValueFactory<Map<String, String>> getQualifierTypeValueFactory() {
        return new DataValueFactory<Map<String, String>>() {
            @Override
            public Map<String, String> create(final DataHolder value) {
                return getQualifierTypeMap();
            }
        };
    }

    public static Map<String, String> getQualifierTypeMap() {
        HashMap<String, String> infoSvgMap = new HashMap<String, String>();
        // qualifier type map
        infoSvgMap.put("abstract", "abstract");
        infoSvgMap.put("summary", "abstract");
        infoSvgMap.put("tldr", "abstract");

        infoSvgMap.put("bug", "bug");

        infoSvgMap.put("danger", "danger");
        infoSvgMap.put("error", "danger");

        infoSvgMap.put("example", "example");
        infoSvgMap.put("snippet", "example");

        infoSvgMap.put("fail", "fail");
        infoSvgMap.put("failure", "fail");
        infoSvgMap.put("missing", "fail");

        infoSvgMap.put("faq", "faq");
        infoSvgMap.put("question", "faq");
        infoSvgMap.put("help", "faq");

        infoSvgMap.put("info", "info");
        infoSvgMap.put("todo", "info");

        infoSvgMap.put("note", "note");
        infoSvgMap.put("seealso", "note");

        infoSvgMap.put("quote", "quote");
        infoSvgMap.put("cite", "quote");

        infoSvgMap.put("success", "success");
        infoSvgMap.put("check", "success");
        infoSvgMap.put("done", "success");

        infoSvgMap.put("tip", "tip");
        infoSvgMap.put("hint", "tip");
        infoSvgMap.put("important", "tip");

        infoSvgMap.put("warning", "warning");
        infoSvgMap.put("caution", "warning");
        infoSvgMap.put("attention", "warning");

        return infoSvgMap;
    }

    private static DataValueFactory<Map<String, String>> getQualifierTitleValueFactory() {
        return new DataValueFactory<Map<String, String>>() {
            @Override
            public Map<String, String> create(final DataHolder value) {
                return getQualifierTitleMap();
            }
        };
    }

    public static Map<String, String> getQualifierTitleMap() {
        HashMap<String, String> infoTitleMap = new HashMap<String, String>();
        infoTitleMap.put("abstract", "Abstract");
        infoTitleMap.put("summary", "Summary");
        infoTitleMap.put("tldr", "TLDR");

        infoTitleMap.put("bug", "Bug");

        infoTitleMap.put("danger", "Danger");
        infoTitleMap.put("error", "Error");

        infoTitleMap.put("example", "Example");
        infoTitleMap.put("snippet", "Snippet");

        infoTitleMap.put("fail", "Fail");
        infoTitleMap.put("failure", "Failure");
        infoTitleMap.put("missing", "Missing");

        infoTitleMap.put("faq", "Faq");
        infoTitleMap.put("question", "Question");
        infoTitleMap.put("help", "Help");

        infoTitleMap.put("info", "Info");
        infoTitleMap.put("todo", "To Do");

        infoTitleMap.put("note", "Note");
        infoTitleMap.put("seealso", "See Also");

        infoTitleMap.put("quote", "Quote");
        infoTitleMap.put("cite", "Cite");

        infoTitleMap.put("success", "Success");
        infoTitleMap.put("check", "Check");
        infoTitleMap.put("done", "Done");

        infoTitleMap.put("tip", "Tip");
        infoTitleMap.put("hint", "Hint");
        infoTitleMap.put("important", "Important");

        infoTitleMap.put("warning", "Warning");
        infoTitleMap.put("caution", "Caution");
        infoTitleMap.put("attention", "Attention");

        return infoTitleMap;
    }

    private static DataValueFactory<Map<String, String>> getQualifierSvgValueFactory() {
        return new DataValueFactory<Map<String, String>>() {
            @Override
            public Map<String, String> create(final DataHolder value) {
                return getQualifierSvgValueMap();
            }
        };
    }

    public static Map<String, String> getQualifierSvgValueMap() {
        HashMap<String, String> typeSvgMap = new HashMap<String, String>();
        typeSvgMap.put("abstract", getInputStreamContent(AdmonitionExtension.class.getResourceAsStream("/images/adm-abstract.svg")));
        typeSvgMap.put("bug", getInputStreamContent(AdmonitionExtension.class.getResourceAsStream("/images/adm-bug.svg")));
        typeSvgMap.put("danger", getInputStreamContent(AdmonitionExtension.class.getResourceAsStream("/images/adm-danger.svg")));
        typeSvgMap.put("example", getInputStreamContent(AdmonitionExtension.class.getResourceAsStream("/images/adm-example.svg")));
        typeSvgMap.put("fail", getInputStreamContent(AdmonitionExtension.class.getResourceAsStream("/images/adm-fail.svg")));
        typeSvgMap.put("faq", getInputStreamContent(AdmonitionExtension.class.getResourceAsStream("/images/adm-faq.svg")));
        typeSvgMap.put("info", getInputStreamContent(AdmonitionExtension.class.getResourceAsStream("/images/adm-info.svg")));
        typeSvgMap.put("note", getInputStreamContent(AdmonitionExtension.class.getResourceAsStream("/images/adm-note.svg")));
        typeSvgMap.put("quote", getInputStreamContent(AdmonitionExtension.class.getResourceAsStream("/images/adm-quote.svg")));
        typeSvgMap.put("success", getInputStreamContent(AdmonitionExtension.class.getResourceAsStream("/images/adm-success.svg")));
        typeSvgMap.put("tip", getInputStreamContent(AdmonitionExtension.class.getResourceAsStream("/images/adm-tip.svg")));
        typeSvgMap.put("warning", getInputStreamContent(AdmonitionExtension.class.getResourceAsStream("/images/adm-warning.svg")));
        return typeSvgMap;
    }

    public static String getInputStreamContent(InputStream inputStream) {
        try {
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            StringWriter stringWriter = new StringWriter();
            copy(streamReader, stringWriter);
            stringWriter.close();

            String svgContent = stringWriter.toString();
            return svgContent;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDefaultCSS() {
        return getInputStreamContent(AdmonitionExtension.class.getResourceAsStream("/admonition.css"));
    }

    public static String getDefaultScript() {
        return getInputStreamContent(AdmonitionExtension.class.getResourceAsStream("/admonition.js"));
    }

    public static void copy(final Reader reader, final Writer writer) throws IOException {
        char[] buffer = new char[4096];
        int n;
        while (-1 != (n = reader.read(buffer))) {
            writer.write(buffer, 0, n);
        }
        writer.flush();
        reader.close();
    }

    private AdmonitionExtension() {
    }

    public static Extension create() {
        return new AdmonitionExtension();
    }

    @Override
    public void extend(final Formatter.Builder builder) {
        builder.nodeFormatterFactory(new AdmonitionNodeFormatter.Factory());
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new AdmonitionBlockParser.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererBuilder.isRendererType("HTML")) {
            rendererBuilder.nodeRendererFactory(new AdmonitionNodeRenderer.Factory());
        } else if (rendererBuilder.isRendererType("JIRA")) {

        }
    }
}
