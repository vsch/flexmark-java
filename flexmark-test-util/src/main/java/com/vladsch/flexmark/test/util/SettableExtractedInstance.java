package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Instance based on aggregated options used for spec test settings which itself is part of a settable instance
 * <p>
 * For example: Rendering profile contains HTML, Parser and CSS settings. Rendering profile and its contained settings can be set by
 * spec options. In order to handle this properly rendering profile settable instance is defined with HTML, Parser and CSS extracted settable instances.
 * thus allowing setting options on contained instances directly or through the rendering profile container, while keeping the results consistent.
 *
 * @param <T> type for the container setting
 * @param <S> type for the setting
 */
final public class SettableExtractedInstance<T, S> {
    final private @NotNull DataKey<Consumer<S>> myConsumerKey;
    final private @NotNull Function<T, S> myDataExtractor;

    public SettableExtractedInstance(@NotNull DataKey<Consumer<S>> consumerKey, @NotNull Function<T, S> dataExtractor) {
        myConsumerKey = consumerKey;
        myDataExtractor = dataExtractor;
    }

    public void aggregate(@NotNull T instance, @NotNull DataHolder dataHolder) {
        if (dataHolder.contains(myConsumerKey)) {
            myConsumerKey.get(dataHolder).accept(myDataExtractor.apply(instance));
        }
    }

    @NotNull
    public DataHolder aggregateActions(@NotNull DataHolder dataHolder, @Nullable DataHolder other, @Nullable DataHolder overrides) {
        if (other != null && other.contains(myConsumerKey) && overrides != null && overrides.contains(myConsumerKey)) {
            // both, need to combine
            Consumer<S> otherSetter = myConsumerKey.get(other);
            Consumer<S> overridesSetter = myConsumerKey.get(overrides);
            dataHolder = dataHolder.toMutable().set(myConsumerKey, otherSetter.andThen(overridesSetter));
        }

        return dataHolder;
    }
}
