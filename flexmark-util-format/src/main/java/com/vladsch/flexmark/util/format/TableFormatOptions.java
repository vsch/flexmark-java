package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.data.*;
import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import com.vladsch.flexmark.util.format.options.TableCaptionHandling;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("WeakerAccess")
public class TableFormatOptions implements MutableDataSetter {
    // NOTE: the value of \u001f is hardcoded in Parsing patterns
    final public static char INTELLIJ_DUMMY_IDENTIFIER_CHAR = SequenceUtils.US;
    final public static String INTELLIJ_DUMMY_IDENTIFIER = SequenceUtils.US_CHARS;
    final public static CharPredicate INTELLIJ_DUMMY_IDENTIFIER_SET = value -> value == SequenceUtils.US;

    final public static DataKey<Boolean> FORMAT_TABLE_LEAD_TRAIL_PIPES = new DataKey<>("FORMAT_TABLE_LEAD_TRAIL_PIPES", true);
    final public static DataKey<Boolean> FORMAT_TABLE_SPACE_AROUND_PIPES = new DataKey<>("FORMAT_TABLE_SPACE_AROUND_PIPES", true);
    final public static DataKey<Boolean> FORMAT_TABLE_ADJUST_COLUMN_WIDTH = new DataKey<>("FORMAT_TABLE_ADJUST_COLUMN_WIDTH", true);
    final public static DataKey<Boolean> FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT = new DataKey<>("FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT", true);
    final public static DataKey<Boolean> FORMAT_TABLE_FILL_MISSING_COLUMNS = new DataKey<>("FORMAT_TABLE_FILL_MISSING_COLUMNS", false);
    /**
     * Used by table formatting to set min column from which to add missing columns, null to use default
     */
    final public static NullableDataKey<Integer> FORMAT_TABLE_FILL_MISSING_MIN_COLUMN = new NullableDataKey<>("FORMAT_TABLE_FILL_MISSING_MIN_COLUMN", (Integer) null);

    final public static DataKey<DiscretionaryText> FORMAT_TABLE_LEFT_ALIGN_MARKER = new DataKey<>("FORMAT_TABLE_LEFT_ALIGN_MARKER", DiscretionaryText.AS_IS);
    final public static DataKey<Integer> FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH = new DataKey<>("FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH", 3);
    final public static DataKey<Integer> FORMAT_TABLE_MIN_SEPARATOR_DASHES = new DataKey<>("FORMAT_TABLE_MIN_SEPARATOR_DASHES", 1);
    final public static DataKey<Boolean> FORMAT_TABLE_TRIM_CELL_WHITESPACE = new DataKey<>("FORMAT_TABLE_TRIM_CELL_WHITESPACE", true);
    final public static DataKey<TableCaptionHandling> FORMAT_TABLE_CAPTION = new DataKey<>("FORMAT_TABLE_CAPTION", TableCaptionHandling.AS_IS);
    final public static DataKey<DiscretionaryText> FORMAT_TABLE_CAPTION_SPACES = new DataKey<>("FORMAT_TABLE_CAPTION_SPACES", DiscretionaryText.AS_IS);
    final public static DataKey<String> FORMAT_TABLE_INDENT_PREFIX = new DataKey<>("FORMAT_TABLE_INDENT_PREFIX", "");
    final public static DataKey<TableManipulator> FORMAT_TABLE_MANIPULATOR = new DataKey<>("FORMAT_TABLE_MANIPULATOR", TableManipulator.NULL);

    final public static DataKey<CharWidthProvider> FORMAT_CHAR_WIDTH_PROVIDER = new DataKey<>("FORMAT_CHAR_WIDTH_PROVIDER", CharWidthProvider.NULL);
    final public static DataKey<Boolean> FORMAT_TABLE_DUMP_TRACKING_OFFSETS = new DataKey<>("FORMAT_TABLE_DUMP_TRACKING_OFFSETS", false);

    final public boolean leadTrailPipes;
    final public boolean spaceAroundPipes;
    final public boolean adjustColumnWidth;
    final public boolean applyColumnAlignment;
    final public boolean fillMissingColumns;
    final public Integer formatTableFillMissingMinColumn;

    final public boolean trimCellWhitespace;
    final public boolean dumpIntellijOffsets;
    final public DiscretionaryText leftAlignMarker;
    final public TableCaptionHandling formatTableCaption;
    final public DiscretionaryText formatTableCaptionSpaces;
    final public int minSeparatorColumnWidth;
    final public int minSeparatorDashes;
    final public CharWidthProvider charWidthProvider;
    final public String formatTableIndentPrefix;
    final public TableManipulator tableManipulator;

    final public int spaceWidth;
    final public int spacePad;
    final public int pipeWidth;
    final public int colonWidth;
    final public int dashWidth;

    public TableFormatOptions() {
        this(null);
    }

    public TableFormatOptions(DataHolder options) {
        leadTrailPipes = FORMAT_TABLE_LEAD_TRAIL_PIPES.get(options);
        spaceAroundPipes = FORMAT_TABLE_SPACE_AROUND_PIPES.get(options);
        adjustColumnWidth = FORMAT_TABLE_ADJUST_COLUMN_WIDTH.get(options);
        applyColumnAlignment = FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT.get(options);
        fillMissingColumns = FORMAT_TABLE_FILL_MISSING_COLUMNS.get(options);
        formatTableFillMissingMinColumn = FORMAT_TABLE_FILL_MISSING_MIN_COLUMN.get(options);
        leftAlignMarker = FORMAT_TABLE_LEFT_ALIGN_MARKER.get(options);
        minSeparatorColumnWidth = FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH.get(options);
        minSeparatorDashes = FORMAT_TABLE_MIN_SEPARATOR_DASHES.get(options);
        charWidthProvider = FORMAT_CHAR_WIDTH_PROVIDER.get(options);
        formatTableCaption = FORMAT_TABLE_CAPTION.get(options);
        formatTableCaptionSpaces = FORMAT_TABLE_CAPTION_SPACES.get(options);
        formatTableIndentPrefix = FORMAT_TABLE_INDENT_PREFIX.get(options);
        trimCellWhitespace = FORMAT_TABLE_TRIM_CELL_WHITESPACE.get(options);
        tableManipulator = FORMAT_TABLE_MANIPULATOR.get(options);
        dumpIntellijOffsets = FORMAT_TABLE_DUMP_TRACKING_OFFSETS.get(options);

        spaceWidth = charWidthProvider.getSpaceWidth();
        spacePad = spaceAroundPipes ? 2 * spaceWidth : 0;
        pipeWidth = charWidthProvider.getCharWidth('|');
        colonWidth = charWidthProvider.getCharWidth(':');
        dashWidth = charWidthProvider.getCharWidth('-');
    }

    @NotNull
    @Override
    public MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder) {
        dataHolder.set(FORMAT_TABLE_LEAD_TRAIL_PIPES, leadTrailPipes);
        dataHolder.set(FORMAT_TABLE_SPACE_AROUND_PIPES, spaceAroundPipes);
        dataHolder.set(FORMAT_TABLE_ADJUST_COLUMN_WIDTH, adjustColumnWidth);
        dataHolder.set(FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT, applyColumnAlignment);
        dataHolder.set(FORMAT_TABLE_FILL_MISSING_COLUMNS, fillMissingColumns);
        dataHolder.set(FORMAT_TABLE_FILL_MISSING_MIN_COLUMN, formatTableFillMissingMinColumn);
        dataHolder.set(FORMAT_TABLE_LEFT_ALIGN_MARKER, leftAlignMarker);
        dataHolder.set(FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH, minSeparatorColumnWidth);
        dataHolder.set(FORMAT_TABLE_MIN_SEPARATOR_DASHES, minSeparatorDashes);
        dataHolder.set(FORMAT_CHAR_WIDTH_PROVIDER, charWidthProvider);
        dataHolder.set(FORMAT_TABLE_CAPTION, formatTableCaption);
        dataHolder.set(FORMAT_TABLE_CAPTION_SPACES, formatTableCaptionSpaces);
        dataHolder.set(FORMAT_TABLE_INDENT_PREFIX, formatTableIndentPrefix);
        dataHolder.set(FORMAT_TABLE_TRIM_CELL_WHITESPACE, trimCellWhitespace);
        dataHolder.set(FORMAT_TABLE_MANIPULATOR, tableManipulator);
        dataHolder.set(FORMAT_TABLE_DUMP_TRACKING_OFFSETS, dumpIntellijOffsets);
        return dataHolder;
    }
}
