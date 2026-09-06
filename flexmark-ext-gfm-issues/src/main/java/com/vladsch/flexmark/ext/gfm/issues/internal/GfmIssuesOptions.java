package com.vladsch.flexmark.ext.gfm.issues.internal;

import com.vladsch.flexmark.ext.gfm.issues.GfmIssuesExtension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;

class GfmIssuesOptions implements MutableDataSetter {
    final public String gitHubIssuesUrlRoot;
    final public String gitHubIssueUrlPrefix;
    final public String gitHubIssueUrlSuffix;
    final public String gitHubIssueTextPrefix;
    final public String gitHubIssueTextSuffix;

    public GfmIssuesOptions(DataHolder options) {
        gitHubIssuesUrlRoot = GfmIssuesExtension.GIT_HUB_ISSUES_URL_ROOT.get(options);
        gitHubIssueUrlPrefix = GfmIssuesExtension.GIT_HUB_ISSUE_URL_PREFIX.get(options);
        gitHubIssueUrlSuffix = GfmIssuesExtension.GIT_HUB_ISSUE_URL_SUFFIX.get(options);
        gitHubIssueTextPrefix = GfmIssuesExtension.GIT_HUB_ISSUE_HTML_PREFIX.get(options);
        gitHubIssueTextSuffix = GfmIssuesExtension.GIT_HUB_ISSUE_HTML_SUFFIX.get(options);
    }

    @Override
    public MutableDataHolder setIn(MutableDataHolder dataHolder) {
        dataHolder.set(GfmIssuesExtension.GIT_HUB_ISSUES_URL_ROOT, gitHubIssuesUrlRoot);
        dataHolder.set(GfmIssuesExtension.GIT_HUB_ISSUE_URL_PREFIX, gitHubIssueUrlPrefix);
        dataHolder.set(GfmIssuesExtension.GIT_HUB_ISSUE_URL_SUFFIX, gitHubIssueUrlSuffix);
        dataHolder.set(GfmIssuesExtension.GIT_HUB_ISSUE_HTML_PREFIX, gitHubIssueTextPrefix);
        dataHolder.set(GfmIssuesExtension.GIT_HUB_ISSUE_HTML_SUFFIX, gitHubIssueTextSuffix);
        return dataHolder;
    }
}
