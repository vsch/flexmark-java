package com.vladsch.flexmark.util.builder;

import com.vladsch.flexmark.util.SharedDataKeys;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.data.NullableDataKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public abstract class BuilderBase<T extends BuilderBase<T>> extends MutableDataSet {

    // loaded extensions
    private final HashSet<Class<?>> loadedExtensions = new HashSet<>();

    // map of which api points were loaded by which extensions
    private final HashMap<Class<?>, HashSet<Object>> extensionApiPoints = new HashMap<>();
    private Extension currentExtension;

    /**
     * Remove apiPoint from state information
     *
     * @param apiPoint api point object
     */
    protected abstract void removeApiPoint(@NotNull Object apiPoint);

    /**
     * Preload operation for extension, perform any data config and other operation needed for loading extension
     *
     * @param extension to preload
     */
    protected abstract void preloadExtension(@NotNull Extension extension);

    /**
     * Load extension if it is valid
     *
     * @param extension to load
     * @return true if extension was loaded
     */
    protected abstract boolean loadExtension(@NotNull Extension extension);

    /**
     * @param extensions extensions to load
     * @return {@code this}
     */
    final public @NotNull T extensions(@NotNull Collection<? extends Extension> extensions) {
        ArrayList<Extension> addedExtensions = new ArrayList<>(SharedDataKeys.EXTENSIONS.get(this).size() + extensions.size());

        // first give extensions a chance to modify parser options
        for (Extension extension : extensions) {
            currentExtension = extension;
            if (!loadedExtensions.contains(extension.getClass())) {
                preloadExtension(extension);
                addedExtensions.add(extension);
            }
            currentExtension = null;
        }

        for (Extension extension : extensions) {
            currentExtension = extension;
            Class<? extends Extension> extensionClass = extension.getClass();
            if (!loadedExtensions.contains(extensionClass)) {
                if (loadExtension(extension)) {
                    loadedExtensions.add(extensionClass);
                    addedExtensions.add(extension);
                }
            }
            currentExtension = null;
        }

        if (!addedExtensions.isEmpty()) {
            // need to set extensions to options to make it all consistent
            addedExtensions.addAll(0, SharedDataKeys.EXTENSIONS.get(this));
            set(SharedDataKeys.EXTENSIONS, addedExtensions);
        }

        //noinspection unchecked
        return (T) this;
    }

    /**
     * @return actual instance the builder is supposed to build
     */
    @NotNull
    public abstract Object build();

    /**
     * Call to add extension API point to track
     *
     * @param apiPoint point registered
     */
    protected void addExtensionApiPoint(@NotNull Object apiPoint) {
        Extension extension = currentExtension;

        if (extension != null) {
            Class<? extends Extension> extensionClass = extension.getClass();
            HashSet<Object> apiPoints = extensionApiPoints.computeIfAbsent(extensionClass, k -> new HashSet<>());
            apiPoints.add(apiPoint);
        }
    }

    /**
     * Tracks keys set by extension initialization
     *
     * @param key   data key
     * @param value value for the key
     * @return builder
     */
    @NotNull
    @Override
    public <V> MutableDataSet set(@NotNull DataKey<V> key, @NotNull V value) {
        addExtensionApiPoint(key);
        return super.set(key, value);
    }

    @NotNull
    @Override
    public <V> MutableDataSet set(@NotNull NullableDataKey<V> key, @Nullable V value) {
        addExtensionApiPoint(key);
        return super.set(key, value);
    }

    @Override
    public <V> V get(@NotNull DataKey<V> key) {
        return key.get(this);
    }

    protected BuilderBase(DataHolder options) {
        super(options);
    }

    protected void loadExtensions() {
        if (contains(SharedDataKeys.EXTENSIONS)) {
            extensions(SharedDataKeys.EXTENSIONS.get(this));
        }
    }

    protected BuilderBase() {
        super();
    }
}
