package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.misc.Ref;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class HtmlConverterTest extends ComboSpecTestCase {
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(FlexmarkHtmlConverter.OUTPUT_ATTRIBUTES_ID, false)
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
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
        optionsMap.put("div-tables", new MutableDataSet().set(FlexmarkHtmlConverter.DIV_TABLE_PROCESSING, true));
        optionsMap.put("no-github-id", new MutableDataSet().set(FlexmarkHtmlConverter.OUTPUT_ID_ATTRIBUTE_REGEX, ""));

        optionsMap.put("links-none", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_LINK, LinkConversion.NONE));
        optionsMap.put("links-exp", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_LINK, LinkConversion.MARKDOWN_EXPLICIT));
        optionsMap.put("links-ref", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_LINK, LinkConversion.MARKDOWN_REFERENCE));
        optionsMap.put("links-ref-uniquifier", new MutableDataSet().set(FlexmarkHtmlConverter.UNIQUE_LINK_REF_ID_GENERATOR, (refId, index) -> String.format("%s - %d", refId, index)));
        optionsMap.put("links-text", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_LINK, LinkConversion.TEXT));
        optionsMap.put("links-html", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_LINK, LinkConversion.HTML));
        optionsMap.put("img-none", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_IMAGE, LinkConversion.NONE));
        optionsMap.put("img-exp", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_IMAGE, LinkConversion.MARKDOWN_EXPLICIT));
        optionsMap.put("img-ref", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_IMAGE, LinkConversion.MARKDOWN_REFERENCE));
        optionsMap.put("img-text", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_IMAGE, LinkConversion.TEXT));
        optionsMap.put("img-html", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_INLINE_IMAGE, LinkConversion.HTML));
        optionsMap.put("paren-lists", new MutableDataSet().set(FlexmarkHtmlConverter.DOT_ONLY_NUMERIC_LISTS, false));
        optionsMap.put("no-br-as-para-breaks", new MutableDataSet().set(FlexmarkHtmlConverter.BR_AS_PARA_BREAKS, false));
        optionsMap.put("no-br-as-extra-blank-lines", new MutableDataSet().set(FlexmarkHtmlConverter.BR_AS_EXTRA_BLANK_LINES, false));
        optionsMap.put("table-no-alignment", new MutableDataSet().set(TableFormatOptions.FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT, false));
        optionsMap.put("table-no-width", new MutableDataSet().set(TableFormatOptions.FORMAT_TABLE_ADJUST_COLUMN_WIDTH, false));
        optionsMap.put("table-ignore-mid-heading", new MutableDataSet().set(FlexmarkHtmlConverter.IGNORE_TABLE_HEADING_AFTER_ROWS, true));
        optionsMap.put("math-none", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_MATH, ExtensionConversion.NONE));
        optionsMap.put("math-text", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_MATH, ExtensionConversion.TEXT));
        optionsMap.put("math-markdown", new MutableDataSet().set(FlexmarkHtmlConverter.EXT_MATH, ExtensionConversion.MARKDOWN));

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

    public HtmlConverterTest(@NotNull SpecExample example, @Nullable Map<String, ? extends DataHolder> optionMap, @Nullable DataHolder... defaultOptions) {
        super(example, ComboSpecTestCase.optionsMaps(optionsMap, optionMap), ComboSpecTestCase.dataHolders(OPTIONS, defaultOptions));
    }

    @Override
    @NotNull
    final public SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combineOptions = aggregate(myDefaultOptions, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combineOptions, new HtmlConverter(combineOptions), new HtmlRootNodeRenderer(combineOptions), true);
    }

    @Override
    public @NotNull SpecExample checkExample(@NotNull SpecExample example) {
        // reverse source and html
        return example.withHtml(example.getSource()).withSource(example.getHtml());
    }
}
