package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.spec.UrlString;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.test.DumpSpecReader;
import com.vladsch.flexmark.util.ast.IParse;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import org.junit.ComparisonFailure;
import org.junit.runners.Parameterized;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ComboAppHtmlConverterTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/app_html_converter_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(FlexmarkHtmlConverter.OUTPUT_ATTRIBUTES_ID, false)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            //.set(Parser.EXTENSIONS, Collections.singleton(FlexmarkHtmlConverter.create())
            ;

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("paren-lists", new MutableDataSet().set(FlexmarkHtmlConverter.DOT_ONLY_NUMERIC_LISTS, false));
        optionsMap.put("output-unknown", new MutableDataSet().set(FlexmarkHtmlConverter.OUTPUT_UNKNOWN_TAGS, true));
        optionsMap.put("nbsp", new MutableDataSet().set(FlexmarkHtmlConverter.NBSP_TEXT, "&nbsp;"));
        optionsMap.put("no-quotes", new MutableDataSet().set(FlexmarkHtmlConverter.TYPOGRAPHIC_QUOTES, false));
        optionsMap.put("no-smarts", new MutableDataSet().set(FlexmarkHtmlConverter.TYPOGRAPHIC_SMARTS, false));
        optionsMap.put("wrap-autolinks", new MutableDataSet().set(FlexmarkHtmlConverter.WRAP_AUTO_LINKS, true));
        optionsMap.put("no-wrap-autolinks", new MutableDataSet().set(FlexmarkHtmlConverter.WRAP_AUTO_LINKS, false));
        optionsMap.put("no-autolinks", new MutableDataSet().set(FlexmarkHtmlConverter.EXTRACT_AUTO_LINKS, false));
        optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        optionsMap.put("div-as-para", new MutableDataSet().set(FlexmarkHtmlConverter.DIV_AS_PARAGRAPH, true));
        optionsMap.put("no-br-as-para-breaks", new MutableDataSet().set(FlexmarkHtmlConverter.BR_AS_PARA_BREAKS, false));
        optionsMap.put("no-br-as-extra-blank-lines", new MutableDataSet().set(FlexmarkHtmlConverter.BR_AS_EXTRA_BLANK_LINES, false));
        optionsMap.put("table-no-alignment", new MutableDataSet().set(TableFormatOptions.FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT, false));
        optionsMap.put("table-no-width", new MutableDataSet().set(TableFormatOptions.FORMAT_TABLE_ADJUST_COLUMN_WIDTH, false));
        optionsMap.put("table-ignore-mid-heading", new MutableDataSet().set(FlexmarkHtmlConverter.IGNORE_TABLE_HEADING_AFTER_ROWS, true));
        optionsMap.put("skip-heading-1", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_1, true));
        optionsMap.put("skip-heading-2", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_2, true));
        optionsMap.put("skip-heading-3", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_3, true));
        optionsMap.put("skip-heading-4", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_4, true));
        optionsMap.put("skip-heading-5", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_5, true));
        optionsMap.put("skip-heading-6", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_6, true));
        optionsMap.put("skip-attributes", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_ATTRIBUTES, true));
        optionsMap.put("math-none", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_MATH, ExtensionConversion.NONE));
        optionsMap.put("math-text", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_MATH, ExtensionConversion.TEXT));
        optionsMap.put("math-markdown", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_MATH, ExtensionConversion.MARKDOWN));
    }

    private static final IParse PARSER = new HtmlConverter(OPTIONS);

    private static final IRender RENDERER = new HtmlRootNodeRenderer(OPTIONS);

    private static DataHolder optionsSet(String optionSet) {
        return optionsMap.get(optionSet);
    }

    public ComboAppHtmlConverterTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
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
    public SpecReader create(InputStream inputStream, String fileUrl) {
        dumpSpecReader = new HtmlSpecReader(inputStream, this, fileUrl, this);
        return dumpSpecReader;
    }

    // reverse source and html
    @Override
    protected void assertRendering(UrlString fileUrl, String source, String expectedHtml, String optionsSet) {
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
