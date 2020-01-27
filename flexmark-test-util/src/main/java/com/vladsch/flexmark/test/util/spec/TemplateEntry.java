package com.vladsch.flexmark.test.util.spec;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateEntry {
    final private int entryNumber;
    final private String source;
    final private HashSet<String> params = new HashSet<>();

    final private static Pattern PARAMETER_PATTERN = Pattern.compile("\\$[a-zA-Z_]+\\$");

    public TemplateEntry(int entryNumber, String source) {
        this.entryNumber = entryNumber;
        this.source = source;

        // parse out the parameters
        Matcher m = PARAMETER_PATTERN.matcher(source);
        while (m.find()) {
            String param = m.group().substring(1, m.group().length() - 1);
            params.add(param);
        }
    }

    public String getSource() {
        return source;
    }

    public Set<String> getParams() {
        return params;
    }

    public void replaceParams(Map<String, String> params, StringBuilder sb) {
        // create an expanded template result
        Matcher m = PARAMETER_PATTERN.matcher(source);
        int pos = 0;
        while (m.find()) {
            String param = m.group().substring(1, m.group().length() - 1);

            if (pos < m.start()) {
                sb.append(source.substring(pos, m.start()));
                pos = m.end();
            }

            // append parameter if exists
            if (params.containsKey(param)) {
                sb.append(params.get(param));
            }
        }

        if (pos < source.length()) {
            sb.append(source.substring(pos));
        }
    }

    public int getEntryNumber() {
        return entryNumber;
    }

    @Override
    public String toString() {
        return "entry " + entryNumber;
    }
}
