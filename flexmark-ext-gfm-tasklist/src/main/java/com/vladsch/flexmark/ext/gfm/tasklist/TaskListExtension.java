package com.vladsch.flexmark.ext.gfm.tasklist;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.gfm.tasklist.internal.TaskListItemBlockPreProcessor;
import com.vladsch.flexmark.ext.gfm.tasklist.internal.TaskListNodeFormatter;
import com.vladsch.flexmark.ext.gfm.tasklist.internal.TaskListNodeRenderer;
import com.vladsch.flexmark.formatter.internal.Formatter;
import com.vladsch.flexmark.formatter.internal.NodeFormatter;
import com.vladsch.flexmark.formatter.internal.NodeFormatterFactory;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.collection.DynamicDefaultKey;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

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
public class TaskListExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, Formatter.FormatterExtension {
    // for webview use "<span class=\"taskitem\">" + (node.isDone() ? "X" : "O") + "</span>"
    // for swing use ""
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

    // formatting options
    public static final DataKey<TaskListItemCase> FORMAT_LIST_ITEM_CASE = new DataKey<>("FORMAT_LIST_ITEM_CASE", TaskListItemCase.AS_IS);
    public static final DataKey<TaskListItemPlacement> FORMAT_LIST_ITEM_PLACEMENT = new DataKey<>("FORMAT_LIST_ITEM_PLACEMENT", TaskListItemPlacement.AS_IS);

    private TaskListExtension() {
    }

    public static Extension create() {
        return new TaskListExtension();
    }

    @Override
    public void extend(final Formatter.Builder builder) {
        builder.nodeFormatterFactory(new NodeFormatterFactory() {
            @Override
            public NodeFormatter create(DataHolder options) {
                return new TaskListNodeFormatter(options);
            }
        });
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {
        ListOptions.addItemMarkerSuffixes(options, "[ ]", "[x]", "[X]" );
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.blockPreProcessorFactory(new TaskListItemBlockPreProcessor.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        switch (rendererType) {
            case "HTML":
                rendererBuilder.nodeRendererFactory(new TaskListNodeRenderer.Factory());
                break;

            case "JIRA":
            case "YOUTRACK":
                break;
        }
    }
}
