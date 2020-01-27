package com.vladsch.flexmark.core.test.util.renderer;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.List;

final public class ComboCoreDirectionalSpecTest extends CoreRendererSpecTest {
    static final String SPEC_RESOURCE = "/ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 0)
            .set(Parser.INLINE_DELIMITER_DIRECTIONAL_PUNCTUATIONS, true)
            .set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(TestUtils.NO_FILE_EOL, false)
            .toImmutable();
    ;

    public ComboCoreDirectionalSpecTest(@NotNull SpecExample example) {
        super(example, null, OPTIONS);
    }

    protected boolean compoundSections() {
        return false;
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
