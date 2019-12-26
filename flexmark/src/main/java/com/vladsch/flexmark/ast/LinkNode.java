package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.DoNotLinkDecorate;
import com.vladsch.flexmark.util.ast.TextContainer;
import com.vladsch.flexmark.util.collection.BitFieldSet;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;

public abstract class LinkNode extends LinkNodeBase implements DoNotLinkDecorate, TextContainer {

    public LinkNode() {
    }

    public LinkNode(BasedSequence chars) {
        super(chars);
    }

    @Override
    public boolean collectText(@NotNull SequenceBuilder out, int flags) {
        int urlType = flags & F_LINK_TEXT_TYPE;

        BasedSequence url;

        switch (urlType) {
            case F_LINK_PAGE_REF:
                url = getPageRef();
                break;
            case F_LINK_ANCHOR:
                url = getAnchorRef();
                break;
            case F_LINK_URL:
                url = getUrl();
                break;

            default:
            case F_LINK_TEXT:
                return true;
        }

        if (BitFieldSet.any(flags, F_NODE_TEXT)) {
            out.append(url);
        } else {
            ReplacedTextMapper textMapper = new ReplacedTextMapper(url);
            BasedSequence unescaped = Escaping.unescape(url, textMapper);
            BasedSequence percentDecoded = Escaping.percentEncodeUrl(unescaped, textMapper);
            out.append(percentDecoded);
        }

        return false;
    }
}
