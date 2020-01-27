package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.core.test.util.TranslationFormatterSpecTest;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import com.vladsch.flexmark.util.format.options.TableCaptionHandling;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboTableTranslationFormatterSpecTest extends TranslationFormatterSpecTest {
    final private static String SPEC_RESOURCE = "/ext_tables_translation_formatter_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(TablesExtension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("gfm", new MutableDataSet()
                .set(TablesExtension.COLUMN_SPANS, false)
                .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
                .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
                .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true)
        );
        optionsMap.put("no-caption", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_CAPTION, TableCaptionHandling.REMOVE));
        optionsMap.put("no-alignment", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT, false));
        optionsMap.put("no-width", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_ADJUST_COLUMN_WIDTH, false));
        optionsMap.put("keep-whitespace", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_TRIM_CELL_WHITESPACE, false));
        optionsMap.put("lead-trail-pipes", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_LEAD_TRAIL_PIPES, false));
        optionsMap.put("space-around-pipe", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_SPACE_AROUND_PIPES, false));
        optionsMap.put("adjust-column-width", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_ADJUST_COLUMN_WIDTH, false));
        optionsMap.put("apply-column-alignment", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT, false));
        optionsMap.put("fill-missing-columns", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_FILL_MISSING_COLUMNS, true));
        optionsMap.put("left-align-marker-as-is", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_LEFT_ALIGN_MARKER, DiscretionaryText.AS_IS));
        optionsMap.put("left-align-marker-add", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_LEFT_ALIGN_MARKER, DiscretionaryText.ADD));
        optionsMap.put("left-align-marker-remove", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_LEFT_ALIGN_MARKER, DiscretionaryText.REMOVE));
    }
    public ComboTableTranslationFormatterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
