package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItem;
import com.vladsch.flexmark.formatter.CustomNodeFormatter;
import com.vladsch.flexmark.formatter.internal.*;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class TaskListNodeFormatter implements NodeFormatter {
    private final FormatOptions myOptions;

    public TaskListNodeFormatter(DataHolder options) {
        myOptions = new FormatOptions(options);
    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        //noinspection unchecked
        HashSet<NodeFormattingHandler<?>> set = new HashSet<>();
        set.add(
                new NodeFormattingHandler<>(TaskListItem.class, new CustomNodeFormatter<TaskListItem>() {
                    @Override
                    public void render(TaskListItem node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TaskListNodeFormatter.this.render(node, context, markdown);
                    }
                })
        );

        return set;
    }

    private void render(final TaskListItem node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        markdown.append(node.getChars());
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new TaskListNodeFormatter(options);
        }
    }
}
