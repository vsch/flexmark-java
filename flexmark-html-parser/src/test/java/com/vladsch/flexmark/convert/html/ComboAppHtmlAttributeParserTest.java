package com.vladsch.flexmark.convert.html;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.test.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.SpecExampleRenderer;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.runners.Parameterized;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboAppHtmlAttributeParserTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/app_html_attribute_parser_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(FlexmarkHtmlParser.OUTPUT_ATTRIBUTES_ID, true)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            //.set(Parser.EXTENSIONS, Collections.singleton(FlexmarkHtmlParser.create())
            ;

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("paren-lists", new MutableDataSet().set(FlexmarkHtmlParser.DOT_ONLY_NUMERIC_LISTS, false));
        optionsMap.put("output-unknown", new MutableDataSet().set(FlexmarkHtmlParser.OUTPUT_UNKNOWN_TAGS, true));
        optionsMap.put("nbsp", new MutableDataSet().set(FlexmarkHtmlParser.NBSP_TEXT, "&nbsp;"));
        optionsMap.put("no-quotes", new MutableDataSet().set(FlexmarkHtmlParser.TYPOGRAPHIC_QUOTES, false));
        optionsMap.put("no-smarts", new MutableDataSet().set(FlexmarkHtmlParser.TYPOGRAPHIC_SMARTS, false));
        optionsMap.put("wrap-autolinks", new MutableDataSet().set(FlexmarkHtmlParser.WRAP_AUTO_LINKS, true));
        optionsMap.put("no-autolinks", new MutableDataSet().set(FlexmarkHtmlParser.EXTRACT_AUTO_LINKS, false));
        optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        optionsMap.put("div-as-para", new MutableDataSet().set(FlexmarkHtmlParser.DIV_AS_PARAGRAPH, true));
        optionsMap.put("no-br-as-para-breaks", new MutableDataSet().set(FlexmarkHtmlParser.BR_AS_PARA_BREAKS, false));
        optionsMap.put("no-br-as-extra-blank-lines", new MutableDataSet().set(FlexmarkHtmlParser.BR_AS_EXTRA_BLANK_LINES, false));
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
        // optionsMap.put("option1", new MutableDataSet().set(FlexmarkHtmlParserExtension.FLEXMARK_HTML_PARSER_OPTION1, true));
    }
    public ComboAppHtmlAttributeParserTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
    }

    @Nullable
    @Override
    public DataHolder options(String option) {
        return optionsMap.get(option);
    }

    @NotNull
    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combineOptions = combineOptions(OPTIONS, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combineOptions, new HtmlParser(combineOptions), new HtmlRootNodeRenderer(combineOptions), true);
    }

    @NotNull
    @Override
    public SpecReader create(@NotNull InputStream inputStream, @Nullable String fileUrl) {
        dumpSpecReader = new HtmlSpecReader(inputStream, this, fileUrl);
        return dumpSpecReader;
    }

    @Override
    protected void assertRendering(@Nullable String fileUrl, @NotNull String source, @NotNull String expectedHtml, @Nullable String expectedAst, @Nullable String optionsSet) {
        // reverse source and html
        super.assertRendering(fileUrl, expectedHtml, source, null, optionsSet);
    }
}
