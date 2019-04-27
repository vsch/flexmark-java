package com.vladsch.flexmark.ext.definition.internal;

import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.util.format.options.DefinitionMarker;
import com.vladsch.flexmark.util.data.DataHolder;

public class FormatOptions {

    public final int markerSpaces;
    public final DefinitionMarker markerType;

    public FormatOptions(DataHolder options) {
        markerSpaces = DefinitionExtension.FORMAT_MARKER_SPACES.getFrom(options);
        markerType = DefinitionExtension.FORMAT_MARKER_TYPE.getFrom(options);
    }
}
