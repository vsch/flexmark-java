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
    public static final DataKey<Boolean> LEAD_TRAIL_PIPES = new DataKey<Boolean>("FORMAT_LEAD_TRAIL_PIPES", true);
    public static final DataKey<Boolean> SPACE_AROUND_PIPES = new DataKey<Boolean>("FORMAT_SPACE_AROUND_PIPES", true);
    public static final DataKey<Boolean> ADJUST_COLUMN_WIDTH = new DataKey<Boolean>("FORMAT_ADJUST_COLUMN_WIDTH", true);
    public static final DataKey<Boolean> APPLY_COLUMN_ALIGNMENT = new DataKey<Boolean>("FORMAT_APPLY_COLUMN_ALIGNMENT", true);
    public static final DataKey<Boolean> FILL_MISSING_COLUMNS = new DataKey<Boolean>("FORMAT_FILL_MISSING_COLUMNS", false);
    public static final DataKey<Boolean> REMOVE_CAPTION = new DataKey<Boolean>("FORMAT_REMOVE_CAPTION", false);
    public static final DataKey<DiscretionaryText> LEFT_ALIGN_MARKER = new DataKey<DiscretionaryText>("FORMAT_LEFT_ALIGN_MARKER", DiscretionaryText.AS_IS);
    public static final DataKey<Integer> MIN_SEPARATOR_COLUMN_WIDTH = new DataKey<Integer>("FORMAT_MIN_SEPARATOR_COLUMN_WIDTH", 3);
    public static final DataKey<Integer> MIN_SEPARATOR_DASHES = new DataKey<Integer>("FORMAT_MIN_SEPARATOR_DASHES", 1);
    public static final DataKey<CharWidthProvider> CHAR_WIDTH_PROVIDER = new DataKey<CharWidthProvider>("CHAR_WIDTH_PROVIDER", CharWidthProvider.NULL);
    public static final DataKey<TableCaptionHandling> FORMAT_TABLE_CAPTION = new DataKey<>("FORMAT_TABLE_CAPTION", TableCaptionHandling.AS_IS);
    public static final DataKey<DiscretionaryText> FORMAT_TABLE_CAPTION_SPACES = new DataKey<>("FORMAT_TABLE_CAPTION_SPACES", DiscretionaryText.AS_IS);
    public static final DataKey<String> FORMAT_TABLE_INDENT_PREFIX = new DataKey<>("FORMAT_TABLE_INDENT_PREFIX", "");
    public static final DataKey<Boolean> TRIM_CELL_WHITESPACE = new DataKey<Boolean>("TRIM_CELL_WHITESPACE", true);

    public final boolean leadTrailPipes;
    public final boolean spaceAroundPipes;
    public final boolean adjustColumnWidth;
    public final boolean applyColumnAlignment;
    public final boolean fillMissingColumns;
    public final boolean removeCaption;
    public final boolean trimCellWhitespace;
    public final DiscretionaryText leftAlignMarker;
    public final TableCaptionHandling formatTableCaption;
    public final DiscretionaryText formatTableCaptionSpaces;
    public final int minSeparatorColumnWidth;
    public final int minSeparatorDashes;
    public final CharWidthProvider charWidthProvider;
    public final String formatTableIndentPrefix;

    public final int spaceWidth;
    public final int spacePad;
    public final int pipeWidth;
    public final int colonWidth;
    public final int dashWidth;

    public TableFormatOptions() {
        this(null);
    }

    public TableFormatOptions(DataHolder options) {
        leadTrailPipes = LEAD_TRAIL_PIPES.getFrom(options);
        spaceAroundPipes = SPACE_AROUND_PIPES.getFrom(options);
        adjustColumnWidth = ADJUST_COLUMN_WIDTH.getFrom(options);
        applyColumnAlignment = APPLY_COLUMN_ALIGNMENT.getFrom(options);
        fillMissingColumns = FILL_MISSING_COLUMNS.getFrom(options);
        leftAlignMarker = LEFT_ALIGN_MARKER.getFrom(options);
        removeCaption = REMOVE_CAPTION.getFrom(options);
        minSeparatorColumnWidth = MIN_SEPARATOR_COLUMN_WIDTH.getFrom(options);
        minSeparatorDashes = MIN_SEPARATOR_DASHES.getFrom(options);
        charWidthProvider = CHAR_WIDTH_PROVIDER.getFrom(options);
        formatTableCaption = FORMAT_TABLE_CAPTION.getFrom(options);
        formatTableCaptionSpaces = FORMAT_TABLE_CAPTION_SPACES.getFrom(options);
        formatTableIndentPrefix = FORMAT_TABLE_INDENT_PREFIX.getFrom(options);
        trimCellWhitespace = TRIM_CELL_WHITESPACE.getFrom(options);

        spaceWidth = charWidthProvider.spaceWidth();
        spacePad = spaceAroundPipes ? 2 * spaceWidth : 0;
        pipeWidth = charWidthProvider.charWidth('|');
        colonWidth = charWidthProvider.charWidth(':');
        dashWidth = charWidthProvider.charWidth('-');
    }

    @Override
    public MutableDataHolder setIn(final MutableDataHolder dataHolder) {
        dataHolder.set(LEAD_TRAIL_PIPES, leadTrailPipes);
        dataHolder.set(SPACE_AROUND_PIPES, spaceAroundPipes);
        dataHolder.set(ADJUST_COLUMN_WIDTH, adjustColumnWidth);
        dataHolder.set(APPLY_COLUMN_ALIGNMENT, applyColumnAlignment);
        dataHolder.set(FILL_MISSING_COLUMNS, fillMissingColumns);
        dataHolder.set(LEFT_ALIGN_MARKER, leftAlignMarker);
        dataHolder.set(REMOVE_CAPTION, removeCaption);
        dataHolder.set(MIN_SEPARATOR_COLUMN_WIDTH, minSeparatorColumnWidth);
        dataHolder.set(MIN_SEPARATOR_DASHES, minSeparatorDashes);
        dataHolder.set(CHAR_WIDTH_PROVIDER, charWidthProvider);
        dataHolder.set(FORMAT_TABLE_CAPTION, formatTableCaption);
        dataHolder.set(FORMAT_TABLE_CAPTION_SPACES, formatTableCaptionSpaces);
        dataHolder.set(FORMAT_TABLE_INDENT_PREFIX, formatTableIndentPrefix);
        dataHolder.set(TRIM_CELL_WHITESPACE, trimCellWhitespace);
        return dataHolder;
    }
}
