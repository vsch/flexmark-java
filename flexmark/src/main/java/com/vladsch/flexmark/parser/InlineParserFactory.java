package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.parser.delimiter.DelimiterProcessor;
import com.vladsch.flexmark.parser.internal.LinkRefProcessorData;
import com.vladsch.flexmark.util.data.DataHolder;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

public interface InlineParserFactory {
  InlineParser inlineParser(
      DataHolder options,
      BitSet specialCharacters,
      BitSet delimiterCharacters,
      Map<Character, DelimiterProcessor> delimiterProcessors,
      LinkRefProcessorData linkRefProcessors,
      List<InlineParserExtensionFactory> inlineParserExtensions);
}
