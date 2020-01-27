package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.ast.HtmlCommentBlock;
import com.vladsch.flexmark.ast.HtmlInnerBlockComment;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.formatter.FormatterOptions;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class FormatControlProcessor {
    final public static String OPEN_COMMENT = "<!--";
    final public static String CLOSE_COMMENT = "-->";

    final private String formatterOnTag;
    final private String formatterOffTag;
    final private boolean formatterTagsEnabled;

    private boolean myFormatterOff = false;
    private boolean justTurnedOffFormatting = false;
    private boolean justTurnedOnFormatting = false;
    private boolean formatterTagsAcceptRegexp;
    private volatile Pattern formatterOffPattern;
    private volatile Pattern formatterOnPattern;

    public FormatControlProcessor(@NotNull Document document, @Nullable DataHolder options) {
        FormatterOptions formatterOptions = new FormatterOptions(options);
        this.formatterOnTag = formatterOptions.formatterOnTag;
        this.formatterOffTag = formatterOptions.formatterOffTag;
        this.formatterTagsEnabled = formatterOptions.formatterTagsEnabled;
        this.formatterTagsAcceptRegexp = formatterOptions.formatterTagsAcceptRegexp;
    }

    public boolean isFormattingOff() {
        return myFormatterOff;
    }

    @Nullable
    public Pattern getFormatterOffPattern() {
        if (this.formatterOffPattern == null && formatterTagsEnabled && formatterTagsAcceptRegexp) {
            this.formatterOffPattern = this.getPatternOrDisableRegexp(formatterOffTag);
        }

        return this.formatterOffPattern;
    }

    @Nullable
    public Pattern getFormatterOnPattern() {
        if (this.formatterOffPattern == null && formatterTagsEnabled && formatterTagsAcceptRegexp) {
            this.formatterOnPattern = this.getPatternOrDisableRegexp(formatterOnTag);
        }

        return this.formatterOnPattern;
    }

    @Nullable
    private Pattern getPatternOrDisableRegexp(@NotNull String markerText) {
        try {
            return Pattern.compile(markerText);
        } catch (PatternSyntaxException var3) {
            formatterTagsAcceptRegexp = false;
            return null;
        }
    }

    public boolean isFormattingRegion() {
        return !myFormatterOff;
    }

    public String getFormatterOnTag() {
        return formatterOnTag;
    }

    public String getFormatterOffTag() {
        return formatterOffTag;
    }

    public boolean getFormatterTagsEnabled() {
        return formatterTagsEnabled;
    }

    public boolean getFormatterRegExEnabled() {
        return formatterTagsAcceptRegexp;
    }

    public boolean isJustTurnedOffFormatting() {
        return justTurnedOffFormatting;
    }

    public boolean isJustTurnedOnFormatting() {
        return justTurnedOnFormatting;
    }

    @Nullable
    private Boolean isFormatterOffTag(@Nullable CharSequence commentText) {
        if (commentText == null) return null;

        String text = commentText.toString().trim();
        text = text.substring(OPEN_COMMENT.length(), text.length() - CLOSE_COMMENT.length()).trim();

        if (formatterTagsAcceptRegexp && formatterOffPattern != null && formatterOnPattern != null) {
            if (formatterOnPattern.matcher(text).matches()) {
                return false;
            } else if (formatterOffPattern.matcher(text).matches()) {
                return true;
            }
        } else if (formatterTagsEnabled) {
            if (text.equals(formatterOnTag)) {
                return false;
            } else if (text.equals(formatterOffTag)) {
                return true;
            }
        }
        return null;
    }

    public void initializeFrom(@NotNull Node element) {
        myFormatterOff = !isFormattingRegion(element.getStartOffset(), element, true);
    }

    public void processFormatControl(@NotNull Node node) {
        justTurnedOffFormatting = false;
        justTurnedOnFormatting = false;

        if ((node instanceof HtmlCommentBlock || node instanceof HtmlInnerBlockComment) && formatterTagsEnabled) {
            // could be formatter control
            boolean formatterOff = myFormatterOff;
            Boolean isFormatterOff = isFormatterOffTag(node.getChars());
            if (isFormatterOff == null) return;

            myFormatterOff = isFormatterOff;

            if (!formatterOff && myFormatterOff) justTurnedOffFormatting = true;
            if (formatterOff && !myFormatterOff) justTurnedOnFormatting = true;
        }
    }

    private boolean isFormattingRegion(int offset, @NotNull Node node, boolean checkParent) {
        while (node != null) {
            if (node.getStartOffset() <= offset) {
                if (node instanceof Block && !(node instanceof Paragraph) && node.hasChildren()) {
                    Node lastChild = node.getLastChild();
                    return lastChild != null && isFormattingRegion(offset, lastChild, false);
                } else if (node instanceof HtmlCommentBlock || node instanceof HtmlInnerBlockComment) {
                    Boolean formatterOff = isFormatterOffTag(node.getChars());
                    if (formatterOff != null) return !formatterOff;
                }
            }

            if (node.getPrevious() == null && checkParent) {
                node = node.getParent();
                if (node instanceof Document) break;
                if (node != null) node = node.getPrevious();
            } else {
                node = node.getPrevious();
            }
        }
        return true;
    }

    public boolean isFormattingRegion(@NotNull Node node) {
        // find the first HTML comment with a formatter directive
        if (!formatterTagsEnabled || node.getStartOffset() == 0) return true;
        return isFormattingRegion(node.getStartOffset(), node, true);
    }
}
