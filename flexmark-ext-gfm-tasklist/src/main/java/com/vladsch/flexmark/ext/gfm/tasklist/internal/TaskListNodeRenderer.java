package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItem;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.internal.ListOptions;
import com.vladsch.flexmark.internal.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TaskListNodeRenderer implements NodeRenderer {
    final static public AttributablePart TASK_ITEM_PARAGRAPH = new AttributablePart("TASK_ITEM_PARAGRAPH");
    
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
            if (!itemClass.isEmpty()) html.attr("class", itemClass);
            html.srcPos(node.getFirstChild() != null ? node.getFirstChild().getChars() : node.getChars()).withAttr(CoreNodeRenderer.TIGHT_LIST_ITEM).withCondIndent().tagLine("li", () -> {
                html.raw(node.isItemDoneMarker() ? doneMarker : notDoneMarker);
                context.renderChildren(node);
            });
        } else {
            if (!looseItemClass.isEmpty()) html.attr("class", looseItemClass);
            html.withAttr(CoreNodeRenderer.LOOSE_LIST_ITEM).tagIndent("li", () -> {
                if (!paragraphClass.isEmpty()) html.attr("class", paragraphClass);
                html.srcPos(node.getFirstChild() != null ? node.getFirstChild().getChars() : node.getChars()).withAttr(TASK_ITEM_PARAGRAPH).tagLine("p", () -> {
                    html.raw(node.isItemDoneMarker() ? doneMarker : notDoneMarker);
                    context.renderChildren(node);
                });
            });
        }
    }
}
