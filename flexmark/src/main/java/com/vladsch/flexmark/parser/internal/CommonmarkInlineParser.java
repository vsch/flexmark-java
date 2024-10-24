package com.vladsch.flexmark.parser.internal;

import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.parser.delimiter.DelimiterProcessor;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

class CommonmarkInlineParser extends InlineParserImpl {
  CommonmarkInlineParser(
      DataHolder options,
      BitSet specialCharacters,
      BitSet delimiterCharacters,
      Map<Character, DelimiterProcessor> delimiterProcessors,
      LinkRefProcessorData referenceLinkProcessors,
      List<InlineParserExtensionFactory> inlineParserExtensions) {
    super(
        options,
        specialCharacters,
        delimiterCharacters,
        delimiterProcessors,
        referenceLinkProcessors,
        inlineParserExtensions);
  }

  @Override
  public void initializeDocument(Document document) {
    super.initializeDocument(document);
  }
}
