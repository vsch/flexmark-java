package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;

import static com.vladsch.flexmark.test.util.TestUtils.*;

@SuppressWarnings("EqualsBetweenInconvertibleTypes")
public class ExampleOption {
    final private static HashSet<String> BUILT_IN_OPTIONS_SET = new HashSet<>();
    static {
        BUILT_IN_OPTIONS_SET.add(EMBED_TIMED_OPTION_NAME);
        BUILT_IN_OPTIONS_SET.add(FAIL_OPTION_NAME);
        BUILT_IN_OPTIONS_SET.add(FILE_EOL_OPTION_NAME);
        BUILT_IN_OPTIONS_SET.add(IGNORE_OPTION_NAME);
        BUILT_IN_OPTIONS_SET.add(NO_FILE_EOL_OPTION_NAME);
        BUILT_IN_OPTIONS_SET.add(TIMED_ITERATIONS_OPTION_NAME);
        BUILT_IN_OPTIONS_SET.add(TIMED_OPTION_NAME);
    }
    private static ExampleOption build(@NotNull CharSequence option) {
        BasedSequence optionName;
        BasedSequence customParams;
        boolean isDisabled = false;
        BasedSequence optionText = BasedSequence.of(option);

        int pos = optionText.indexOf("[");
        if (pos > 0 && pos < optionText.length() && optionText.endsWith("]")) {
            // parameterized, see if there is a handler defined for it
            optionName = optionText.subSequence(0, pos);
            customParams = optionText.subSequence(pos + 1, optionText.length() - 1);
        } else {
            optionName = optionText;
            customParams = BasedSequence.NULL;
        }

        if (optionName.startsWith(DISABLED_OPTION_PREFIX)) {
            optionName = optionName.subSequence(1);
            isDisabled = true;
        }

        return new ExampleOption(
                optionText,
                optionName,
                customParams,
                BUILT_IN_OPTIONS_SET.contains(optionName.toString()) && customParams.isNull(),
                isDisabled,
                customParams.isNotNull(),
                !optionName.isBlank()
        );
    }

    public static ExampleOption of(@NotNull CharSequence optionText) {
        return build(optionText);
    }

    final private static HashMap<String, ExampleOption> BUILT_IN_OPTIONS_MAP = new HashMap<>();
    static {
        BUILT_IN_OPTIONS_MAP.put(EMBED_TIMED_OPTION_NAME, build(EMBED_TIMED_OPTION_NAME));
        BUILT_IN_OPTIONS_MAP.put(FAIL_OPTION_NAME, build(FAIL_OPTION_NAME));
        BUILT_IN_OPTIONS_MAP.put(FILE_EOL_OPTION_NAME, build(FILE_EOL_OPTION_NAME));
        BUILT_IN_OPTIONS_MAP.put(IGNORE_OPTION_NAME, build(IGNORE_OPTION_NAME));
        BUILT_IN_OPTIONS_MAP.put(NO_FILE_EOL_OPTION_NAME, build(NO_FILE_EOL_OPTION_NAME));
        // NOTE: this one is not an option to use in spec example but set in data holder
//        BUILT_IN_OPTIONS_MAP.put(TIMED_ITERATIONS_OPTION_NAME, build(TIMED_ITERATIONS_OPTION_NAME));
        BUILT_IN_OPTIONS_MAP.put(TIMED_OPTION_NAME, build(TIMED_OPTION_NAME));
    }
    @NotNull
    public static HashMap<String, ExampleOption> getBuiltInOptions() {
        return new HashMap<>(BUILT_IN_OPTIONS_MAP);
    }

    final public @NotNull BasedSequence optionText;
    final public @NotNull BasedSequence optionName;
    final public @NotNull BasedSequence customParams;
    final public boolean isBuiltIn;
    final public boolean isDisabled;
    final public boolean isCustom;
    final public boolean isValid;

    private ExampleOption(@NotNull BasedSequence optionText, @NotNull BasedSequence optionName, @NotNull BasedSequence customParams, boolean isBuiltIn, boolean isDisabled, boolean isCustom, boolean isValid) {
        this.optionText = optionText;
        this.optionName = optionName;
        this.customParams = customParams;
        this.isBuiltIn = isBuiltIn;
        this.isDisabled = isDisabled;
        this.isCustom = isCustom;
        this.isValid = isValid;
    }

    @NotNull
    public String getOptionText() {
        return optionText.toString();
    }

    @NotNull
    public String getOptionName() {
        return optionName.toString();
    }

    @Nullable
    public String getCustomParams() {
        return customParams.isNull() ? null : customParams.toString();
    }

    public boolean isBuiltIn() {
        return isBuiltIn;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public boolean isValid() {
        return isValid;
    }

    public boolean isIgnore() {
        return isBuiltIn && optionName.equals(IGNORE_OPTION_NAME);
    }

    public boolean isFail() {
        return isBuiltIn && optionName.equals(FAIL_OPTION_NAME);
    }

    public boolean isTimed() {
        return isBuiltIn && optionName.equals(TIMED_OPTION_NAME);
    }

    public boolean isTimedIterations() {
        return isBuiltIn && optionName.equals(TIMED_ITERATIONS_OPTION_NAME);
    }

    public boolean isEmbedTimed() {
        return isBuiltIn && optionName.equals(EMBED_TIMED_OPTION_NAME);
    }

    public boolean isFileEol() {
        return isBuiltIn && optionName.equals(FILE_EOL_OPTION_NAME);
    }

    public boolean isNoFileEol() {
        return isBuiltIn && optionName.equals(NO_FILE_EOL_OPTION_NAME);
    }
}
