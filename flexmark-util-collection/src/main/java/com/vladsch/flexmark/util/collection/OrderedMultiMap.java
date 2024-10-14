package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.collection.iteration.BitSetIterable;
import com.vladsch.flexmark.util.collection.iteration.BitSetIterator;
import com.vladsch.flexmark.util.collection.iteration.Indexed;
import com.vladsch.flexmark.util.collection.iteration.IndexedIterable;
import com.vladsch.flexmark.util.collection.iteration.IndexedIterator;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIndexedIterator;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.misc.Paired;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OrderedMultiMap<K, V> implements Map<K, V>, Iterable<Map.Entry<K, V>> {
  private final @NotNull OrderedSet<K> keySet;
  private final @NotNull OrderedSet<V> valueSet;
  private final @Nullable CollectionHost<Paired<K, V>> host;
  private boolean isInKeyUpdate;
  private boolean isInValueUpdate;
  private @Nullable Indexed<Entry<K, V>> indexedProxy;

  public OrderedMultiMap() {
    this(0, null);
  }

  public OrderedMultiMap(@NotNull CollectionHost<Paired<K, V>> host) {
    this(0, host);
  }

  private OrderedMultiMap(int capacity, @Nullable CollectionHost<Paired<K, V>> host) {
    this.host = host;
    this.indexedProxy = null;
    this.valueSet =
        new OrderedSet<>(
            capacity,
            new CollectionHost<V>() {
              @Override
              public void adding(int index, @Nullable V v, @Nullable Object k) {
                addingValue(index, v, k);
              }

              @Override
              public Object removing(int index, @Nullable V v) {
                return removingValue(index, v);
              }

              @Override
              public void clearing() {
                clear();
              }

              @Override
              public void addingNulls(int index) {
                addingNullValue(index);
              }

              @Override
              public boolean skipHostUpdate() {
                return isInKeyUpdate;
              }

              @Override
              public int getIteratorModificationCount() {
                return OrderedMultiMap.this.getModificationCount();
              }
            });

    this.keySet =
        new OrderedSet<>(
            capacity,
            new CollectionHost<K>() {
              @Override
              public void adding(int index, @Nullable K k, @Nullable Object v) {
                addingKey(index, k, v);
              }

              @Override
              public Object removing(int index, @Nullable K k) {
                return removingKey(index, k);
              }

              @Override
              public void clearing() {
                clear();
              }

              @Override
              public void addingNulls(int index) {
                addingNullKey(index);
              }

              @Override
              public boolean skipHostUpdate() {
                return isInValueUpdate;
              }

              @Override
              public int getIteratorModificationCount() {
                return OrderedMultiMap.this.getModificationCount();
              }
            });
  }

  public Indexed<Map.Entry<K, V>> getIndexedProxy() {
    if (indexedProxy != null) {
      return indexedProxy;
    }

    indexedProxy =
        new Indexed<>() {
          @Override
          public Map.Entry<K, V> get(int index) {
            return OrderedMultiMap.this.getEntry(index);
          }

          @Override
          public void set(int index, Map.Entry<K, V> item) {
            throw new UnsupportedOperationException();
          }

          @Override
          public void removeAt(int index) {
            OrderedMultiMap.this.removeEntryIndex(index);
          }

          @Override
          public int size() {
            return OrderedMultiMap.this.size();
          }

          @Override
          public int modificationCount() {
            return OrderedMultiMap.this.getModificationCount();
          }
        };

    return indexedProxy;
  }

  private Map.Entry<K, V> getEntry(int index) {
    return new MapEntry<>(keySet.getValueOrNull(index), valueSet.getValueOrNull(index));
  }

  public int getModificationCount() {
    return (int) ((long) keySet.getModificationCount() + (long) valueSet.getModificationCount());
  }

  private void addingKey(int index, @Nullable K k, @Nullable Object v) {
    isInValueUpdate = true;
    if (host != null && !host.skipHostUpdate()) {
      host.adding(index, new Pair<>(k, (V) v), null);
    }
    if (v == null) valueSet.addNulls(index);
    else valueSet.add((V) v);
    isInValueUpdate = false;
  }

  private void addingNullKey(int index) {
    isInValueUpdate = true;
    if (host != null && !host.skipHostUpdate()) {
      host.addingNulls(index);
    }
    while (valueSet().size() <= index) valueSet.add(null);
    isInValueUpdate = false;
  }

  private Object removingKey(int index, @Nullable K k) {
    isInValueUpdate = true;
    if (host != null && !host.skipHostUpdate()) {
      host.removing(index, new Pair<>(k, null));
    }
    Object r = valueSet.removeIndexHosted(index);
    isInValueUpdate = false;
    return r;
  }

  private void addingValue(int index, @Nullable V v, @Nullable Object k) {
    isInKeyUpdate = true;
    if (host != null && !host.skipHostUpdate()) {
      host.adding(index, new Pair<>((K) k, v), null);
    }
    if (k == null) keySet.addNulls(index);
    else keySet.add((K) k);
    isInKeyUpdate = false;
  }

  private void addingNullValue(int index) {
    isInKeyUpdate = true;
    if (host != null && !host.skipHostUpdate()) {
      host.addingNulls(index);
    }
    while (keySet.size() <= index) keySet.add(null);
    isInKeyUpdate = false;
  }

  private Object removingValue(int index, @Nullable V v) {
    isInKeyUpdate = true;
    if (host != null && !host.skipHostUpdate()) {
      host.removing(index, new Pair<>(null, v));
    }
    Object r = keySet.removeIndexHosted(index);
    isInKeyUpdate = false;
    return r;
  }

  @Override
  public int size() {
    return keySet.size();
  }

  @Override
  public boolean isEmpty() {
    return keySet.isEmpty();
  }

  @Override
  public boolean containsKey(@Nullable Object o) {
    return keySet.contains(o);
  }

  @Override
  public boolean containsValue(@Nullable Object o) {
    int index = valueSet.indexOf(o);
    return keySet.isValidIndex(index);
  }

  @Override
  public @Nullable V get(@Nullable Object o) {
    return getKeyValue(o);
  }

  public @Nullable V getKeyValue(@Nullable Object o) {
    int index = keySet.indexOf(o);
    return index == -1 ? null : valueSet.getValue(index);
  }

  public @Nullable K getValueKey(@Nullable Object o) {
    int index = valueSet.indexOf(o);
    return index == -1 ? null : keySet.getValue(index);
  }

  @Override
  public @Nullable V put(@Nullable K k, @Nullable V v) {
    return putKeyValue(k, v);
  }

  private void addNullEntry(int index) {
    isInKeyUpdate = true;
    isInValueUpdate = true;

    if (host != null && !host.skipHostUpdate()) {
      host.addingNulls(index);
    }
    keySet.addNulls(index);
    valueSet.addNulls(index);

    isInValueUpdate = false;
    isInKeyUpdate = false;
  }

  boolean putKeyValuePair(@NotNull Paired<K, V> e) {
    return addKeyValue(e.getFirst(), e.getSecond());
  }

  boolean putValueKeyPair(@NotNull Paired<V, K> e) {
    return addKeyValue(e.getSecond(), e.getFirst());
  }

  public V putKeyValue(@Nullable K k, @Nullable V v) {
    return !addKeyValue(k, v) ? v : null;
  }

  public K putValueKey(@Nullable V v, @Nullable K k) {
    return !addKeyValue(k, v) ? k : null;
  }

  private boolean addKeyValue(@Nullable K k, @Nullable V v) {
    int keyIndex = keySet.indexOf(k);
    int valueIndex = valueSet.indexOf(v);

    if (keyIndex == -1 && valueIndex == -1) {
      // neither one exists/ we add both
      isInKeyUpdate = true;
      isInValueUpdate = true;
      if (host != null && !host.skipHostUpdate()) {
        host.adding(keySet.getValueList().size(), new Pair<>(k, v), null);
      }

      if (k == null) keySet.addNull();
      else keySet.add(k, v);

      if (k == null) valueSet.addNull();
      else valueSet.add(v, k);

      isInValueUpdate = false;
      isInKeyUpdate = false;

      return true;
    }

    if (keyIndex == -1) {
      isInKeyUpdate = true;
      isInValueUpdate = true;
      if (host != null && !host.skipHostUpdate()) {
        host.adding(valueIndex, new Pair<>(k, v), null);
      }

      if (k == null) keySet.removeIndex(valueIndex);
      else keySet.setValueAt(valueIndex, k, v);

      isInValueUpdate = false;
      isInKeyUpdate = false;
      return true;
    }

    if (valueIndex == -1) {
      isInKeyUpdate = true;
      isInValueUpdate = true;
      if (host != null && !host.skipHostUpdate()) {
        host.adding(keyIndex, new Pair<>(k, v), null);
      }

      if (k == null) valueSet.removeIndex(valueIndex);
      else valueSet.setValueAt(keyIndex, v, k);

      isInValueUpdate = false;
      return true;
    }

    if (valueIndex != keyIndex) {
      throw new IllegalStateException(
          "keySet["
              + keyIndex
              + "]="
              + k
              + " and valueSet["
              + valueIndex
              + "]="
              + v
              + " are out of sync");
    }

    return false;
  }

  @Override
  public @Nullable V remove(@Nullable Object o) {
    return removeKey(o);
  }

  private boolean removeEntryIndex(int index) {
    return removeEntryIndex(index, keySet.getValueOrNull(index), valueSet.getValueOrNull(index));
  }

  private boolean removeEntryIndex(int index, @Nullable K k, @Nullable V v) {
    int keyIndex = keySet.indexOf(k);
    int valueIndex = valueSet.indexOf(v);

    if (keyIndex != valueIndex) {
      throw new IllegalStateException(
          "keySet["
              + keyIndex
              + "]="
              + k
              + " and valueSet["
              + valueIndex
              + "]="
              + v
              + " are out of sync");
    }

    if (index != -1 && keyIndex != index) {
      throw new IllegalStateException(
          "removeEntryIndex "
              + index
              + " does not match keySet["
              + keyIndex
              + "]="
              + k
              + " and valueSet["
              + valueIndex
              + "]="
              + v
              + " are out of sync");
    }

    if (keyIndex != -1) {
      isInKeyUpdate = true;
      isInValueUpdate = true;
      if (host != null && !host.skipHostUpdate()) {
        host.removing(keyIndex, new Pair<>(k, v));
      }
      keySet.removeHosted(k);
      valueSet.removeHosted(v);
      isInValueUpdate = false;
      isInKeyUpdate = false;
      return true;
    }
    return false;
  }

  public V removeKey(Object object) {
    isInKeyUpdate = true;
    if (host != null && !host.skipHostUpdate()) {
      int index = keySet.indexOf(object);
      if (index != -1) {
        host.removing(
            index,
            new Pair<>((K) object, valueSet.isValidIndex(index) ? valueSet.getValue(index) : null));
      }
    }
    V r = (V) keySet.removeHosted(object);
    isInKeyUpdate = false;
    return r;
  }

  public K removeValue(Object object) {
    isInValueUpdate = true;
    int index = valueSet.indexOf(object);
    if (host != null && !host.skipHostUpdate()) {
      if (index != -1) {
        host.removing(
            index,
            new Pair<>(keySet.isValidIndex(index) ? keySet.getValue(index) : null, (V) object));
      }
    }
    K r = (K) valueSet.removeHosted(object);
    isInValueUpdate = false;
    return r;
  }

  @Override
  public void putAll(@NotNull Map<? extends K, ? extends V> map) {
    putAllKeyValues(map);
  }

  private void putAllKeyValues(Map<? extends K, ? extends V> map) {
    for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public void clear() {
    isInValueUpdate = true;
    isInKeyUpdate = true;

    if (host != null && !host.skipHostUpdate()) {
      host.clearing();
    }
    keySet.clear();
    valueSet.clear();

    isInKeyUpdate = false;
    isInValueUpdate = false;
  }

  @NotNull
  @Override
  public OrderedSet<K> keySet() {
    return keySet;
  }

  @NotNull
  @Override
  public Collection<V> values() {
    if (!keySet.isSparse()) {
      return valueSet;
    }

    List<V> values = new ArrayList<>(keySet.size());
    values.addAll(valueSet);
    return values;
  }

  public OrderedSet<V> valueSet() {
    return valueSet;
  }

  @NotNull
  @Override
  public OrderedSet<Map.Entry<K, V>> entrySet() {
    return keyValueEntrySet();
  }

  ReversibleIndexedIterator<V> valueIterator() {
    return valueSet.iterator();
  }

  ReversibleIndexedIterator<K> keyIterator() {
    return keySet().iterator();
  }

  private ReversibleIndexedIterator<Entry<K, V>> entrySetIterator() {
    BitSet bitSet = getKeyValueUnionSet();
    return new IndexedIterator<>(getIndexedProxy(), new BitSetIterator(bitSet));
  }

  ReversibleIterable<Entry<K, V>> entrySetIterable() {
    BitSet bitSet = getKeyValueUnionSet();
    return new IndexedIterable<>(getIndexedProxy(), new BitSetIterable(bitSet));
  }

  private BitSet getKeyValueUnionSet() {
    BitSet bitSet = new BitSet(keySet.size());
    bitSet.or(keySet.getValidIndices());
    bitSet.or(valueSet.getValidIndices());
    return bitSet;
  }

  @NotNull
  @Override
  public Iterator<Map.Entry<K, V>> iterator() {
    return entrySetIterator();
  }

  @Override
  public void forEach(Consumer<? super Entry<K, V>> consumer) {
    Iterator<Map.Entry<K, V>> iterator = entrySetIterator();
    while (iterator.hasNext()) {
      consumer.accept(iterator.next());
    }
  }

  private OrderedSet<Map.Entry<K, V>> keyValueEntrySet() {
    // create it with inHostUpdate already set so we can populate it without callbacks
    isInValueUpdate = true;
    isInKeyUpdate = true;

    OrderedSet<Map.Entry<K, V>> values =
        new OrderedSet<>(
            keySet.size(),
            new CollectionHost<Map.Entry<K, V>>() {
              @Override
              public void adding(int index, @Nullable Map.Entry<K, V> entry, @Nullable Object v) {
                OrderedMultiMap.this.putKeyValue(entry.getKey(), entry.getValue());
              }

              @Override
              public Object removing(int index, @Nullable Map.Entry<K, V> entry) {
                boolean b =
                    OrderedMultiMap.this.removeEntryIndex(index, entry.getKey(), entry.getValue());
                return b ? entry : null;
              }

              @Override
              public void clearing() {
                OrderedMultiMap.this.clear();
              }

              @Override
              public void addingNulls(int index) {
                OrderedMultiMap.this.addNullEntry(index);
              }

              @Override
              public boolean skipHostUpdate() {
                return isInKeyUpdate || isInValueUpdate;
              }

              @Override
              public int getIteratorModificationCount() {
                return OrderedMultiMap.this.getModificationCount();
              }
            });

    Iterator<Map.Entry<K, V>> iterator = entrySetIterator();
    while (iterator.hasNext()) {
      values.add(iterator.next());
    }

    // release it for host update
    isInValueUpdate = false;
    isInKeyUpdate = false;

    return values;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }

    OrderedMultiMap<?, ?> set = (OrderedMultiMap<?, ?>) object;

    if (size() != set.size()) {
      return false;
    }
    return entrySet().equals(set.entrySet());
  }

  @Override
  public int hashCode() {
    int result = keySet.hashCode();
    result = 31 * result + valueSet.hashCode();
    return result;
  }
}
