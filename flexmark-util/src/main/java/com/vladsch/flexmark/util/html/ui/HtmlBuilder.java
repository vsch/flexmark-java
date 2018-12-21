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

import com.vladsch.flexmark.util.html.*;
import com.vladsch.flexmark.util.sequence.CharSubSequence;

import javax.swing.plaf.FontUIResource;
import java.awt.Font;
import java.util.HashMap;

@SuppressWarnings("WeakerAccess")
public class HtmlBuilder extends HtmlFormattingAppendableBase<HtmlBuilder> {
    private final Appendable myBuilder;

    public HtmlBuilder() {
        super(new StringBuilder(), 0, FormattingAppendable.PASS_THROUGH);
        myBuilder = getAppendable();
    }

    public HtmlBuilder(final int indentSize, final int formatOptions) {
        super(new StringBuilder(), indentSize, formatOptions);
        myBuilder = getAppendable();
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
        //if (!myOpenTags.isEmpty()) throw new IllegalStateException("Unclosed tags on toHtml call: " + tagStack());
        closeAllTags();
        flush();
        return myBuilder.toString();
    }

    @Override
    public String toString() {
        return myBuilder.toString();
    }

    @SuppressWarnings("WeakerAccess")
    public HtmlBuilder attr(Object... convertible) {
        for (Object convert : convertible) {
            if (convert instanceof Attribute) {
                super.attr((Attribute) convert);
                super.withAttr();
            } else {
                HtmlStyler styler = getHtmlStyler(convert);
                if (styler == null) throw new IllegalStateException("Don't know how to style " + convert.getClass().getSimpleName());

                //noinspection unchecked
                final String value = styler.getStyle(styler.getStyleable(convert));
                if (value != null && !value.isEmpty()) {
                    Attribute style = AttributeImpl.of(Attribute.STYLE_ATTR, value);
                    super.attr(style);
                    super.withAttr();
                }
            }
        }
        return this;
    }

    @Override
    public HtmlBuilder attr(final CharSequence name, final CharSequence value) {
        super.withAttr();
        return super.attr(name, value);
    }

    public HtmlBuilder style(final CharSequence value) {
        super.withAttr();
        return super.attr(Attribute.STYLE_ATTR, value);
    }

    @Override
    public HtmlBuilder attr(final Attribute... attribute) {
        super.withAttr();
        return super.attr(attribute);
    }

    @Override
    public HtmlBuilder attr(final Attributes attributes) {
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

    public HtmlBuilder span(final boolean withLine, final Runnable runnable) {
        return tag("span", false, withLine, runnable);
    }

    public HtmlBuilder span(final Runnable runnable) {
        return span(false, runnable);
    }

    public HtmlBuilder spanLine(final Runnable runnable) {
        return span(true, runnable);
    }

    public HtmlBuilder closeSpan() {
        return closeTag("span");
    }

    // statics
    public static final HashMap<Class, HtmlStyler> stylerMap = new HashMap<>();
    static {
        final ColorStyler colorStyler = new ColorStyler();
        stylerMap.put(BackgroundColor.class, colorStyler);
        stylerMap.put(Color.class, colorStyler);
        //stylerMap.put(JBColor.class, colorStyler);
        stylerMap.put(java.awt.Color.class, colorStyler);

        final FontStyler fontStyler = new FontStyler();
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
            final String value = styler.getStyle(styler.getStyleable(item));
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

    public HtmlBuilder append(CharSequence s) { return super.append(s); }

    public HtmlBuilder append(CharSequence s, int start, int end) { return super.append(s, start, end); }

    public HtmlBuilder append(char[] str) { return super.append(CharSubSequence.of(str, 0, str.length)); }

    public HtmlBuilder append(char[] str, int offset, int len) { return super.append(CharSubSequence.of(str, offset, offset + len)); }

    public HtmlBuilder append(boolean b) { return super.append(b ? "true" : "false"); }

    public HtmlBuilder append(char c) { return super.append(c); }

    public HtmlBuilder append(int i) { return super.append(String.valueOf(i)); }

    public HtmlBuilder append(long l) { return super.append(String.valueOf(l)); }

    public HtmlBuilder append(float f) { return super.append(String.valueOf(f)); }

    public HtmlBuilder append(double d) { return super.append(String.valueOf(d)); }
}
