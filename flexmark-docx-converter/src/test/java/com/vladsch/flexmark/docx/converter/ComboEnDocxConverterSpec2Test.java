package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.List;

public class ComboEnDocxConverterSpec2Test extends ComboDocxConverterSpecTestBase {
    final private static String SPEC_RESOURCE = "/docx_converter_ast_spec2.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);

    public ComboEnDocxConverterSpec2Test(@NotNull SpecExample example) {
        super(example, null, getDefaultOptions(RESOURCE_LOCATION));
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
