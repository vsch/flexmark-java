package com.vladsch.flexmark.core.test.util.formatter;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.data.SharedDataKeys;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.List;

public class ComboCoreWrappingSpecTest extends ComboCoreFormatterSpecTestBase {
    final private static String SPEC_RESOURCE = "/core_wrapping_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);

    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(SharedDataKeys.RUNNING_TESTS, false)  // Set to true to get stdout printout of intermediate wrapping information
            .toImmutable();

    public ComboCoreWrappingSpecTest(@NotNull SpecExample example) {
        super(example, null, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
