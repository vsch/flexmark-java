package com.vladsch.flexmark.core.test.util;

import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class FormatterTranslationSpecTestBase extends ComboSpecTestCase {
    static final boolean SKIP_IGNORED_TESTS = true;

    private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.BLANK_LINES_IN_AST, true)
            .set(Parser.HEADING_NO_ATX_SPACE, true)
            .toImmutable();

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("IGNORED", new MutableDataSet().set(TestUtils.IGNORE, SKIP_IGNORED_TESTS));
        optionsMap.put("format-fixed-indent", new MutableDataSet().set(Formatter.FORMATTER_EMULATION_PROFILE, ParserEmulationProfile.FIXED_INDENT));
        optionsMap.put("parse-fixed-indent", new MutableDataSet().set(Parser.PARSER_EMULATION_PROFILE, ParserEmulationProfile.FIXED_INDENT));
        optionsMap.put("format-github", new MutableDataSet().set(Formatter.FORMATTER_EMULATION_PROFILE, ParserEmulationProfile.GITHUB_DOC));
        optionsMap.put("parse-github", new MutableDataSet().set(Parser.PARSER_EMULATION_PROFILE, ParserEmulationProfile.GITHUB_DOC));
        optionsMap.put("max-blank-lines-1", new MutableDataSet().set(Formatter.MAX_BLANK_LINES, 1));
        optionsMap.put("max-blank-lines-2", new MutableDataSet().set(Formatter.MAX_BLANK_LINES, 2));
        optionsMap.put("max-blank-lines-3", new MutableDataSet().set(Formatter.MAX_BLANK_LINES, 3));
        optionsMap.put("no-tailing-blanks", new MutableDataSet().set(Formatter.MAX_TRAILING_BLANK_LINES, 0));
    }
    public FormatterTranslationSpecTestBase(@NotNull SpecExample example, @Nullable Map<String, DataHolder> optionMap, @Nullable DataHolder... defaultOptions) {
        super(example, ComboSpecTestCase.optionsMaps(optionsMap, optionMap), ComboSpecTestCase.dataHolders(OPTIONS, defaultOptions));
    }
}
