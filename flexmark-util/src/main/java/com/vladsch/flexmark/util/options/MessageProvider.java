package com.vladsch.flexmark.util.options;

import java.text.MessageFormat;

public interface MessageProvider {
    MessageProvider DEFAULT = new MessageProvider() {
        @Override
        public String message(String key, String defaultText, Object... params) {
            return params.length > 0 && defaultText.indexOf('{') >= 0 ? MessageFormat.format(defaultText, params) : defaultText;
        }
    };

    String message(String key, String defaultText, Object... params);
}
