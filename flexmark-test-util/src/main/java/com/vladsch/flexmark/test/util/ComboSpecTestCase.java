package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.ast.KeepType;
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

    public ComboSpecTestCase(@NotNull SpecExample example, @Nullable Map<String, ? extends DataHolder> optionMap, @Nullable DataHolder... defaultOptions) {
        this.example = example;
        myDefaultOptions = TestUtils.combineDefaultOptions(defaultOptions);
        if (optionMap != null) optionsMap.putAll(optionMap);
    }

    public static @NotNull Map<String, DataHolder> placementAndSortOptions(DataKey<ElementPlacement> placementDataKey, DataKey<ElementPlacementSort> sortDataKey) {
        return placementAndSortOptions(null, placementDataKey, sortDataKey);
    }

    public static @NotNull Map<String, DataHolder> placementAndSortOptions(@Nullable DataKey<KeepType> keepTypeDataKey, @Nullable DataKey<ElementPlacement> placementDataKey, @Nullable DataKey<ElementPlacementSort> sortDataKey) {
        Map<String, DataHolder> optionsMap = new HashMap<>();
        if (keepTypeDataKey != null) {
            optionsMap.put("references-keep-last", new MutableDataSet().set(keepTypeDataKey, KeepType.LAST));
            optionsMap.put("references-keep-first", new MutableDataSet().set(keepTypeDataKey, KeepType.FIRST));
            optionsMap.put("references-keep-fail", new MutableDataSet().set(keepTypeDataKey, KeepType.FAIL));
            optionsMap.put("references-keep-locked", new MutableDataSet().set(keepTypeDataKey, KeepType.LOCKED));
        }

        if (placementDataKey != null) {
            optionsMap.put("references-as-is", new MutableDataSet().set(placementDataKey, ElementPlacement.AS_IS));
            optionsMap.put("references-document-top", new MutableDataSet().set(placementDataKey, ElementPlacement.DOCUMENT_TOP));
            optionsMap.put("references-group-with-first", new MutableDataSet().set(placementDataKey, ElementPlacement.GROUP_WITH_FIRST));
            optionsMap.put("references-group-with-last", new MutableDataSet().set(placementDataKey, ElementPlacement.GROUP_WITH_LAST));
            optionsMap.put("references-document-bottom", new MutableDataSet().set(placementDataKey, ElementPlacement.DOCUMENT_BOTTOM));
        }

        if (sortDataKey != null) {
            optionsMap.put("references-sort", new MutableDataSet().set(sortDataKey, ElementPlacementSort.SORT));
            optionsMap.put("references-sort-unused-last", new MutableDataSet().set(sortDataKey, ElementPlacementSort.SORT_UNUSED_LAST));
            optionsMap.put("references-sort-delete-unused", new MutableDataSet().set(sortDataKey, ElementPlacementSort.SORT_DELETE_UNUSED));
            optionsMap.put("references-delete-unused", new MutableDataSet().set(sortDataKey, ElementPlacementSort.DELETE_UNUSED));
        }
        return optionsMap;
    }

    protected boolean compoundSections() {
        return true;
    }

    @Nullable
    public static Map<String, ? extends DataHolder> optionsMaps(@Nullable Map<String, ? extends DataHolder> other, @Nullable Map<String, ? extends DataHolder> overrides) {
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
