package com.vladsch.flexmark.ext.gfm.tasklist;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.gfm.tasklist.internal.TaskListNodeRenderer;
import com.vladsch.flexmark.ext.gfm.tasklist.internal.TaskListParagraphPreProcessor;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.collection.DynamicDefaultKey;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

/**
 * Extension for GFM style task list items
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The bullet list items that begin with [ ], [x] or [X] are turned into TaskListItem nodes
 * </p>
 */
public class TaskListExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    // for webview use "<span class=\"taskitem\">" + (node.isDone() ? "X" : "O") + "</span>"
    // for swing use ""
    public static final DataKey<Boolean> CONVERT_ORDERED_LIST_ITEMS = new DataKey<>("CONVERT_ORDERED_LIST_ITEMS", true);
    public static final DataKey<String> ITEM_DONE_MARKER = new DataKey<>("ITEM_DONE_MARKER", "<input type=\"checkbox\" class=\"task-list-item-checkbox\" checked=\"checked\" disabled=\"disabled\" />");
    public static final DataKey<String> ITEM_NOT_DONE_MARKER = new DataKey<>("ITEM_NOT_DONE_MARKER", "<input type=\"checkbox\" class=\"task-list-item-checkbox\" disabled=\"disabled\" />");
    public static final DataKey<String> ITEM_CLASS = new DataKey<>("ITEM_CLASS", "task-list-item");
    public static final DataKey<String> LOOSE_ITEM_CLASS = new DynamicDefaultKey<>("LOOSE_ITEM_CLASS", new DataValueFactory<String>() {
        @Override
        public String create(DataHolder holder) {
            return ITEM_CLASS.getFrom(holder);
        }
    });
    public static final DataKey<String> PARAGRAPH_CLASS = new DataKey<>("PARAGRAPH_CLASS", "");

    private TaskListExtension() {
    }

    public static Extension create() {
        return new TaskListExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.paragraphPreProcessorFactory(new TaskListParagraphPreProcessor.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("JIRA") || rendererType.equals("YOUTRACK")) {
        } else if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
                @Override
                public NodeRenderer create(DataHolder options) {
                    return new TaskListNodeRenderer(options);
                }
            });
        }
    }
}
