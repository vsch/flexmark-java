package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItem;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.HashSet;
import java.util.Set;

public class TaskListNodeRenderer implements NodeRenderer {
    public static final AttributablePart TASK_ITEM_PARAGRAPH = new AttributablePart("TASK_ITEM_PARAGRAPH");

    final String doneMarker;
    final String notDoneMarker;
    private final String tightItemClass;
    private final String looseItemClass;
    private final String itemDoneClass;
    private final String itemNotDoneClass;
    final String paragraphClass;
    private final ListOptions listOptions;

    public TaskListNodeRenderer(DataHolder options) {
        doneMarker = options.get(TaskListExtension.ITEM_DONE_MARKER);
        notDoneMarker = options.get(TaskListExtension.ITEM_NOT_DONE_MARKER);
        tightItemClass = options.get(TaskListExtension.TIGHT_ITEM_CLASS);
        looseItemClass = options.get(TaskListExtension.LOOSE_ITEM_CLASS);
        itemDoneClass = options.get(TaskListExtension.ITEM_DONE_CLASS);
        itemNotDoneClass = options.get(TaskListExtension.ITEM_NOT_DONE_CLASS);
        paragraphClass = options.get(TaskListExtension.PARAGRAPH_CLASS);
        listOptions = ListOptions.getFrom(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        //noinspection unchecked
        HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
        set.add(
                new NodeRenderingHandler<TaskListItem>(TaskListItem.class, new CustomNodeRenderer<TaskListItem>() {
                    @Override
                    public void render(TaskListItem node, NodeRendererContext context, HtmlWriter html) {
                        TaskListNodeRenderer.this.render(node, context, html);
                    }
                })
        );

        return set;
    }

    void render(TaskListItem node, NodeRendererContext context, HtmlWriter html) {
        BasedSequence sourceText = context.getHtmlOptions().sourcePositionParagraphLines || node.getFirstChild() == null ? node.getChars() : node.getFirstChild().getChars();
        String itemDoneStatusClass = node.isItemDoneMarker() ? itemDoneClass : itemNotDoneClass;
        if (listOptions.isTightListItem(node)) {
            if (!tightItemClass.isEmpty()) html.attr("class", tightItemClass);
            if (!itemDoneStatusClass.isEmpty() && !itemDoneStatusClass.equals(tightItemClass)) html.attr("class", itemDoneStatusClass);
            html.srcPos(sourceText.getStartOffset(), sourceText.getEndOffset()).withAttr(CoreNodeRenderer.TIGHT_LIST_ITEM).withCondIndent().tagLine("li", new Runnable() {
                @Override
                public void run() {
                    html.raw(node.isItemDoneMarker() ? doneMarker : notDoneMarker);
                    context.renderChildren(node);
                }
            });
        } else {
            if (!looseItemClass.isEmpty()) html.attr("class", looseItemClass);
            if (!itemDoneStatusClass.isEmpty() && !itemDoneStatusClass.equals(looseItemClass)) html.attr("class", itemDoneStatusClass);
            html.withAttr(CoreNodeRenderer.LOOSE_LIST_ITEM).tagIndent("li", new Runnable() {
                @Override
                public void run() {
                    if (!paragraphClass.isEmpty()) html.attr("class", paragraphClass);
                    html.srcPos(sourceText.getStartOffset(), sourceText.getEndOffset()).withAttr(TASK_ITEM_PARAGRAPH).tagLine("p", new Runnable() {
                        @Override
                        public void run() {
                            html.raw(node.isItemDoneMarker() ? doneMarker : notDoneMarker);
                            context.renderChildren(node);
                        }
                    });
                }
            });
        }
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer apply(DataHolder options) {
            return new TaskListNodeRenderer(options);
        }
    }
}
