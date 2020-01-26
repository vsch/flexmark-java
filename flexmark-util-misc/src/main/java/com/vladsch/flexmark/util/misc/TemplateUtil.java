package com.vladsch.flexmark.util.misc;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateUtil {
    final public static Resolver NULL_RESOLVER = groups -> null;

    public static class MappedResolver implements Resolver {
        final protected Map<String, String> resolved;

        public MappedResolver(Map<String, String> map) {
            resolved = map;
        }

        public MappedResolver() {
            this(new HashMap<>());
        }

        public MappedResolver set(String name, String value) {
            resolved.put(name, value);
            return this;
        }

        public Map<String, String> getMMap() {
            return resolved;
        }

        @Override
        public String resolve(String[] groups) {
            return groups.length > 2 ? null : resolved.get(groups[1]);
        }
    }

    public interface Resolver {
        String resolve(String[] groups);
    }

    public static String resolveRefs(CharSequence text, Pattern pattern, Resolver resolver) {
        if (text == null) return "";

        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            StringBuffer sb = new StringBuffer();

            do {
                String[] groups = new String[matcher.groupCount() + 1];
                for (int i = 0; i < groups.length; i++) {
                    groups[i] = matcher.group(i);
                }

                String resolved = resolver.resolve(groups);

                matcher.appendReplacement(sb, resolved == null ? "" : resolved.replace("\\", "\\\\").replace("$", "\\$"));
            } while (matcher.find());

            matcher.appendTail(sb);
            return sb.toString();
        }
        return text.toString();
    }
}
