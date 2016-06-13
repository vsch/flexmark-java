package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.internal.LinkRefProcessorData;
import com.vladsch.flexmark.internal.util.DataHolder;

import java.util.BitSet;
import java.util.Map;

public interface InlineParserFactory {
    InlineParser inlineParser(DataHolder options, BitSet specialCharacters, BitSet delimiterCharacters, Map<Character, DelimiterProcessor> delimiterProcessors, LinkRefProcessorData linkRefProcessors);
}
