package com.vladsch.flexmark.ext.media.tags;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ext.media.tags.internal.AbstractMediaLink;

public class AudioLink extends AbstractMediaLink {

    final public static String PREFIX = "!A";
    final private static String TYPE = "Audio";

    public AudioLink() {
        super(PREFIX, TYPE);
    }

    public AudioLink(Link other) {
        super(PREFIX, TYPE, other);
    }

    // This class leaves room for specialization, should we need it.
    // Additionally, it makes managing different Node types easier for users.
}
