package com.vladsch.flexmark.ext.emoji;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.internal.util.MutableDataSet;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.FullSpecTestCase;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class EmojiFullSpecTest extends FullSpecTestCase {
    static final String SPEC_RESOURCE = "/ext_emoji_ast_spec.txt";
    private static final Set<Extension> EXTENSIONS = Collections.singleton(EmojiExtension.create());
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(EmojiExtension.ROOT_IMAGE_PATH, "/img/");

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("url", new MutableDataSet()
                .set(EmojiExtension.USE_IMAGE_URLS, true)
        );
    }

    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).extensions(EXTENSIONS).build();
    static final Parser PARSER = Parser.builder(OPTIONS).extensions(EXTENSIONS).build();

    static DataHolder optionsSet(String optionSet) {
        return optionsMap.get(optionSet);
    }

    @Override
    protected DataHolder options(String optionSet) {
        return optionsSet(optionSet);
    }

    @Override
    protected String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    protected Parser parser() {
        return PARSER;
    }

    @Override
    protected HtmlRenderer renderer() {
        return RENDERER;
    }

    @Test
    public void delimited() {
        Node document = PARSER.parse(":warning:");
        Emoji emoji = (Emoji) document.getFirstChild().getFirstChild();
        assertEquals(":", emoji.getOpeningMarker().toString());
        assertEquals(":", emoji.getClosingMarker().toString());
    }
}
