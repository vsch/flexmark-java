package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.collection.iteration.Indexed;
import com.vladsch.flexmark.util.collection.iteration.IndexedIterable;
import com.vladsch.flexmark.util.collection.iteration.IndexedIterator;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIndexedIterator;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class OrderedMap<K, V> implements Map<K, V>, Iterable<Map.Entry<K, V>> {
  private final OrderedSet<K> keySet;
  private final ArrayList<V> valueList;
  private final CollectionHost<K> host;
  private boolean inUpdate;
  private Indexed<Entry<K, V>> indexedEntryProxy;
  private Indexed<V> indexedValueProxy;

  public OrderedMap() {
    this(0, null);
  }

  public OrderedMap(int capacity) {
    this(capacity, null);
  }

  OrderedMap(CollectionHost<K> host) {
    this(0, host);
  }

  private OrderedMap(int capacity, CollectionHost<K> host) {
    this.valueList = new ArrayList<>(capacity);
    this.host = host;
    this.indexedEntryProxy = null;
    this.indexedValueProxy = null;
    this.keySet =
        new OrderedSet<>(
            capacity,
            new CollectionHost<K>() {
              @Override
              public void adding(int index, K k, Object v) {
                OrderedMap.this.adding(index, k, v);
              }

              @Override
              public Object removing(int index, K k) {
                return OrderedMap.this.removing(index, k);
              }

              @Override
              public void clearing() {
                OrderedMap.this.clearing();
              }

              @Override
              public void addingNulls(int index) {
                // can add anything, it will not be accessed, its a dummy holder
                OrderedMap.this.addingNull(index);
              }

              @Override
              public boolean skipHostUpdate() {
                return inUpdate;
              }

              @Override
              public int getIteratorModificationCount() {
                return OrderedMap.this.getModificationCount();
              }
            });
  }

  public Indexed<Map.Entry<K, V>> getIndexedEntryProxy() {
    if (indexedEntryProxy != null) {
      return indexedEntryProxy;
    }

    indexedEntryProxy =
        new Indexed<>() {
          @Override
          public Map.Entry<K, V> get(int index) {
            return OrderedMap.this.getEntry(index);
          }

          @Override
          public void set(int index, Map.Entry<K, V> item) {
            throw new UnsupportedOperationException();
          }

          @Override
          public void removeAt(int index) {
            OrderedMap.this.keySet.removeIndexHosted(index);
          }

          @Override
          public int size() {
            return OrderedMap.this.size();
          }

          @Override
          public int modificationCount() {
            return OrderedMap.this.getModificationCount();
          }
        };

    return indexedEntryProxy;
  }

  public Indexed<V> getIndexedValueProxy() {
    if (indexedValueProxy != null) {
      return indexedValueProxy;
    }

    indexedValueProxy =
        new Indexed<>() {
          @Override
          public V get(int index) {
            return OrderedMap.this.getValue(index);
          }

          @Override
          public void set(int index, V item) {
            throw new UnsupportedOperationException();
          }

          @Override
          public void removeAt(int index) {
            OrderedMap.this.keySet.removeIndexHosted(index);
          }

          @Override
          public int size() {
            return OrderedMap.this.size();
          }

          @Override
          public int modificationCount() {
            return OrderedMap.this.getModificationCount();
          }
        };

    return indexedValueProxy;
  }

  private Map.Entry<K, V> getEntry(int index) {
    return new MapEntry<>(keySet.getValue(index), valueList.get(index));
  }

  public int getModificationCount() {
    return keySet.getModificationCount();
  }

  private void adding(int index, K k, Object v) {
    if (v == null) {
      throw new IllegalArgumentException();
    }
    if (host != null && !host.skipHostUpdate()) {
      host.adding(index, k, v);
    }
    valueList.add((V) v);
  }

  private void addingNull(int index) {
    if (host != null && !host.skipHostUpdate()) {
      host.addingNulls(index);
    }
    addNulls(index);
  }

  private Object removing(int index, K k) {
    if (host != null && !host.skipHostUpdate()) {
      host.removing(index, k);
    }
    return valueList.get(index);
  }

  void clearing() {
    if (host != null && !host.skipHostUpdate()) {
      host.clearing();
    }
    valueList.clear();
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
  public boolean containsKey(Object o) {
    return keySet.contains(o);
  }

  @Override
  public boolean containsValue(Object o) {
    int index = valueList.indexOf(o);
    return keySet.isValidIndex(index);
  }

  private void addNulls(int index) {
    if (index < valueList.size())
      throw new IllegalArgumentException(
          "addNulls(" + index + ") called when valueList size is " + valueList.size());
    while (valueList.size() <= index) valueList.add(null);
  }

  @Override
  public V get(Object o) {
    int index = keySet.indexOf(o);
    return index == -1 ? null : valueList.get(index);
  }

  @Override
  public V put(K k, V v) {
    int index = keySet.indexOf(k);
    if (index == -1) {
      keySet.add(k, v);
      return null;
    }

    V old = valueList.get(index);
    valueList.set(index, v);
    return old;
  }

  @Override
  public V remove(Object o) {
    return (V) keySet.removeHosted(o);
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> map) {
    for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

  public void addAll(Collection<? extends Map.Entry<? extends K, ? extends V>> entries) {
    for (Map.Entry<? extends K, ? extends V> entry : entries) {
      put(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public void clear() {
    keySet.clear();
  }

  @Override
  public OrderedSet<K> keySet() {
    return keySet;
  }

  @Override
  public Collection<V> values() {
    if (!keySet.isSparse()) {
      return valueList;
    }

    List<V> values = new ArrayList<>(keySet.size());
    Iterator<Integer> iterator = keySet.indexIterator();
    while (iterator.hasNext()) {
      values.add(valueList.get(iterator.next()));
    }
    return values;
  }

  public V getValue(int index) {
    if (!keySet.isValidIndex(index)) {
      return null;
    }

    return valueList.get(index);
  }

  @Override
  public OrderedSet<Map.Entry<K, V>> entrySet() {
    // create it with inHostUpdate already set so we can populate it without callbacks
    inUpdate = true;
    OrderedSet<Map.Entry<K, V>> values =
        new OrderedSet<>(keySet.size(), new EntryCollectionHost<>());
    Iterator<Map.Entry<K, V>> iterator = entryIterator();
    while (iterator.hasNext()) {
      values.add(iterator.next());
    }

    // release it for host update
    inUpdate = false;

    return values;
  }

  public List<Entry<K, V>> entries() {
    // create it with inHostUpdate already set so we can populate it without callbacks
    List<Map.Entry<K, V>> values = new ArrayList<>();
    Iterator<Map.Entry<K, V>> iterator = entryIterator();
    while (iterator.hasNext()) {
      values.add(iterator.next());
    }
    return values;
  }

  public ReversibleIndexedIterator<V> valueIterator() {
    return new IndexedIterator<>(getIndexedValueProxy(), keySet.indexIterator());
  }

  private ReversibleIndexedIterator<Map.Entry<K, V>> entryIterator() {
    return new IndexedIterator<>(getIndexedEntryProxy(), keySet.indexIterator());
  }

  ReversibleIndexedIterator<Map.Entry<K, V>> reversedEntryIterator() {
    return new IndexedIterator<>(getIndexedEntryProxy(), keySet.reversedIndexIterator());
  }

  public ReversibleIterable<V> valueIterable() {
    return new IndexedIterable<>(getIndexedValueProxy(), keySet.indexIterable());
  }

  ReversibleIterable<Map.Entry<K, V>> entryIterable() {
    return new IndexedIterable<>(getIndexedEntryProxy(), keySet.indexIterable());
  }

  private ReversibleIterable<Entry<K, V>> reversedEntryIterable() {
    return new IndexedIterable<>(getIndexedEntryProxy(), keySet.reversedIndexIterable());
  }

  ReversibleIterable<Map.Entry<K, V>> reversedIterable() {
    return reversedEntryIterable();
  }

  @Override
  public ReversibleIndexedIterator<Map.Entry<K, V>> iterator() {
    return entryIterator();
  }

  @Override
  public void forEach(Consumer<? super Entry<K, V>> consumer) {
    Iterator<Map.Entry<K, V>> iterator = iterator();
    while (iterator.hasNext()) {
      consumer.accept(iterator.next());
    }
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }

    OrderedMap<?, ?> set = (OrderedMap<?, ?>) object;

    if (size() != set.size()) {
      return false;
    }

    return entrySet().equals(set.entrySet());
  }

  @Override
  public int hashCode() {
    int result = keySet.hashCode();
    result = 31 * result + valueList.hashCode();
    return result;
  }

  private class EntryCollectionHost<K2 extends K, V2 extends V>
      implements CollectionHost<Map.Entry<K2, V2>> {
    @Override
    public void adding(int index, Entry<K2, V2> entry, Object v) {
      OrderedMap.this.keySet.add(entry.getKey(), entry.getValue());
    }

    @Override
    public Object removing(int index, Entry<K2, V2> entry) {
      OrderedMap.this.keySet.removeIndex(index);
      return entry;
    }

    @Override
    public void clearing() {
      OrderedMap.this.keySet.clear();
    }

    @Override
    public void addingNulls(int index) {
      OrderedMap.this.keySet.addNulls(index);
    }

    @Override
    public boolean skipHostUpdate() {
      return inUpdate;
    }

    @Override
    public int getIteratorModificationCount() {
      return OrderedMap.this.getModificationCount();
    }
  }
}
