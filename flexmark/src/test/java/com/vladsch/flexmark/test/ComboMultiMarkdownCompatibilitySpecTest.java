package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationFamily;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboMultiMarkdownCompatibilitySpecTest extends ComboExtraSpecTest {
    private static final String SPEC_RESOURCE = "/multi_markdown_compatibility_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .setFrom(ParserEmulationFamily.FIXED_INDENT.getOptions().getMutable()
                    .setAutoLoose(true)
                    .setAutoLooseOneLevelLists(true)
                    .setDelimiterMismatchToNewList(false)
                    .setCodeIndent(8)
                    .setEndOnDoubleBlank(false)
                    .setItemIndent(4)
                    .setItemMarkerSpace(false)
                    .setItemTypeMismatchToNewList(false)
                    .setItemTypeMismatchToSubList(false)
                    .setLooseOnPrevLooseItem(false)
                    .setLooseWhenBlankFollowsItemParagraph(true)
                    .setLooseWhenHasTrailingBlankLine(false)
                    .setNewItemCodeIndent(Integer.MAX_VALUE)
                    .setOrderedItemDotOnly(true)
                    .setOrderedListManualStart(false)

                    .setItemInterrupt(new ListOptions.MutableItemInterrupt()
                            .setBulletItemInterruptsParagraph(false)
                            .setOrderedItemInterruptsParagraph(false)
                            .setOrderedNonOneItemInterruptsParagraph(false)

                            .setEmptyBulletItemInterruptsParagraph(false)
                            .setEmptyOrderedItemInterruptsParagraph(false)
                            .setEmptyOrderedNonOneItemInterruptsParagraph(false)

                            .setBulletItemInterruptsItemParagraph(true)
                            .setOrderedItemInterruptsItemParagraph(true)
                            .setOrderedNonOneItemInterruptsItemParagraph(true)

                            .setEmptyBulletItemInterruptsItemParagraph(true)
                            .setEmptyOrderedItemInterruptsItemParagraph(true)
                            .setEmptyOrderedNonOneItemInterruptsItemParagraph(true)

                            .setEmptyBulletSubItemInterruptsItemParagraph(true)
                            .setEmptyOrderedSubItemInterruptsItemParagraph(true)
                            .setEmptyOrderedNonOneSubItemInterruptsItemParagraph(true)
                    )
            )
            .set(HtmlRenderer.INDENT_SIZE, 4)
            .set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(HtmlRenderer.RENDER_HEADER_ID, true)
            .set(HtmlRenderer.HEADER_ID_GENERATOR_RESOLVE_DUPES, false)
            .set(HtmlRenderer.HEADER_ID_GENERATOR_TO_DASH_CHARS, "")
            .set(HtmlRenderer.HEADER_ID_GENERATOR_NO_DUPED_DASHES, true)
            .set(HtmlRenderer.SOFT_BREAK, " ");

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {

    }

    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    private static DataHolder optionsSet(String optionSet) {
        return optionsMap.get(optionSet);
    }

    public ComboMultiMarkdownCompatibilitySpecTest(SpecExample example) {
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
