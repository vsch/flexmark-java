package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItem;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItemPlacement;
import com.vladsch.flexmark.formatter.CustomNodeFormatter;
import com.vladsch.flexmark.formatter.internal.*;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class TaskListNodeFormatter implements NodeFormatter {
    private final FormatOptions myOptions;

    public TaskListNodeFormatter(DataHolder options) {
        myOptions = new FormatOptions(options);
    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        //noinspection unchecked
        return new HashSet<NodeFormattingHandler<?>>(Arrays.asList(
                new NodeFormattingHandler<TaskListItem>(TaskListItem.class, new CustomNodeFormatter<TaskListItem>() {
                    @Override
                    public void render(TaskListItem node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TaskListNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<BulletList>(BulletList.class, new CustomNodeFormatter<BulletList>() {
                    @Override
                    public void render(BulletList node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TaskListNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<OrderedList>(OrderedList.class, new CustomNodeFormatter<OrderedList>() {
                    @Override
                    public void render(OrderedList node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TaskListNodeFormatter.this.render(node, context, markdown);
                    }
                })
        ));
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    private void render(final TaskListItem node, final NodeFormatterContext context, final MarkdownWriter markdown) {
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

        CoreNodeFormatter.renderListItem(node, context, markdown, markerSuffix.isEmpty() ? markerSuffix : markerSuffix.append(" "));
    }

    private void render(final BulletList node, final NodeFormatterContext context, MarkdownWriter markdown) {
        renderList(node, context, markdown, myOptions);
    }

    private void render(final OrderedList node, final NodeFormatterContext context, MarkdownWriter markdown) {
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
            final ListBlock node,
            final NodeFormatterContext context,
            MarkdownWriter markdown,
            FormatOptions formatOptions
    ) {
        ArrayList<Node> itemList = new ArrayList<Node>();

        TaskListItemPlacement taskListItemPlacement = formatOptions.taskListItemPlacement;
        if (taskListItemPlacement != TaskListItemPlacement.AS_IS) {
            ArrayList<Node> incompleteTasks = new ArrayList<Node>();
            ArrayList<Node> completeItems = new ArrayList<Node>();
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

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new TaskListNodeFormatter(options);
        }
    }
}
