package com.vladsch.flexmark.ext.definition.internal;

import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.data.DataHolder;

class DefinitionOptions {
  final int markerSpaces;
  final boolean tildeMarker;
  final boolean colonMarker;
  final ParserEmulationProfile myParserEmulationProfile;
  final boolean autoLoose;
  final boolean doubleBlankLineBreaksList;
  final int codeIndent;
  final int itemIndent;
  final int newItemCodeIndent;

  DefinitionOptions(DataHolder options) {
    markerSpaces = DefinitionExtension.MARKER_SPACES.get(options);
    tildeMarker = DefinitionExtension.TILDE_MARKER.get(options);
    colonMarker = DefinitionExtension.COLON_MARKER.get(options);
    myParserEmulationProfile = Parser.PARSER_EMULATION_PROFILE.get(options);
    autoLoose = Parser.LISTS_AUTO_LOOSE.get(options);
    codeIndent = Parser.LISTS_CODE_INDENT.get(options);
    itemIndent = Parser.LISTS_ITEM_INDENT.get(options);
    newItemCodeIndent = Parser.LISTS_NEW_ITEM_CODE_INDENT.get(options);
    doubleBlankLineBreaksList = DefinitionExtension.DOUBLE_BLANK_LINE_BREAKS_LIST.get(options);
  }
}
