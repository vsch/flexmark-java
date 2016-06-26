package com.vladsch.flexmark.ext.anchorlink.internal;

import java.util.HashMap;

public class GitHubHeaderIdGenerator {
    private final HashMap<String, Integer> headerBaseIds = new HashMap<>();

    public String generate(CharSequence headerText) {
        String baseRefId = generateId(headerText);

        if (headerBaseIds.containsKey(baseRefId)) {
            int index = headerBaseIds.get(baseRefId);
            
            index++;
            headerBaseIds.put(baseRefId, index);
            baseRefId += "-" + index;
        } else {
            headerBaseIds.put(baseRefId, 0);
        }

        return baseRefId;
    }

    public static String generateId(CharSequence headerText) {
        int iMax = headerText.length();
        StringBuilder baseRefId = new StringBuilder(iMax);

        for (int i = 0; i < iMax; i++) {
            char c = headerText.charAt(i);
            switch (c) {
                case ' ':
                    baseRefId.append('-');
                    break;
                case '-':
                    baseRefId.append('-');
                    break;
                case '_':
                    baseRefId.append('_');
                    break;

                default:
                    if (Character.isAlphabetic(c)) baseRefId.append(Character.toLowerCase(c));
                    else if (Character.isDigit(c)) baseRefId.append(c);
                    break;
            }
        }
        return baseRefId.toString();
    }
}

  
