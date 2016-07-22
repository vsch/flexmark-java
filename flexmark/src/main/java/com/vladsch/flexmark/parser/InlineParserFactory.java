package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.internal.LinkRefProcessorData;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.parser.delimiter.DelimiterProcessor;

import java.util.BitSet;
import java.util.Map;

public interface InlineParserFactory {
    InlineParser inlineParser(DataHolder options, BitSet specialCharacters, BitSet delimiterCharacters, Map<Character, DelimiterProcessor> delimiterProcessors, LinkRefProcessorData linkRefProcessors);
}
