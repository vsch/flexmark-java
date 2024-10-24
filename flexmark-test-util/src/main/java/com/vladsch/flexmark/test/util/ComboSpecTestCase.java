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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public abstract class ComboSpecTestCase extends FullSpecTestCase {
  public static final DataKey<BiFunction<String, String, DataHolder>> CUSTOM_OPTION =
      TestUtils.CUSTOM_OPTION;

  protected final SpecExample example;
  protected final Map<String, DataHolder> optionsMap = new HashMap<>();
  protected final DataHolder myDefaultOptions;

  protected ComboSpecTestCase(
      SpecExample example,
      Map<String, ? extends DataHolder> optionMap,
      DataHolder... defaultOptions) {
    this.example = example;
    myDefaultOptions = TestUtils.combineDefaultOptions(defaultOptions);
    if (optionMap != null) optionsMap.putAll(optionMap);
  }

  public static Map<String, DataHolder> placementAndSortOptions(
      DataKey<ElementPlacement> placementDataKey, DataKey<ElementPlacementSort> sortDataKey) {
    return placementAndSortOptions(null, placementDataKey, sortDataKey);
  }

  public static Map<String, DataHolder> placementAndSortOptions(
      DataKey<KeepType> keepTypeDataKey,
      DataKey<ElementPlacement> placementDataKey,
      DataKey<ElementPlacementSort> sortDataKey) {
    Map<String, DataHolder> optionsMap = new HashMap<>();
    if (keepTypeDataKey != null) {
      optionsMap.put(
          "references-keep-last", new MutableDataSet().set(keepTypeDataKey, KeepType.LAST));
      optionsMap.put(
          "references-keep-first", new MutableDataSet().set(keepTypeDataKey, KeepType.FIRST));
      optionsMap.put(
          "references-keep-fail", new MutableDataSet().set(keepTypeDataKey, KeepType.FAIL));
      optionsMap.put(
          "references-keep-locked", new MutableDataSet().set(keepTypeDataKey, KeepType.LOCKED));
    }

    if (placementDataKey != null) {
      optionsMap.put(
          "references-as-is", new MutableDataSet().set(placementDataKey, ElementPlacement.AS_IS));
      optionsMap.put(
          "references-document-top",
          new MutableDataSet().set(placementDataKey, ElementPlacement.DOCUMENT_TOP));
      optionsMap.put(
          "references-group-with-first",
          new MutableDataSet().set(placementDataKey, ElementPlacement.GROUP_WITH_FIRST));
      optionsMap.put(
          "references-group-with-last",
          new MutableDataSet().set(placementDataKey, ElementPlacement.GROUP_WITH_LAST));
      optionsMap.put(
          "references-document-bottom",
          new MutableDataSet().set(placementDataKey, ElementPlacement.DOCUMENT_BOTTOM));
    }

    if (sortDataKey != null) {
      optionsMap.put(
          "references-sort", new MutableDataSet().set(sortDataKey, ElementPlacementSort.SORT));
      optionsMap.put(
          "references-sort-unused-last",
          new MutableDataSet().set(sortDataKey, ElementPlacementSort.SORT_UNUSED_LAST));
      optionsMap.put(
          "references-sort-delete-unused",
          new MutableDataSet().set(sortDataKey, ElementPlacementSort.SORT_DELETE_UNUSED));
      optionsMap.put(
          "references-delete-unused",
          new MutableDataSet().set(sortDataKey, ElementPlacementSort.DELETE_UNUSED));
    }
    return optionsMap;
  }

  @Override
  protected boolean compoundSections() {
    return true;
  }

  public static Map<String, ? extends DataHolder> optionsMaps(
      Map<String, ? extends DataHolder> other, Map<String, ? extends DataHolder> overrides) {
    return TestUtils.optionsMaps(other, overrides);
  }

  public static DataHolder[] dataHolders(DataHolder other, DataHolder[] overrides) {
    return TestUtils.dataHolders(other, overrides);
  }

  public static DataHolder aggregate(DataHolder other, DataHolder overrides) {
    return DataSet.aggregate(other, overrides);
  }

  @Override
  public DataHolder options(String option) {
    return TestUtils.processOption(optionsMap, option);
  }

  @Override
  protected final ResourceLocation getSpecResourceLocation() {
    return example.getResourceLocation();
  }

  @Override
  @Test
  public void testSpecExample() {
    if (example.isFullSpecExample()) {
      super.testSpecExample();
    } else {
      assertRendering(example);
    }
  }

  protected static List<Object[]> getTestData(ResourceLocation location) {
    return TestUtils.getTestData(location);
  }
}
