package com.vladsch.flexmark.jira.converter;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboJiraConverterSpecTest extends RendererSpecTest {
    final private static String SPEC_RESOURCE = "/jira_converter_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Arrays.asList(
                    JiraConverterExtension.create()
                    , StrikethroughSubscriptExtension.create()
                    , TablesExtension.create()
                    , WikiLinkExtension.create()
                    , InsExtension.create()
                    )
            )
            .set(WikiLinkExtension.ALLOW_ANCHORS, true);

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("list-no-auto-loose", new MutableDataSet().set(Parser.LISTS_AUTO_LOOSE, false));
        optionsMap.put("links-first", new MutableDataSet().set(WikiLinkExtension.LINK_FIRST_SYNTAX, true));
        optionsMap.put("gfm", new MutableDataSet()
                .set(TablesExtension.COLUMN_SPANS, false)
                .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
                .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
                .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true)
        );

        optionsMap.put("keep-whitespace", new MutableDataSet().set(TablesExtension.TRIM_CELL_WHITESPACE, false));
    }
    public ComboJiraConverterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
