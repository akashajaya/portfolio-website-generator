package com.luminar.snacpack.model;

import lombok.Data;

@Data
public class GitHubRepo {
    private String name;
    private String html_url;
    private String description;
    private String language;
    private int stargazers_count;
    private int forks_count;
    private int open_issues_count;
    private int contributors_count;
}

