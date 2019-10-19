package com.vladsch.flexmark.ext.autolink;

import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.spec.ResourceLocation;
import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

@RunWith(Parameterized.class)
public class ComboAutolinkSpecTest extends RendererSpecTest {
    static final String SPEC_RESOURCE = "/ext_autolink_ast_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(AutolinkExtension.create()))
            .toImmutable();
    ;

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("no-autolink", new MutableDataSet().set(TestUtils.UNLOAD_EXTENSIONS, Collections.singleton(AutolinkExtension.class)));
        optionsMap.put("ignore-google", new MutableDataSet().set(AutolinkExtension.IGNORE_LINKS, "www.google.com"));
        optionsMap.put("intellij-dummy", new MutableDataSet().set(Parser.INTELLIJ_DUMMY_IDENTIFIER, true));
        optionsMap.put("typographic-ext", new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(AutolinkExtension.create(), TypographicExtension.create())));
    }
    public ComboAutolinkSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
    }

    @Override
    public @NotNull ResourceLocation getSpecResourceLocation() {
        return ResourceLocation.of(SPEC_RESOURCE);
    }
}
