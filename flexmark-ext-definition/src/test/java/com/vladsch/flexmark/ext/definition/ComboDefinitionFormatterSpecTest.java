package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.core.test.util.FormatterSpecTest;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.options.DefinitionMarker;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboDefinitionFormatterSpecTest extends FormatterSpecTest {
    final private static String SPEC_RESOURCE = "/ext_definition_formatter_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(DefinitionExtension.create()))
            .set(Parser.LISTS_AUTO_LOOSE, false)
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("marker-spaces-1", new MutableDataSet().set(DefinitionExtension.FORMAT_MARKER_SPACES, 1));
        optionsMap.put("marker-spaces-2", new MutableDataSet().set(DefinitionExtension.FORMAT_MARKER_SPACES, 2));
        optionsMap.put("marker-spaces-3", new MutableDataSet().set(DefinitionExtension.FORMAT_MARKER_SPACES, 3));
        optionsMap.put("marker-type-any", new MutableDataSet().set(DefinitionExtension.FORMAT_MARKER_TYPE, DefinitionMarker.ANY));
        optionsMap.put("marker-type-colon", new MutableDataSet().set(DefinitionExtension.FORMAT_MARKER_TYPE, DefinitionMarker.COLON));
        optionsMap.put("marker-type-tilde", new MutableDataSet().set(DefinitionExtension.FORMAT_MARKER_TYPE, DefinitionMarker.TILDE));
        optionsMap.put("no-blank-lines", new MutableDataSet().set(Parser.BLANK_LINES_IN_AST, false));
    }
    public ComboDefinitionFormatterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
