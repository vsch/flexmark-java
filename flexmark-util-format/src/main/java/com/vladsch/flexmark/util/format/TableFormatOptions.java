package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;
import com.vladsch.flexmark.util.data.NullableDataKey;
import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import com.vladsch.flexmark.util.format.options.TableCaptionHandling;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.jetbrains.annotations.NotNull;

public class TableFormatOptions implements MutableDataSetter {
  // NOTE: the value of \u001f is hardcoded in Parsing patterns
  public static final char INTELLIJ_DUMMY_IDENTIFIER_CHAR = SequenceUtils.US;
  public static final String INTELLIJ_DUMMY_IDENTIFIER = SequenceUtils.US_CHARS;

  public static final DataKey<Boolean> FORMAT_TABLE_LEAD_TRAIL_PIPES =
      new DataKey<>("FORMAT_TABLE_LEAD_TRAIL_PIPES", true);
  public static final DataKey<Boolean> FORMAT_TABLE_SPACE_AROUND_PIPES =
      new DataKey<>("FORMAT_TABLE_SPACE_AROUND_PIPES", true);
  public static final DataKey<Boolean> FORMAT_TABLE_ADJUST_COLUMN_WIDTH =
      new DataKey<>("FORMAT_TABLE_ADJUST_COLUMN_WIDTH", true);
  public static final DataKey<Boolean> FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT =
      new DataKey<>("FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT", true);
  public static final DataKey<Boolean> FORMAT_TABLE_FILL_MISSING_COLUMNS =
      new DataKey<>("FORMAT_TABLE_FILL_MISSING_COLUMNS", false);

  /**
   * Used by table formatting to set min column from which to add missing columns, null to use
   * default
   */
  public static final NullableDataKey<Integer> FORMAT_TABLE_FILL_MISSING_MIN_COLUMN =
      new NullableDataKey<>("FORMAT_TABLE_FILL_MISSING_MIN_COLUMN", (Integer) null);

  public static final DataKey<DiscretionaryText> FORMAT_TABLE_LEFT_ALIGN_MARKER =
      new DataKey<>("FORMAT_TABLE_LEFT_ALIGN_MARKER", DiscretionaryText.AS_IS);
  public static final DataKey<Integer> FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH =
      new DataKey<>("FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH", 3);
  public static final DataKey<Integer> FORMAT_TABLE_MIN_SEPARATOR_DASHES =
      new DataKey<>("FORMAT_TABLE_MIN_SEPARATOR_DASHES", 1);
  public static final DataKey<Boolean> FORMAT_TABLE_TRIM_CELL_WHITESPACE =
      new DataKey<>("FORMAT_TABLE_TRIM_CELL_WHITESPACE", true);
  public static final DataKey<TableCaptionHandling> FORMAT_TABLE_CAPTION =
      new DataKey<>("FORMAT_TABLE_CAPTION", TableCaptionHandling.AS_IS);
  public static final DataKey<DiscretionaryText> FORMAT_TABLE_CAPTION_SPACES =
      new DataKey<>("FORMAT_TABLE_CAPTION_SPACES", DiscretionaryText.AS_IS);
  public static final DataKey<String> FORMAT_TABLE_INDENT_PREFIX =
      new DataKey<>("FORMAT_TABLE_INDENT_PREFIX", "");
  public static final DataKey<TableManipulator> FORMAT_TABLE_MANIPULATOR =
      new DataKey<>("FORMAT_TABLE_MANIPULATOR", TableManipulator.NULL);

  public static final DataKey<CharWidthProvider> FORMAT_CHAR_WIDTH_PROVIDER =
      new DataKey<>("FORMAT_CHAR_WIDTH_PROVIDER", CharWidthProvider.NULL);
  private static final DataKey<Boolean> FORMAT_TABLE_DUMP_TRACKING_OFFSETS =
      new DataKey<>("FORMAT_TABLE_DUMP_TRACKING_OFFSETS", false);

  public final boolean leadTrailPipes;
  public final boolean spaceAroundPipes;
  final boolean adjustColumnWidth;
  final boolean applyColumnAlignment;
  final boolean fillMissingColumns;
  final Integer formatTableFillMissingMinColumn;

  public final boolean trimCellWhitespace;
  public final boolean dumpIntellijOffsets;
  public final DiscretionaryText leftAlignMarker;
  final TableCaptionHandling formatTableCaption;
  final DiscretionaryText formatTableCaptionSpaces;
  final int minSeparatorColumnWidth;
  final int minSeparatorDashes;
  final CharWidthProvider charWidthProvider;
  final String formatTableIndentPrefix;
  public final TableManipulator tableManipulator;

  final int spaceWidth;
  final int spacePad;
  final int pipeWidth;
  final int colonWidth;
  final int dashWidth;

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
