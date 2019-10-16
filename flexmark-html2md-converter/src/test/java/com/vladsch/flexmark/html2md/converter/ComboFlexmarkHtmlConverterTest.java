package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.spec.UrlString;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.test.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.SpecExampleRenderer;
import com.vladsch.flexmark.util.Ref;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.IParse;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.runners.Parameterized;

import java.io.InputStream;
import java.util.*;

@SuppressWarnings("deprecation")
public class ComboFlexmarkHtmlConverterTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/flexmark_html_converter_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(FlexmarkHtmlConverter.OUTPUT_ATTRIBUTES_ID, false)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            //.set(Parser.EXTENSIONS, Collections.singleton(FlexmarkHtmlConverter.create())
            ;

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("output-unknown", new MutableDataSet().set(FlexmarkHtmlConverter.OUTPUT_UNKNOWN_TAGS, true));
        optionsMap.put("nbsp", new MutableDataSet().set(FlexmarkHtmlConverter.NBSP_TEXT, "&nbsp;"));
        optionsMap.put("no-quotes", new MutableDataSet().set(FlexmarkHtmlConverter.TYPOGRAPHIC_QUOTES, false));
        optionsMap.put("no-smarts", new MutableDataSet().set(FlexmarkHtmlConverter.TYPOGRAPHIC_SMARTS, false));
        optionsMap.put("wrap-autolinks", new MutableDataSet().set(FlexmarkHtmlConverter.WRAP_AUTO_LINKS, true));
        optionsMap.put("no-wrap-autolinks", new MutableDataSet().set(FlexmarkHtmlConverter.WRAP_AUTO_LINKS, false));
        optionsMap.put("no-autolinks", new MutableDataSet().set(FlexmarkHtmlConverter.EXTRACT_AUTO_LINKS, false));
        optionsMap.put("list-break", new MutableDataSet().set(FlexmarkHtmlConverter.LISTS_END_ON_DOUBLE_BLANK, true));
        optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        optionsMap.put("code-emphasis", new MutableDataSet().set(FlexmarkHtmlConverter.PRE_CODE_PRESERVE_EMPHASIS, true));
        optionsMap.put("div-as-para", new MutableDataSet().set(FlexmarkHtmlConverter.DIV_AS_PARAGRAPH, true));
        optionsMap.put("skip-heading-1", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_1, true));
        optionsMap.put("skip-heading-2", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_2, true));
        optionsMap.put("skip-heading-3", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_3, true));
        optionsMap.put("skip-heading-4", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_4, true));
        optionsMap.put("skip-heading-5", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_5, true));
        optionsMap.put("skip-heading-6", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_6, true));
        optionsMap.put("skip-attributes", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_ATTRIBUTES, true));
        optionsMap.put("skip-fenced-code", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_FENCED_CODE, true));
        optionsMap.put("skip-char-escape", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_CHAR_ESCAPE, true));
        optionsMap.put("text-ext-inline-strong", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_STRONG, ExtensionConversion.TEXT));
        optionsMap.put("text-ext-inline-emphasis", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_EMPHASIS, ExtensionConversion.TEXT));
        optionsMap.put("text-ext-inline-code", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_CODE, ExtensionConversion.TEXT));
        optionsMap.put("text-ext-inline-del", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_DEL, ExtensionConversion.TEXT));
        optionsMap.put("text-ext-inline-ins", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_INS, ExtensionConversion.TEXT));
        optionsMap.put("text-ext-inline-sub", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_SUB, ExtensionConversion.TEXT));
        optionsMap.put("text-ext-inline-sup", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_SUP, ExtensionConversion.TEXT));
        optionsMap.put("html-ext-inline-strong", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_STRONG, ExtensionConversion.HTML));
        optionsMap.put("html-ext-inline-emphasis", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_EMPHASIS, ExtensionConversion.HTML));
        optionsMap.put("html-ext-inline-code", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_CODE, ExtensionConversion.HTML));
        optionsMap.put("html-ext-inline-del", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_DEL, ExtensionConversion.HTML));
        optionsMap.put("html-ext-inline-ins", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_INS, ExtensionConversion.HTML));
        optionsMap.put("html-ext-inline-sub", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_SUB, ExtensionConversion.HTML));
        optionsMap.put("html-ext-inline-sup", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_SUP, ExtensionConversion.HTML));

        optionsMap.put("links-none", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_LINK, LinkConversion.NONE));
        optionsMap.put("links-exp", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_LINK, LinkConversion.MARKDOWN_EXPLICIT));
        optionsMap.put("links-ref", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_LINK, LinkConversion.MARKDOWN_REFERENCE));
        optionsMap.put("links-text", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_LINK, LinkConversion.TEXT));
        optionsMap.put("links-html", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_LINK, LinkConversion.HTML));
        optionsMap.put("img-none", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_IMAGE, LinkConversion.NONE));
        optionsMap.put("img-exp", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_IMAGE, LinkConversion.MARKDOWN_EXPLICIT));
        optionsMap.put("img-ref", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_IMAGE, LinkConversion.MARKDOWN_REFERENCE));
        optionsMap.put("img-text", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_IMAGE, LinkConversion.TEXT));
        optionsMap.put("img-html", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_IMAGE, LinkConversion.HTML));

        optionsMap.put("typo-map", new MutableDataSet().set(FlexmarkHtmlConverter.TYPOGRAPHIC_REPLACEMENT_MAP, getTypographicReplacement()));

        optionsMap.put("for-document", new MutableDataSet().set(FlexmarkHtmlConverter.FOR_DOCUMENT, new Ref<>(linkDocument())));
        optionsMap.put("link-resolver", new MutableDataSet().set(Parser.EXTENSIONS, Collections.singletonList(HtmlConverterTextExtension.create())));
    }
    private static Map<String, String> getTypographicReplacement() {
        HashMap<String, String> map = new HashMap<>();
        map.put("“", "''");
        map.put("”", "''");
        map.put("&ldquo;", "''");
        map.put("&rdquo;", "''");
        map.put("‘", "'");
        map.put("’", "'");
        map.put("&lsquo;", "'");
        map.put("&rsquo;", "'");
        map.put("&apos;", "'");
        map.put("«", "<<<<");
        map.put("&laquo;", "<<<<");
        map.put("»", ">>>>");
        map.put("&raquo;", ">>>>");
        map.put("…", " etc.");
        map.put("&hellip;", " etc.");
        map.put("–", "--");
        map.put("&endash;", "--");
        map.put("—", "---");
        map.put("&emdash;", "---");
        return map;
    }

    private static Document linkDocument() {
        return Parser.builder().build().parse("" +
                "[example.com]: http://example.com\n" +
                "[example image]: http://example.com/image.png 'Title'\n" +
                "");
    }

    private static final IParse PARSER = new HtmlConverter(OPTIONS);

    private static final IRender RENDERER = new HtmlRootNodeRenderer(OPTIONS);

    private static DataHolder optionsSet(String optionSet) {
        return optionsMap.get(optionSet);
    }

    public ComboFlexmarkHtmlConverterTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
    }

    @Nullable
    @Override
    public DataHolder options(String optionSet) {
        return optionsSet(optionSet);
    }

    @NotNull
    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @NotNull
    public IParse parser() {
        return PARSER;
    }

    @NotNull
    public IRender renderer() {
        return RENDERER;
    }

    @Override
    public @NotNull
    SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        return new FlexmarkSpecExampleRenderer(example, exampleOptions, parser(), renderer(), true);
    }

    @NotNull
    @Override
    public SpecReader create(@NotNull InputStream inputStream, @Nullable String fileUrl) {
        dumpSpecReader = new HtmlSpecReader(inputStream, this, fileUrl);
        return dumpSpecReader;
    }

    @Override
    protected void assertRendering(UrlString fileUrl, String source, String expectedHtml, String expectedAst, String optionsSet) {
        // reverse source and html
        super.assertRendering(fileUrl, expectedHtml, source, null, optionsSet);
    }
}
