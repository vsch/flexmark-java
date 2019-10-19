package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.PathologicalSpcUrlTest;
import com.vladsch.flexmark.html.PathologicalTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PathologicalTest.class,
        PathologicalSpcUrlTest.class,
})
final public class PathologicalTestSuite {
}
