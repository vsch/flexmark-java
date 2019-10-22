package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NullableDataKey<T> {
    protected final @NotNull String name;
    protected final @NotNull DataValueFactory<T> factory;
    protected final @Nullable T defaultValue;

    /**
     * Creates a NullableDataKey with a computed default value and a provided default value when data holder is null.
     * <p>
     * Use this constructor to ensure that factory is never called with null data holder value
     *
     * @param name         See {@link #getName()}.
     * @param defaultValue default to use when data holder is null
     * @param factory      data value factory for creating a new default value for the key for a non-null data holder
     */
    public NullableDataKey(@NotNull String name, @Nullable T defaultValue, @NotNull DataValueNotNullFactory<T> factory) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.factory = factory;
    }

    /**
     * Creates a NullableDataKey with a computed default value dynamically.
     *
     * On construction will invoke factory with null data holder to get the default value
     *
     * @param name    See {@link #getName()}.
     * @param factory data value factory for creating a new default value for the key
     */
    public NullableDataKey(@NotNull String name, @NotNull DataValueFactory<T> factory) {
        this.name = name;
        this.defaultValue = factory.apply(null);
        this.factory = factory;
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
    public NullableDataKey(String name, NullableDataKey<T> defaultKey) {
        this(name, defaultKey.defaultValue, defaultKey::get);
    }

    public NullableDataKey(String name, T defaultValue) {
        this(name, defaultValue, options -> defaultValue);
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public DataValueFactory<T> getFactory() {
        return factory;
    }

    @Nullable
    public T getDefaultValue(@Nullable DataHolder holder) {
        return holder == null ? defaultValue : factory.apply(holder);
    }

    @Nullable
    public T get(@Nullable DataHolder holder) {
        return holder == null ? defaultValue : holder.get(this);
    }

    /**
     * @param holder    data holder
     * @return return default value if holder is null, current value in holder or compute a new value
     * @deprecated use get
     */
    @Deprecated
    @Nullable
    public T getFrom(@Nullable DataHolder holder) {
        return get(holder);
    }

    public MutableDataHolder set(MutableDataHolder holder, T value) {
        holder.set(this, value);
        return holder;
    }

    @Override
    public String toString() {
        // factory applied to null in constructor, no sense doing it again here
        if (defaultValue != null) {
            return "NullableDataKey<" + defaultValue.getClass().getName().substring(defaultValue.getClass().getPackage().getName().length() + 1) + "> " + name;
        } else {
            return "NullableDataKey<unknown> " + name;
        }
    }

    /**
     * Compare only by address. Every key instance is unique
     *
     * @param o other
     * @return true if equal
     */
    @Override
    final public boolean equals(Object o) {
        return this == o;
    }

    @Override
    final public int hashCode() {
        return super.hashCode();
    }
}
