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

public class ComboTableManipulationSpecTest extends FormatterSpecTest {
    final private static String SPEC_RESOURCE = "/ext_tables_manipulation_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(TablesExtension.create()))
            .set(Parser.LISTS_AUTO_LOOSE, false)
            .set(Parser.BLANK_LINES_IN_AST, true);

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
        optionsMap.put("markdown-navigator", new MutableDataSet()
                .set(TablesExtension.FORMAT_TABLE_INDENT_PREFIX, "")
                .set(TablesExtension.FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH, 3)
                .set(TablesExtension.FORMAT_TABLE_LEAD_TRAIL_PIPES, true)
                .set(TablesExtension.FORMAT_TABLE_ADJUST_COLUMN_WIDTH, true)
                .set(TablesExtension.FORMAT_TABLE_FILL_MISSING_COLUMNS, true)
                .set(TablesExtension.FORMAT_TABLE_LEFT_ALIGN_MARKER, DiscretionaryText.ADD)
                .set(TablesExtension.FORMAT_TABLE_CAPTION_SPACES, DiscretionaryText.AS_IS)
                .set(TablesExtension.FORMAT_TABLE_SPACE_AROUND_PIPES, true)
                .set(TablesExtension.FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT, true)
                .set(TablesExtension.FORMAT_TABLE_MIN_SEPARATOR_DASHES, 3)
                .set(TablesExtension.FORMAT_TABLE_CAPTION, TableCaptionHandling.AS_IS)
                .set(TablesExtension.FORMAT_TABLE_TRIM_CELL_WHITESPACE, true)
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

        // @formatter:off
        optionsMap.put("delete-row-1-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(0, 1)));
        optionsMap.put("delete-row-2-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(1, 1)));
        optionsMap.put("delete-row-3-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(2, 1)));
        optionsMap.put("delete-row-4-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(3, 1)));
        optionsMap.put("delete-row-5-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(4, 1)));
        optionsMap.put("delete-row-6-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(5, 1)));
        optionsMap.put("delete-row-7-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(6, 1)));
        optionsMap.put("delete-row-8-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(7, 1)));
        optionsMap.put("delete-row-9-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(8, 1)));

        optionsMap.put("delete-row-1-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(0, 2)));
        optionsMap.put("delete-row-2-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(1, 2)));
        optionsMap.put("delete-row-3-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(2, 2)));
        optionsMap.put("delete-row-4-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(3, 2)));
        optionsMap.put("delete-row-5-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(4, 2)));
        optionsMap.put("delete-row-6-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(5, 2)));
        optionsMap.put("delete-row-7-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(6, 2)));
        optionsMap.put("delete-row-8-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(7, 2)));
        optionsMap.put("delete-row-9-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteRows(8, 2)));

        optionsMap.put("insert-row-1-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(0, 1)));
        optionsMap.put("insert-row-2-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(1, 1)));
        optionsMap.put("insert-row-3-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(2, 1)));
        optionsMap.put("insert-row-4-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(3, 1)));
        optionsMap.put("insert-row-5-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(4, 1)));
        optionsMap.put("insert-row-6-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(5, 1)));
        optionsMap.put("insert-row-7-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(6, 1)));
        optionsMap.put("insert-row-8-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(7, 1)));
        optionsMap.put("insert-row-9-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(8, 1)));

        optionsMap.put("insert-row-1-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(0, 2)));
        optionsMap.put("insert-row-2-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(1, 2)));
        optionsMap.put("insert-row-3-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(2, 2)));
        optionsMap.put("insert-row-4-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(3, 2)));
        optionsMap.put("insert-row-5-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(4, 2)));
        optionsMap.put("insert-row-6-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(5, 2)));
        optionsMap.put("insert-row-7-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(6, 2)));
        optionsMap.put("insert-row-8-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(7, 2)));
        optionsMap.put("insert-row-9-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertRows(8, 2)));

        optionsMap.put("delete-col-0-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(-1, 1)));
        optionsMap.put("delete-col-1-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(0, 1)));
        optionsMap.put("delete-col-2-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(1, 1)));
        optionsMap.put("delete-col-3-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(2, 1)));
        optionsMap.put("delete-col-4-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(3, 1)));
        optionsMap.put("delete-col-5-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(4, 1)));
        optionsMap.put("delete-col-6-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(5, 1)));
        optionsMap.put("delete-col-7-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(6, 1)));
        optionsMap.put("delete-col-8-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(7, 1)));
        optionsMap.put("delete-col-9-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(8, 1)));

        optionsMap.put("delete-col-1-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(0, 2)));
        optionsMap.put("delete-col-2-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(1, 2)));
        optionsMap.put("delete-col-3-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(2, 2)));
        optionsMap.put("delete-col-4-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(3, 2)));
        optionsMap.put("delete-col-5-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(4, 2)));
        optionsMap.put("delete-col-6-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(5, 2)));
        optionsMap.put("delete-col-7-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(6, 2)));
        optionsMap.put("delete-col-8-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(7, 2)));
        optionsMap.put("delete-col-9-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.deleteColumns(8, 2)));

        optionsMap.put("insert-col-0-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(-1, 1)));
        optionsMap.put("insert-col-1-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(0, 1)));
        optionsMap.put("insert-col-2-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(1, 1)));
        optionsMap.put("insert-col-3-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(2, 1)));
        optionsMap.put("insert-col-4-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(3, 1)));
        optionsMap.put("insert-col-5-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(4, 1)));
        optionsMap.put("insert-col-6-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(5, 1)));
        optionsMap.put("insert-col-7-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(6, 1)));
        optionsMap.put("insert-col-8-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(7, 1)));
        optionsMap.put("insert-col-9-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(8, 1)));

        optionsMap.put("insert-col-1-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(0, 2)));
        optionsMap.put("insert-col-2-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(1, 2)));
        optionsMap.put("insert-col-3-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(2, 2)));
        optionsMap.put("insert-col-4-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(3, 2)));
        optionsMap.put("insert-col-5-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(4, 2)));
        optionsMap.put("insert-col-6-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(5, 2)));
        optionsMap.put("insert-col-7-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(6, 2)));
        optionsMap.put("insert-col-8-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(7, 2)));
        optionsMap.put("insert-col-9-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.insertColumns(8, 2)));

        optionsMap.put("move-col-0-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(-1, 1)));
        optionsMap.put("move-col-2-0", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(1, -1)));

        optionsMap.put("move-col-1-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(0, 0)));
        optionsMap.put("move-col-1-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(0, 1)));
        optionsMap.put("move-col-1-3", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(0, 2)));
        optionsMap.put("move-col-1-4", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(0, 3)));
        optionsMap.put("move-col-1-5", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(0, 4)));
        optionsMap.put("move-col-1-6", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(0, 5)));
        optionsMap.put("move-col-1-7", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(0, 6)));

        optionsMap.put("move-col-2-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(1, 0)));
        optionsMap.put("move-col-2-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(1, 1)));
        optionsMap.put("move-col-2-3", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(1, 2)));
        optionsMap.put("move-col-2-4", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(1, 3)));
        optionsMap.put("move-col-2-5", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(1, 4)));
        optionsMap.put("move-col-2-6", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(1, 5)));
        optionsMap.put("move-col-2-7", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(1, 6)));

        optionsMap.put("move-col-3-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(2, 0)));
        optionsMap.put("move-col-3-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(2, 1)));
        optionsMap.put("move-col-3-3", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(2, 2)));
        optionsMap.put("move-col-3-4", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(2, 3)));
        optionsMap.put("move-col-3-5", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(2, 4)));
        optionsMap.put("move-col-3-6", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(2, 5)));
        optionsMap.put("move-col-3-7", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(2, 6)));

        optionsMap.put("move-col-4-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(3, 0)));
        optionsMap.put("move-col-4-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(3, 1)));
        optionsMap.put("move-col-4-3", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(3, 2)));
        optionsMap.put("move-col-4-4", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(3, 3)));
        optionsMap.put("move-col-4-5", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(3, 4)));
        optionsMap.put("move-col-4-6", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(3, 5)));
        optionsMap.put("move-col-4-7", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(3, 6)));

        optionsMap.put("move-col-5-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(4, 0)));
        optionsMap.put("move-col-5-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(4, 1)));
        optionsMap.put("move-col-5-3", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(4, 2)));
        optionsMap.put("move-col-5-4", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(4, 3)));
        optionsMap.put("move-col-5-5", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(4, 4)));
        optionsMap.put("move-col-5-6", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(4, 5)));
        optionsMap.put("move-col-5-7", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(4, 6)));

        optionsMap.put("move-col-6-1", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(5, 0)));
        optionsMap.put("move-col-6-2", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(5, 1)));
        optionsMap.put("move-col-6-3", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(5, 2)));
        optionsMap.put("move-col-6-4", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(5, 3)));
        optionsMap.put("move-col-6-5", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(5, 4)));
        optionsMap.put("move-col-6-6", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(5, 5)));
        optionsMap.put("move-col-6-7", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_MANIPULATOR, (table,tableNoe)-> table.moveColumn(5, 6)));

        // @formatter:on
    }
    public ComboTableManipulationSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
