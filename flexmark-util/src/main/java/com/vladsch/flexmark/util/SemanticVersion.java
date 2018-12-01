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

package com.vladsch.flexmark.util;

import static com.vladsch.flexmark.util.Utils.parseIntOrNull;

public class SemanticVersion implements Comparable<SemanticVersion> {
    public final Integer major;
    public final Integer minor;
    public final Integer patch;
    public final Integer tweak;
    public final String tail;
    public final boolean tailSignificant;

    public SemanticVersion(CharSequence version) {
        this(version, false);
    }
    
    public SemanticVersion(CharSequence version, boolean tailIsSignificant) {
        String text = String.valueOf(version);
        String[] parts = text.split("\\.", 5);
        major = parseIntOrNull(parts[0]);
        minor = parts.length > 1 ? parseIntOrNull(parts[1]) : null;
        patch = parts.length > 2 ? parseIntOrNull(parts[2]) : null;
        tweak = parts.length > 3 ? parseIntOrNull(parts[3]) : null;
        tail = parts.length > 4 ? parts[4] : null;
        tailSignificant = tailIsSignificant;
    }

    public int compareTo(final SemanticVersion o) {
        int val = Utils.compareNullable(major, o.major);
        if (val == 0) {
            val = Utils.compareNullable(minor, o.minor);
            if (val == 0) {
                val = Utils.compareNullable(patch, o.patch);
                if (val == 0) {
                    val = Utils.compareNullable(tweak, o.tweak);
                    if (val == 0 && tailSignificant && tail != null && o.tail != null) {
                        val = tail.compareTo(o.tail); 
                    }
                }
            }
        }
        return val;
    }

    public int compareTo(final String text) {
        SemanticVersion o = new SemanticVersion(text, tailSignificant);
        return compareTo(o);
    }
}
