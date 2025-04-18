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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HtmlHelpersTest {

    @Test
    public void test_mixedColor() {
        assertEquals(Color.of("#807240").toString(), Color.of(HtmlHelpers.mixedColor(Color.BLACK, Color.ORANGE)).toString());
    }
}
