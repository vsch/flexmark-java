package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItemCase;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItemPlacement;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class TaskListFormatOptions implements MutableDataSetter {
  public final TaskListItemCase taskListItemCase;
  public final TaskListItemPlacement taskListItemPlacement;
  public final int formatOrderedTaskItemPriority;
  public final int formatDefaultTaskItemPriority;
  public final boolean formatPrioritizedTaskItems;
  public final Map<Character, Integer> formatTaskItemPriorities;

  public TaskListFormatOptions() {
    this(null);
  }

  public TaskListFormatOptions(DataHolder options) {
    taskListItemCase = TaskListExtension.FORMAT_LIST_ITEM_CASE.get(options);
    taskListItemPlacement = TaskListExtension.FORMAT_LIST_ITEM_PLACEMENT.get(options);
    formatOrderedTaskItemPriority =
        TaskListExtension.FORMAT_ORDERED_TASK_ITEM_PRIORITY.get(options);
    formatDefaultTaskItemPriority =
        TaskListExtension.FORMAT_DEFAULT_TASK_ITEM_PRIORITY.get(options);
    formatTaskItemPriorities = TaskListExtension.FORMAT_TASK_ITEM_PRIORITIES.get(options);
    formatPrioritizedTaskItems = TaskListExtension.FORMAT_PRIORITIZED_TASK_ITEMS.get(options);
  }

  @NotNull
  @Override
  public MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder) {
    dataHolder.set(TaskListExtension.FORMAT_LIST_ITEM_CASE, taskListItemCase);
    dataHolder.set(TaskListExtension.FORMAT_LIST_ITEM_PLACEMENT, taskListItemPlacement);
    dataHolder.set(
        TaskListExtension.FORMAT_ORDERED_TASK_ITEM_PRIORITY, formatOrderedTaskItemPriority);
    dataHolder.set(TaskListExtension.FORMAT_TASK_ITEM_PRIORITIES, formatTaskItemPriorities);
    dataHolder.set(TaskListExtension.FORMAT_PRIORITIZED_TASK_ITEMS, formatPrioritizedTaskItems);
    return dataHolder;
  }
}
