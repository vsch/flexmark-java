package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.parser.delimiter.DelimiterProcessor;
import com.vladsch.flexmark.parser.internal.LinkRefProcessorData;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.BitSet;
import java.util.List;
import java.util.Map;

public interface InlineParserFactory {
    InlineParser inlineParser(
            @NotNull DataHolder options,
            @NotNull BitSet specialCharacters,
            @NotNull BitSet delimiterCharacters,
            @NotNull Map<Character,
                    DelimiterProcessor> delimiterProcessors,
            @NotNull LinkRefProcessorData linkRefProcessors,
            @NotNull List<InlineParserExtensionFactory> inlineParserExtensions
    );
}
