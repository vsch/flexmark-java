package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.formatter.options.DiscretionaryText;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSetter;

public class TableFormatOptions implements MutableDataSetter {
    public final boolean leadTrailPipes;
    public final boolean spaceAroundPipe;
    public final boolean adjustColumnWidth;
    public final boolean applyColumnAlignment;
    public final boolean fillMissingColumns;
    public final boolean deleteEmptyColumns;
    public final boolean deleteEmptyRows;
    public final boolean trimCells;
    public final boolean formatRemoveCaption;
    public final DiscretionaryText leftAlignMarker;

    public TableFormatOptions() {
        this(null);
    }

    public TableFormatOptions(DataHolder options) {
        leadTrailPipes = TablesExtension.FORMAT_LEAD_TRAIL_PIPES.getFrom(options);
        spaceAroundPipe = TablesExtension.FORMAT_SPACE_AROUND_PIPE.getFrom(options);
        adjustColumnWidth = TablesExtension.FORMAT_ADJUST_COLUMN_WIDTH.getFrom(options);
        applyColumnAlignment = TablesExtension.FORMAT_APPLY_COLUMN_ALIGNMENT.getFrom(options);
        fillMissingColumns = TablesExtension.FORMAT_FILL_MISSING_COLUMNS.getFrom(options);
        deleteEmptyColumns = TablesExtension.FORMAT_DELETE_EMPTY_COLUMNS.getFrom(options);
        deleteEmptyRows = TablesExtension.FORMAT_DELETE_EMPTY_ROWS.getFrom(options);
        trimCells = TablesExtension.FORMAT_TRIM_CELLS.getFrom(options);
        leftAlignMarker = TablesExtension.FORMAT_LEFT_ALIGN_MARKER.getFrom(options);
        formatRemoveCaption = TablesExtension.FORMAT_REMOVE_CAPTION.getFrom(options);
    }

    @Override
    public MutableDataHolder setIn(final MutableDataHolder dataHolder) {
        dataHolder.set(TablesExtension.FORMAT_LEAD_TRAIL_PIPES, leadTrailPipes);
        dataHolder.set(TablesExtension.FORMAT_SPACE_AROUND_PIPE, spaceAroundPipe);
        dataHolder.set(TablesExtension.FORMAT_ADJUST_COLUMN_WIDTH, adjustColumnWidth);
        dataHolder.set(TablesExtension.FORMAT_APPLY_COLUMN_ALIGNMENT, applyColumnAlignment);
        dataHolder.set(TablesExtension.FORMAT_FILL_MISSING_COLUMNS, fillMissingColumns);
        dataHolder.set(TablesExtension.FORMAT_DELETE_EMPTY_COLUMNS, deleteEmptyColumns);
        dataHolder.set(TablesExtension.FORMAT_DELETE_EMPTY_ROWS, deleteEmptyRows);
        dataHolder.set(TablesExtension.FORMAT_TRIM_CELLS, trimCells);
        dataHolder.set(TablesExtension.FORMAT_LEFT_ALIGN_MARKER, leftAlignMarker);
        dataHolder.set(TablesExtension.FORMAT_REMOVE_CAPTION, formatRemoveCaption);
        return dataHolder;
    }
}
