package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.spec.ResourceLocation;
import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
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

@RunWith(Parameterized.class)
public abstract class ComboSpecTestCase extends FullSpecTestCase {

    protected final @NotNull SpecExample example;
    protected final @NotNull Map<String, DataHolder> optionsMap = new HashMap<>();
    protected final @Nullable DataHolder myDefaultOptions;

    public ComboSpecTestCase(@NotNull SpecExample example, @Nullable Map<String, DataHolder> optionMap, @Nullable DataHolder... defaultOptions) {
        this.example = example;
        DataHolder combinedOptions = null;
        if (defaultOptions != null) {
            for (DataHolder options : defaultOptions) {
                combinedOptions = combineOptions(combinedOptions, options);
            }
        }
        this.myDefaultOptions = combinedOptions == null ? null : combinedOptions.toImmutable();
        mergeMaps(optionMap);
    }

    private void mergeMaps(@Nullable Map<String, DataHolder> overrides) {
        if (overrides != null) {
            optionsMap.putAll(overrides);
        }
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
        if (other != null && overrides != null) {
            HashMap<String, DataHolder> map = new HashMap<>(other);
            map.putAll(overrides);
            return map;
        } else if (other != null) {
            return other;
        } else {
            return overrides;
        }
    }

    @Nullable
    @Override
    final public DataHolder options(String option) {
        if (option == null) return null;
        return optionsMap.get(option);
    }

    @Nullable
    public static DataHolder[] dataHolders(@Nullable DataHolder other, @Nullable DataHolder[] overrides) {
        if (other == null) return overrides;
        else if (overrides == null || overrides.length == 0) return new DataHolder[] { other };

        DataHolder[] holders = new DataHolder[overrides.length + 1];
        System.arraycopy(overrides, 0, holders, 1, overrides.length);
        holders[0] = other;
        return holders;
    }

    protected @NotNull ResourceLocation getLocation(@NotNull String resourcePath) {
        return new ResourceLocation(ComboSpecTestCase.class, resourcePath, TestUtils.DEFAULT_URL_PREFIX);
    }

    protected @NotNull ResourceLocation getLocation(@NotNull Class<?> resourceClass, @NotNull String resourcePath) {
        return new ResourceLocation(resourceClass, resourcePath, TestUtils.DEFAULT_URL_PREFIX);
    }

    protected @NotNull ResourceLocation getLocation(@NotNull Class<?> resourceClass, @NotNull String resourcePath, @NotNull String fileUrlPrefix) {
        return new ResourceLocation(resourceClass, resourcePath, fileUrlPrefix);
    }

    @Test
    public void testHtmlRendering() {
        if (!example.isSpecExample()) return;
        assertRendering(example);
    }

    @Test
    public void testFullSpec() {
        if (!example.isFullSpecExample()) return;
        super.testFullSpec();
    }

    protected static @NotNull List<Object[]> getTestData(@NotNull String resourcePath) {
        return TestUtils.getTestData(ComboSpecTestCase.class, resourcePath, TestUtils.DEFAULT_URL_PREFIX);
    }

    protected static @NotNull List<Object[]> getTestData(@NotNull Class<?> resourceClass, @NotNull String resourcePath) {
        return TestUtils.getTestData(resourceClass, resourcePath, TestUtils.DEFAULT_URL_PREFIX);
    }

    protected static @NotNull List<Object[]> getTestData(@NotNull ResourceLocation location) {
        return TestUtils.getTestData(location);
    }

    protected static @NotNull List<Object[]> getTestData(@NotNull Class<?> resourceClass, @NotNull String resourcePath, @NotNull String fileUrlPrefix) {
        return TestUtils.getTestData(resourceClass, resourcePath, fileUrlPrefix);
    }
}
