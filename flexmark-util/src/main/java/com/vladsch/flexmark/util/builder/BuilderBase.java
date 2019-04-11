package com.vladsch.flexmark.util.builder;

import com.vladsch.flexmark.util.ast.NodeRepository;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.util.*;

public abstract class BuilderBase<T extends BuilderBase> extends MutableDataSet {
    public static final DataKey<Iterable<Extension>> EXTENSIONS = new DataKey<>("EXTENSIONS", Extension.EMPTY_LIST);
    public static final DataKey<Iterable<Extension>> UNLOAD_EXTENSIONS = new DataKey<>("UNLOAD_EXTENSIONS", Extension.EMPTY_LIST);
    public static final DataKey<Boolean> RELOAD_EXTENSIONS = new DataKey<>("RELOAD_EXTENSIONS", true);

    // loaded extensions
    private final HashSet<Class> loadedExtensions = new HashSet<>();

    // map of which api points were loaded by which extensions 
    private final HashMap<Class, HashSet<Object>> extensionApiPoints = new HashMap<>();
    private Extension currentExtension;

    /**
     * Remove apiPoint from state information
     *
     * @param apiPoint
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
     */
    final public T extensions(Iterable<? extends Extension> extensions) {
        // first give extensions a chance to modify parser options
        for (Extension extension : extensions) {
            currentExtension = extension;
            if (!loadedExtensions.contains(extension.getClass())) {
                preloadExtension(extension);
            }
            currentExtension = null;
        }

        for (Extension extension : extensions) {
            currentExtension = extension;
            Class<? extends Extension> extensionClass = extension.getClass();
            if (!loadedExtensions.contains(extensionClass)) {
                if (loadExtension(extension)) {
                    loadedExtensions.add(extensionClass);
                }
            }
            currentExtension = null;
        }

        return (T) this;
    }

    /**
     * Call to add extension API point to track
     *
     * @param apiPoint point registered
     */
    protected void addExtensionApiPoint(Object apiPoint) {
        Extension extension = currentExtension;

        if (extension != null) {
            Class<? extends Extension> extensionClass = extension.getClass();
            HashSet<Object> apiPoints = extensionApiPoints.get(extensionClass);
            if (apiPoints == null) {
                apiPoints = new HashSet<>();
                extensionApiPoints.put(extensionClass, apiPoints);
            }
            apiPoints.add(apiPoint);
        }
    }

    public void unloadExtension(Class extensionClass) {
        if (extensionClass != null) {
            if (loadedExtensions.contains(extensionClass)) {
                HashSet<Object> apiPoints = extensionApiPoints.get(extensionClass);
                if (apiPoints != null) {
                    // remove existing one of the same class
                    ArrayList<Object> list = new ArrayList<>(apiPoints);
                    for (Object apiPoint : list) {
                        if (apiPoint instanceof DataKey) this.getAll().remove(apiPoint);
                        else removeApiPoint(apiPoint);
                        apiPoints.remove(apiPoint);
                    }
                }

                loadedExtensions.remove(extensionClass);
            }
        }
    }

    /**
     * Tracks keys set by extension initialization
     *
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    @Override
    public <T> MutableDataSet set(final DataKey<? extends T> key, final T value) {
        addExtensionApiPoint(key);
        return super.set(key, value);
    }

    public void unloadExtensions() {
        // unload any loaded extension points and extensions that are in the set so they can be re-loaded
        ArrayList<Class> list = new ArrayList<>(loadedExtensions);
        for (Class extension : list) {
            unloadExtension(extension);
        }
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

        HashMap<Class, HashSet<Object>> points = ((BuilderBase) other).extensionApiPoints;
        for (Map.Entry<Class, HashSet<Object>> entry : points.entrySet()) {
            extensionApiPoints.put(entry.getKey(), new HashSet<Object>(entry.getValue()));
        }
        loadedExtensions.addAll(((BuilderBase) other).loadedExtensions);
    }

    protected void withOptions(DataHolder options) {
        List<Extension> extensions = new ArrayList<Extension>();
        HashSet<Class> extensionSet = new HashSet<Class>();
        HashSet<Class> unloadExtensionSet = null;

        if (options != null && options.contains(UNLOAD_EXTENSIONS)) {
            unloadExtensionSet = new HashSet<Class>();
            for (Extension extension : UNLOAD_EXTENSIONS.getFrom(options)) {
                unloadExtensionSet.add(extension.getClass());
            }
        }

        for (Extension extension : get(EXTENSIONS)) {
            if (unloadExtensionSet == null || !unloadExtensionSet.contains(extension.getClass())) {
                extensions.add(extension);
                extensionSet.add(extension.getClass());
            }
        }

        if (options != null) {
            for (DataKey key : options.keySet()) {
                if (key == EXTENSIONS) {
                    for (Extension extension : options.get(EXTENSIONS)) {
                        if (unloadExtensionSet == null || !unloadExtensionSet.contains(extension.getClass())) {
                            if (!extensionSet.contains(extension.getClass())) {
                                extensions.add(extension);
                            }
                        }
                    }
                } else {
                    set(key, options.get(key));
                }
            }
        }

        if (options != null && options.contains(RELOAD_EXTENSIONS)) {
            if (RELOAD_EXTENSIONS.getFrom(options)) {
                unloadExtensions();
            }
        } else if (RELOAD_EXTENSIONS.getFrom(this)) {
            unloadExtensions();
        }

        set(EXTENSIONS, extensions);
        extensions(extensions);
    }
}
