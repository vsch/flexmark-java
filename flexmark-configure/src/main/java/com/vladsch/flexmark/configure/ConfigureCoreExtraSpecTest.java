package com.vladsch.flexmark.configure;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import org.junit.runners.Parameterized;

import java.util.*;

public class ConfigureCoreExtraSpecTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/configure_core_extra_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(HtmlRenderer.PERCENT_ENCODE_URLS, true);

    // for configuration testing each option defines a combination set, when included combinations are created from all included
    // sets by disabling the set, and generating a binary on/off sequence for each option in the set 
    public static final DataKey<DataHolder> OPTION_COMBINATIONS = new DataKey<>("OPTION_COMBINATIONS", (DataHolder) null);
    public static final DataKey<Option<Integer>> INTEGER_OPTION = new DataKey<>("INTEGER_OPTION", (Option<Integer>) null);
    public static final DataKey<Option<String>> STRING_OPTION = new DataKey<>("STRING_OPTION", (Option<String>) null);

    public static class Option<T> {
        public final DataKey<T> dataKey;
        public final T[] values;

        public Option(DataKey<T> dataKey, T... values) {
            this.dataKey = dataKey;
            this.values = values;
        }
    }

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("reference", new MutableDataSet().set(Parser.REFERENCES_KEEP, KeepType.LAST));

        optionsMap.put("heading", new MutableDataSet()
                .set(Parser.HEADING_NO_ATX_SPACE, true)
                .set(Parser.HEADING_NO_LEAD_SPACE, true)
        );

        optionsMap.put("bullet-list", new MutableDataSet()
                .set(Parser.LISTS_END_ON_DOUBLE_BLANK, true)
                .set(Parser.LISTS_AUTO_LOOSE, false)
                .set(Parser.LISTS_LOOSE_ON_PREV_LOOSE_ITEM, true)
                .set(Parser.LISTS_ORDERED_LIST_MANUAL_START, false)
                .set(Parser.LISTS_BULLET_MATCH, false)
                .set(Parser.LISTS_ITEM_TYPE_MATCH, false)
                .set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH, false)
                .set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH, false)
                .set(Parser.LISTS_ITEM_MISMATCH_TO_SUBITEM, true)
                .set(Parser.LISTS_FIXED_INDENT, 4)
                .set(Parser.LISTS_CONTENT_INDENT, true)
        );

        optionsMap.put("numbered-list", new MutableDataSet()
                .set(Parser.LISTS_END_ON_DOUBLE_BLANK, true)
                .set(Parser.LISTS_AUTO_LOOSE, false)
                .set(Parser.LISTS_LOOSE_ON_PREV_LOOSE_ITEM, true)
                .set(Parser.LISTS_ORDERED_LIST_MANUAL_START, false)
                .set(Parser.LISTS_ITEM_TYPE_MATCH, false)
                .set(Parser.LISTS_ITEM_MISMATCH_TO_SUBITEM, true)
                .set(Parser.LISTS_ORDERED_ITEM_DOT_ONLY, true)
                .set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH, false)
                .set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH, true)
                .set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH, false)
                .set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARENT_ITEM_PARAGRAPH, true)
                .set(INTEGER_OPTION, new Option<Integer>(Parser.LISTS_FIXED_INDENT, 0, 4))
                .set(Parser.LISTS_CONTENT_INDENT, true)
        );

        optionsMap.put("thematic-break", new MutableDataSet().set(Parser.THEMATIC_BREAK_RELAXED_START, false));

        optionsMap.put("escape-html", new MutableDataSet()
                .set(HtmlRenderer.ESCAPE_HTML, true)
                .set(HtmlRenderer.ESCAPE_HTML_COMMENT_BLOCKS, true)
                .set(HtmlRenderer.ESCAPE_HTML_BLOCKS, true)
                .set(HtmlRenderer.ESCAPE_INLINE_HTML_COMMENTS, true)
                .set(HtmlRenderer.ESCAPE_INLINE_HTML, true)
        );

        optionsMap.put("suppress-html", new MutableDataSet()
                .set(HtmlRenderer.SUPPRESS_HTML, true)
                .set(HtmlRenderer.SUPPRESS_HTML_BLOCKS, true)
                .set(HtmlRenderer.SUPPRESS_HTML_COMMENT_BLOCKS, true)
                .set(HtmlRenderer.SUPPRESS_INLINE_HTML, true)
                .set(HtmlRenderer.SUPPRESS_INLINE_HTML_COMMENTS, true)
        );

        optionsMap.put("fenced-code", new MutableDataSet()
                .set(STRING_OPTION, new Option<String>(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX, "", "language-"))
                .set(Parser.MATCH_CLOSING_FENCE_CHARACTERS, false)
        );

        optionsMap.put("indented-code", new MutableDataSet().set(Parser.INDENTED_CODE_NO_TRAILING_BLANK_LINES, true));

        optionsMap.put("html-comments", new MutableDataSet().set(Parser.PARSE_INNER_HTML_COMMENTS, true));

        //optionsMap.put("dummy-identifier", new MutableDataSet().set(Parser.INTELLIJ_DUMMY_IDENTIFIER, true));

        optionsMap.put("block-quote", new MutableDataSet().set(Parser.BLOCK_QUOTE_TO_BLANK_LINE, true));
    }

    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    private static DataHolder optionsSet(String optionSet) {
        return optionsMap.get(optionSet);
    }

    public ConfigureCoreExtraSpecTest(SpecExample example) {
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
