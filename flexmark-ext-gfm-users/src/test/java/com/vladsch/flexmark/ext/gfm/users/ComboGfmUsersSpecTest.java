package com.vladsch.flexmark.ext.gfm.users;

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

public class ComboGfmUsersSpecTest extends RendererSpecTest {
    private static final String SPEC_RESOURCE = "/gfm_users_ast_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(GfmUsersExtension.create()))
            .toImmutable();

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("root", new MutableDataSet().set(GfmUsersExtension.GIT_HUB_USERS_URL_ROOT, "http://github.com"));
        optionsMap.put("prefix", new MutableDataSet().set(GfmUsersExtension.GIT_HUB_USER_URL_PREFIX, "?user="));
        optionsMap.put("suffix", new MutableDataSet().set(GfmUsersExtension.GIT_HUB_USER_URL_SUFFIX, "&"));
        optionsMap.put("plain", new MutableDataSet().set(GfmUsersExtension.GIT_HUB_USER_HTML_PREFIX, "").set(GfmUsersExtension.GIT_HUB_USER_HTML_SUFFIX, ""));
    }
    public ComboGfmUsersSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
    }

    @Override
    public @NotNull ResourceLocation getSpecResourceLocation() {
        return ResourceLocation.of(SPEC_RESOURCE);
    }
}
