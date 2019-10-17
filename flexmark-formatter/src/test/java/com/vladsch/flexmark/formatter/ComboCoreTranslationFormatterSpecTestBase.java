package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.html.renderer.HeaderIdGenerator;
import com.vladsch.flexmark.parser.Parser;
iimport com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.test.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.SpecExampleRenderer;
import com.vladsch.flexmark.test.TestUtils;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ComboCoreTranslationFormatterSpecTestBase extends ComboSpecTestCase {
    static final boolean SKIP_IGNORED_TESTS = true;
    private static final String SPEC_RESOURCE = "/core_translation_formatter_spec.md";
    private static final boolean SHOW_INTERMEDIATE = false;

    static final DataKey<Boolean> DETAILS = new DataKey<>("DETAILS", SHOW_INTERMEDIATE);

    // spacer
    private final Map<String, DataHolder> optionsMap = new HashMap<>();
    private final DataHolder myDefaultOptions;

    public ComboCoreTranslationFormatterSpecTestBase(SpecExample example, @Nullable DataHolder defaultOptions, @Nullable Map<String, DataHolder> optionsMap) {
        super(example);

        // standard options
        DataHolder OPTIONS = new MutableDataSet()
                .set(Parser.BLANK_LINES_IN_AST, true)
                .set(Parser.HTML_FOR_TRANSLATOR, true)
                .set(Parser.PARSE_INNER_HTML_COMMENTS, true)
                .set(Parser.HEADING_NO_ATX_SPACE, true)
                .set(Formatter.MAX_TRAILING_BLANK_LINES, 0)
                .set(TestUtils.NO_FILE_EOL, false).toImmutable();

        myDefaultOptions = combineOptions(OPTIONS, defaultOptions);

        // add standard options
        this.optionsMap.put("details", new MutableDataSet().set(DETAILS, true));
        this.optionsMap.put("IGNORED", new MutableDataSet().set(TestUtils.IGNORE, SKIP_IGNORED_TESTS));

        if (optionsMap != null) {
            this.optionsMap.putAll(optionsMap);
        }
    }

    @Nullable
    @Override
    final public DataHolder options(String optionSet) {
        if (optionSet == null) return null;
        return optionsMap.get(optionSet);
    }

    private Parser getParser(@Nullable DataHolder OPTIONS) {
        return Parser.builder(OPTIONS).build();
    }

    private IRender getRenderer(@Nullable DataHolder OPTIONS) {
        return new TranslationFormatter(Formatter.builder(OPTIONS).build());
    }

    @Override
    final public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = combineOptions(myDefaultOptions, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, getParser(combinedOptions), getRenderer(combinedOptions), true);
    }

    static CharSequence translate(CharSequence text) {
        StringBuilder sb = new StringBuilder();
        int iMax = text.length();
        for (int i = 0; i < iMax; i++) {
            char c = text.charAt(i);

            if ("htpcom".indexOf(c) != -1) {
                sb.append(c);
                continue;
            }

            if ("aeiouy".indexOf(c) != -1) {
                sb.append(c);
            }

            if (Character.isUpperCase(c)) {
                sb.append(Character.toLowerCase(c));
            } else if (Character.isLowerCase(c)) {
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb;
    }

    static class TranslationFormatter implements IRender {
        final Formatter myFormatter;

        public TranslationFormatter(Formatter formatter) {
            myFormatter = formatter;
        }

        @Nullable
        @Override
        public DataHolder getOptions() {
            return myFormatter.getOptions();
        }

        @Override
        public void render(@NotNull Node node, @NotNull Appendable output) {
            TranslationHandler handler = myFormatter.getTranslationHandler(new HeaderIdGenerator.Factory());
            String formattedOutput = myFormatter.translationRender(node, handler, RenderPurpose.TRANSLATION_SPANS);

            // now need to output translation strings, delimited
            List<String> translatingTexts = handler.getTranslatingTexts();

            boolean showIntermediate = node.getDocument().get(DETAILS);

            try {
                if (showIntermediate) {
                    output.append("--------------------------\n");
                    output.append(formattedOutput);
                    output.append("--------------------------\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<CharSequence> translatedTexts = new ArrayList<>(translatingTexts.size());
            for (CharSequence text : translatingTexts) {
                CharSequence translated = translate(text);
                translatedTexts.add(translated);
                try {
                    if (showIntermediate) {
                        output.append("<<<").append(text).append('\n');
                        output.append(">>>").append(translated).append('\n');
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // use the translations
            if (showIntermediate) {
                try {
                    output.append("--------------------------\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            handler.setTranslatedTexts(translatedTexts);
            String partial = myFormatter.translationRender(node, handler, RenderPurpose.TRANSLATED_SPANS);

            if (showIntermediate) {
                try {
                    output.append(partial);
                    output.append("--------------------------\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Node partialDoc = Parser.builder(getOptions()).build().parse(partial);
            String translated = myFormatter.translationRender(partialDoc, handler, RenderPurpose.TRANSLATED);
            try {
                output.append(translated);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @NotNull
        @Override
        public String render(@NotNull Node document) {
            StringBuilder sb = new StringBuilder();
            render(document, sb);
            return sb.toString();
        }
    }

}
