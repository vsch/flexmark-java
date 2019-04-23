package com.vladsch.flexmark.util.collection;

import java.util.function.Function;
import com.vladsch.flexmark.util.options.DataHolder;

public interface DataValueFactory<T> extends Function<DataHolder, T> {

}

