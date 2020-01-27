package com.vladsch.flexmark.core.test.util;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class RendererSpecTest extends ComboSpecTestCase {
    private static DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
    }
    public RendererSpecTest(@NotNull SpecExample example, @Nullable Map<String, ? extends DataHolder> optionMap, @Nullable DataHolder... defaultOptions) {
        super(example, ComboSpecTestCase.optionsMaps(optionsMap, optionMap), ComboSpecTestCase.dataHolders(OPTIONS, defaultOptions));
    }

    /**
     * @return false to disable example information in spec
     */
    protected boolean wantExampleInfo() {
        return true;
    }

    @Override
    final public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = aggregate(myDefaultOptions, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, Parser.builder(combinedOptions).build(), HtmlRenderer.builder(combinedOptions).build(), wantExampleInfo());
    }
}
