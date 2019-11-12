package com.vladsch.flexmark.ext.autolink;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AutolinkTest.class,
        ComboAutolinkSpecTest.class,
        MergeAutoLinkTest.class,
})
public class ExtAutolinkTestSuite {
}
