package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItemCase;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItemPlacement;
import com.vladsch.flexmark.formatter.internal.FormatterOptions;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSetter;

@SuppressWarnings("WeakerAccess")
public class FormatOptions implements MutableDataSetter {
    public final TaskListItemCase listItemCase;
    public final TaskListItemPlacement listItemPlacement;

    public FormatOptions() {
        this(null);
    }

    public FormatOptions(DataHolder options) {
        listItemCase = TaskListExtension.FORMAT_LIST_ITEM_CASE.getFrom(options);
        listItemPlacement = TaskListExtension.FORMAT_LIST_ITEM_PLACEMENT.getFrom(options);
    }

    @Override
    public MutableDataHolder setIn(final MutableDataHolder dataHolder) {
        dataHolder.set(TaskListExtension.FORMAT_LIST_ITEM_CASE, listItemCase);
        dataHolder.set(TaskListExtension.FORMAT_LIST_ITEM_PLACEMENT, listItemPlacement);
        return dataHolder;
    }
}
