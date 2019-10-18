package com.vladsch.flexmark.formatter.test;

import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.test.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.SpecExampleRenderer;
import com.vladsch.flexmark.test.TestUtils;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class ComboFormatterSpecTestBase extends ComboSpecTestCase {
    static final boolean SKIP_IGNORED_TESTS = true;

    private final Map<String, DataHolder> optionsMap = new HashMap<>();
    private final DataHolder myDefaultOptions;

    public ComboFormatterSpecTestBase(SpecExample example, @Nullable DataHolder defaultOptions, @Nullable Map<String, DataHolder> optionsMap) {
        super(example);

        // standard options
        DataHolder OPTIONS = new MutableDataSet()
                .set(Parser.BLANK_LINES_IN_AST, true)
                .set(Parser.HEADING_NO_ATX_SPACE, true)
                .toImmutable();

        myDefaultOptions = combineOptions(OPTIONS, defaultOptions);

        // add standard options
        this.optionsMap.put("IGNORED", new MutableDataSet().set(TestUtils.IGNORE, SKIP_IGNORED_TESTS));
        this.optionsMap.put("format-fixed-indent", new MutableDataSet().set(Formatter.FORMATTER_EMULATION_PROFILE, ParserEmulationProfile.FIXED_INDENT));
        this.optionsMap.put("parse-fixed-indent", new MutableDataSet().set(Parser.PARSER_EMULATION_PROFILE, ParserEmulationProfile.FIXED_INDENT));
        this.optionsMap.put("format-github", new MutableDataSet().set(Formatter.FORMATTER_EMULATION_PROFILE, ParserEmulationProfile.GITHUB_DOC));
        this.optionsMap.put("parse-github", new MutableDataSet().set(Parser.PARSER_EMULATION_PROFILE, ParserEmulationProfile.GITHUB_DOC));
        this.optionsMap.put("max-blank-lines-1", new MutableDataSet().set(Formatter.MAX_BLANK_LINES, 1));
        this.optionsMap.put("max-blank-lines-2", new MutableDataSet().set(Formatter.MAX_BLANK_LINES, 2));
        this.optionsMap.put("max-blank-lines-3", new MutableDataSet().set(Formatter.MAX_BLANK_LINES, 3));
        this.optionsMap.put("no-tailing-blanks", new MutableDataSet().set(Formatter.MAX_TRAILING_BLANK_LINES, 0));

        if (optionsMap != null) {
            this.optionsMap.putAll(optionsMap);
        }
    }

    @Nullable
    public static Map<String, DataHolder> mergeMaps(@Nullable Map<String, DataHolder> other, @Nullable Map<String, DataHolder> overrides) {
        if (other != null && overrides != null) {
            HashMap<String, DataHolder> map = new HashMap<>(other);
            map.putAll(overrides);
            return map;
        } else if (other != null) {
            return other;
        } else {
            return overrides;
        }
    }

    @Nullable
    @Override
    final public DataHolder options(String option) {
        if (option == null) return null;
        return optionsMap.get(option);
    }

    @Override
    final public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = combineOptions(myDefaultOptions, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, Parser.builder(combinedOptions).build(), Formatter.builder(combinedOptions).build(), true);
    }
}
