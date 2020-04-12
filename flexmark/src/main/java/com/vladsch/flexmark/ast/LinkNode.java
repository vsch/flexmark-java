package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.DoNotLinkDecorate;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.TextContainer;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;

public abstract class LinkNode extends LinkNodeBase implements DoNotLinkDecorate, TextContainer {

    public LinkNode() {
    }

    public LinkNode(BasedSequence chars) {
        super(chars);
    }

    @Override
    public boolean collectText(ISequenceBuilder<? extends ISequenceBuilder<?, BasedSequence>, BasedSequence> out, int flags, NodeVisitor nodeVisitor) {
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

            case F_LINK_NODE_TEXT:
                url = BasedSequence.NULL; // not used
                break;

            default:
            case F_LINK_TEXT:
                return true;
        }

        if (urlType == F_LINK_NODE_TEXT) {
            out.append(getChars());
        } else {
            ReplacedTextMapper textMapper = new ReplacedTextMapper(url);
            BasedSequence unescaped = Escaping.unescape(url, textMapper);
            BasedSequence percentDecoded = Escaping.percentDecodeUrl(unescaped, textMapper);
            out.append(percentDecoded);
        }

        return false;
    }
}
