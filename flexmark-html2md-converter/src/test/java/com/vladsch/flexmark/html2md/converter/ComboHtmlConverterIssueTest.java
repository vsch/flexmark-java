package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.List;

public class ComboHtmlConverterIssueTest extends HtmlConverterTest {
    final private static String SPEC_RESOURCE = "/html_converter_issue_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);

    public ComboHtmlConverterIssueTest(@NotNull SpecExample example) {
        super(example, null);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
