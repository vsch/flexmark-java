package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.Zzzzzz;
import com.vladsch.flexmark.parser.LinkRefProcessor;
import com.vladsch.flexmark.parser.LinkRefProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public class ZzzzzzLinkRefProcessor implements LinkRefProcessor {
    static final boolean WANT_EXCLAMATION_PREFIX = false;
    static final int BRACKET_NESTING_LEVEL = 1;

    final private ZzzzzzOptions options;

    public ZzzzzzLinkRefProcessor(Document document) {
        this.options = new ZzzzzzOptions(document);
    }

    @Override
    public boolean getWantExclamationPrefix() {
        return WANT_EXCLAMATION_PREFIX;
    }

    @Override
    public int getBracketNestingLevel() {
        return BRACKET_NESTING_LEVEL;
    }

    @Override
    public boolean isMatch(@NotNull BasedSequence nodeChars) {
        return nodeChars.length() >= 4 && nodeChars.charAt(0) == '[' && nodeChars.charAt(1) == '[' && nodeChars.endCharAt(1) == ']' && nodeChars.endCharAt(2) == ']';
    }

    @NotNull
    @Override
    public BasedSequence adjustInlineText(@NotNull Document document, @NotNull Node node) {
        // nothing to do, our prefixes are stripped out of a link ref
        return node.getChars();
    }

    @Override
    public boolean allowDelimiters(@NotNull BasedSequence chars, @NotNull Document document, @NotNull Node node) {
        return true;
    }

    @Override
    public void updateNodeElements(@NotNull Document document, @NotNull Node node) {

    }

    @NotNull
    @Override
    public Node createNode(@NotNull BasedSequence nodeChars) {
        return new Zzzzzz(nodeChars, options.zzzzzzOption2);
    }

    public static class Factory implements LinkRefProcessorFactory {
        @NotNull
        @Override
        public LinkRefProcessor apply(@NotNull Document document) {
            return new ZzzzzzLinkRefProcessor(document);
        }

        @Override
        public boolean getWantExclamationPrefix(@NotNull DataHolder options) {
            return WANT_EXCLAMATION_PREFIX;
        }

        @Override
        public int getBracketNestingLevel(@NotNull DataHolder options) {
            return BRACKET_NESTING_LEVEL;
        }
    }
}
