package com.vladsch.flexmark.ext.definition.internal;

import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.options.DefinitionMarker;

class DefinitionFormatOptions {
  final int markerSpaces;
  final DefinitionMarker markerType;

  DefinitionFormatOptions(DataHolder options) {
    markerSpaces = DefinitionExtension.FORMAT_MARKER_SPACES.get(options);
    markerType = DefinitionExtension.FORMAT_MARKER_TYPE.get(options);
  }
}
