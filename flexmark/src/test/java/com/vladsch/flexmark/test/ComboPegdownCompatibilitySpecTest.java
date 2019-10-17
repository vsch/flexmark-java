package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboPegdownCompatibilitySpecTest extends ComboExtraSpecTest {
    private static final String SPEC_RESOURCE = "/pegdown_compatibility_spec.md";
    static final DataHolder OPTIONS = new MutableDataSet().setFrom(ParserEmulationProfile.PEGDOWN_STRICT)
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(HtmlRenderer.PERCENT_ENCODE_URLS, true);

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("lists-item-indent", new MutableDataSet().set(Parser.LISTS_ITEM_INDENT, 2));
        optionsMap.put("blank-line-interrupts-html", new MutableDataSet().set(Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS, true));
        optionsMap.put("lists-no-loose", new MutableDataSet()
                .set(Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS, false)
                .set(Parser.LISTS_AUTO_LOOSE, false)
                .set(Parser.LISTS_AUTO_LOOSE_ONE_LEVEL_LISTS, false)
                .set(Parser.LISTS_LOOSE_WHEN_PREV_HAS_TRAILING_BLANK_LINE, false)
                .set(Parser.LISTS_LOOSE_WHEN_LAST_ITEM_PREV_HAS_TRAILING_BLANK_LINE, false)
                .set(Parser.LISTS_LOOSE_WHEN_HAS_NON_LIST_CHILDREN, false)
                .set(Parser.LISTS_LOOSE_WHEN_BLANK_LINE_FOLLOWS_ITEM_PARAGRAPH, false)
                .set(Parser.LISTS_LOOSE_WHEN_HAS_LOOSE_SUB_ITEM, false)
                .set(Parser.LISTS_LOOSE_WHEN_HAS_TRAILING_BLANK_LINE, false)
                .set(Parser.LISTS_LOOSE_WHEN_CONTAINS_BLANK_LINE, false)
        );
    }

    public ComboPegdownCompatibilitySpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData( SPEC_RESOURCE);
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
        DataHolder combinedOptions = combineOptions(OPTIONS, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, Parser.builder(combinedOptions).build(), HtmlRenderer.builder(combinedOptions).build(), true);
}
}
