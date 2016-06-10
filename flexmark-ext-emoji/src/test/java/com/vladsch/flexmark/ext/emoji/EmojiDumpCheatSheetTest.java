package com.vladsch.flexmark.ext.emoji;

import com.vladsch.flexmark.spec.TemplateEntry;
import com.vladsch.flexmark.test.TemplateTestCase;

import java.util.HashMap;

public class EmojiDumpCheatSheetTest extends TemplateTestCase {
    static final String SPEC_RESOURCE = "/EmojiCheatSheet.txt";

    @Override
    public void getExpandedEntry(TemplateEntry entry, StringBuilder sb) {
        HashMap<String, String> params = new HashMap<>();
        for (String emojiShortcut : EmojiCheatSheetRaw.aliasMap.keySet()) {
            params.put("name", emojiShortcut);
            params.put("image", EmojiCheatSheetRaw.aliasMap.get(emojiShortcut));
            params.put("url", EmojiCheatSheetRaw.urlMap.get(emojiShortcut));
            params.put("category", EmojiCheatSheetRaw.categoryMap.get(emojiShortcut));
            
            entry.replaceParams(params, sb);
        }
    }

    @Override
    protected String getTemplateResourceName() {
        return SPEC_RESOURCE;
    }
}
