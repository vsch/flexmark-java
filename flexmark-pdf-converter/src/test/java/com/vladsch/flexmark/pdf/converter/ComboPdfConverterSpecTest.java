package com.vladsch.flexmark.pdf.converter;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboPdfConverterSpecTest extends ComboSpecTestCase {
    final private static String SPEC_RESOURCE = "/pdf_converter_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("ltr-text", new MutableDataSet().set(PdfConverterExtension.DEFAULT_TEXT_DIRECTION, PdfRendererBuilder.TextDirection.LTR));
        optionsMap.put("rtl-text", new MutableDataSet().set(PdfConverterExtension.DEFAULT_TEXT_DIRECTION, PdfRendererBuilder.TextDirection.RTL));
    }
    public ComboPdfConverterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = aggregate(null, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, Parser.builder(combinedOptions).build(), HtmlRenderer.builder(combinedOptions).build(), true) {
            @Override
            protected @NotNull String renderHtml() {
                String html = super.renderHtml();
                return PdfConverterExtension.embedCss(html, PdfConverterExtension.DEFAULT_CSS.get(myDefaultOptions));
            }
        };
    }
}
