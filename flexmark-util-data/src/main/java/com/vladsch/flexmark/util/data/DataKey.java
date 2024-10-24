package com.vladsch.flexmark.util.data;

/**
 * NOTE: Constructors have changed in a breaking way from 0.50.x and prior implementations
 *
 * <p>Previously you could provide:
 *
 * <p>1. [T] defaultValue 2. DataValueFactory[T] 3. DataKey[T]
 *
 * <p>Options 1. and 2. are not available separately and both have to be provided to the constructor
 * to eliminate the need for handling null for DataHolder in the data value factory.
 *
 * <p>Now you have the following choices:
 *
 * <p>1. [T] defaultValue AND DataNotNullValueFactory 2. NotNullValueSupplier[T] 3. DataKey[T] from
 * which default value will be taken on construction, and values will be retrieved if no value is
 * set for this key
 *
 * <p>Additional changes include separating NullableDataKey out so that DataKey values cannot be
 * null. If you need a key with null result value then use NullableDataKey which is identical to
 * DataKey but allows nulls to be used for values.
 *
 * @param <T> type of data held by the key
 */
public class DataKey<T> extends DataKeyBase<T> {
  /**
   * Creates a DataKey with non-null data value and factory
   *
   * <p>Use this constructor to ensure that factory is never called with null data holder value
   *
   * @param name See {@link #getName()}.
   * @param defaultValue default to use when data holder is null
   * @param factory data value factory for creating a new default value for the key for a non-null
   *     data holder
   */
  public DataKey(String name, T defaultValue, DataNotNullValueFactory<T> factory) {
    super(name, defaultValue, factory);
  }

  /**
   * Creates a DataKey with non-null data value and factory
   *
   * <p>Use this constructor to ensure that factory is never called with null data holder value
   *
   * @param name See {@link #getName()}.
   * @param supplier data value factory for creating a new default value for the key not dependent
   *     on dataHolder
   */
  public DataKey(String name, NotNullValueSupplier<T> supplier) {
    super(name, supplier.get(), holder -> supplier.get());
  }

  /**
   * Creates a DataKey with a dynamic default value taken from a value of another key
   *
   * <p>does not cache the returned default value but will always delegate to another key until this
   * key gets its own value set.
   *
   * @param name See {@link #getName()}.
   * @param defaultKey The NullableDataKey to take the default value from at time of construction.
   */
  public DataKey(String name, DataKey<T> defaultKey) {
    this(name, defaultKey.getDefaultValue(), defaultKey::get);
  }

  public DataKey(String name, T defaultValue) {
    this(name, defaultValue, options -> defaultValue);
  }

  @Override
  public DataNotNullValueFactory<T> getFactory() {
    return (DataNotNullValueFactory<T>) super.getFactory();
  }

  @Override
  public T getDefaultValue() {
    return super.getDefaultValue();
  }

  @Override
  public T getDefaultValue(DataHolder holder) {
    return super.getDefaultValue(holder);
  }

  @Override
  public T get(DataHolder holder) {
    return super.get(holder);
  }

  @Override
  public MutableDataHolder set(MutableDataHolder dataHolder, T value) {
    return dataHolder.set(this, value);
  }

  @Override
  public String toString() {
    // factory applied to null in constructor, no sense doing it again here
    T defaultValue = getDefaultValue();
    return "DataKey<" + defaultValue.getClass().getSimpleName() + "> " + getName();
  }
}
