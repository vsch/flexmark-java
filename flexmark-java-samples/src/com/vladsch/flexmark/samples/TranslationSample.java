package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.formatter.RenderPurpose;
import com.vladsch.flexmark.formatter.TranslationHandler;
import com.vladsch.flexmark.formatter.internal.Formatter;
import com.vladsch.flexmark.html.renderer.HeaderIdGenerator;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.util.ArrayList;
import java.util.List;

public class TranslationSample {

    // simulated translation: letters h, o left as is
    // all other vowels doubled up, with second changing case
    // all other consonants flip case
    static CharSequence translate(CharSequence text) {
        StringBuilder sb = new StringBuilder();
        int iMax = text.length();
        for (int i = 0; i < iMax; i++) {
            char c = text.charAt(i);

            if ("hoHO".indexOf(c) != -1) {
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

    public static void main(String[] args) {
        MutableDataSet OPTIONS = new MutableDataSet()
                .set(Parser.BLANK_LINES_IN_AST, true)
                .set(Parser.HTML_FOR_TRANSLATOR, true)
                .set(Parser.PARSE_INNER_HTML_COMMENTS, true)
                .set(Formatter.MAX_TRAILING_BLANK_LINES, 0);

        Parser PARSER = Parser.builder(OPTIONS).build();
        Formatter FORMATTER = Formatter.builder(OPTIONS).build();

        // 1. Parse the document to get markdown
        final String markdown = "This is [*Sparta*](http://sparta.com)";

        System.out.println(markdown);
        System.out.append("--------------------------\n");

        Document node = PARSER.parse(markdown);

        // 2. Format the document to get markdown strings for translation
        final TranslationHandler handler = FORMATTER.getTranslationHandler(new HeaderIdGenerator.Factory());
        handler.setRenderPurpose(RenderPurpose.TRANSLATION_SPANS);
        final String formattedOutput = FORMATTER.translationRender(node, handler);

        // 3. Get the strings to be translated from translation handler
        final List<String> translatingTexts = handler.getTranslatingTexts();

        // 4. Have the strings translated by your translation service of preference
        final ArrayList<CharSequence> translatedTexts = new ArrayList<>(translatingTexts.size());
        for (CharSequence text : translatingTexts) {
            final CharSequence translated = translate(text);

            // simulated translation
            translatedTexts.add(translated);

            System.out.append("<<<").append(text).append('\n');
            System.out.append(">>>").append(translated).append('\n');
        }
        System.out.append("--------------------------\n");

        // 5. Set the translated strings in the translation handler
        handler.setTranslatedTexts(translatedTexts);

        // 6. Generate markdown with placeholders for non-translating string and out of context translations
        // the rest will already contain translated text
        handler.setRenderPurpose(RenderPurpose.TRANSLATED_FOR_PARSER);
        final String partial = FORMATTER.translationRender(node, handler);

        // 7. Parse the document with placeholders
        handler.setRenderPurpose(RenderPurpose.TRANSLATED);
        Node partialDoc = PARSER.parse(partial);

        // 8. Generate the final translated markdown
        final String translated = FORMATTER.translationRender(partialDoc, handler);

        System.out.println(translated);

        /* OUTPUT:
This is [*Sparta*](http://sparta.com)
--------------------------
<<<*Sparta*
>>>*sPaARTaA*
<<<This is [_1_](_2_)
>>>thiIS iIS [_1_](_2_)
--------------------------
thiIS iIS [*sPaARTaA*](http://sparta.com)
        */
    }
}
