package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.test.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.SpecExampleRenderer;
import com.vladsch.flexmark.test.TestUtils;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboTableSpecTest extends ComboSpecTestCase {
    static final String SPEC_RESOURCE = "/ext_tables_ast_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(Parser.EXTENSIONS, Collections.singleton(TablesExtension.create()));

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
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
    }

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    static DataHolder optionsSet(String optionSet) {
        if (optionSet == null) return null;

        return optionsMap.get(optionSet);
    }

    public ComboTableSpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
    }

    @Nullable
    @Override
    public DataHolder options(String optionSet) {
        return optionsSet(optionSet);
    }

    @NotNull
    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = combineOptions(OPTIONS, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, PARSER.withOptions(combinedOptions), RENDERER.withOptions(combinedOptions), true);
    }

    @Test
    public void testTable() throws Exception {
        if (!example.isFullSpecExample()) return;

        final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();
        final Parser PARSER = Parser.builder(OPTIONS).build();

        String source = readResource("/table.md");
        String html = readResource("/table.html");

        assertRendering(example.getFileUrl(), source, html);
    }
}
