package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.util.data.DataHolder;

public interface ParagraphItemContainer {
  boolean isParagraphInTightListItem(Paragraph node);

  boolean isItemParagraph(Paragraph node);

  boolean isParagraphWrappingDisabled(Paragraph node, ListOptions listOptions, DataHolder options);
}
