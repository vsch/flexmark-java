package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.TextContainer;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import org.jetbrains.annotations.NotNull;

public class WikiNode extends Node implements DoNotDecorate, TextContainer {
    final public static char SEPARATOR_CHAR = '|';
    
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence link = BasedSequence.NULL;
    protected BasedSequence pageRef = BasedSequence.NULL;
    protected BasedSequence anchorMarker = BasedSequence.NULL;
    protected BasedSequence anchorRef = BasedSequence.NULL;
    protected BasedSequence textSeparatorMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected final boolean linkIsFirst;

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        if (linkIsFirst) {
            return new BasedSequence[] {
                    openingMarker,
                    link,
                    pageRef,
                    anchorMarker,
                    anchorRef,
                    textSeparatorMarker,
                    text,
                    closingMarker
            };
        } else {
            return new BasedSequence[] {
                    openingMarker,
                    text,
                    textSeparatorMarker,
                    link,
                    pageRef,
                    anchorMarker,
                    anchorRef,
                    closingMarker
            };
        }
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        if (linkIsFirst) {
            segmentSpanChars(out, openingMarker, "linkOpen");
            segmentSpanChars(out, text, "text");
            segmentSpanChars(out, textSeparatorMarker, "textSep");
            segmentSpanChars(out, link, "link");
            if (pageRef.isNotNull()) segmentSpanChars(out, pageRef, "pageRef");
            if (anchorMarker.isNotNull()) segmentSpanChars(out, anchorMarker, "anchorMarker");
            if (anchorRef.isNotNull()) segmentSpanChars(out, anchorRef, "anchorRef");
            segmentSpanChars(out, closingMarker, "linkClose");
        } else {
            segmentSpanChars(out, openingMarker, "linkOpen");
            segmentSpanChars(out, link, "link");
            if (pageRef.isNotNull()) segmentSpanChars(out, pageRef, "pageRef");
            if (anchorMarker.isNotNull()) segmentSpanChars(out, anchorMarker, "anchorMarker");
            if (anchorRef.isNotNull()) segmentSpanChars(out, anchorRef, "anchorRef");
            segmentSpanChars(out, textSeparatorMarker, "textSep");
            segmentSpanChars(out, text, "text");
            segmentSpanChars(out, closingMarker, "linkClose");
        }
    }

    public boolean isLinkIsFirst() {
        return linkIsFirst;
    }

    public WikiNode(boolean linkIsFirst) {
        this.linkIsFirst = linkIsFirst;
    }

    public WikiNode(BasedSequence chars, boolean linkIsFirst, boolean allowAnchors, boolean canEscapePipe, boolean canEscapeAnchor) {
        super(chars);
        this.linkIsFirst = linkIsFirst;
        setLinkChars(chars, allowAnchors, canEscapePipe, canEscapeAnchor);
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getPageRef() {
        return pageRef;
    }

    public void setPageRef(BasedSequence pageRef) {
        this.pageRef = pageRef;
    }

    public BasedSequence getTextSeparatorMarker() {
        return textSeparatorMarker;
    }

    public void setTextSeparatorMarker(BasedSequence textSeparatorMarker) {
        this.textSeparatorMarker = textSeparatorMarker;
    }

    public BasedSequence getText() {
        return text;
    }

    public void setText(BasedSequence text) {
        this.text = text;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }

    public BasedSequence getAnchorMarker() {
        return anchorMarker;
    }

    public void setAnchorMarker(BasedSequence anchorMarker) {
        this.anchorMarker = anchorMarker;
    }

    public BasedSequence getAnchorRef() {
        return anchorRef;
    }

    public void setAnchorRef(BasedSequence anchorRef) {
        this.anchorRef = anchorRef;
    }

    public BasedSequence getLink() {
        return link;
    }

    public void setLink(BasedSequence linkChars, boolean allowAnchors, boolean canEscapeAnchor) {
        // now parse out the # from the link
        this.link = linkChars;

        if (!allowAnchors) {
            this.pageRef = linkChars;
        } else {
            int pos = -1;
            do {
                pos = linkChars.indexOf('#', pos + 1);
            } while (pos != -1 && canEscapeAnchor && (pos > 0 && linkChars.charAt(pos - 1) == '\\' && (linkChars.subSequence(0, pos).countTrailing(CharPredicate.BACKSLASH) & 1) == 1));

            if (pos < 0) {
                this.pageRef = linkChars;
            } else {
                this.pageRef = linkChars.subSequence(0, pos);
                this.anchorMarker = linkChars.subSequence(pos, pos + 1);
                this.anchorRef = linkChars.subSequence(pos + 1);
            }
        }
    }

    public void setLinkChars(BasedSequence linkChars, boolean allowAnchors, boolean canEscapePipe, boolean canEscapeAnchor) {
        int length = linkChars.length();
        final int start = this instanceof WikiImage ? 3 : 2;
        openingMarker = linkChars.subSequence(0, start);
        closingMarker = linkChars.subSequence(length - 2, length);

        int pos;
        if (linkIsFirst) {
            pos = linkChars.length() - 2;
            do {
                pos = linkChars.lastIndexOf(SEPARATOR_CHAR, pos - 1);
            } while (pos != -1 && canEscapePipe && (pos > 0 && linkChars.charAt(pos - 1) == '\\' && (linkChars.subSequence(0, pos).countTrailing(CharPredicate.BACKSLASH) & 1) == 1));
        } else {
            pos = -1;
            do {
                pos = linkChars.indexOf(SEPARATOR_CHAR, pos + 1);
            } while (pos != -1 && canEscapePipe && (pos > 0 && linkChars.charAt(pos - 1) == '\\' && (linkChars.subSequence(0, pos).countTrailing(CharPredicate.BACKSLASH) & 1) == 1));
        }

        BasedSequence link;
        if (pos < 0) {
            link = linkChars.subSequence(start, length - 2);
        } else {
            textSeparatorMarker = linkChars.subSequence(pos, pos + 1);
            if (linkIsFirst) {
                link = linkChars.subSequence(start, pos);
                text = linkChars.subSequence(pos + 1, length - 2);
            } else {
                text = linkChars.subSequence(start, pos);
                link = linkChars.subSequence(pos + 1, length - 2);
            }
        }

        setLink(link, allowAnchors, canEscapeAnchor);

        if (text.isNull() && allowAnchors && !anchorMarker.isNull()) {
            // have anchor ref, remove it from text
            text = pageRef;
        }
    }

    @Override
    public boolean collectText(ISequenceBuilder<? extends ISequenceBuilder<?, BasedSequence>, BasedSequence> out, int flags) {
        int urlType = flags & F_LINK_TEXT_TYPE;

        BasedSequence text;

        switch (urlType) {
            case F_LINK_PAGE_REF:
                text = getPageRef();
                break;
            case F_LINK_ANCHOR:
                text = getAnchorRef();
                break;
            case F_LINK_URL:
                text = getLink();
                break;

            case F_LINK_NODE_TEXT:
                text = BasedSequence.NULL; // not used
                break;

            default:
            case F_LINK_TEXT:
                return true;
        }

        if (urlType == F_LINK_NODE_TEXT) {
            out.append(getChars());
        } else {
            ReplacedTextMapper textMapper = new ReplacedTextMapper(text);
            BasedSequence unescaped = Escaping.unescape(text, textMapper);
            out.append(unescaped);
        }

        return false;
    }
}
