package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.*;
import com.vladsch.flexmark.util.misc.Extension;
import org.junit.Test;

import java.util.*;

import static com.vladsch.flexmark.test.util.LoadUnloadDataKeyAggregator.LOAD_EXTENSIONS;
import static com.vladsch.flexmark.test.util.LoadUnloadDataKeyAggregator.UNLOAD_EXTENSIONS;
import static org.junit.Assert.assertEquals;

public class LoadUnloadDataKeyAggregatorTest {
    final public static DataKey<Collection<Class<? extends Extension>>> LOAD_EXTENSION_CLASSES = new DataKey<>("LOAD_EXTENSION_CLASSES", Collections.emptyList());

    final static HashMap<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("load1", new MutableDataSet().set(LOAD_EXTENSIONS, Collections.singletonList(new Extension1())));
        optionsMap.put("load2", new MutableDataSet().set(LOAD_EXTENSIONS, Collections.singletonList(new Extension2())));
        optionsMap.put("load3", new MutableDataSet().set(LOAD_EXTENSIONS, Collections.singletonList(new Extension3())));
        optionsMap.put("unload1", new MutableDataSet().set(UNLOAD_EXTENSIONS, Collections.singletonList(Extension1.class)));
        optionsMap.put("unload2", new MutableDataSet().set(UNLOAD_EXTENSIONS, Collections.singletonList(Extension2.class)));
        optionsMap.put("unload3", new MutableDataSet().set(UNLOAD_EXTENSIONS, Collections.singletonList(Extension3.class)));
    }
    static DataHolder getOption(String option) {
        return optionsMap.get(option);
    }

    static class Extension1 implements Extension { }

    static class Extension2 implements Extension { }

    static class Extension3 implements Extension { }

    @Test
    public void test_loadExtension() {
        DataHolder OPTIONS = new MutableDataSet();

        DataHolder result = TestUtils.getOptions(SpecExample.NULL, "load1", LoadUnloadDataKeyAggregatorTest::getOption);
        assertEquals(Collections.singletonList(Extension1.class), toClasses(LOAD_EXTENSIONS.get(result)));
    }

    @Test
    public void test_loadExtensions() {
        DataHolder OPTIONS = new MutableDataSet();

        DataHolder result = TestUtils.getOptions(SpecExample.NULL, "load1,load3", LoadUnloadDataKeyAggregatorTest::getOption);
        assertEquals(Arrays.asList(Extension1.class, Extension3.class), toClasses(LOAD_EXTENSIONS.get(result)));
        DataHolder result1 = DataSet.aggregate(OPTIONS, result);
        assertEquals(Arrays.asList(Extension1.class, Extension3.class), toClasses(SharedDataKeys.EXTENSIONS.get(result1)));

        DataHolder result2 = TestUtils.getOptions(SpecExample.NULL, "unload1,unload2", LoadUnloadDataKeyAggregatorTest::getOption);

        DataHolder result3 = DataSet.aggregate(result1, result2);
        assertEquals(Collections.singletonList(Extension3.class), toClasses(SharedDataKeys.EXTENSIONS.get(result3)));
    }

    static Collection<Class<?>> toClasses(Collection<Extension> extensions) {
        ArrayList<Class<?>> list = new ArrayList<>();
        for (Extension extension : extensions) {
            list.add(extension.getClass());
        }
        return list;
    }
}
