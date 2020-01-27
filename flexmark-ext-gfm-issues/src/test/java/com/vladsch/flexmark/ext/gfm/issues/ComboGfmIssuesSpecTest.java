package com.vladsch.flexmark.ext.gfm.issues;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboGfmIssuesSpecTest extends RendererSpecTest {
    final private static String SPEC_RESOURCE = "/gfm_issues_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(GfmIssuesExtension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("root", new MutableDataSet().set(GfmIssuesExtension.GIT_HUB_ISSUES_URL_ROOT, "https://github.com/vsch/flexmark-java/issues"));
        optionsMap.put("prefix", new MutableDataSet().set(GfmIssuesExtension.GIT_HUB_ISSUE_URL_PREFIX, "?issue="));
        optionsMap.put("suffix", new MutableDataSet().set(GfmIssuesExtension.GIT_HUB_ISSUE_URL_SUFFIX, "&"));
        optionsMap.put("bold", new MutableDataSet().set(GfmIssuesExtension.GIT_HUB_ISSUE_HTML_PREFIX, "<strong>").set(GfmIssuesExtension.GIT_HUB_ISSUE_HTML_SUFFIX, "</strong>"));
    }
    public ComboGfmIssuesSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
