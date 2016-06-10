/*
 * Copyright (c) 2015-2015 Vladimir Schneider <vladimir.schneider@gmail.com>
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.vladsch.flexmark;

import com.vladsch.flexmark.ext.abbreviation.ExtAbbreviationTestSuite;
import com.vladsch.flexmark.ext.autolink.ExtAutolinkTestSuite;
import com.vladsch.flexmark.ext.emoji.ExtEmojiTestSuite;
import com.vladsch.flexmark.ext.footnotes.ExtFootnotesTestSuite;
import com.vladsch.flexmark.ext.front.matter.ExtYamlFrontMatterTestSuite;
import com.vladsch.flexmark.ext.gfm.strikethrough.ExtGfmStrikethroughTestSuite;
import com.vladsch.flexmark.ext.gfm.tables.ExtGfmTablesTestSuite;
import com.vladsch.flexmark.integration.IntegrationTestSuite;
import com.vladsch.flexmark.test.CoreTestSuite;
import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        CoreTestSuite.class,
        ExtAutolinkTestSuite.class,
        ExtGfmStrikethroughTestSuite.class,
        ExtGfmTablesTestSuite.class,
        ExtYamlFrontMatterTestSuite.class,
        ExtAbbreviationTestSuite.class,
        ExtEmojiTestSuite.class,
        ExtFootnotesTestSuite.class,
        IntegrationTestSuite.class,
})
public class TestTestSuite {
}
