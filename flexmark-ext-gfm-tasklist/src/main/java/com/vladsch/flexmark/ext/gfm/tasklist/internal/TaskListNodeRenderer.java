package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItem;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class TaskListNodeRenderer implements NodeRenderer {
    final public static AttributablePart TASK_ITEM_PARAGRAPH = new AttributablePart("TASK_ITEM_PARAGRAPH");

    final String doneMarker;
    final String notDoneMarker;
    final private String tightItemClass;
    final private String looseItemClass;
    final private String itemDoneClass;
    final private String itemNotDoneClass;
    final String paragraphClass;
    final private ListOptions listOptions;

    public TaskListNodeRenderer(DataHolder options) {
        doneMarker = TaskListExtension.ITEM_DONE_MARKER.get(options);
        notDoneMarker = TaskListExtension.ITEM_NOT_DONE_MARKER.get(options);
        tightItemClass = TaskListExtension.TIGHT_ITEM_CLASS.get(options);
        looseItemClass = TaskListExtension.LOOSE_ITEM_CLASS.get(options);
        itemDoneClass = TaskListExtension.ITEM_DONE_CLASS.get(options);
        itemNotDoneClass = TaskListExtension.ITEM_NOT_DONE_CLASS.get(options);
        paragraphClass = TaskListExtension.PARAGRAPH_CLASS.get(options);
        listOptions = ListOptions.get(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(TaskListItem.class, TaskListNodeRenderer.this::render));
        return set;
    }

    void render(TaskListItem node, NodeRendererContext context, HtmlWriter html) {
        BasedSequence sourceText = context.getHtmlOptions().sourcePositionParagraphLines || node.getFirstChild() == null ? node.getChars() : node.getFirstChild().getChars();
        String itemDoneStatusClass = node.isItemDoneMarker() ? itemDoneClass : itemNotDoneClass;
        if (listOptions.isTightListItem(node)) {
            if (!tightItemClass.isEmpty()) html.attr("class", tightItemClass);
            if (!itemDoneStatusClass.isEmpty() && !itemDoneStatusClass.equals(tightItemClass)) html.attr("class", itemDoneStatusClass);
            html.srcPos(sourceText.getStartOffset(), sourceText.getEndOffset()).withAttr(CoreNodeRenderer.TIGHT_LIST_ITEM).withCondIndent().tagLine("li", () -> {
                html.raw(node.isItemDoneMarker() ? doneMarker : notDoneMarker);
                context.renderChildren(node);
            });
        } else {
            if (!looseItemClass.isEmpty()) html.attr("class", looseItemClass);
            if (!itemDoneStatusClass.isEmpty() && !itemDoneStatusClass.equals(looseItemClass)) html.attr("class", itemDoneStatusClass);
            html.withAttr(CoreNodeRenderer.LOOSE_LIST_ITEM).tagIndent("li", () -> {
                if (!paragraphClass.isEmpty()) html.attr("class", paragraphClass);
                html.srcPos(sourceText.getStartOffset(), sourceText.getEndOffset()).withAttr(TASK_ITEM_PARAGRAPH).tagLine("p", () -> {
                    html.raw(node.isItemDoneMarker() ? doneMarker : notDoneMarker);
                    context.renderChildren(node);
                });
            });
        }
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new TaskListNodeRenderer(options);
        }
    }
}
