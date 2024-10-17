package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.ext.tables.internal.TableJiraRenderer;
import com.vladsch.flexmark.ext.tables.internal.TableNodeFormatter;
import com.vladsch.flexmark.ext.tables.internal.TableNodeRenderer;
import com.vladsch.flexmark.ext.tables.internal.TableParagraphPreProcessor;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for GFM tables using "|" pipes (GitHub Flavored Markdown).
 *
 * <p>Create it with {@link #create()} and then configure it on the builders
 *
 * <p>The parsed tables are turned into {@link TableBlock} blocks.
 */
public class TablesExtension
    implements Parser.ParserExtension,
        HtmlRenderer.HtmlRendererExtension,
        Formatter.FormatterExtension {
  public static final DataKey<Boolean> TRIM_CELL_WHITESPACE =
      new DataKey<>("TRIM_CELL_WHITESPACE", Boolean.TRUE);
  public static final DataKey<Integer> MIN_SEPARATOR_DASHES =
      new DataKey<>("MIN_SEPARATOR_DASHES", 3);
  public static final DataKey<Integer> MAX_HEADER_ROWS =
      new DataKey<>("MAX_HEADER_ROWS", Integer.MAX_VALUE);
  public static final DataKey<Integer> MIN_HEADER_ROWS = new DataKey<>("MIN_HEADER_ROWS", 0);
  public static final DataKey<Boolean> APPEND_MISSING_COLUMNS =
      new DataKey<>("APPEND_MISSING_COLUMNS", Boolean.FALSE);
  public static final DataKey<Boolean> DISCARD_EXTRA_COLUMNS =
      new DataKey<>("DISCARD_EXTRA_COLUMNS", Boolean.FALSE);
  public static final DataKey<Boolean> COLUMN_SPANS = new DataKey<>("COLUMN_SPANS", Boolean.TRUE);
  public static final DataKey<Boolean> HEADER_SEPARATOR_COLUMN_MATCH =
      new DataKey<>("HEADER_SEPARATOR_COLUMN_MATCH", Boolean.FALSE);
  public static final DataKey<String> CLASS_NAME = new DataKey<>("CLASS_NAME", "");
  public static final DataKey<Boolean> WITH_CAPTION = new DataKey<>("WITH_CAPTION", Boolean.TRUE);

  public static TablesExtension create() {
    return new TablesExtension();
  }

  @Override
  public void extend(Formatter.Builder formatterBuilder) {
    formatterBuilder.nodeFormatterFactory(new TableNodeFormatter.Factory());
  }

  @Override
  public void rendererOptions(@NotNull MutableDataHolder options) {}

  @Override
  public void parserOptions(MutableDataHolder options) {}

  @Override
  public void extend(Parser.Builder parserBuilder) {
    parserBuilder.paragraphPreProcessorFactory(TableParagraphPreProcessor.factory());
  }

  @Override
  public void extend(
      @NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
    if (htmlRendererBuilder.isRendererType("HTML")) {
      htmlRendererBuilder.nodeRendererFactory(new TableNodeRenderer.Factory());
    } else if (htmlRendererBuilder.isRendererType("JIRA")) {
      htmlRendererBuilder.nodeRendererFactory(new TableJiraRenderer.Factory());
    }
  }
}
