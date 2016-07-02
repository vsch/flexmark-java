package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItem;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItemMarker;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.internal.ListOptions;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.node.Node;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TaskListNodeRenderer implements NodeRenderer {
    private final String doneMarker;
    private final String notDoneMarker;
    private final String itemClass;
    private final String looseItemClass;
    private final String paragraphClass;
    private final ListOptions listOptions;

    public TaskListNodeRenderer(DataHolder options) {
        this.doneMarker = options.get(TaskListExtension.ITEM_DONE_MARKER);
        this.notDoneMarker = options.get(TaskListExtension.ITEM_NOT_DONE_MARKER);
        this.itemClass = options.get(TaskListExtension.ITEM_CLASS);
        this.looseItemClass = options.get(TaskListExtension.LOOSE_ITEM_CLASS);
        this.paragraphClass = options.get(TaskListExtension.PARAGRAPH_CLASS);
        this.listOptions = new ListOptions(options);
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<Class<? extends Node>>(Arrays.asList(
                TaskListItem.class,
                TaskListItemMarker.class
        ));
    }

    @Override
    public void render(NodeRendererContext context, HtmlWriter html, Node node) {
        if (node instanceof TaskListItem) {
            render(context, html, (TaskListItem) node);
        } else if (node instanceof TaskListItemMarker) {
            render(context, html, (TaskListItemMarker) node);
        }
    }

    private void render(NodeRendererContext context, HtmlWriter html, TaskListItem taskItem) {
        if (listOptions.isTightListItem(taskItem)) {
            html.withAttr().attr("class", itemClass).withCondIndent().tagLine("li", () -> {
                context.renderChildren(taskItem);
            });
        } else {
            html.withAttr().attr("class", looseItemClass).tagIndent("li", () -> {
                html.withAttr().attr("class", paragraphClass).tagLine("p", () -> {
                    context.renderChildren(taskItem);
                });
            });
        }
    }

    private void render(NodeRendererContext context, HtmlWriter html, TaskListItemMarker taskListItemMarker) {
        html.raw(taskListItemMarker.isItemDoneMarker() ? doneMarker : notDoneMarker);
    }
}
