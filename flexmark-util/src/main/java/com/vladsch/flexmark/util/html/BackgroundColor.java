/*
 * Copyright (c) 2016-2018 Vladimir Schneider <vladimir.schneider@gmail.com>
 *
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */

package com.vladsch.flexmark.util.html;

@SuppressWarnings({ "WeakerAccess" })
public class BackgroundColor extends java.awt.Color {
    public static final BackgroundColor NULL = new BackgroundColor(new java.awt.Color(0, true));
    public final static BackgroundColor WHITE = new BackgroundColor(java.awt.Color.WHITE);
    public final static BackgroundColor LIGHT_GRAY = new BackgroundColor(java.awt.Color.LIGHT_GRAY);
    public final static BackgroundColor GRAY = new BackgroundColor(java.awt.Color.GRAY);
    public final static BackgroundColor DARK_GRAY = new BackgroundColor(java.awt.Color.DARK_GRAY);
    public final static BackgroundColor BLACK = new BackgroundColor(java.awt.Color.BLACK);
    public final static BackgroundColor RED = new BackgroundColor(java.awt.Color.RED);
    public final static BackgroundColor PINK = new BackgroundColor(java.awt.Color.PINK);
    public final static BackgroundColor ORANGE = new BackgroundColor(java.awt.Color.ORANGE);
    public final static BackgroundColor YELLOW = new BackgroundColor(java.awt.Color.YELLOW);
    public final static BackgroundColor GREEN = new BackgroundColor(java.awt.Color.GREEN);
    public final static BackgroundColor MAGENTA = new BackgroundColor(java.awt.Color.MAGENTA);
    public final static BackgroundColor CYAN = new BackgroundColor(java.awt.Color.CYAN);
    public final static BackgroundColor BLUE = new BackgroundColor(java.awt.Color.BLUE);

    protected BackgroundColor(java.awt.Color other) { super(other.getRGB()); }

    protected BackgroundColor(int rgb) { super(rgb); }

    public static BackgroundColor of(java.awt.Color color) { return new BackgroundColor(color); }

    public static BackgroundColor of(int rgb) { return new BackgroundColor(rgb); }

    public static BackgroundColor of(String colorName) {
        Integer rgb = ColorStyler.getNamedColor(colorName);
        return rgb == null ? NULL : new BackgroundColor(rgb);
    }
}
