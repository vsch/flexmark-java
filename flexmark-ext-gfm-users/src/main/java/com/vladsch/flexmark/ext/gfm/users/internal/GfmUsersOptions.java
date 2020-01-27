package com.vladsch.flexmark.ext.gfm.users.internal;

import com.vladsch.flexmark.ext.gfm.users.GfmUsersExtension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;
import org.jetbrains.annotations.NotNull;

class GfmUsersOptions implements MutableDataSetter {
    final public String gitHubIssuesUrlRoot;
    final public String gitHubIssueUrlPrefix;
    final public String gitHubIssueUrlSuffix;
    final public String gitHubUserTextPrefix;
    final public String gitHubUserTextSuffix;

    public GfmUsersOptions(DataHolder options) {
        gitHubIssuesUrlRoot = GfmUsersExtension.GIT_HUB_USERS_URL_ROOT.get(options);
        gitHubIssueUrlPrefix = GfmUsersExtension.GIT_HUB_USER_URL_PREFIX.get(options);
        gitHubIssueUrlSuffix = GfmUsersExtension.GIT_HUB_USER_URL_SUFFIX.get(options);
        gitHubUserTextPrefix = GfmUsersExtension.GIT_HUB_USER_HTML_PREFIX.get(options);
        gitHubUserTextSuffix = GfmUsersExtension.GIT_HUB_USER_HTML_SUFFIX.get(options);
    }

    @NotNull
    @Override
    public MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder) {
        dataHolder.set(GfmUsersExtension.GIT_HUB_USERS_URL_ROOT, gitHubIssuesUrlRoot);
        dataHolder.set(GfmUsersExtension.GIT_HUB_USER_URL_PREFIX, gitHubIssueUrlPrefix);
        dataHolder.set(GfmUsersExtension.GIT_HUB_USER_URL_SUFFIX, gitHubIssueUrlSuffix);
        dataHolder.set(GfmUsersExtension.GIT_HUB_USER_HTML_PREFIX, gitHubUserTextPrefix);
        dataHolder.set(GfmUsersExtension.GIT_HUB_USER_HTML_SUFFIX, gitHubUserTextSuffix);
        return dataHolder;
    }
}
