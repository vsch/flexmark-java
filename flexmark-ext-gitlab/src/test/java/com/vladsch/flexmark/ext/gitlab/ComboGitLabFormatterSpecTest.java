package com.vladsch.flexmark.ext.gitlab;

import com.vladsch.flexmark.core.test.util.FormatterSpecTest;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.List;

public class ComboGitLabFormatterSpecTest extends FormatterSpecTest {
    final private static String SPEC_RESOURCE = "/ext_gitlab_formatter_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(GitLabExtension.create()))
            .set(Parser.LISTS_AUTO_LOOSE, false)
            .toImmutable();

    public ComboGitLabFormatterSpecTest(@NotNull SpecExample example) {
        super(example, null, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
