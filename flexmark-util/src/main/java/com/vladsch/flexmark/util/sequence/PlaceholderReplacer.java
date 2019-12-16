package com.vladsch.flexmark.util.sequence;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Used to replace placeholder text in the form of open/close char such as &lt;text&gt; in a markdown document.
 * <p>
 * Used by docx conversion application to replace custom fields.
 */
public class PlaceholderReplacer {
    public static <T> void replaceAll(Collection<T> spanList, Function<String, String> mapper, char openPlaceholder, char closePlaceholder, Function<T, String> getter, BiConsumer<T, String> setter) {
        if (spanList.isEmpty()) return;
        StringBuilder sb = null;

        // accumulate text from < to >, because placeholder can be broken up across multiple spans
        for (T span : spanList) {
            String textValue = getter.apply(span);

            int length = textValue.length();
            int lastPos = 0;
            StringBuilder plainText = null;

            while (lastPos < length) {
                if (sb == null) {
                    int pos = textValue.indexOf(openPlaceholder, lastPos);
                    if (pos == -1) {
                        // nothing in this one
                        if (lastPos > 0) {
                            // had partial text
                            if (plainText != null) plainText.append(textValue.substring(lastPos));
                            else setter.accept(span, textValue.substring(lastPos));
                        }
                        break;
                    } else {
                        sb = new StringBuilder();
                        if (lastPos < pos) {
                            // have plain text
                            if (plainText == null) plainText = new StringBuilder();
                            plainText.append(textValue.substring(lastPos, pos));
                        }
                        lastPos = pos + 1;
                        if (lastPos >= length && plainText == null) setter.accept(span, "");
                    }
                } else {
                    int pos = textValue.indexOf(closePlaceholder, lastPos);

                    if (pos == -1) {
                        sb.append(textValue.substring(lastPos));
                        if (plainText == null) setter.accept(span, "");
                        lastPos = length;
                    } else {
                        // part of it is non-plain text
                        sb.append(textValue.substring(lastPos, pos));
                        lastPos = pos + 1;

                        String placeholder = sb.toString();
                        String result = mapper.apply(placeholder);
                        sb = null;

                        if (result == null) {
                            result = openPlaceholder + placeholder + closePlaceholder;
                        }

                        if (plainText == null) plainText = new StringBuilder();
                        plainText.append(result);
                    }
                }
            }

            if (plainText != null) {
                // have replacement text for the span
                setter.accept(span, plainText.toString());
            }
        }
    }
}
