package com.vladsch.flexmark.ext.gfm.users.internal;

import com.vladsch.flexmark.ext.gfm.users.GfmUsersExtension;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSetter;

class GfmUsersOptions implements MutableDataSetter {
    public final String gitHubIssuesUrlRoot;
    public final String gitHubIssueUrlPrefix;
    public final String gitHubIssueUrlSuffix;
    public final String gitHubUserTextPrefix;
    public final String gitHubUserTextSuffix;

    public GfmUsersOptions(DataHolder options) {
        gitHubIssuesUrlRoot = GfmUsersExtension.GIT_HUB_USERS_URL_ROOT.getFrom(options);
        gitHubIssueUrlPrefix = GfmUsersExtension.GIT_HUB_USER_URL_PREFIX.getFrom(options);
        gitHubIssueUrlSuffix = GfmUsersExtension.GIT_HUB_USER_URL_SUFFIX.getFrom(options);
        gitHubUserTextPrefix = GfmUsersExtension.GIT_HUB_USER_HTML_PREFIX.getFrom(options);
        gitHubUserTextSuffix = GfmUsersExtension.GIT_HUB_USER_HTML_SUFFIX.getFrom(options);
    }

    @Override
    public MutableDataHolder setIn(final MutableDataHolder dataHolder) {
        dataHolder.set(GfmUsersExtension.GIT_HUB_USERS_URL_ROOT, gitHubIssuesUrlRoot);
        dataHolder.set(GfmUsersExtension.GIT_HUB_USER_URL_PREFIX, gitHubIssueUrlPrefix);
        dataHolder.set(GfmUsersExtension.GIT_HUB_USER_URL_SUFFIX, gitHubIssueUrlSuffix);
        dataHolder.set(GfmUsersExtension.GIT_HUB_USER_HTML_PREFIX,gitHubUserTextPrefix);
        dataHolder.set(GfmUsersExtension.GIT_HUB_USER_HTML_SUFFIX,gitHubUserTextSuffix);
        return dataHolder;
    }
}
