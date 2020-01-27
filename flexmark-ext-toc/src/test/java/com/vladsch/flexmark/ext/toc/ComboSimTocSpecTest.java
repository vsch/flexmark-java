package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.ext.toc.internal.TocOptions;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboSimTocSpecTest extends RendererSpecTest {
    final private static String SPEC_RESOURCE = "/ext_simtoc_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.RENDER_HEADER_ID, true)
            .set(Parser.EXTENSIONS, Collections.singletonList(SimTocExtension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("text-only", new MutableDataSet().set(TocExtension.IS_TEXT_ONLY, true));
        optionsMap.put("formatted", new MutableDataSet().set(TocExtension.IS_TEXT_ONLY, false));
        optionsMap.put("hierarchy", new MutableDataSet().set(TocExtension.LIST_TYPE, TocOptions.ListType.HIERARCHY));
        optionsMap.put("flat", new MutableDataSet().set(TocExtension.LIST_TYPE, TocOptions.ListType.FLAT));
        optionsMap.put("flat-reversed", new MutableDataSet().set(TocExtension.LIST_TYPE, TocOptions.ListType.FLAT_REVERSED));
        optionsMap.put("sorted", new MutableDataSet().set(TocExtension.LIST_TYPE, TocOptions.ListType.SORTED));
        optionsMap.put("sorted-reversed", new MutableDataSet().set(TocExtension.LIST_TYPE, TocOptions.ListType.SORTED_REVERSED));
        optionsMap.put("with-option-list", new MutableDataSet().set(TocExtension.AST_INCLUDE_OPTIONS, true));
        optionsMap.put("blank-line-spacer", new MutableDataSet().set(TocExtension.BLANK_LINE_SPACER, true));
        optionsMap.put("typographic", new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(SimTocExtension.create(), TypographicExtension.create())));
        optionsMap.put("github", new MutableDataSet().setFrom(ParserEmulationProfile.GITHUB_DOC));
        optionsMap.put("div-class", new MutableDataSet().set(TocExtension.DIV_CLASS, "content-class"));
        optionsMap.put("list-class", new MutableDataSet().set(TocExtension.LIST_CLASS, "list-class"));
        optionsMap.put("not-case-sensitive", new MutableDataSet().set(TocExtension.CASE_SENSITIVE_TOC_TAG, false));
        optionsMap.put("on-format-as-is", new MutableDataSet().set(TocExtension.FORMAT_UPDATE_ON_FORMAT, SimTocGenerateOnFormat.AS_IS));
        optionsMap.put("on-format-remove", new MutableDataSet().set(TocExtension.FORMAT_UPDATE_ON_FORMAT, SimTocGenerateOnFormat.REMOVE));
        optionsMap.put("on-format-update", new MutableDataSet().set(TocExtension.FORMAT_UPDATE_ON_FORMAT, SimTocGenerateOnFormat.UPDATE));
    }
    public ComboSimTocSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
