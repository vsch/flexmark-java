package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.internal.util.Options;

import java.util.BitSet;
import java.util.Map;

public interface InlineParserFactory {
    InlineParser inlineParser(Options options, BitSet specialCharacters, BitSet delimiterCharacters, Map<Character, DelimiterProcessor> delimiterProcessors);
}
