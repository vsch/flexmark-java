package com.vladsch.flexmark.util.builder;

import com.vladsch.flexmark.util.data.*;
import com.vladsch.flexmark.util.misc.Extension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import static com.vladsch.flexmark.util.data.SharedDataKeys.EXTENSIONS;

public abstract class BuilderBase<T extends BuilderBase<T>> extends MutableDataSet {

    // loaded extensions
    final private HashSet<Class<?>> loadedExtensions = new HashSet<>();

    // map of which api points were loaded by which extensions
    final private HashMap<Class<?>, HashSet<Object>> extensionApiPoints = new HashMap<>();
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
        ArrayList<Extension> addedExtensions = new ArrayList<>(EXTENSIONS.get(this).size() + extensions.size());

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
            addedExtensions.addAll(0, EXTENSIONS.get(this));
            set(EXTENSIONS, addedExtensions);
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

    /**
     * Get the given key, if it does not exist then use the key's factory to create a new value and put it into the collection
     * so that the following get of the same key will find a value
     *
     * @param key data key
     * @return return stored value or newly created value
     * @deprecated use key.get(dataHolder) instead, which will do the same thing an carries nullable information for the data
     */
    @Deprecated
    @Override
    public <V> V get(@NotNull DataKey<V> key) {
        return key.get(this);
    }

    protected BuilderBase(@Nullable DataHolder options) {
        super(options);
    }

    protected void loadExtensions() {
        if (contains(EXTENSIONS)) {
            extensions(EXTENSIONS.get(this));
        }
    }

    protected BuilderBase() {
        super();
    }

    /**
     * Remove given extensions from options[EXTENSIONS] data key.
     *
     * @param options           options where EXTENSIONS key is set
     * @param excludeExtensions collection of extension classes to remove from extensions
     * @return modified options if removed and options were immutable or the same options if nothing to remove or options were mutable.
     */
    public static DataHolder removeExtensions(@NotNull DataHolder options, @NotNull Collection<Class<? extends Extension>> excludeExtensions) {
        if (options.contains(EXTENSIONS)) {
            ArrayList<Extension> extensions = new ArrayList<>(EXTENSIONS.get(options));
            boolean removed = extensions.removeIf(it -> excludeExtensions.contains(it.getClass()));
            if (removed) {
                if (options instanceof MutableDataHolder) {
                    return ((MutableDataHolder) options).set(EXTENSIONS, extensions);
                } else {
                    return options.toMutable().set(EXTENSIONS, extensions).toImmutable();
                }
            }
        }
        return options;
    }
}
