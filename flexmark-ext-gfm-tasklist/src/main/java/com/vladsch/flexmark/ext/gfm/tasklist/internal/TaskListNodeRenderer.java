package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItem;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TaskListNodeRenderer implements NodeRenderer {
    public static final AttributablePart TASK_ITEM_PARAGRAPH = new AttributablePart("TASK_ITEM_PARAGRAPH");

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
        //noinspection unchecked
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(
                new NodeRenderingHandler<>(TaskListItem.class, new CustomNodeRenderer<TaskListItem>() {
                    @Override
                    public void render(TaskListItem node, NodeRendererContext context, HtmlWriter html) {
                        TaskListNodeRenderer.this.render(node, context, html);
                    }
                })
        );

        return set;
    }

    private void render(final TaskListItem node, final NodeRendererContext context, final HtmlWriter html) {
        final BasedSequence sourceText = context.getHtmlOptions().sourcePositionParagraphLines || node.getFirstChild() == null ? node.getChars() : node.getFirstChild().getChars();
        if (listOptions.isTightListItem(node)) {
            if (!itemClass.isEmpty()) html.attr("class", itemClass);
            html.srcPos(sourceText.getStartOffset(), sourceText.getEndOffset()).withAttr(CoreNodeRenderer.TIGHT_LIST_ITEM).withCondIndent().tagLine("li", new Runnable() {
                @Override
                public void run() {
                    html.raw(node.isItemDoneMarker() ? TaskListNodeRenderer.this.doneMarker : TaskListNodeRenderer.this.notDoneMarker);
                    context.renderChildren(node);
                }
            });
        } else {
            if (!looseItemClass.isEmpty()) html.attr("class", looseItemClass);
            html.withAttr(CoreNodeRenderer.LOOSE_LIST_ITEM).tagIndent("li", new Runnable() {
                @Override
                public void run() {
                    if (!TaskListNodeRenderer.this.paragraphClass.isEmpty()) html.attr("class", TaskListNodeRenderer.this.paragraphClass);
                    html.srcPos(sourceText.getStartOffset(), sourceText.getEndOffset()).withAttr(TASK_ITEM_PARAGRAPH).tagLine("p", new Runnable() {
                        @Override
                        public void run() {
                            html.raw(node.isItemDoneMarker() ? TaskListNodeRenderer.this.doneMarker : TaskListNodeRenderer.this.notDoneMarker);
                            context.renderChildren(node);
                        }
                    });
                }
            });
        }
    }
}
