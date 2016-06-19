package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItem;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItemMarker;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.node.Node;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TaskListNodeRenderer implements NodeRenderer {
    private final NodeRendererContext context;
    private final HtmlWriter html;
    private final String doneMarker;
    private final String notDoneMarker;
    private final String itemClass;
    private final String looseItemClass;
    private final String paragraphClass;

    public TaskListNodeRenderer(NodeRendererContext context) {
        this.context = context;
        this.html = context.getHtmlWriter();
        this.doneMarker = context.getOptions().get(TaskListExtension.ITEM_DONE_MARKER);
        this.notDoneMarker = context.getOptions().get(TaskListExtension.ITEM_NOT_DONE_MARKER);
        this.itemClass = context.getOptions().get(TaskListExtension.ITEM_CLASS);
        this.looseItemClass = context.getOptions().get(TaskListExtension.LOOSE_ITEM_CLASS);
        this.paragraphClass = context.getOptions().get(TaskListExtension.PARAGRAPH_CLASS);
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<Class<? extends Node>>(Arrays.asList(
                TaskListItem.class,
                TaskListItemMarker.class
        ));
    }

    @Override
    public void render(Node node) {
        if (node instanceof TaskListItem) {
            render((TaskListItem) node);
        } else if (node instanceof TaskListItemMarker) {
            render((TaskListItemMarker) node);
        }
    }

    private void render(TaskListItem taskItem) {
        if (taskItem.getFirstChild() == null || taskItem.isInTightList()) {
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

    private void render(TaskListItemMarker taskListItemMarker) {
        html.raw(taskListItemMarker.isItemDoneMarker() ? doneMarker : notDoneMarker);
    }
}
