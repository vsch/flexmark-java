package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class NullableDataKey<T> extends DataKeyBase<T> {
    /**
     * Creates a DataKey with nullable data value and factory with non-nullable dataHolder
     * <p>
     * Use this constructor to ensure that factory is never called with null data holder value
     *
     * @param name         See {@link #getName()}.
     * @param defaultValue default to use when data holder is null
     * @param factory      data value factory for creating a new default value for the key for a non-null data holder
     */
    public NullableDataKey(@NotNull String name, @Nullable T defaultValue, @NotNull DataValueFactory<T> factory) {
        super(name, defaultValue, factory);
    }

    /**
     * Creates a DataKey with a computed default value dynamically.
     * <p>
     * On construction will invoke factory with null data holder to get the default value
     *
     * @param name    See {@link #getName()}.
     * @param factory data value factory for creating a new default value for the key
     */
    public NullableDataKey(@NotNull String name, @NotNull DataValueNullableFactory<T> factory) {
        super(name, factory.apply(null), factory);
    }

    /**
     * Creates a DataKey with nullable data value and factory not dependent on data holder
     * <p>
     * Use this constructor to ensure that factory is never called with null data holder value
     *
     * @param name     See {@link #getName()}.
     * @param supplier data value factory for creating a new default value for the key not dependent on dataHolder
     */
    public NullableDataKey(@NotNull String name, @NotNull Supplier<T> supplier) {
        super(name, supplier.get(), (holder) -> supplier.get());
    }

    /**
     * Creates a NullableDataKey with a dynamic default value taken from a value of another key
     * <p>
     * does not cache the returned default value but will always delegate to another key until this key
     * gets its own value set.
     *
     * @param name       See {@link #getName()}.
     * @param defaultKey The NullableDataKey to take the default value from at time of construction.
     */
    public NullableDataKey(@NotNull String name, @NotNull DataKeyBase<T> defaultKey) {
        this(name, defaultKey.getDefaultValue(), defaultKey::get);
    }

    public NullableDataKey(@NotNull String name, @Nullable T defaultValue) {
        this(name, defaultValue, options -> defaultValue);
    }

    /**
     * Create a DataKey with null default value and factory producing null values
     *
     * @param name key name
     */
    public NullableDataKey(@NotNull String name) {
        this(name, null, options -> null);
    }

    @Nullable
    public T getDefaultValue() {
        return super.getDefaultValue();
    }

    @Nullable
    public T getDefaultValue(@NotNull DataHolder holder) {
        return super.getDefaultValue(holder);
    }

    @Nullable
    public T get(@Nullable DataHolder holder) {
        return super.get(holder);
    }

    @Override
    public @NotNull MutableDataHolder set(@NotNull MutableDataHolder dataHolder, @Nullable T value) {
        return dataHolder.set(this, value);
    }

    @Override
    public String toString() {
        // factory applied to null in constructor, no sense doing it again here
        T defaultValue = getDefaultValue();
        if (defaultValue != null) {
            return "DataKey<" + defaultValue.getClass().getSimpleName() + "> " + getName();
        } else {
            return "DataKey<null> " + getName();
        }
    }
}
