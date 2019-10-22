package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DataKey<T> extends DataKeyBase<T> {
    /**
     * Creates a DataKey with non-null data value and factory
     * <p>
     * Use this constructor to ensure that factory is never called with null data holder value
     *
     * @param name         See {@link #getName()}.
     * @param defaultValue default to use when data holder is null
     * @param factory      data value factory for creating a new default value for the key for a non-null data holder
     */
    public DataKey(@NotNull String name, @NotNull T defaultValue, @NotNull DataNotNullValueFactory<T> factory) {
        super(name, defaultValue, factory);
    }

    /**
     * Creates a DataKey with non-null data value and factory
     * <p>
     * Use this constructor to ensure that factory is never called with null data holder value
     *
     * @param name         See {@link #getName()}.
     * @param supplier     data value factory for creating a new default value for the key not dependent on dataHolder
     */
    public DataKey(@NotNull String name, @NotNull NotNullValueSupplier<T> supplier) {
        super(name, supplier.get(), (holder) -> supplier.get());
    }

    /**
     * Creates a DataKey with a dynamic default value taken from a value of another key
     * <p>
     * does not cache the returned default value but will always delegate to another key until this key
     * gets its own value set.
     *
     * @param name       See {@link #getName()}.
     * @param defaultKey The NullableDataKey to take the default value from at time of construction.
     */
    public DataKey(@NotNull String name, @NotNull DataKey<T> defaultKey) {
        this(name, defaultKey.getDefaultValue(), defaultKey::get);
    }

    public DataKey(@NotNull String name, @NotNull T defaultValue) {
        this(name, defaultValue, options -> defaultValue);
    }

    @NotNull
    public DataNotNullValueFactory<T> getFactory() {
        return (DataNotNullValueFactory<T>) super.getFactory();
    }

    @NotNull
    public T getDefaultValue() {
        return super.getDefaultValue();
    }

    @NotNull
    public T getDefaultValue(@NotNull DataHolder holder) {
        return super.getDefaultValue(holder);
    }

    @NotNull
    public T get(@Nullable DataHolder holder) {
        return super.get(holder);
    }

    @NotNull
    public MutableDataHolder set(@NotNull MutableDataHolder holder, @NotNull T value) {
        return super.set(holder, value);
    }

    @Override
    public String toString() {
        // factory applied to null in constructor, no sense doing it again here
        T defaultValue = getDefaultValue();
        return "DataKey<" + defaultValue.getClass().getName().substring(defaultValue.getClass().getPackage().getName().length() + 1) + "> " + getName();
    }
}
