package com.vladsch.flexmark.util.sequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Html5Entities {

    final private static Map<String, String> NAMED_CHARACTER_REFERENCES = readEntities();
    final private static Pattern NUMERIC_PATTERN = Pattern.compile("^&#[Xx]?");
    final private static String ENTITY_PATH = "/com/vladsch/flexmark/util/sequence/entities.properties";

    public static String entityToString(String input) {
        Matcher matcher = NUMERIC_PATTERN.matcher(input);

        if (matcher.find()) {
            int base = matcher.end() == 2 ? 10 : 16;
            try {
                int codePoint = Integer.parseInt(input.substring(matcher.end(), input.length() - 1), base);
                if (codePoint == 0) {
                    return "\uFFFD";
                }
                return new String(Character.toChars(codePoint));
            } catch (IllegalArgumentException e) {
                return "\uFFFD";
            }
        } else {
            String name = input.substring(1, input.length() - 1);
            String s = NAMED_CHARACTER_REFERENCES.get(name);
            if (s != null) {
                return s;
            } else {
                return input;
            }
        }
    }

    public static BasedSequence entityToSequence(BasedSequence input) {
        Matcher matcher = NUMERIC_PATTERN.matcher(input);
        BasedSequence baseSeq = input.subSequence(0, 0);

        if (matcher.find()) {
            int base = matcher.end() == 2 ? 10 : 16;
            try {
                int codePoint = Integer.parseInt(input.subSequence(matcher.end(), input.length() - 1).toString(), base);
                if (codePoint == 0) {
                    return PrefixedSubSequence.prefixOf("\uFFFD", baseSeq);
                }
                return PrefixedSubSequence.prefixOf(Arrays.toString(Character.toChars(codePoint)), baseSeq);
            } catch (IllegalArgumentException e) {
                return PrefixedSubSequence.prefixOf("\uFFFD", baseSeq);
            }
        } else {
            String name = input.subSequence(1, input.length() - 1).toString();
            String s = NAMED_CHARACTER_REFERENCES.get(name);
            if (s != null) {
                return PrefixedSubSequence.prefixOf(s, baseSeq);
            } else {
                return input;
            }
        }
    }

    private static Map<String, String> readEntities() {
        Map<String, String> entities = new HashMap<>();
        InputStream stream = Html5Entities.class.getResourceAsStream(ENTITY_PATH);
        Charset charset = StandardCharsets.UTF_8;
        try {
            String line;
            InputStreamReader streamReader = new InputStreamReader(stream, charset);
            BufferedReader bufferedReader = new BufferedReader(streamReader);

            while ((line = bufferedReader.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                int equal = line.indexOf("=");
                String key = line.substring(0, equal);
                String value = line.substring(equal + 1);
                entities.put(key, value);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed reading data for HTML named character references", e);
        }
        entities.put("NewLine", "\n");
        return entities;
    }
}
