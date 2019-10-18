package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.spec.ResourceLocation;
import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final public class ComboPegdownCompatibilitySpecTest extends CoreRendererSpecTest {
    private static final String SPEC_RESOURCE = "/core_pegdown_compatibility_spec.md";

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
    public ComboPegdownCompatibilitySpecTest(@NotNull SpecExample example) {
        super(example, optionsMap);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
    }

    @Override
    public @NotNull ResourceLocation getSpecResourceLocation() {
        return ResourceLocation.of(SPEC_RESOURCE);
    }
}
