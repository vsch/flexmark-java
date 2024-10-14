package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NullableDataKey<T> extends DataKeyBase<T> {
  /**
   * Creates a DataKey with nullable data value and factory with non-nullable dataHolder
   *
   * <p>Use this constructor to ensure that factory is never called with null data holder value
   *
   * @param name See {@link #getName()}.
   * @param defaultValue default to use when data holder is null
   * @param factory data value factory for creating a new default value for the key for a non-null
   *     data holder
   */
  private NullableDataKey(
      @NotNull String name, @Nullable T defaultValue, @NotNull DataValueFactory<T> factory) {
    super(name, defaultValue, factory);
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

  @Override
  @Nullable
  public T getDefaultValue() {
    return super.getDefaultValue();
  }

  @Override
  @Nullable
  public T getDefaultValue(@NotNull DataHolder holder) {
    return super.getDefaultValue(holder);
  }

  @Override
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
    }

    return "DataKey<null> " + getName();
  }
}
