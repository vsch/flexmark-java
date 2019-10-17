package com.vladsch.flexmark.util.builder;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class BuilderBase<T extends BuilderBase<T>> extends MutableDataSet {
    public static final DataKey<Collection<Extension>> EXTENSIONS = new DataKey<>("EXTENSIONS", Extension.EMPTY_LIST);

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
    protected abstract void removeApiPoint(Object apiPoint);

    /**
     * Preload operation for extension, perform any data config and other operation needed for loading extension
     *
     * @param extension to preload
     */
    protected abstract void preloadExtension(Extension extension);

    /**
     * Load extension if it is valid
     *
     * @param extension to load
     * @return true if extension was loaded
     */
    protected abstract boolean loadExtension(Extension extension);

    /**
     * @param extensions extensions to load
     * @return {@code this}
     * @deprecated use options with EXTENSIONS set
     */
    @Deprecated
    final public T extensions(Collection<? extends Extension> extensions) {
        ArrayList<Extension> addedExtensions = new ArrayList<>(get(EXTENSIONS).size() + extensions.size());

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
            addedExtensions.addAll(0, get(EXTENSIONS));
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
    protected void addExtensionApiPoint(Object apiPoint) {
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
    @Override
    public <D> MutableDataSet set(DataKey<? extends D> key, D value) {
        addExtensionApiPoint(key);
        return super.set(key, value);
    }

    protected BuilderBase(DataHolder options) {
        super(options);
    }

    protected void loadExtensions() {
        if (contains(EXTENSIONS)) {
            extensions(get(EXTENSIONS));
        }
    }

    protected BuilderBase() {
        super();
    }

    protected BuilderBase(T other) {
        super(other);

        HashMap<Class<?>, HashSet<Object>> points = ((BuilderBase<T>) other).extensionApiPoints;
        for (Map.Entry<Class<?>, HashSet<Object>> entry : points.entrySet()) {
            extensionApiPoints.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }

        loadedExtensions.addAll(((BuilderBase<?>) other).loadedExtensions);
    }
}
