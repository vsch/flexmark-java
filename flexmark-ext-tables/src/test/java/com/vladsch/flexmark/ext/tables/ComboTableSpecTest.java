package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.misc.Utils;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboTableSpecTest extends RendererSpecTest {
    static final String SPEC_RESOURCE = "/ext_tables_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(TablesExtension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("class-name", new MutableDataSet().set(TablesExtension.CLASS_NAME, "table-class"));
        optionsMap.put("no-caption", new MutableDataSet().set(TablesExtension.WITH_CAPTION, false));
        optionsMap.put("gfm", new MutableDataSet()
                .set(TablesExtension.COLUMN_SPANS, false)
                .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
                .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
                .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true)
        );

        optionsMap.put("typographic", new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), TypographicExtension.create())));
        optionsMap.put("keep-whitespace", new MutableDataSet().set(TablesExtension.TRIM_CELL_WHITESPACE, false));
        optionsMap.put("min-dashes-2", new MutableDataSet().set(TablesExtension.MIN_SEPARATOR_DASHES, 2));
        optionsMap.put("min-dashes-1", new MutableDataSet().set(TablesExtension.MIN_SEPARATOR_DASHES, 1));
        optionsMap.put("strip-indent", new MutableDataSet().set(TestUtils.SOURCE_INDENT, "> > "));
        optionsMap.put("sub-parse", new MutableDataSet()
                .set(TestUtils.SOURCE_PREFIX, "" +
                        "Source Prefix\n" +
                        "")
                .set(TestUtils.SOURCE_SUFFIX, "" +
                        "Source Suffix\n" +
                        "")
        );
        optionsMap.put("sub-parse2", new MutableDataSet()
                .set(TestUtils.SOURCE_SUFFIX, "" +
                        "\n" +
                        "")
        );
    }
    public ComboTableSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }

    @Test
    public void testTable() {
        if (!example.isFullSpecExample()) return;

        String source = Utils.getResourceAsString(ComboTableSpecTest.class, "/table.md");
        String html = Utils.getResourceAsString(ComboTableSpecTest.class, "/table.html");

        assertRendering(example.withSource(source).withHtml(html).withAst(null));
    }
}
