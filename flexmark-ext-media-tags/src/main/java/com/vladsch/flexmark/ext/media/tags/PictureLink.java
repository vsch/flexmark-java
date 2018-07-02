package com.vladsch.flexmark.ext.media.tags;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ext.media.tags.internal.AbstractMediaLink;

public class PictureLink extends AbstractMediaLink {

    public static final String PREFIX = "!P";
    private static final String TYPE = "Picture";

    public PictureLink() {
        super(PREFIX, TYPE);
    }

    public PictureLink(final Link other) {
        super(PREFIX, TYPE, other);
    }

    // This class leaves room for specialization, should we need it.
    // Additionally, it makes managing different Node types easier for users.
}
