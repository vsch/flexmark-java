package com.vladsch.flexmark.ext.gfm.users.internal;

import com.vladsch.flexmark.ext.gfm.users.GfmUsersExtension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;

class GfmUsersOptions implements MutableDataSetter {
    public final String gitHubIssuesUrlRoot;
    public final String gitHubIssueUrlPrefix;
    public final String gitHubIssueUrlSuffix;
    public final String gitHubUserTextPrefix;
    public final String gitHubUserTextSuffix;

    public GfmUsersOptions(DataHolder options) {
        gitHubIssuesUrlRoot = GfmUsersExtension.GIT_HUB_USERS_URL_ROOT.get(options);
        gitHubIssueUrlPrefix = GfmUsersExtension.GIT_HUB_USER_URL_PREFIX.get(options);
        gitHubIssueUrlSuffix = GfmUsersExtension.GIT_HUB_USER_URL_SUFFIX.get(options);
        gitHubUserTextPrefix = GfmUsersExtension.GIT_HUB_USER_HTML_PREFIX.get(options);
        gitHubUserTextSuffix = GfmUsersExtension.GIT_HUB_USER_HTML_SUFFIX.get(options);
    }

    @Override
    public MutableDataHolder setIn(MutableDataHolder dataHolder) {
        dataHolder.set(GfmUsersExtension.GIT_HUB_USERS_URL_ROOT, gitHubIssuesUrlRoot);
        dataHolder.set(GfmUsersExtension.GIT_HUB_USER_URL_PREFIX, gitHubIssueUrlPrefix);
        dataHolder.set(GfmUsersExtension.GIT_HUB_USER_URL_SUFFIX, gitHubIssueUrlSuffix);
        dataHolder.set(GfmUsersExtension.GIT_HUB_USER_HTML_PREFIX, gitHubUserTextPrefix);
        dataHolder.set(GfmUsersExtension.GIT_HUB_USER_HTML_SUFFIX, gitHubUserTextSuffix);
        return dataHolder;
    }
}
