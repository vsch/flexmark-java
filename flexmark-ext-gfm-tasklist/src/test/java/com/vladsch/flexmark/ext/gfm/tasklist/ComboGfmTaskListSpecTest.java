package com.vladsch.flexmark.ext.gfm.tasklist;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.internal.util.MutableDataSet;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboGfmTaskListSpecTest extends ComboSpecTestCase {
    static final String SPEC_RESOURCE = "/ext_gfm_tasklist_ast_spec.txt";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(Parser.EXTENSIONS, Collections.singleton(TaskListExtension.create()));

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("item-class", new MutableDataSet()
                .set(TaskListExtension.ITEM_CLASS, "")
        );
        optionsMap.put("loose-class", new MutableDataSet()
                .set(TaskListExtension.LOOSE_ITEM_CLASS, "")
        );
        optionsMap.put("p-class", new MutableDataSet()
                .set(TaskListExtension.PARAGRAPH_CLASS, "task-item")
        );
        optionsMap.put("done", new MutableDataSet()
                .set(TaskListExtension.ITEM_DONE_MARKER, "<span class=\"taskitem\">X</span>")
        );
        optionsMap.put("not-done", new MutableDataSet()
                .set(TaskListExtension.ITEM_NOT_DONE_MARKER, "<span class=\"taskitem\">O</span>")
        );
    }

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    static DataHolder optionsSet(String optionSet) {
        return optionsMap.get(optionSet);
    }

    public ComboGfmTaskListSpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<SpecExample> examples = SpecReader.readExamples(SPEC_RESOURCE);
        List<Object[]> data = new ArrayList<>();

        // NULL example runs full spec test
        data.add(new Object[] { SpecExample.NULL });

        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    @Override
    protected DataHolder options(String optionSet) {
        return optionsSet(optionSet);
    }

    @Override
    protected String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    protected Parser parser() {
        return PARSER;
    }

    @Override
    protected HtmlRenderer renderer() {
        return RENDERER;
    }
}
