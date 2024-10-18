package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.misc.Paired;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;

class CollectionHostValidator<T> {
  private final List<Paired<String, Object[]>> expectedCallBacks = new ArrayList<>();
  private int nextCallbackIndex;
  private int modificationCount;
  private String id;
  private boolean conditional;
  private int repeat;

  CollectionHostValidator() {
    reset();
  }

  private CollectionHostValidator<T> start() {
    nextCallbackIndex = 0;
    modificationCount = 0;
    this.id = "";
    hadExpect();
    return this;
  }

  CollectionHostValidator<T> setRepeat(int repeat) {
    this.repeat = repeat;
    return this;
  }

  CollectionHostValidator<T> reset() {
    start();
    expectedCallBacks.clear();
    return this;
  }

  private void hadExpect() {
    conditional = true;
    repeat = 1;
  }

  CollectionHostValidator<T> setId(int id) {
    this.id = String.valueOf(id);
    return this;
  }

  CollectionHostValidator<T> setConditional(boolean conditional) {
    this.conditional = conditional;
    return this;
  }

  private void expect(String callBack, Object... params) {
    if (conditional) {
      int i = 0;
      while (i++ < repeat) {
        expectedCallBacks.add(new Pair<>(callBack, params));
      }
    }
    hadExpect();
  }

  CollectionHostValidator<T> test(Runnable test) {
    test.run();
    validate();
    start();
    return this;
  }

  private static String hostName(String host) {
    return host.trim().isEmpty() ? "" : "" + host + ".";
  }

  private String id() {
    return id.trim().isEmpty() ? "" : "[" + id + "] ";
  }

  CollectionHostValidator<T> expectAdding(int index, T s, Object v) {
    return expectAddingFrom("", index, s, v);
  }

  CollectionHostValidator<T> expectRemoving(int index, T s) {
    return expectRemovingFrom("", index, s);
  }

  CollectionHostValidator<T> expectClearing() {
    return expectClearingFrom("");
  }

  private CollectionHostValidator<T> expectAddingFrom(String host, int index, T s, Object v) {
    expect(hostName(host) + "adding", index, s, v);
    return this;
  }

  private CollectionHostValidator<T> expectRemovingFrom(String host, int index, T s) {
    expect(hostName(host) + "removing", index, s);
    return this;
  }

  private CollectionHostValidator<T> expectClearingFrom(String host) {
    expect(hostName(host) + "clearing");
    return this;
  }

  private CollectionHostValidator<T> validate() {
    if (nextCallbackIndex < expectedCallBacks.size()) {
      StringBuilder out = new StringBuilder();

      out.append("\n").append(id()).append("Missing callbacks").append(":\n");
      for (int i = nextCallbackIndex; i < expectedCallBacks.size(); i++) {
        Paired<String, Object[]> pair = expectedCallBacks.get(i);
        String expected = prepareMessage(pair.getFirst(), pair.getSecond());
        out.append("    [").append(i).append("]:").append(expected).append("\n");
      }

      Assert.fail(out.toString());
    }
    start();
    return this;
  }

  CollectionHost<T> getHost() {
    return getHost("");
  }

  private CollectionHost<T> getHost(String host) {
    return new CollectionHost<>() {
      @Override
      public void adding(int index, @Nullable T s, @Nullable Object v) {
        validate(hostName(host) + "adding", index, s, v);
        modificationCount++;
      }

      @Override
      public Object removing(int index, @Nullable T s) {
        validate(hostName(host) + "removing", index, s);
        modificationCount++;
        return null;
      }

      @Override
      public void clearing() {
        validate(hostName(host) + "clearing");
        modificationCount++;
      }

      @Override
      public void addingNulls(int index) {
        validate(hostName(host) + "addingNull", index);
        modificationCount++;
      }

      @Override
      public boolean skipHostUpdate() {
        return false;
      }

      @Override
      public int getIteratorModificationCount() {
        return modificationCount;
      }
    };
  }

  private static String prepareMessage(String callBack, Object... params) {
    StringBuilder out = new StringBuilder();
    out.append(' ').append(callBack).append('(');
    boolean first = true;
    for (Object param : params) {
      if (first) {
        first = false;
      } else {
        out.append(", ");
      }

      if (param == null) {
        out.append("null");
      } else {
        out.append(
                param
                    .getClass()
                    .getName()
                    .substring(param.getClass().getPackage().getName().length() + 1))
            .append(' ')
            .append(param);
      }
    }
    out.append(')');
    return out.toString();
  }

  private void validate(String callBack, Object... params) {
    String actual = prepareMessage(callBack, params);
    int index = nextCallbackIndex;

    if (nextCallbackIndex >= expectedCallBacks.size()) {
      throw new IllegalStateException(
          id() + "un-expected callback[" + nextCallbackIndex++ + "]" + actual);
    }

    Paired<String, Object[]> pair = expectedCallBacks.get(nextCallbackIndex++);
    String expected = prepareMessage(pair.getFirst(), pair.getSecond());

    Assert.assertEquals(id() + "callback[" + index + "] mismatch", expected, actual);
  }
}
