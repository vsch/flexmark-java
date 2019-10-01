package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboGitHubCompatibilitySpecTest extends ComboExtraSpecTest {
    private static final String SPEC_RESOURCE = "/gfm_doc_compatibility_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .setFrom(ParserEmulationProfile.GITHUB_DOC)

            //.set(Parser.THEMATIC_BREAK_RELAXED_START, true)
            .set(HtmlRenderer.INDENT_SIZE, 4)
            .set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(HtmlRenderer.RENDER_HEADER_ID, true)
            .set(HtmlRenderer.SOFT_BREAK, " ");

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("no-loose-non-list-children", new MutableDataSet().set(Parser.LISTS_LOOSE_WHEN_HAS_NON_LIST_CHILDREN, false).set(Parser.LISTS_LOOSE_WHEN_BLANK_LINE_FOLLOWS_ITEM_PARAGRAPH, false));
    }

    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    private static DataHolder optionsSet(String optionSet) {
        return optionsMap.get(optionSet);
    }

    public ComboGitHubCompatibilitySpecTest(SpecExample example) {
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
    public Parser parser() {
        return PARSER;
    }

    @Override
    public HtmlRenderer renderer() {
        return RENDERER;
    }
}
