package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ast.BulletList;
import com.vladsch.flexmark.ast.ListBlock;
import com.vladsch.flexmark.ast.OrderedList;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItem;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItemPlacement;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.formatter.internal.CoreNodeFormatter;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.util.ast.BlankLine;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class TaskListNodeFormatter implements NodeFormatter {
    private final FormatOptions myOptions;
    private final ListOptions listOptions;

    public TaskListNodeFormatter(DataHolder options) {
        myOptions = new FormatOptions(options);
        listOptions = ListOptions.get(options);
    }

    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeFormattingHandler<>(TaskListItem.class, TaskListNodeFormatter.this::render),
                new NodeFormattingHandler<>(BulletList.class, TaskListNodeFormatter.this::render),
                new NodeFormattingHandler<>(OrderedList.class, TaskListNodeFormatter.this::render)
        ));
    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    private void render(TaskListItem node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.isTransformingText()) {
            CoreNodeFormatter.renderListItem(node, context, markdown, listOptions, node.getMarkerSuffix(), false);
        } else {
            BasedSequence markerSuffix = node.getMarkerSuffix();
            switch (myOptions.taskListItemCase) {
                case AS_IS:
                    break;
                case LOWERCASE:
                    markerSuffix = markerSuffix.toLowerCase();
                    break;
                case UPPERCASE:
                    markerSuffix = markerSuffix.toUpperCase();
                    break;
                default:
                    throw new IllegalStateException("Missing case for TaskListItemCase " + myOptions.taskListItemCase.name());
            }

            if (node.isItemDoneMarker()) {
                switch (myOptions.taskListItemPlacement) {
                    case AS_IS:
                    case INCOMPLETE_FIRST:
                    case INCOMPLETE_NESTED_FIRST:
                        break;
                    case COMPLETE_TO_NON_TASK:
                    case COMPLETE_NESTED_TO_NON_TASK:
                        markerSuffix = BasedSequence.NULL;
                        break;
                    default:
                        throw new IllegalStateException("Missing case for ListItemPlacement " + myOptions.taskListItemPlacement.name());
                }
            }

            // task list item node overrides isParagraphWrappingDisabled which affects empty list item blank line rendering
            boolean forceLooseItem = node.isLoose() && (node.hasChildren() && node.getFirstChildAnyNot(BlankLine.class) != null);
            CoreNodeFormatter.renderListItem(node, context, markdown, listOptions, markerSuffix.isEmpty() ? markerSuffix : markerSuffix.append(" "), forceLooseItem);
        }
    }

    private void render(BulletList node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderList(node, context, markdown, myOptions);
    }

    private void render(OrderedList node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderList(node, context, markdown, myOptions);
    }

    public static boolean hasIncompleteDescendants(Node node) {
        Node item = node.getFirstChild();
        while (item != null) {
            if (item instanceof TaskListItem) {
                if (!((TaskListItem) item).isItemDoneMarker()) {
                    return true;
                }
            }
            if (item instanceof Block && !(item instanceof Paragraph) && hasIncompleteDescendants(item)) {
                return true;
            }
            item = item.getNext();
        }

        return false;
    }

    public static void renderList(
            ListBlock node,
            NodeFormatterContext context,
            MarkdownWriter markdown,
            FormatOptions formatOptions
    ) {
        if (context.isTransformingText()) {
            context.renderChildren(node);
        } else {
            ArrayList<Node> itemList = new ArrayList<>();

            TaskListItemPlacement taskListItemPlacement = formatOptions.taskListItemPlacement;
            if (taskListItemPlacement != TaskListItemPlacement.AS_IS) {
                ArrayList<Node> incompleteTasks = new ArrayList<>();
                ArrayList<Node> completeItems = new ArrayList<>();
                boolean incompleteDescendants = taskListItemPlacement == TaskListItemPlacement.INCOMPLETE_NESTED_FIRST || taskListItemPlacement == TaskListItemPlacement.COMPLETE_NESTED_TO_NON_TASK;

                Node item = node.getFirstChild();
                while (item != null) {
                    if (item instanceof TaskListItem) {
                        TaskListItem taskItem = (TaskListItem) item;
                        if (!taskItem.isItemDoneMarker() || (incompleteDescendants && hasIncompleteDescendants(item))) {
                            incompleteTasks.add(item);
                        } else {
                            completeItems.add(item);
                        }
                    } else {
                        if (incompleteDescendants && hasIncompleteDescendants(item)) {
                            incompleteTasks.add(item);
                        } else {
                            completeItems.add(item);
                        }
                    }
                    item = item.getNext();
                }

                itemList.addAll(incompleteTasks);
                itemList.addAll(completeItems);
            } else {
                Node item = node.getFirstChild();
                while (item != null) {
                    itemList.add(item);
                    item = item.getNext();
                }
            }

            CoreNodeFormatter.renderList(node, context, markdown, itemList);
        }
    }

    public static class Factory implements NodeFormatterFactory {
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new TaskListNodeFormatter(options);
        }
    }
}
