package com.vladsch.flexmark.util.sequence;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.junit.Assert.assertArrayEquals;

public class PlaceholderReplacerTest {
    static List<String[]> spansOf(String... spans) {
        ArrayList<String[]> params = new ArrayList<>(spans.length);
        for (String span : spans) {
            params.add(new String[] { span });
        }
        return params;
    }

    static String[] spansOf(List<String[]> spans) {
        String[] params = new String[spans.size()];

        int i = 0;
        for (String[] span : spans) {
            params[i++] = span[0];
        }
        return params;
    }

    static Function<String[], String> ourGetter = span -> span[0];
    static BiConsumer<String[], String> ourSetter = (span, text) -> span[0] = text;

    @Test
    public void test_simple() {
        HashMap<String, String> map = new HashMap<>();
        map.put("NAME", "Joe Smith");
        map.put("USER", "<NAME>");

        List<String[]> params = spansOf("<NAME>", "<USER>");
        PlaceholderReplacer.replaceAll(params, map::get, '<', '>', ourGetter, ourSetter);

        assertArrayEquals(new String[] { "Joe Smith", "<NAME>" }, spansOf(params));
    }

    @Test
    public void test_spanSimple2() {
        HashMap<String, String> map = new HashMap<>();
        map.put("NAME", "Joe Smith");
        map.put("USER", "<NAME>");

        List<String[]> params = spansOf("<NA", "ME>");
        PlaceholderReplacer.replaceAll(params, map::get, '<', '>', ourGetter, ourSetter);

        assertArrayEquals(new String[] { "", "Joe Smith" }, spansOf(params));
    }

    @Test
    public void test_spanSimple3() {
        HashMap<String, String> map = new HashMap<>();
        map.put("NAME", "Joe Smith");
        map.put("USER", "<NAME>");

        List<String[]> params = spansOf("<", "USER", ">");
        PlaceholderReplacer.replaceAll(params, map::get, '<', '>', ourGetter, ourSetter);

        assertArrayEquals(new String[] { "", "", "<NAME>" }, spansOf(params));
    }

    @Test
    public void test_spanComplex() {
        HashMap<String, String> map = new HashMap<>();
        map.put("NAME", "Joe Smith");
        map.put("USER", "<NAME>");

        List<String[]> params = spansOf("<NA", "ME> <", "USER", ">");
        PlaceholderReplacer.replaceAll(params, map::get, '<', '>', ourGetter, ourSetter);

        assertArrayEquals(new String[] { "", "Joe Smith ", "", "<NAME>" }, spansOf(params));
    }

    @Test
    public void test_spanComplex2() {
        HashMap<String, String> map = new HashMap<>();
        map.put("NAME", "Joe Smith");
        map.put("USER", "<NAME>");

        List<String[]> params = spansOf("<NA", "ME> <U", "SER", ">");
        PlaceholderReplacer.replaceAll(params, map::get, '<', '>', ourGetter, ourSetter);

        assertArrayEquals(new String[] { "", "Joe Smith ", "", "<NAME>" }, spansOf(params));
    }

    @Test
    public void test_spanUndefined() {
        HashMap<String, String> map = new HashMap<>();
        map.put("NAME", "Joe Smith");
        map.put("USER", "<NAME>");

        List<String[]> params = spansOf("<NA", "ME> <U", "SER", ">");
        PlaceholderReplacer.replaceAll(params, map::get, '<', '>', ourGetter, ourSetter);

        assertArrayEquals(new String[] { "", "Joe Smith ", "", "<NAME>" }, spansOf(params));
    }
}
