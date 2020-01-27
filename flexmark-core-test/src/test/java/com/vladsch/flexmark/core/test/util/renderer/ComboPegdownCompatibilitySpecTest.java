package com.vladsch.flexmark.core.test.util.renderer;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final public class ComboPegdownCompatibilitySpecTest extends CoreRendererSpecTest {
    final private static String SPEC_RESOURCE = "/core_pegdown_compatibility_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);

    final private static DataHolder OPTIONS = new MutableDataSet().setFrom(ParserEmulationProfile.PEGDOWN_STRICT)
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .toMutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
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
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return ComboSpecTestCase.getTestData(RESOURCE_LOCATION);
    }
}
