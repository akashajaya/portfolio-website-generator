package com.luminar.snacpack.model;

import lombok.Data;

@Data
public class GitHubUser {
    private String login;
    private String name;
    private String bio;
    private String html_url;
    private String avatar_url;
    private String public_repos;
}
