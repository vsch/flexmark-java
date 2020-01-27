package com.vladsch.flexmark.core.test.util.formatter;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.List;

public class ComboCoreFormatterSpecTest extends ComboCoreFormatterSpecTestBase {
    final private static String SPEC_RESOURCE = "/core_formatter_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);

    public ComboCoreFormatterSpecTest(@NotNull SpecExample example) {
        super(example, null);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
