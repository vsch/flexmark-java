package com.vladsch.flexmark.ext.enumerated.reference;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class ComboExtEnumeratedReferenceSpecTest extends RendererSpecTest {
    final private static String SPEC_RESOURCE = "/ext_enumerated_reference_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(TablesExtension.WITH_CAPTION, true)
            .set(Parser.EXTENSIONS, Arrays.asList(EnumeratedReferenceExtension.create(), AttributesExtension.create(), TablesExtension.create()))
            .toImmutable();

    public ComboExtEnumeratedReferenceSpecTest(@NotNull SpecExample example) {
        super(example, null, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
