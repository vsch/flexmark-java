package com.vladsch.flexmark.profile.pegdown;

import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.runners.Parameterized;

import java.util.List;

public class ComboPegdownSpecTest extends ComboSpecTestCase {
    final private static String SPEC_RESOURCE = "/pegdown_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(PegdownParser.PEGDOWN_EXTENSIONS, Extensions.FENCED_CODE_BLOCKS);

    public ComboPegdownSpecTest(@NotNull SpecExample example) {
        super(example, null, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combineOptions = aggregate(OPTIONS, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combineOptions, new PegdownParser(combineOptions), new PegdownRenderer(combineOptions), true);
    }
}
