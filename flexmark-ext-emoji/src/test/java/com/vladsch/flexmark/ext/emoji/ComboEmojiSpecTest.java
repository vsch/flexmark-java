package com.vladsch.flexmark.ext.emoji;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboEmojiSpecTest extends RendererSpecTest {
    static final String SPEC_RESOURCE = "/ext_emoji_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(EmojiExtension.create()))
            .set(EmojiExtension.ROOT_IMAGE_PATH, "/img/")
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("use-github", new MutableDataSet().set(EmojiExtension.USE_SHORTCUT_TYPE, EmojiShortcutType.GITHUB));
        optionsMap.put("use-cheat", new MutableDataSet().set(EmojiExtension.USE_SHORTCUT_TYPE, EmojiShortcutType.EMOJI_CHEAT_SHEET));
        optionsMap.put("prefer-github", new MutableDataSet().set(EmojiExtension.USE_SHORTCUT_TYPE, EmojiShortcutType.ANY_GITHUB_PREFERRED));
        optionsMap.put("prefer-cheat", new MutableDataSet().set(EmojiExtension.USE_SHORTCUT_TYPE, EmojiShortcutType.ANY_EMOJI_CHEAT_SHEET_PREFERRED));
        optionsMap.put("unicode", new MutableDataSet().set(EmojiExtension.USE_IMAGE_TYPE, EmojiImageType.UNICODE_FALLBACK_TO_IMAGE));
        optionsMap.put("unicode-only", new MutableDataSet().set(EmojiExtension.USE_IMAGE_TYPE, EmojiImageType.UNICODE_ONLY));
        optionsMap.put("unicode-file", new MutableDataSet().set(EmojiExtension.USE_UNICODE_FILE_NAMES, true));
        optionsMap.put("size", new MutableDataSet().set(EmojiExtension.ATTR_IMAGE_SIZE, "40"));
        optionsMap.put("no-size", new MutableDataSet().set(EmojiExtension.ATTR_IMAGE_SIZE, ""));
        optionsMap.put("no-align", new MutableDataSet().set(EmojiExtension.ATTR_ALIGN, ""));
    }
    public ComboEmojiSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
