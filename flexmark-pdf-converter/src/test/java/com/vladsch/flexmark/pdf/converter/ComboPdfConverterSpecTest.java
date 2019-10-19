package com.vladsch.flexmark.pdf.converter;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.spec.ResourceLocation;
import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboPdfConverterSpecTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/pdf_converter_ast_spec.md";

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("ltr-text", new MutableDataSet().set(PdfConverterExtension.DEFAULT_TEXT_DIRECTION, PdfRendererBuilder.TextDirection.LTR));
        optionsMap.put("rtl-text", new MutableDataSet().set(PdfConverterExtension.DEFAULT_TEXT_DIRECTION, PdfRendererBuilder.TextDirection.RTL));
    }
    public ComboPdfConverterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
    }

    @Override
    public @NotNull ResourceLocation getSpecResourceLocation() {
        return ResourceLocation.of(SPEC_RESOURCE);
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = combineOptions(null, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, Parser.builder(combinedOptions).build(), HtmlRenderer.builder(combinedOptions).build(), true) {
            @Override
            protected @NotNull String renderHtml() {
                String html = super.renderHtml();
                return PdfConverterExtension.embedCss(html, PdfConverterExtension.DEFAULT_CSS.getFrom(options(null)));
            }
        };
    }
}
