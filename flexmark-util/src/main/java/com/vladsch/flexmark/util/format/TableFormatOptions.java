package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import com.vladsch.flexmark.util.format.options.TableCaptionHandling;
import com.vladsch.flexmark.util.mappers.CharWidthProvider;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSetter;

@SuppressWarnings("WeakerAccess")
public class TableFormatOptions implements MutableDataSetter {
    public static final char INTELLIJ_DUMMY_IDENTIFIER_CHAR = '\u001f';
    public static final String INTELLIJ_DUMMY_IDENTIFIER = "\u001f";

    public static final DataKey<Boolean> FORMAT_TABLE_LEAD_TRAIL_PIPES = new DataKey<>("FORMAT_TABLE_LEAD_TRAIL_PIPES", true);
    public static final DataKey<Boolean> FORMAT_TABLE_SPACE_AROUND_PIPES = new DataKey<>("FORMAT_TABLE_SPACE_AROUND_PIPES", true);
    public static final DataKey<Boolean> FORMAT_TABLE_ADJUST_COLUMN_WIDTH = new DataKey<>("FORMAT_TABLE_ADJUST_COLUMN_WIDTH", true);
    public static final DataKey<Boolean> FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT = new DataKey<>("FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT", true);
    public static final DataKey<Boolean> FORMAT_TABLE_FILL_MISSING_COLUMNS = new DataKey<>("FORMAT_TABLE_FILL_MISSING_COLUMNS", false);

    public static final DataKey<DiscretionaryText> FORMAT_TABLE_LEFT_ALIGN_MARKER = new DataKey<>("FORMAT_TABLE_LEFT_ALIGN_MARKER", DiscretionaryText.AS_IS);
    public static final DataKey<Integer> FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH = new DataKey<>("FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH", 3);
    public static final DataKey<Integer> FORMAT_TABLE_MIN_SEPARATOR_DASHES = new DataKey<>("FORMAT_TABLE_MIN_SEPARATOR_DASHES", 1);
    public static final DataKey<Boolean> FORMAT_TABLE_TRIM_CELL_WHITESPACE = new DataKey<>("FORMAT_TABLE_TRIM_CELL_WHITESPACE", true);
    public static final DataKey<TableCaptionHandling> FORMAT_TABLE_CAPTION = new DataKey<>("FORMAT_TABLE_CAPTION", TableCaptionHandling.AS_IS);
    public static final DataKey<DiscretionaryText> FORMAT_TABLE_CAPTION_SPACES = new DataKey<>("FORMAT_TABLE_CAPTION_SPACES", DiscretionaryText.AS_IS);
    public static final DataKey<String> FORMAT_TABLE_INDENT_PREFIX = new DataKey<>("FORMAT_TABLE_INDENT_PREFIX", "");
    public static final DataKey<TableManipulator> FORMAT_TABLE_MANIPULATOR = new DataKey<>("FORMAT_TABLE_MANIPULATOR", TableManipulator.NULL);

    public static final DataKey<CharWidthProvider> FORMAT_CHAR_WIDTH_PROVIDER = new DataKey<>("FORMAT_CHAR_WIDTH_PROVIDER", CharWidthProvider.NULL);
    
    public static final DataKey<Boolean> FORMAT_TABLE_DUMP_TRACKING_OFFSETS = new DataKey<>("FORMAT_TABLE_DUMP_TRACKING_OFFSETS", false);

    /**
     * @deprecated use FORMAT_TABLE_CAPTION with enum value, this option only has effect FORMAT_TABLE_CAPTION is set to AS_IS
     */
    @Deprecated public static final DataKey<Boolean> REMOVE_CAPTION = new DataKey<>("REMOVE_CAPTION", false);

    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> LEAD_TRAIL_PIPES = FORMAT_TABLE_LEAD_TRAIL_PIPES;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> SPACE_AROUND_PIPES = FORMAT_TABLE_SPACE_AROUND_PIPES;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> ADJUST_COLUMN_WIDTH = FORMAT_TABLE_ADJUST_COLUMN_WIDTH;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> APPLY_COLUMN_ALIGNMENT = FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> FILL_MISSING_COLUMNS = FORMAT_TABLE_FILL_MISSING_COLUMNS;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<DiscretionaryText> LEFT_ALIGN_MARKER = FORMAT_TABLE_LEFT_ALIGN_MARKER;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Integer> MIN_SEPARATOR_COLUMN_WIDTH = FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Integer> MIN_SEPARATOR_DASHES = FORMAT_TABLE_MIN_SEPARATOR_DASHES;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> TRIM_CELL_WHITESPACE = FORMAT_TABLE_TRIM_CELL_WHITESPACE;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<CharWidthProvider> CHAR_WIDTH_PROVIDER = FORMAT_CHAR_WIDTH_PROVIDER;

    public final boolean leadTrailPipes;
    public final boolean spaceAroundPipes;
    public final boolean adjustColumnWidth;
    public final boolean applyColumnAlignment;
    public final boolean fillMissingColumns;

    /**
     * Use formatTableCaption == TableCaptionHandling.REMOVE instead
     */
    @Deprecated
    public final boolean removeCaption;

    public final boolean trimCellWhitespace;
    public final boolean dumpIntellijOffsets;
    public final DiscretionaryText leftAlignMarker;
    public final TableCaptionHandling formatTableCaption;
    public final DiscretionaryText formatTableCaptionSpaces;
    public final int minSeparatorColumnWidth;
    public final int minSeparatorDashes;
    public final CharWidthProvider charWidthProvider;
    public final String formatTableIndentPrefix;
    public final TableManipulator tableManipulator;

    public final int spaceWidth;
    public final int spacePad;
    public final int pipeWidth;
    public final int colonWidth;
    public final int dashWidth;

    public TableFormatOptions() {
        this(null);
    }

    public TableFormatOptions(DataHolder options) {
        leadTrailPipes = FORMAT_TABLE_LEAD_TRAIL_PIPES.getFrom(options);
        spaceAroundPipes = FORMAT_TABLE_SPACE_AROUND_PIPES.getFrom(options);
        adjustColumnWidth = FORMAT_TABLE_ADJUST_COLUMN_WIDTH.getFrom(options);
        applyColumnAlignment = FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT.getFrom(options);
        fillMissingColumns = FORMAT_TABLE_FILL_MISSING_COLUMNS.getFrom(options);
        leftAlignMarker = FORMAT_TABLE_LEFT_ALIGN_MARKER.getFrom(options);
        removeCaption = REMOVE_CAPTION.getFrom(options);
        minSeparatorColumnWidth = FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH.getFrom(options);
        minSeparatorDashes = FORMAT_TABLE_MIN_SEPARATOR_DASHES.getFrom(options);
        charWidthProvider = FORMAT_CHAR_WIDTH_PROVIDER.getFrom(options);
        formatTableCaption = FORMAT_TABLE_CAPTION.getFrom(options);
        formatTableCaptionSpaces = FORMAT_TABLE_CAPTION_SPACES.getFrom(options);
        formatTableIndentPrefix = FORMAT_TABLE_INDENT_PREFIX.getFrom(options);
        trimCellWhitespace = FORMAT_TABLE_TRIM_CELL_WHITESPACE.getFrom(options);
        tableManipulator = FORMAT_TABLE_MANIPULATOR.getFrom(options);
        dumpIntellijOffsets = FORMAT_TABLE_DUMP_TRACKING_OFFSETS.getFrom(options);

        spaceWidth = charWidthProvider.spaceWidth();
        spacePad = spaceAroundPipes ? 2 * spaceWidth : 0;
        pipeWidth = charWidthProvider.charWidth('|');
        colonWidth = charWidthProvider.charWidth(':');
        dashWidth = charWidthProvider.charWidth('-');
    }

    @Override
    public MutableDataHolder setIn(final MutableDataHolder dataHolder) {
        dataHolder.set(FORMAT_TABLE_LEAD_TRAIL_PIPES, leadTrailPipes);
        dataHolder.set(FORMAT_TABLE_SPACE_AROUND_PIPES, spaceAroundPipes);
        dataHolder.set(FORMAT_TABLE_ADJUST_COLUMN_WIDTH, adjustColumnWidth);
        dataHolder.set(FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT, applyColumnAlignment);
        dataHolder.set(FORMAT_TABLE_FILL_MISSING_COLUMNS, fillMissingColumns);
        dataHolder.set(FORMAT_TABLE_LEFT_ALIGN_MARKER, leftAlignMarker);
        dataHolder.set(REMOVE_CAPTION, removeCaption);
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
