package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.formatter.options.*;

public class MarkdownCodeStyleSettings {
    public boolean USE_ACTUAL_CHAR_WIDTH = true;

    // headers
    public boolean SETEXT_HEADER_EQUALIZE_MARKER = true;
    public EqualizeTrailingMarker ATX_HEADER_TRAILING_MARKER = EqualizeTrailingMarker.AS_IS;
    public DiscretionaryText SPACE_AFTER_ATX_MARKER = DiscretionaryText.ADD;

    public boolean ESCAPE_SPECIAL_CHARS_ON_WRAP = true;
    public boolean UNESCAPE_SPECIAL_CHARS_ON_WRAP = true;

    // text formatting
    public boolean KEEP_HARD_BREAKS = true;
    public boolean KEEP_EMBEDDED_SPACES = false;
    public boolean WRAP_TEXT = true;
    public int KEEP_BLANK_LINES = 2;
    public TrailingSpaces KEEP_TRAILING_SPACES = TrailingSpaces.KEEP_LINE_BREAK;
    public TrailingSpaces FLEXMARK_EXAMPLE_KEEP_TRAILING_SPACES = TrailingSpaces.KEEP_ALL;
    public TrailingSpaces CODE_KEEP_TRAILING_SPACES = TrailingSpaces.KEEP_ALL;
    public ContinuationIndent CONTINUATION_ALIGNMENT = ContinuationIndent.ALIGN_TO_FIRST;

    // list formatting
    public boolean LIST_ADD_BLANK_LINE_BEFORE = false;
    public boolean LIST_ALIGN_FIRST_LINE_TEXT = false;
    public boolean LIST_ALIGN_CHILD_BLOCKS = true;
    public boolean LIST_RIGHT_ALIGN_NUMERIC = false;
    public boolean LIST_RENUMBER_ITEMS = true;
    public TaskListItemCase TASK_LIST_ITEM_CASE = TaskListItemCase.AS_IS;
    public TaskListItemPlacement TASK_LIST_ITEM_PLACEMENT = TaskListItemPlacement.AS_IS;
    public BulletListItemMarker BULLET_LIST_ITEM_MARKER = BulletListItemMarker.ANY;
    public BulletListItemMarker NEW_BULLET_LIST_ITEM_MARKER = BulletListItemMarker.DASH;
    public ListSpacing LIST_SPACING = ListSpacing.AS_IS;

    // verbatim and code fence
    public boolean VERBATIM_MINIMIZE_INDENT = false;
    public boolean CODE_FENCE_MINIMIZE_INDENT = false;
    public boolean CODE_FENCE_MATCH_CLOSING_MARKER = false;
    public boolean CODE_FENCE_SPACE_BEFORE_INFO = false;
    public int CODE_FENCE_MARKER_LENGTH = 3;
    public CodeFenceMarker CODE_FENCE_MARKER_TYPE = CodeFenceMarker.ANY;

    // definition marker
    public int DEFINITION_MARKER_SPACES = 3;
    public DefinitionMarker DEFINITION_MARKER_TYPE = DefinitionMarker.ANY;

    // block quote
    public BlockQuoteMarker BLOCK_QUOTE_FIRST_LINE_MARKERS = BlockQuoteMarker.AS_IS;
    public BlockQuoteContinuationMarker BLOCK_QUOTE_CONTINUATION_MARKERS = BlockQuoteContinuationMarker.ADD_AS_FIRST;

    public ElementPlacement ABBREVIATIONS_PLACEMENT = ElementPlacement.AS_IS;
    public ElementPlacement FOOTNOTE_PLACEMENT = ElementPlacement.AS_IS;
    public ElementPlacement REFERENCE_PLACEMENT = ElementPlacement.AS_IS;

    public ElementPlacementSort ABBREVIATIONS_SORT = ElementPlacementSort.AS_IS;
    public ElementPlacementSort FOOTNOTE_SORT = ElementPlacementSort.AS_IS;
    public ElementPlacementSort REFERENCE_SORT = ElementPlacementSort.AS_IS;

    public KeepAtStartOfLine KEEP_AT_START_IMAGE_LINKS = KeepAtStartOfLine.JEKYLL;
    public KeepAtStartOfLine KEEP_AT_START_EXPLICIT_LINK = KeepAtStartOfLine.JEKYLL;
}
