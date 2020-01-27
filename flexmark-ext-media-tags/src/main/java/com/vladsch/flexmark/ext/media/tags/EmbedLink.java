package com.vladsch.flexmark.ext.media.tags;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ext.media.tags.internal.AbstractMediaLink;

public class EmbedLink extends AbstractMediaLink {

    final public static String PREFIX = "!E";
    final private static String TYPE = "Embed";

    public EmbedLink() {
        super(PREFIX, TYPE);
    }

    public EmbedLink(Link other) {
        super(PREFIX, TYPE, other);
    }

    // This class leaves room for specialization, should we need it.
    // Additionally, it makes managing different Node types easier for users.
}
