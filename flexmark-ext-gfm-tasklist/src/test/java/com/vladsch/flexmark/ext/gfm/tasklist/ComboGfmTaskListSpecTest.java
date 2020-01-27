package com.vladsch.flexmark.ext.gfm.tasklist;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboGfmTaskListSpecTest extends RendererSpecTest {
    static final String SPEC_RESOURCE = "/ext_gfm_tasklist_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(TaskListExtension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("no-suffix-content", new MutableDataSet().set(Parser.LISTS_ITEM_CONTENT_AFTER_SUFFIX, true));
        optionsMap.put("marker-space", new MutableDataSet().set(Parser.LISTS_ITEM_MARKER_SPACE, true));
        optionsMap.put("src-pos-lines", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_PARAGRAPH_LINES, true));
        optionsMap.put("item-class", new MutableDataSet().set(TaskListExtension.TIGHT_ITEM_CLASS, ""));
        optionsMap.put("loose-class", new MutableDataSet().set(TaskListExtension.LOOSE_ITEM_CLASS, ""));
        optionsMap.put("closed-item-class", new MutableDataSet().set(TaskListExtension.ITEM_DONE_CLASS, "closed-task"));
        optionsMap.put("open-item-class", new MutableDataSet().set(TaskListExtension.ITEM_NOT_DONE_CLASS, "open-task"));
        optionsMap.put("p-class", new MutableDataSet().set(TaskListExtension.PARAGRAPH_CLASS, "task-item"));
        optionsMap.put("done", new MutableDataSet().set(TaskListExtension.ITEM_DONE_MARKER, "<span class=\"taskitem\">X</span>"));
        optionsMap.put("not-done", new MutableDataSet().set(TaskListExtension.ITEM_NOT_DONE_MARKER, "<span class=\"taskitem\">O</span>"));
        optionsMap.put("no-ordered-items", new MutableDataSet().set(Parser.LISTS_NUMBERED_ITEM_MARKER_SUFFIXED, false));
        optionsMap.put("kramdown", new MutableDataSet()
                .setFrom(ParserEmulationProfile.KRAMDOWN.getOptions())
                .set(Parser.EXTENSIONS, Collections.singleton(TaskListExtension.create()))
        );
        optionsMap.put("markdown", new MutableDataSet()
                .setFrom(ParserEmulationProfile.MARKDOWN.getOptions())
                .set(Parser.EXTENSIONS, Collections.singleton(TaskListExtension.create()))
        );
    }
    public ComboGfmTaskListSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
