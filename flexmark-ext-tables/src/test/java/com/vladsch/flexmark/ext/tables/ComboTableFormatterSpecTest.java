package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.core.test.util.FormatterSpecTest;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.CharWidthProvider;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import com.vladsch.flexmark.util.format.options.TableCaptionHandling;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboTableFormatterSpecTest extends FormatterSpecTest {
    final private static String SPEC_RESOURCE = "/ext_tables_formatter_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(TablesExtension.create()))
            .set(Parser.LISTS_AUTO_LOOSE, false)
            .toImmutable();

    private static CharWidthProvider WIDTH_PROVIDER = new WidthProvider();

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
        optionsMap.put("line-prefix", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_INDENT_PREFIX, ">   "));
        optionsMap.put("add-caption-spaces", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_CAPTION_SPACES, DiscretionaryText.ADD));
        optionsMap.put("remove-caption-spaces", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_CAPTION_SPACES, DiscretionaryText.REMOVE));
        optionsMap.put("add-caption", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_CAPTION, TableCaptionHandling.ADD));
        optionsMap.put("remove-empty-caption", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_CAPTION, TableCaptionHandling.REMOVE_EMPTY));
        optionsMap.put("remove-caption", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_CAPTION, TableCaptionHandling.REMOVE));
        optionsMap.put("markdown-navigator", new MutableDataSet()
                .set(TablesExtension.FORMAT_TABLE_INDENT_PREFIX, "")
                //.set(TablesExtension.FORMAT_TABLE_EMBED_INTELLIJ_DUMMY_IDENTIFIER, true)
                .set(TablesExtension.FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH, 3)
                .set(TablesExtension.FORMAT_TABLE_LEAD_TRAIL_PIPES, true)
                .set(TablesExtension.FORMAT_TABLE_ADJUST_COLUMN_WIDTH, true)
                .set(TablesExtension.FORMAT_TABLE_FILL_MISSING_COLUMNS, true)
                .set(TablesExtension.FORMAT_TABLE_LEFT_ALIGN_MARKER, DiscretionaryText.ADD)
                .set(TablesExtension.FORMAT_TABLE_CAPTION_SPACES, DiscretionaryText.AS_IS)
                .set(TablesExtension.FORMAT_TABLE_SPACE_AROUND_PIPES, true)
                .set(TablesExtension.FORMAT_TABLE_CAPTION, TableCaptionHandling.AS_IS)
                .set(TablesExtension.FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT, true)
                .set(TablesExtension.FORMAT_TABLE_MIN_SEPARATOR_DASHES, 3)
                .set(TablesExtension.FORMAT_TABLE_TRIM_CELL_WHITESPACE, false)
                .set(TablesExtension.FORMAT_CHAR_WIDTH_PROVIDER, new CharWidthProvider() {
                    @Override
                    public int getSpaceWidth() {
                        return 1;
                    }

                    @Override
                    public int getCharWidth(char c) {
                        return c == TableFormatOptions.INTELLIJ_DUMMY_IDENTIFIER_CHAR ? 0 : 1;
                    }
                })
        );
        optionsMap.put("width-provider", new MutableDataSet().set(TablesExtension.FORMAT_CHAR_WIDTH_PROVIDER, WIDTH_PROVIDER));
    }
    public ComboTableFormatterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    static class WidthProvider implements CharWidthProvider {
        @Override
        public int getSpaceWidth() {
            return 8;
        }

        @Override
        public int getCharWidth(char c) {
            return c <= 255 ? 8 : 13;
        }
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
