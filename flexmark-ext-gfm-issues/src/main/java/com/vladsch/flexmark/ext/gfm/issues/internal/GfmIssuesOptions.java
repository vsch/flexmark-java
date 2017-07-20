package com.vladsch.flexmark.ext.gfm.issues.internal;

import com.vladsch.flexmark.ext.gfm.issues.GfmIssuesExtension;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSetter;

class GfmIssuesOptions implements MutableDataSetter {
    public final String gitHubIssuesUrlRoot;
    public final String gitHubIssueUrlPrefix;
    public final String gitHubIssueUrlSuffix;
    public final String gitHubIssueTextPrefix;
    public final String gitHubIssueTextSuffix;

    public GfmIssuesOptions(DataHolder options) {
        gitHubIssuesUrlRoot = GfmIssuesExtension.GIT_HUB_ISSUES_URL_ROOT.getFrom(options);
        gitHubIssueUrlPrefix = GfmIssuesExtension.GIT_HUB_ISSUE_URL_PREFIX.getFrom(options);
        gitHubIssueUrlSuffix = GfmIssuesExtension.GIT_HUB_ISSUE_URL_SUFFIX.getFrom(options);
        gitHubIssueTextPrefix = GfmIssuesExtension.GIT_HUB_ISSUE_HTML_PREFIX.getFrom(options);
        gitHubIssueTextSuffix = GfmIssuesExtension.GIT_HUB_ISSUE_HTML_SUFFIX.getFrom(options);
    }

    @Override
    public MutableDataHolder setIn(final MutableDataHolder dataHolder) {
        dataHolder.set(GfmIssuesExtension.GIT_HUB_ISSUES_URL_ROOT, gitHubIssuesUrlRoot);
        dataHolder.set(GfmIssuesExtension.GIT_HUB_ISSUE_URL_PREFIX, gitHubIssueUrlPrefix);
        dataHolder.set(GfmIssuesExtension.GIT_HUB_ISSUE_URL_SUFFIX, gitHubIssueUrlSuffix);
        dataHolder.set(GfmIssuesExtension.GIT_HUB_ISSUE_HTML_PREFIX,gitHubIssueTextPrefix);
        dataHolder.set(GfmIssuesExtension.GIT_HUB_ISSUE_HTML_SUFFIX,gitHubIssueTextSuffix);
        return dataHolder;
    }
}
