package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.util.data.*;
import com.vladsch.flexmark.util.misc.Extension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class LoadUnloadDataKeyAggregator implements DataKeyAggregator {
    final public static DataKey<Collection<Class<? extends Extension>>> UNLOAD_EXTENSIONS = new DataKey<>("UNLOAD_EXTENSIONS", Collections.emptyList());
    final public static DataKey<Collection<Extension>> LOAD_EXTENSIONS = new DataKey<>("LOAD_EXTENSIONS", Extension.EMPTY_LIST);
    final private static LoadUnloadDataKeyAggregator INSTANCE = new LoadUnloadDataKeyAggregator();
    static {
        DataSet.registerDataKeyAggregator(INSTANCE);
    }
    private LoadUnloadDataKeyAggregator() {

    }

    @Override
    public @NotNull DataHolder aggregate(@NotNull DataHolder combined) {
        if (combined.contains(LOAD_EXTENSIONS) || combined.contains(UNLOAD_EXTENSIONS)) {
            // have something to work with, or at least clean
            if (combined.contains(SharedDataKeys.EXTENSIONS) || combined.contains(LOAD_EXTENSIONS)) {
                Collection<Extension> extensions = SharedDataKeys.EXTENSIONS.get(combined);
                Collection<Extension> loadExtensions = LOAD_EXTENSIONS.get(combined);
                Collection<Class<? extends Extension>> unloadExtensions = UNLOAD_EXTENSIONS.get(combined);

                if (!loadExtensions.isEmpty() || !unloadExtensions.isEmpty() && !extensions.isEmpty()) {
                    LinkedHashSet<Extension> resolvedExtensions = new LinkedHashSet<>(extensions);
                    resolvedExtensions.addAll(loadExtensions);
                    resolvedExtensions.removeIf((extension) -> unloadExtensions.contains(extension.getClass()));
                    return combined.toMutable()
                            .remove(LOAD_EXTENSIONS)
                            .remove(UNLOAD_EXTENSIONS)
                            .set(SharedDataKeys.EXTENSIONS, new ArrayList<>(resolvedExtensions))
                            .toImmutable();
                }
            }
            return combined.toMutable().remove(LOAD_EXTENSIONS).remove(UNLOAD_EXTENSIONS);
        }
        return combined;
    }

    @Override
    public @NotNull DataHolder aggregateActions(@NotNull DataHolder combined, @NotNull DataHolder other, @NotNull DataHolder overrides) {
        if (other.contains(LOAD_EXTENSIONS) && overrides.contains(LOAD_EXTENSIONS)) {
            // have to combine these
            ArrayList<Extension> loadExtensions = new ArrayList<>(LOAD_EXTENSIONS.get(other));
            loadExtensions.addAll(LOAD_EXTENSIONS.get(overrides));
            combined = combined.toMutable().set(LOAD_EXTENSIONS, loadExtensions);
        }

        if (other.contains(UNLOAD_EXTENSIONS) && overrides.contains(UNLOAD_EXTENSIONS)) {
            // have to combine these
            ArrayList<Class<? extends Extension>> loadExtensions = new ArrayList<>(UNLOAD_EXTENSIONS.get(other));
            loadExtensions.addAll(UNLOAD_EXTENSIONS.get(overrides));
            combined = combined.toMutable().set(UNLOAD_EXTENSIONS, loadExtensions);
        }
        return combined;
    }

    @Override
    public @NotNull DataHolder clean(DataHolder combined) {
        if (combined.contains(LOAD_EXTENSIONS) || combined.contains(UNLOAD_EXTENSIONS)) {
            return combined.toMutable().remove(LOAD_EXTENSIONS).remove(UNLOAD_EXTENSIONS);
        }
        return combined;
    }

    @Override
    public @Nullable Set<Class<?>> invokeAfterSet() {
        return null;
    }
}
