package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface DataKeyAggregator {
    /**
     * Combine options by applying aggregate action keys
     *
     * @param combined set of combined options (by overwriting or combined by prior aggregator)
     * @return combined and cleaned of aggregate action keys, return MutableDataHolder if it was modified so downstream aggregators re-use the mutable
     */
    @NotNull DataHolder aggregate(@NotNull DataHolder combined);

    /**
     * Combine aggregate action keys from two sets but do not apply them
     *
     * @param combined  set of combined options (by overwriting or combined by prior aggregator)
     * @param other     set of original uncombined options
     * @param overrides overriding set of options
     * @return combined aggregate actions from other and overrides overwritten in combined
     */
    @NotNull DataHolder aggregateActions(@NotNull DataHolder combined, @NotNull DataHolder other, @NotNull DataHolder overrides);

    /**
     * Remove any keys which contain aggregation actions and do not represent a state
     *
     * @param combined combined data holder
     * @return cleaned of all aggregate action keys
     */
    @NotNull DataHolder clean(DataHolder combined);

    /**
     * return a set of aggregator classes this aggregator should run after
     *
     * @return keys
     */
    @Nullable Set<Class<?>> invokeAfterSet();

//    /**
//     * return a set of aggregator classes this aggregator should run before
//     *
//     * @return keys
//     */
//    @Nullable Set<Class<?>> invokeBeforeSet();
}
