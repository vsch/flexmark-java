package com.vladsch.flexmark.ext.media.tags;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ext.media.tags.internal.AbstractMediaLink;

public class EmbedLink extends AbstractMediaLink {

    public static final String PREFIX = "!E";
    private static final String TYPE = "Embed";

    public EmbedLink() {
        super(PREFIX, TYPE);
    }

    public EmbedLink(Link other) {
        super(PREFIX, TYPE, other);
    }

    // This class leaves room for specialization, should we need it.
    // Additionally, it makes managing different Node types easier for users.
}
