package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class DataKeyBase<T> implements MutableDataValueSetter<T> {
  private final @NotNull String name;
  private final @NotNull DataValueFactory<T> factory;
  private final T defaultValue;

  /**
   * Creates a NullableDataKey with a computed default value and a provided default value when data
   * holder is null.
   *
   * <p>Use this constructor to ensure that factory is never called with null data holder value
   *
   * @param name See {@link #getName()}.
   * @param defaultValue default to use when data holder is null
   * @param factory data value factory for creating a new default value for the key for a non-null
   *     data holder
   */
  public DataKeyBase(@NotNull String name, T defaultValue, @NotNull DataValueFactory<T> factory) {
    this.name = name;
    this.defaultValue = defaultValue;
    this.factory = factory;
  }

  /**
   * Creates a NullableDataKey with a dynamic default value taken from a value of another key
   *
   * <p>does not cache the returned default value but will always delegate to another key until this
   * key gets its own value set.
   *
   * @param name See {@link #getName()}.
   * @param defaultKey The NullableDataKey to take the default value from at time of construction.
   */
  public DataKeyBase(@NotNull String name, @NotNull DataKeyBase<T> defaultKey) {
    this(name, defaultKey.defaultValue, defaultKey::get);
  }

  public DataKeyBase(@NotNull String name, T defaultValue) {
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

  public T getDefaultValue() {
    return defaultValue;
  }

  public T getDefaultValue(@NotNull DataHolder holder) {
    return factory.apply(holder);
  }

  public T get(@Nullable DataHolder holder) {
    //noinspection unchecked
    return holder == null ? defaultValue : (T) holder.getOrCompute(this, this::getDefaultValue);
  }

  /**
   * @param holder data holder
   * @return return default value if holder is null, current value in holder or compute a new value
   * @deprecated use get
   */
  @Deprecated
  public final T getFrom(@Nullable DataHolder holder) {
    return get(holder);
  }

  @Override
  public String toString() {
    if (defaultValue != null) {
      return "NullableDataKey<" + defaultValue.getClass().getSimpleName() + "> " + name;
    }

    return "NullableDataKey<unknown> " + name;
  }

  /**
   * Compare only by address. Every key instance is unique
   *
   * @param o other
   * @return true if equal
   */
  @Override
  public final boolean equals(Object o) {
    return this == o;
  }

  @Override
  public final int hashCode() {
    return super.hashCode();
  }
}
