package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboParserCompatibilitySpecTest extends ComboExtraSpecTest {
    private static final String SPEC_RESOURCE = "/parser_compatibility_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(HtmlRenderer.PERCENT_ENCODE_URLS, true);

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        // TODO: add inherited options handling to flexmark-plugin in Markdown Navigator
        //optionsMap.putAll(ComboExtraSpecTest.optionsMap);
        optionsMap.put("hdr-no-atx-space", new MutableDataSet().set(Parser.HEADING_NO_ATX_SPACE, true));
        optionsMap.put("hdr-no-lead-space", new MutableDataSet().set(Parser.HEADING_NO_LEAD_SPACE, true));
        optionsMap.put("list-fixed-indent", new MutableDataSet().set(Parser.LISTS_FIXED_INDENT, 4));
        optionsMap.put("list-no-break", new MutableDataSet().set(Parser.LISTS_END_ON_DOUBLE_BLANK, false));
        optionsMap.put("list-break", new MutableDataSet().set(Parser.LISTS_END_ON_DOUBLE_BLANK, true));
        optionsMap.put("list-no-loose", new MutableDataSet().set(Parser.LISTS_AUTO_LOOSE, false));
        optionsMap.put("list-loose-if-prev", new MutableDataSet().set(Parser.LISTS_LOOSE_ON_PREV_LOOSE_ITEM, true));
        optionsMap.put("list-no-start", new MutableDataSet().set(Parser.LISTS_ORDERED_LIST_MANUAL_START, false));
        optionsMap.put("list-no-bullet-match", new MutableDataSet().set(Parser.LISTS_BULLET_MATCH, false));
        optionsMap.put("list-no-type-match", new MutableDataSet().set(Parser.LISTS_ITEM_TYPE_MATCH, false));
        optionsMap.put("list-item-mismatch-to-sub-item", new MutableDataSet().set(Parser.LISTS_ITEM_MISMATCH_TO_SUB_ITEM, true));
        optionsMap.put("list-first-item-indent-based-content-indent", new MutableDataSet().set(Parser.LISTS_FIRST_ITEM_INDENT_BASED_CONTENT_INDENT, true));
        optionsMap.put("lists-not-item-indent-relative-to-last-item", new MutableDataSet().set(Parser.LISTS_ITEM_INDENT_RELATIVE_TO_LAST_ITEM, false));
        optionsMap.put("list-first-item-indent-based-content-indent-offset-1", new MutableDataSet().set(Parser.LISTS_FIRST_ITEM_INDENT_BASED_CONTENT_INDENT_OFFSET, 1));
        optionsMap.put("list-first-item-indent-based-content-indent-offset-2", new MutableDataSet().set(Parser.LISTS_FIRST_ITEM_INDENT_BASED_CONTENT_INDENT_OFFSET, 2));
        optionsMap.put("list-first-item-indent-based-content-indent-offset-3", new MutableDataSet().set(Parser.LISTS_FIRST_ITEM_INDENT_BASED_CONTENT_INDENT_OFFSET, 3));
        optionsMap.put("list-first-item-indent-based-limit", new MutableDataSet().set(Parser.LISTS_FIRST_ITEM_INDENT_BASED_LIMIT, true));
        optionsMap.put("list-first-item-indent-based-limit-offset-1", new MutableDataSet().set(Parser.LISTS_FIRST_ITEM_INDENT_BASED_LIMIT_OFFSET, 1));
        optionsMap.put("list-first-item-indent-based-limit-offset-2", new MutableDataSet().set(Parser.LISTS_FIRST_ITEM_INDENT_BASED_LIMIT_OFFSET, 2));
        optionsMap.put("list-first-item-indent-based-limit-offset-3", new MutableDataSet().set(Parser.LISTS_FIRST_ITEM_INDENT_BASED_LIMIT_OFFSET, 3));
        optionsMap.put("list-content-indent-overrides-code-indent", new MutableDataSet().set(Parser.LISTS_CONTENT_INDENT_OVERRIDES_CODE_INDENT, true));
        optionsMap.put("list-content-indent-overrides-code-offset-5", new MutableDataSet().set(Parser.LISTS_CONTENT_INDENT_OVERRIDES_CODE_INDENT_OFFSET, 5));
        optionsMap.put("list-content-indent-overrides-code-offset-6", new MutableDataSet().set(Parser.LISTS_CONTENT_INDENT_OVERRIDES_CODE_INDENT_OFFSET, 6));
        optionsMap.put("list-content-indent-overrides-code-offset-7", new MutableDataSet().set(Parser.LISTS_CONTENT_INDENT_OVERRIDES_CODE_INDENT_OFFSET, 7));
        optionsMap.put("list-item-over-indents-to-first-item", new MutableDataSet().set(Parser.LISTS_ITEM_INDENT_OVER_MARKER_TO_LIST, true));
        optionsMap.put("list-item-over-indents-to-sub-item", new MutableDataSet().set(Parser.LISTS_ITEM_INDENT_OVER_MARKER_TO_SUB_ITEM, true));
        optionsMap.put("bullet-no-para-break", new MutableDataSet().set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH, false));
        optionsMap.put("bullet-no-item-break", new MutableDataSet().set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH, false));
        optionsMap.put("empty-bullet-item-break", new MutableDataSet().set(Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true));
        optionsMap.put("ordered-no-para-break", new MutableDataSet().set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH, false));
        optionsMap.put("ordered-non-1-para-break", new MutableDataSet().set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH, true));
        optionsMap.put("ordered-no-item-break", new MutableDataSet().set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH, false));
        optionsMap.put("ordered-non-1-item-break", new MutableDataSet().set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARENT_ITEM_PARAGRAPH, true));
        optionsMap.put("thematic-break-no-relaxed-start", new MutableDataSet().set(Parser.THEMATIC_BREAK_RELAXED_START, false));
        optionsMap.put("list-markdown-navigator", new MutableDataSet()
                .set(Parser.LISTS_AUTO_LOOSE, false)
                .set(Parser.LISTS_AUTO_LOOSE, false)
                .set(Parser.LISTS_BULLET_MATCH, false)
                .set(Parser.LISTS_ITEM_TYPE_MATCH, false)
                .set(Parser.LISTS_ITEM_MISMATCH_TO_SUB_ITEM, false)
                .set(Parser.LISTS_END_ON_DOUBLE_BLANK, false)
                .set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH, false)
                .set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true)
                .set(Parser.LISTS_ORDERED_ITEM_DOT_ONLY, true)
                .set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH, false)
                .set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true)
                .set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH, false)
                .set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARENT_ITEM_PARAGRAPH, true)
                .set(Parser.LISTS_ORDERED_LIST_MANUAL_START, false)
                .set(Parser.LISTS_FIXED_INDENT, 4)
        );
    }

    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    private static DataHolder optionsSet(String optionSet) {
        return optionsMap.get(optionSet);
    }

    public ComboParserCompatibilitySpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<SpecExample> examples = SpecReader.readExamples(SPEC_RESOURCE);
        List<Object[]> data = new ArrayList<>();

        // NULL example runs full spec test
        data.add(new Object[] { SpecExample.NULL });

        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    @Override
    protected DataHolder options(String optionSet) {
        return optionsSet(optionSet);
    }

    @Override
    protected String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    protected Parser parser() {
        return PARSER;
    }

    @Override
    protected HtmlRenderer renderer() {
        return RENDERER;
    }
}
