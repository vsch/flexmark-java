package com.vladsch.flexmark.ext.gitlab;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.html.HtmlRenderer;
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

public class ComboGitLabSpecTest extends RendererSpecTest {
    final private static String SPEC_RESOURCE = "/ext_gitlab_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.RENDER_HEADER_ID, true)
            .set(Parser.EXTENSIONS, Collections.singletonList(GitLabExtension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("no-del", new MutableDataSet().set(GitLabExtension.DEL_PARSER, false));
        optionsMap.put("no-ins", new MutableDataSet().set(GitLabExtension.INS_PARSER, false));
        optionsMap.put("no-quotes", new MutableDataSet().set(GitLabExtension.BLOCK_QUOTE_PARSER, false));
        optionsMap.put("no-math", new MutableDataSet().set(GitLabExtension.RENDER_BLOCK_MATH, false));
        optionsMap.put("no-mermaid", new MutableDataSet().set(GitLabExtension.RENDER_BLOCK_MERMAID, false));
        optionsMap.put("no-video", new MutableDataSet().set(GitLabExtension.RENDER_VIDEO_IMAGES, false));
        optionsMap.put("no-video-link", new MutableDataSet().set(GitLabExtension.RENDER_VIDEO_LINK, false));
        optionsMap.put("no-nested-quotes", new MutableDataSet().set(GitLabExtension.NESTED_BLOCK_QUOTES, false));
        optionsMap.put("block-delimiters", new MutableDataSet().set(HtmlRenderer.FENCED_CODE_LANGUAGE_DELIMITERS, "-"));
        optionsMap.put("math-class", new MutableDataSet().set(GitLabExtension.BLOCK_MATH_CLASS, "math-class"));
        optionsMap.put("math-latex", new MutableDataSet().set(GitLabExtension.MATH_LANGUAGES, new String[]{"math", "latex"}));
        optionsMap.put("mermaid-class", new MutableDataSet().set(GitLabExtension.BLOCK_MERMAID_CLASS, "mermaid-class"));
        optionsMap.put("mermaid-alias", new MutableDataSet().set(GitLabExtension.MERMAID_LANGUAGES, new String[]{"mermaid", "alias"}));
        optionsMap.put("code-content-block", new MutableDataSet().set(Parser.FENCED_CODE_CONTENT_BLOCK, true));
        optionsMap.put("video-extensions", new MutableDataSet().set(GitLabExtension.VIDEO_IMAGE_EXTENSIONS, "tst"));
        optionsMap.put("video-link-format", new MutableDataSet().set(GitLabExtension.VIDEO_IMAGE_LINK_TEXT_FORMAT, "Get Video '%s'"));
        optionsMap.put("video-class", new MutableDataSet().set(GitLabExtension.VIDEO_IMAGE_CLASS, "video-class"));
    }
    public ComboGitLabSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
