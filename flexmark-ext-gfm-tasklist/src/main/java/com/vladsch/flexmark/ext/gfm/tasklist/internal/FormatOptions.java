package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItemCase;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItemPlacement;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("WeakerAccess")
public class FormatOptions implements MutableDataSetter {
    public final TaskListItemCase taskListItemCase;
    public final TaskListItemPlacement taskListItemPlacement;
    public final int formatOrderedTaskItemPriority;
    public final int formatDefaultTaskItemPriority;
    public final int[] formatTaskItemPriorities;

    public FormatOptions() {
        this(null);
    }

    public FormatOptions(DataHolder options) {
        taskListItemCase = TaskListExtension.FORMAT_LIST_ITEM_CASE.get(options);
        taskListItemPlacement = TaskListExtension.FORMAT_LIST_ITEM_PLACEMENT.get(options);
        formatOrderedTaskItemPriority = TaskListExtension.FORMAT_ORDERED_TASK_ITEM_PRIORITY.get(options);
        formatDefaultTaskItemPriority = TaskListExtension.FORMAT_DEFAULT_TASK_ITEM_PRIORITY.get(options);
        formatTaskItemPriorities = TaskListExtension.FORMAT_TASK_ITEM_PRIORITIES.get(options);
    }

    @NotNull
    @Override
    public MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder) {
        dataHolder.set(TaskListExtension.FORMAT_LIST_ITEM_CASE, taskListItemCase);
        dataHolder.set(TaskListExtension.FORMAT_LIST_ITEM_PLACEMENT, taskListItemPlacement);
        dataHolder.set(TaskListExtension.FORMAT_ORDERED_TASK_ITEM_PRIORITY, formatOrderedTaskItemPriority);
        dataHolder.set(TaskListExtension.FORMAT_TASK_ITEM_PRIORITIES, formatTaskItemPriorities);
        return dataHolder;
    }
}
