package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItem;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.internal.ListOptions;
import com.vladsch.flexmark.internal.util.options.DataHolder;

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
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(TaskListItem.class, this::render)
        ));
    }

    private void render(TaskListItem node, NodeRendererContext context, HtmlWriter html) {
        if (listOptions.isTightListItem(node)) {
            html.withAttr().attr("class", itemClass).withCondIndent().tagLine("li", () -> {
                html.raw(node.isItemDoneMarker() ? doneMarker : notDoneMarker);
                context.renderChildren(node);
            });
        } else {
            html.withAttr().attr("class", looseItemClass).tagIndent("li", () -> {
                html.withAttr().attr("class", paragraphClass).tagLine("p", () -> {
                    html.raw(node.isItemDoneMarker() ? doneMarker : notDoneMarker);
                    context.renderChildren(node);
                });
            });
        }
    }
}
