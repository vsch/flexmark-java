package com.vladsch.flexmark.parser;

import java.util.BitSet;
import java.util.Map;

public interface InlineParserFactory {
    InlineParser inlineParser(BitSet specialCharacters, BitSet delimiterCharacters, Map<Character, DelimiterProcessor> delimiterProcessors);
}
