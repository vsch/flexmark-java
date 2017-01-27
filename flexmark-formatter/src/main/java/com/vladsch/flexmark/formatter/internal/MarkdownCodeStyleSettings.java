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
    public TrailingSpaces KEEP_TRAILING_SPACES = TrailingSpaces.KEEP_LINE_BREAK;
    public TrailingSpaces FLEXMARK_EXAMPLE_KEEP_TRAILING_SPACES = TrailingSpaces.KEEP_ALL;
    public TrailingSpaces CODE_KEEP_TRAILING_SPACES = TrailingSpaces.KEEP_ALL;
    public ContinuationIndent CONTINUATION_ALIGNMENT = ContinuationIndent.ALIGN_TO_FIRST;
}
