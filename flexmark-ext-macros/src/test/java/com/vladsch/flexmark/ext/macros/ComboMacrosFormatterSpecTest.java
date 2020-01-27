package com.vladsch.flexmark.ext.macros;

import com.vladsch.flexmark.core.test.util.FormatterSpecTest;
import com.vladsch.flexmark.ext.gitlab.GitLabExtension;
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
import java.util.Map;

public class ComboMacrosFormatterSpecTest extends FormatterSpecTest {
    final private static String SPEC_RESOURCE = "/ext_macros_formatter_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Arrays.asList(MacrosExtension.create(), GitLabExtension.create(), TablesExtension.create()))
            .set(GitLabExtension.RENDER_BLOCK_MATH, false)
            .set(GitLabExtension.RENDER_BLOCK_MERMAID, false)
            .set(GitLabExtension.DEL_PARSER, false)
            .set(GitLabExtension.INS_PARSER, false)
            .set(GitLabExtension.RENDER_VIDEO_IMAGES, false)
            .set(Parser.LISTS_AUTO_LOOSE, false)
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = placementAndSortOptions(
            MacrosExtension.MACRO_DEFINITIONS_KEEP,
            MacrosExtension.MACRO_DEFINITIONS_PLACEMENT,
            MacrosExtension.MACRO_DEFINITIONS_SORT
    );

    public ComboMacrosFormatterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
