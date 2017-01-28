package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.util.options.DataHolder;

public interface ParagraphItemContainer {
    boolean isItemParagraph(Paragraph node);
    boolean isParagraphWrappingDisabled(Paragraph node, ListOptions listOptions, DataHolder options);
}
