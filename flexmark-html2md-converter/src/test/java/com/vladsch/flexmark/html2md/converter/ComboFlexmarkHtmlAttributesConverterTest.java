package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.spec.UrlString;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.test.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.SpecExampleRenderer;
import com.vladsch.flexmark.util.ast.IParse;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.runners.Parameterized;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboFlexmarkHtmlAttributesConverterTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/flexmark_html_attributes_converter_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(FlexmarkHtmlConverter.OUTPUT_ATTRIBUTES_ID, true)
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
        optionsMap.put("no-autolinks", new MutableDataSet().set(FlexmarkHtmlConverter.EXTRACT_AUTO_LINKS, false));
        optionsMap.put("list-break", new MutableDataSet().set(FlexmarkHtmlConverter.LISTS_END_ON_DOUBLE_BLANK, true));
        optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        optionsMap.put("code-emphasis", new MutableDataSet().set(FlexmarkHtmlConverter.PRE_CODE_PRESERVE_EMPHASIS, true));
        optionsMap.put("skip-heading-1", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_1, true));
        optionsMap.put("skip-heading-2", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_2, true));
        optionsMap.put("skip-heading-3", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_3, true));
        optionsMap.put("skip-heading-4", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_4, true));
        optionsMap.put("skip-heading-5", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_5, true));
        optionsMap.put("skip-heading-6", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_HEADING_6, true));
        optionsMap.put("skip-attributes", new MutableDataSet().set(FlexmarkHtmlConverter.SKIP_ATTRIBUTES, true));
        optionsMap.put("no-github-id", new MutableDataSet().set(FlexmarkHtmlConverter.OUTPUT_ID_ATTRIBUTE_REGEX, ""));
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
    }

    private static final IParse PARSER = new HtmlConverter(OPTIONS);

    private static final IRender RENDERER = new HtmlRootNodeRenderer(OPTIONS);

    private static DataHolder optionsSet(String optionSet) {
        return optionsMap.get(optionSet);
    }

    public ComboFlexmarkHtmlAttributesConverterTest(SpecExample example) {
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
    SpecExampleRenderer getSpecExampleRenderer(@Nullable DataHolder exampleOptions) {
        return new FlexmarkSpecExampleRenderer(exampleOptions, parser(), renderer(), true);
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
