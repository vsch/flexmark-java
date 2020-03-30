package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.List;

public class ComboDocxConverterIssuesSpecTest extends ComboDocxConverterSpecTestBase {
    final private static String SPEC_RESOURCE = "/docx_converter_issues_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);

    public ComboDocxConverterIssuesSpecTest(@NotNull SpecExample example) {
        super(example, null, getDefaultOptions(RESOURCE_LOCATION));
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
