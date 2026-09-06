package com.vladsch.flexmark.util;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.regex.Pattern;

public class ExceptionMatcher extends BaseMatcher<Throwable> {
    final private String prefix;
    final private Pattern pattern;
    final private String message;

    public ExceptionMatcher(Class<? extends Throwable> throwable, Pattern pattern, String message) {
        this.prefix = throwable.getName();
        this.pattern = pattern;
        this.message = message;
    }

    @Override
    public boolean matches(Object o) {
        if (o instanceof RuntimeException) {
            if (o.toString().startsWith(prefix + ": ")) {
                return pattern.matcher(o.toString().substring(prefix.length() + ": ".length())).matches();
            }
        } else if (o instanceof Throwable) {
            if (o.toString().startsWith(prefix)) {
                String input;
                Throwable throwable = (Throwable) o;
                if (throwable.getCause() == null) {
                    input = o.toString();
                } else {
                    input = throwable.getCause().toString();
                }
                return pattern.matcher(input).matches();
            }
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(prefix + ": " + message);
    }

    public static ExceptionMatcher match(Class<? extends Throwable> throwable, String text) {
        return new ExceptionMatcher(throwable, Pattern.compile(Pattern.quote(text)), text);
    }

    public static ExceptionMatcher matchPrefix(Class<? extends Throwable> throwable, String text) {
        return new ExceptionMatcher(throwable, Pattern.compile(Pattern.quote(text) + "(?s:.*)"), text);
    }

    public static ExceptionMatcher matchRegEx(Class<? extends Throwable> throwable, String regEx) {
        return new ExceptionMatcher(throwable, Pattern.compile(regEx), regEx);
    }
}
