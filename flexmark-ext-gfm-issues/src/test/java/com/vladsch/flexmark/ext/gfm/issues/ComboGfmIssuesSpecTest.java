package com.vladsch.flexmark.ext.gfm.issues;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboGfmIssuesSpecTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/gfm_issues_ast_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(Parser.EXTENSIONS, Collections.singleton(GfmIssuesExtension.create()));

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        optionsMap.put("root", new MutableDataSet().set(GfmIssuesExtension.GIT_HUB_ISSUES_URL_ROOT, "https://github.com/vsch/flexmark-java/issues"));
        optionsMap.put("prefix", new MutableDataSet().set(GfmIssuesExtension.GIT_HUB_ISSUE_URL_PREFIX, "?issue="));
        optionsMap.put("suffix", new MutableDataSet().set(GfmIssuesExtension.GIT_HUB_ISSUE_URL_SUFFIX, "&"));
        optionsMap.put("bold", new MutableDataSet().set(GfmIssuesExtension.GIT_HUB_ISSUE_HTML_PREFIX, "<strong>").set(GfmIssuesExtension.GIT_HUB_ISSUE_HTML_SUFFIX, "</strong>"));
    }
    public ComboGfmIssuesSpecTest(SpecExample example) {
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
        DataHolder combinedOptions = combineOptions(OPTIONS, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, Parser.builder(combinedOptions).build(), HtmlRenderer.builder(combinedOptions).build(), true);
    }
}
