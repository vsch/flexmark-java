package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.DataSet;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@RunWith(Parameterized.class)
public abstract class ComboSpecTestCase extends FullSpecTestCase {
    final public static DataKey<BiFunction<String, String, DataHolder>> CUSTOM_OPTION = TestUtils.CUSTOM_OPTION;

    protected final @NotNull SpecExample example;
    protected final @NotNull Map<String, DataHolder> optionsMap = new HashMap<>();
    protected final @Nullable DataHolder myDefaultOptions;

    public ComboSpecTestCase(@NotNull SpecExample example, @Nullable Map<String, DataHolder> optionMap, @Nullable DataHolder... defaultOptions) {
        this.example = example;
        myDefaultOptions = TestUtils.combineDefaultOptions(defaultOptions);
        if (optionMap != null) optionsMap.putAll(optionMap);
    }

    public static @NotNull Map<String, DataHolder> placementAndSortOptions(DataKey<ElementPlacement> placementDataKey, DataKey<ElementPlacementSort> sortDataKey) {
        Map<String, DataHolder> optionsMap = new HashMap<>();
        optionsMap.put("references-as-is", new MutableDataSet().set(placementDataKey, ElementPlacement.AS_IS));
        optionsMap.put("references-document-top", new MutableDataSet().set(placementDataKey, ElementPlacement.DOCUMENT_TOP));
        optionsMap.put("references-group-with-first", new MutableDataSet().set(placementDataKey, ElementPlacement.GROUP_WITH_FIRST));
        optionsMap.put("references-group-with-last", new MutableDataSet().set(placementDataKey, ElementPlacement.GROUP_WITH_LAST));
        optionsMap.put("references-document-bottom", new MutableDataSet().set(placementDataKey, ElementPlacement.DOCUMENT_BOTTOM));
        optionsMap.put("references-sort", new MutableDataSet().set(sortDataKey, ElementPlacementSort.SORT));
        optionsMap.put("references-sort-unused-last", new MutableDataSet().set(sortDataKey, ElementPlacementSort.SORT_UNUSED_LAST));
        return optionsMap;
    }

    @Nullable
    public static Map<String, DataHolder> optionsMaps(@Nullable Map<String, DataHolder> other, @Nullable Map<String, DataHolder> overrides) {
        return TestUtils.optionsMaps(other, overrides);
    }

    @Nullable
    public static DataHolder[] dataHolders(@Nullable DataHolder other, @Nullable DataHolder[] overrides) {
        return TestUtils.dataHolders(other, overrides);
    }

    @NotNull
    public static DataHolder aggregate(@Nullable DataHolder other, @Nullable DataHolder overrides) {
        return DataSet.aggregate(other, overrides);
    }

    @Nullable
    @Override
    public DataHolder options(@NotNull String option) {
        return TestUtils.processOption(optionsMap, option);
    }

    @Override
    final protected @NotNull ResourceLocation getSpecResourceLocation() {
        return example.getResourceLocation();
    }

    @Test
    public void testSpecExample() {
        if (example.isFullSpecExample()) {
            super.testSpecExample();
        } else {
            assertRendering(example);
        }
    }

    protected static @NotNull List<Object[]> getTestData(@NotNull ResourceLocation location) {
        return TestUtils.getTestData(location);
    }
}
