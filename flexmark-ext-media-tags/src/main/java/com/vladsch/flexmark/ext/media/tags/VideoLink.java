package com.vladsch.flexmark.ext.media.tags;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ext.media.tags.internal.AbstractMediaLink;

public class VideoLink extends AbstractMediaLink {

    final public static String PREFIX = "!V";
    final private static String TYPE = "Video";

    public VideoLink() {
        super(PREFIX, TYPE);
    }

    public VideoLink(Link other) {
        super(PREFIX, TYPE, other);
    }

    // This class leaves room for specialization, should we need it.
    // Additionally, it makes managing different Node types easier for users.
}
