package com.vladsch.flexmark.ext.gfm.tasklist;

import com.vladsch.flexmark.core.test.util.FormatterSpecTest;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.options.ListBulletMarker;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboGfmTaskListFormatterSpecTest extends FormatterSpecTest {
    final private static String SPEC_RESOURCE = "/ext_gfm_tasklist_formatter_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(TaskListExtension.create()))
            .set(Parser.BLANK_LINES_IN_AST, true)
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("no-suffix-content", new MutableDataSet().set(Parser.LISTS_ITEM_CONTENT_AFTER_SUFFIX, true));
        optionsMap.put("task-case-as-is", new MutableDataSet().set(TaskListExtension.FORMAT_LIST_ITEM_CASE, TaskListItemCase.AS_IS));
        optionsMap.put("task-case-lowercase", new MutableDataSet().set(TaskListExtension.FORMAT_LIST_ITEM_CASE, TaskListItemCase.LOWERCASE));
        optionsMap.put("task-case-uppercase", new MutableDataSet().set(TaskListExtension.FORMAT_LIST_ITEM_CASE, TaskListItemCase.UPPERCASE));
        optionsMap.put("task-placement-as-is", new MutableDataSet().set(TaskListExtension.FORMAT_LIST_ITEM_PLACEMENT, TaskListItemPlacement.AS_IS));
        optionsMap.put("task-placement-incomplete-first", new MutableDataSet().set(TaskListExtension.FORMAT_LIST_ITEM_PLACEMENT, TaskListItemPlacement.INCOMPLETE_FIRST));
        optionsMap.put("task-placement-incomplete-nested-first", new MutableDataSet().set(TaskListExtension.FORMAT_LIST_ITEM_PLACEMENT, TaskListItemPlacement.INCOMPLETE_NESTED_FIRST));
        optionsMap.put("task-placement-complete-to-non-task", new MutableDataSet().set(TaskListExtension.FORMAT_LIST_ITEM_PLACEMENT, TaskListItemPlacement.COMPLETE_TO_NON_TASK));
        optionsMap.put("task-placement-complete-nested-to-non-task", new MutableDataSet().set(TaskListExtension.FORMAT_LIST_ITEM_PLACEMENT, TaskListItemPlacement.COMPLETE_NESTED_TO_NON_TASK));
        optionsMap.put("remove-empty-items", new MutableDataSet().set(Formatter.LIST_REMOVE_EMPTY_ITEMS, true));
        optionsMap.put("prioritized-tasks", new MutableDataSet()
                .set(TaskListExtension.FORMAT_PRIORITIZED_TASK_ITEMS, true)
                .set(Parser.LISTS_DELIMITER_MISMATCH_TO_NEW_LIST, false)
                .set(Parser.LISTS_AUTO_LOOSE, false)
        );
        optionsMap.put("ordered-task-item-priority-high", new MutableDataSet().set(TaskListExtension.FORMAT_ORDERED_TASK_ITEM_PRIORITY, 1));
        optionsMap.put("ordered-task-item-priority-normal", new MutableDataSet().set(TaskListExtension.FORMAT_ORDERED_TASK_ITEM_PRIORITY, 0));
        optionsMap.put("ordered-task-item-priority-low", new MutableDataSet().set(TaskListExtension.FORMAT_ORDERED_TASK_ITEM_PRIORITY, -1));
        optionsMap.put("list-bullet-any", new MutableDataSet().set(Formatter.LIST_BULLET_MARKER, ListBulletMarker.ANY));
        optionsMap.put("list-bullet-dash", new MutableDataSet().set(Formatter.LIST_BULLET_MARKER, ListBulletMarker.DASH));
        optionsMap.put("list-bullet-asterisk", new MutableDataSet().set(Formatter.LIST_BULLET_MARKER, ListBulletMarker.ASTERISK));
        optionsMap.put("list-bullet-plus", new MutableDataSet().set(Formatter.LIST_BULLET_MARKER, ListBulletMarker.PLUS));
    }
    public ComboGfmTaskListFormatterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
