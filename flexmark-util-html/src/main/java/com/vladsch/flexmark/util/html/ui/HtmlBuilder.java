/*
 * Copyright (c) 2016-2018 Vladimir Schneider <vladimir.schneider@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.vladsch.flexmark.util.html.ui;

import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.AttributeImpl;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.HtmlAppendableBase;
import com.vladsch.flexmark.util.sequence.LineAppendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.plaf.FontUIResource;
import java.awt.Font;
import java.util.HashMap;

@SuppressWarnings("WeakerAccess")
public class HtmlBuilder extends HtmlAppendableBase<HtmlBuilder> {
    public HtmlBuilder() {
        super(0, LineAppendable.F_PASS_THROUGH);
    }

    public HtmlBuilder(int indentSize, int formatOptions) {
        super(indentSize, formatOptions);
    }

    @SuppressWarnings({ "UnusedReturnValue", "WeakerAccess" })
    public HtmlBuilder closeAllTags() {
        while (!getOpenTags().isEmpty()) {
            CharSequence tag = getOpenTags().peek();
            closeTag(tag);
        }
        return this;
    }

    public String toFinalizedString() {
        //if (!openTags.isEmpty()) throw new IllegalStateException("Unclosed tags on toHtml call: " + tagStack());
        closeAllTags();
        return toString(0, 0);
    }

    @SuppressWarnings("WeakerAccess")
    public HtmlBuilder attr(Object... convertible) {
        for (Object convert : convertible) {
            if (convert instanceof Attribute) {
                super.attr((Attribute) convert);
                super.withAttr();
            } else {
                //noinspection rawtypes
                HtmlStyler styler = getHtmlStyler(convert);
                // NOTE: show not simple name but name of container class if any
                if (styler == null) throw new IllegalStateException("Don't know how to style " + convert.getClass().getName().substring(getClass().getPackage().getName().length() + 1));

                //noinspection unchecked
                String value = styler.getStyle(styler.getStyleable(convert));
                if (value != null && !value.isEmpty()) {
                    Attribute style = AttributeImpl.of(Attribute.STYLE_ATTR, value);
                    super.attr(style);
                    super.withAttr();
                }
            }
        }
        return this;
    }

    @NotNull
    @Override
    public HtmlBuilder attr(@NotNull CharSequence name, @Nullable CharSequence value) {
        super.withAttr();
        return super.attr(name, value);
    }

    public HtmlBuilder style(CharSequence value) {
        super.withAttr();
        return super.attr(Attribute.STYLE_ATTR, value);
    }

    @NotNull
    @Override
    public HtmlBuilder attr(@NotNull Attribute... attribute) {
        super.withAttr();
        return super.attr(attribute);
    }

    @NotNull
    @Override
    public HtmlBuilder attr(@NotNull Attributes attributes) {
        super.withAttr();
        return super.attr(attributes);
    }

    public HtmlBuilder span() {
        return tag("span", false);
    }

    public HtmlBuilder span(CharSequence text) {
        tag("span", false);
        text(text);
        return closeSpan();
    }

    public HtmlBuilder span(boolean withLine, Runnable runnable) {
        return tag("span", false, withLine, runnable);
    }

    public HtmlBuilder span(Runnable runnable) {
        return span(false, runnable);
    }

    public HtmlBuilder spanLine(Runnable runnable) {
        return span(true, runnable);
    }

    public HtmlBuilder closeSpan() {
        return closeTag("span");
    }

    // statics
    final public static HashMap<Class, HtmlStyler> stylerMap = new HashMap<>();
    static {
        ColorStyler colorStyler = new ColorStyler();
        stylerMap.put(BackgroundColor.class, colorStyler);
        stylerMap.put(Color.class, colorStyler);
        //stylerMap.put(JBColor.class, colorStyler);
        stylerMap.put(java.awt.Color.class, colorStyler);

        FontStyler fontStyler = new FontStyler();
        stylerMap.put(Font.class, fontStyler);
        stylerMap.put(FontUIResource.class, fontStyler);

        stylerMap.put(FontStyle.class, new FontStyleStyler());
    }
    public static void addColorStylerClass(Class clazz) {
        HtmlStyler styler = stylerMap.get(Color.class);
        stylerMap.put(clazz, styler);
    }

    public static HtmlStyler getHtmlStyler(Object item) {
        HtmlStyler styler = stylerMap.get(item.getClass());
        if (styler != null) return styler;

        // see if we have one that can handle this
        for (Class value : stylerMap.keySet()) {
            //noinspection unchecked
            if (value.isAssignableFrom(item.getClass())) {
                styler = stylerMap.get(value);
                break;
            }
        }

        if (styler != null) {
            stylerMap.put(item.getClass(), styler);
        }
        return styler;
    }

    public static Attribute getAttribute(Object item) {
        HtmlStyler styler = getHtmlStyler(item);
        if (styler != null) {
            //noinspection unchecked
            String value = styler.getStyle(styler.getStyleable(item));
            if (value != null && !value.isEmpty()) {
                return AttributeImpl.of(Attribute.STYLE_ATTR, value);
            }
        }
        return null;
    }

    // mimic string builder for comfort
    public HtmlBuilder append(Object obj) { return super.append(String.valueOf(obj)); }

    public HtmlBuilder append(String str) { return super.append(str); }

    public HtmlBuilder append(StringBuffer sb) { return super.append(sb.toString()); }

    @NotNull
    public HtmlBuilder append(@NotNull CharSequence s) { return super.append(s); }

    @NotNull
    public HtmlBuilder append(@NotNull CharSequence s, int start, int end) { return super.append(s, start, end); }

    public HtmlBuilder append(char[] str) { return super.append(String.valueOf(str)); }

    public HtmlBuilder append(char[] str, int offset, int len) { return super.append(String.valueOf(str, offset, len)); }

    public HtmlBuilder append(boolean b) { return super.append(b ? "true" : "false"); }

    @NotNull
    public HtmlBuilder append(char c) { return super.append(c); }

    public HtmlBuilder append(int i) { return super.append(String.valueOf(i)); }

    public HtmlBuilder append(long l) { return super.append(String.valueOf(l)); }

    public HtmlBuilder append(float f) { return super.append(String.valueOf(f)); }

    public HtmlBuilder append(double d) { return super.append(String.valueOf(d)); }
}
