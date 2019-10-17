package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.html.renderer.HeaderIdGenerator;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.test.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.SpecExampleRenderer;
import com.vladsch.flexmark.test.TestUtils;
import com.vladsch.flexmark.util.ast.Document;
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
    private static final boolean SHOW_INTERMEDIATE = false;
    private static final boolean SHOW_INTERMEDIATE_AST = false;

    static final DataKey<Boolean> DETAILS = new DataKey<>("DETAILS", SHOW_INTERMEDIATE);
    static final DataKey<Boolean> AST_DETAILS = new DataKey<>("AST_DETAILS", SHOW_INTERMEDIATE_AST);

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
//                .set(TestUtils.NO_FILE_EOL, false)
                .toImmutable();

        myDefaultOptions = combineOptions(OPTIONS, defaultOptions);

        // add standard options
        this.optionsMap.put("details", new MutableDataSet().set(DETAILS, true));
        this.optionsMap.put("ast-details", new MutableDataSet().set(AST_DETAILS, true));
        this.optionsMap.put("IGNORED", new MutableDataSet().set(TestUtils.IGNORE, SKIP_IGNORED_TESTS));

        if (optionsMap != null) {
            this.optionsMap.putAll(optionsMap);
        }
    }

    @Nullable
    @Override
    final public DataHolder options(String option) {
        if (option == null) return null;
        return optionsMap.get(option);
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
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, getParser(combinedOptions), getRenderer(combinedOptions), true) {
            @Override
            protected @NotNull String renderAst() {
                TranslationFormatter translationFormatter = (TranslationFormatter) getRenderer();
                if (translationFormatter.isShowIntermediateAst()) {
                    return translationFormatter.getAst();
                } else {
                    return super.renderAst();
                }
            }
        };
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
        boolean myShowIntermediate;
        boolean myShowIntermediateAst;
        private @Nullable String myAst = null;

        public TranslationFormatter(Formatter formatter) {
            myFormatter = formatter;
            myShowIntermediate = myFormatter.getOptions().get(DETAILS);
            myShowIntermediateAst = myFormatter.getOptions().get(AST_DETAILS);
        }

        public boolean isShowIntermediate() {
            return myShowIntermediate;
        }

        public boolean isShowIntermediateAst() {
            return myShowIntermediateAst;
        }

        @NotNull
        public String getAst() {
            return myAst == null ? "" : myAst;
        }

        @Nullable
        @Override
        public DataHolder getOptions() {
            return myFormatter.getOptions();
        }

        @Override
        public void render(@NotNull Node node, @NotNull Appendable output) {
            Document document = (Document) node;

            TranslationHandler handler = myFormatter.getTranslationHandler(new HeaderIdGenerator.Factory());
            String formattedOutput = myFormatter.translationRender(document, handler, RenderPurpose.TRANSLATION_SPANS);

            // now need to output translation strings, delimited
            List<String> translatingTexts = handler.getTranslatingTexts();

            StringBuilder outputAst = myShowIntermediateAst ? new StringBuilder() : null;

            try {
                if (myShowIntermediate) {
                    output.append("- Translating Spans ------\n");
                    output.append(formattedOutput);
                    output.append("- Translated Spans --------\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (myShowIntermediateAst) {
                outputAst.append("- Original ----------------\n");
                outputAst.append(TestUtils.ast(document));
            }

            ArrayList<CharSequence> translatedTexts = new ArrayList<>(translatingTexts.size());
            for (CharSequence text : translatingTexts) {
                CharSequence translated = translate(text);
                translatedTexts.add(translated);
                try {
                    if (myShowIntermediate) {
                        output.append("<<<").append(text).append('\n');
                        output.append(">>>").append(translated).append('\n');
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // use the translations
            if (myShowIntermediate) {
                try {
                    output.append("- Partial ----------------\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            handler.setTranslatedTexts(translatedTexts);
            String partial = myFormatter.translationRender(document, handler, RenderPurpose.TRANSLATED_SPANS);

            if (myShowIntermediate) {
                try {
                    output.append(partial);
                    output.append("- Translated -------------\n");
//                  output.append("--------------------------\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Node partialDoc = Parser.builder(getOptions()).build().parse(partial);

            if (myShowIntermediateAst) {
                outputAst.append("- Partial ----------------\n");
                outputAst.append(TestUtils.ast(partialDoc));
            }

            String translated = myFormatter.translationRender(partialDoc, handler, RenderPurpose.TRANSLATED);
            try {
                output.append(translated);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (myShowIntermediateAst) {
                Node translatedDoc = Parser.builder(getOptions()).build().parse(translated);

                outputAst.append("- Translated -------------\n");
                outputAst.append(TestUtils.ast(translatedDoc));
            }

            if (myShowIntermediateAst) {
                myAst = outputAst.toString();
            }
        }
    }
}
