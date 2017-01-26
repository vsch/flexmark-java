package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.formatter.options.*;

public class MarkdownCodeStyleSettings {
    public boolean USE_ACTUAL_CHAR_WIDTH = true;

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

    // definition marker
    public int DEFINITION_MARKER_SPACES = 3;
    public DefinitionMarker DEFINITION_MARKER_TYPE = DefinitionMarker.ANY;

    public ElementPlacement ABBREVIATIONS_PLACEMENT = ElementPlacement.AS_IS;
    public ElementPlacement FOOTNOTE_PLACEMENT = ElementPlacement.AS_IS;
    public ElementPlacement REFERENCE_PLACEMENT = ElementPlacement.AS_IS;

    public ElementPlacementSort ABBREVIATIONS_SORT = ElementPlacementSort.AS_IS;
    public ElementPlacementSort FOOTNOTE_SORT = ElementPlacementSort.AS_IS;
    public ElementPlacementSort REFERENCE_SORT = ElementPlacementSort.AS_IS;

    public KeepAtStartOfLine KEEP_AT_START_IMAGE_LINKS = KeepAtStartOfLine.JEKYLL;
    public KeepAtStartOfLine KEEP_AT_START_EXPLICIT_LINK = KeepAtStartOfLine.JEKYLL;
}
