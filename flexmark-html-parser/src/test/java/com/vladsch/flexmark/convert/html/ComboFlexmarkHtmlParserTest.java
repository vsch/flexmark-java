package com.vladsch.flexmark.convert.html;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.spec.UrlString;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.test.DumpSpecReader;
import com.vladsch.flexmark.util.IParse;
import com.vladsch.flexmark.util.IRender;
import com.vladsch.flexmark.util.Ref;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.junit.ComparisonFailure;
import org.junit.runners.Parameterized;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("deprecation")
public class ComboFlexmarkHtmlParserTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/flexmark_html_parser_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(FlexmarkHtmlParser.OUTPUT_ATTRIBUTES_ID, false)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            //.set(Parser.EXTENSIONS, Collections.singleton(FlexmarkHtmlParser.create())
            ;

    private static final Map<String, DataHolder> optionsMap = new HashMap<String, DataHolder>();
    static {
        optionsMap.put("output-unknown", new MutableDataSet().set(FlexmarkHtmlParser.OUTPUT_UNKNOWN_TAGS, true));
        optionsMap.put("nbsp", new MutableDataSet().set(FlexmarkHtmlParser.NBSP_TEXT, "&nbsp;"));
        optionsMap.put("no-quotes", new MutableDataSet().set(FlexmarkHtmlParser.TYPOGRAPHIC_QUOTES, false));
        optionsMap.put("no-smarts", new MutableDataSet().set(FlexmarkHtmlParser.TYPOGRAPHIC_SMARTS, false));
        optionsMap.put("wrap-autolinks", new MutableDataSet().set(FlexmarkHtmlParser.WRAP_AUTO_LINKS, true));
        optionsMap.put("no-autolinks", new MutableDataSet().set(FlexmarkHtmlParser.EXTRACT_AUTO_LINKS, false));
        optionsMap.put("list-break", new MutableDataSet().set(FlexmarkHtmlParser.LISTS_END_ON_DOUBLE_BLANK, true));
        optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        optionsMap.put("code-emphasis", new MutableDataSet().set(FlexmarkHtmlParser.PRE_CODE_PRESERVE_EMPHASIS, true));
        optionsMap.put("div-as-para", new MutableDataSet().set(FlexmarkHtmlParser.DIV_AS_PARAGRAPH, true));
        optionsMap.put("skip-inline-strong", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_INLINE_STRONG, true));
        optionsMap.put("skip-inline-emphasis", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_INLINE_EMPHASIS, true));
        optionsMap.put("skip-inline-code", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_INLINE_CODE, true));
        optionsMap.put("skip-inline-del", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_INLINE_DEL, true));
        optionsMap.put("skip-inline-ins", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_INLINE_INS, true));
        optionsMap.put("skip-inline-sub", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_INLINE_SUB, true));
        optionsMap.put("skip-inline-sup", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_INLINE_SUP, true));
        optionsMap.put("skip-heading-1", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_HEADING_1, true));
        optionsMap.put("skip-heading-2", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_HEADING_2, true));
        optionsMap.put("skip-heading-3", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_HEADING_3, true));
        optionsMap.put("skip-heading-4", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_HEADING_4, true));
        optionsMap.put("skip-heading-5", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_HEADING_5, true));
        optionsMap.put("skip-heading-6", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_HEADING_6, true));
        optionsMap.put("skip-attributes", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_ATTRIBUTES, true));
        optionsMap.put("skip-fenced-code", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_FENCED_CODE, true));
        optionsMap.put("skip-links", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_LINKS, true));
        optionsMap.put("skip-char-escape", new MutableDataSet().set(FlexmarkHtmlParser.SKIP_CHAR_ESCAPE, true));
        optionsMap.put("text-ext-inline-strong", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_STRONG, ExtensionConversion.TEXT));
        optionsMap.put("text-ext-inline-emphasis", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_EMPHASIS, ExtensionConversion.TEXT));
        optionsMap.put("text-ext-inline-code", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_CODE, ExtensionConversion.TEXT));
        optionsMap.put("text-ext-inline-del", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_DEL, ExtensionConversion.TEXT));
        optionsMap.put("text-ext-inline-ins", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_INS, ExtensionConversion.TEXT));
        optionsMap.put("text-ext-inline-sub", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_SUB, ExtensionConversion.TEXT));
        optionsMap.put("text-ext-inline-sup", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_SUP, ExtensionConversion.TEXT));
        optionsMap.put("html-ext-inline-strong", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_STRONG, ExtensionConversion.HTML));
        optionsMap.put("html-ext-inline-emphasis", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_EMPHASIS, ExtensionConversion.HTML));
        optionsMap.put("html-ext-inline-code", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_CODE, ExtensionConversion.HTML));
        optionsMap.put("html-ext-inline-del", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_DEL, ExtensionConversion.HTML));
        optionsMap.put("html-ext-inline-ins", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_INS, ExtensionConversion.HTML));
        optionsMap.put("html-ext-inline-sub", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_SUB, ExtensionConversion.HTML));
        optionsMap.put("html-ext-inline-sup", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_SUP, ExtensionConversion.HTML));

        optionsMap.put("links-none", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_LINK, LinkConversion.NONE));
        optionsMap.put("links-exp", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_LINK, LinkConversion.MARKDOWN_EXPLICIT));
        optionsMap.put("links-ref", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_LINK, LinkConversion.MARKDOWN_REFERENCE));
        optionsMap.put("links-text", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_LINK, LinkConversion.TEXT));
        optionsMap.put("links-html", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_LINK, LinkConversion.HTML));
        optionsMap.put("img-none", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_IMAGE, LinkConversion.NONE));
        optionsMap.put("img-exp", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_IMAGE, LinkConversion.MARKDOWN_EXPLICIT));
        optionsMap.put("img-ref", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_IMAGE, LinkConversion.MARKDOWN_REFERENCE));
        optionsMap.put("img-text", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_IMAGE, LinkConversion.TEXT));
        optionsMap.put("img-html", new MutableDataSet().set(FlexmarkHtmlParser.EXT_INLINE_IMAGE, LinkConversion.HTML));
        
        optionsMap.put("typo-map", new MutableDataSet().set(FlexmarkHtmlParser.TYPOGRAPHIC_REPLACEMENT_MAP, getTypographicReplacement()));
        
        optionsMap.put("for-document", new MutableDataSet().set(FlexmarkHtmlParser.FOR_DOCUMENT, new Ref<Document>(linkDocument())));
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

    private static final IParse PARSER = new HtmlParser(OPTIONS);

    private static final IRender RENDERER = new HtmlRootNodeRenderer(OPTIONS);

    private static DataHolder optionsSet(String optionSet) {
        return optionsMap.get(optionSet);
    }

    public ComboFlexmarkHtmlParserTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<SpecExample> examples = SpecReader.readExamples(SPEC_RESOURCE);
        List<Object[]> data = new ArrayList<Object[]>();

        // NULL example runs full spec test
        data.add(new Object[] { SpecExample.NULL });

        for (SpecExample example : examples) {
            // flip source and html
            data.add(new Object[] { example });
        }
        return data;
    }

    @Override
    public DataHolder options(String optionSet) {
        return optionsSet(optionSet);
    }

    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    public IParse parser() {
        return PARSER;
    }

    @Override
    public IRender renderer() {
        return RENDERER;
    }

    @Override
    public SpecReader create(InputStream inputStream, final String fileUrl) {
        dumpSpecReader = new HtmlSpecReader(inputStream, this, fileUrl, this);
        return dumpSpecReader;
    }

    // reverse source and html
    @Override
    protected void assertRendering(final UrlString fileUrl, String source, String expectedHtml, String optionsSet) {
        DataHolder options = optionsSet == null ? null : getOptions(example(), optionsSet);
        String parseSource = expectedHtml;

        if (options != null && options.get(NO_FILE_EOL)) {
            parseSource = DumpSpecReader.trimTrailingEOL(parseSource);
        }

        Node node = parser().withOptions(options).parse(parseSource);
        String renderedResult = renderer().withOptions(options).render(node);
        String expectedResult = source;

        actualSource(renderedResult, optionsSet);

        boolean useActualHtml = useActualHtml();

        // include source for better assertion errors
        String expected;
        String actual;
        if (example() != null && example().getSection() != null) {
            StringBuilder outExpected = new StringBuilder();
            DumpSpecReader.addSpecExample(outExpected, expectedResult, expectedHtml, "", optionsSet, true, example().getSection(), example().getExampleNumber());
            expected = outExpected.toString();

            StringBuilder outActual = new StringBuilder();
            DumpSpecReader.addSpecExample(outActual, useActualHtml ? renderedResult : expectedResult, expectedHtml, "", optionsSet, true, example().getSection(), example().getExampleNumber());
            actual = outActual.toString();
        } else {
            expected = DumpSpecReader.addSpecExample(expectedResult, expectedHtml, "", optionsSet);
            actual = DumpSpecReader.addSpecExample(useActualHtml ? renderedResult : expectedResult, expectedHtml, "", optionsSet);
        }

        specExample(expected, actual, optionsSet);
        if (options != null && options.get(FAIL)) {
            thrown.expect(ComparisonFailure.class);
        }
        
        if (fileUrl != null) {
            assertEquals(fileUrl.toString(), expected, actual);
        } else {
            assertEquals(expected, actual);
        }
    }
}
