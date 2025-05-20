package com.luminar.snacpack.service;

import com.luminar.snacpack.model.CustomUserDetails;
import com.luminar.snacpack.repository.CustomUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.luminar.snacpack.model.GitHubRepo;
import com.luminar.snacpack.model.GitHubUser;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
public class GitHubService {

    @Autowired
    CustomUserRepo customUserRepo;

    private final WebClient client = WebClient.create("https://api.github.com");

    public GitHubUser getUserProfile(String username) {
        try {
            return client.get()
                    .uri("/users/{username}", username)
                    .retrieve()
                    .bodyToMono(GitHubUser.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("GitHub API error while fetching user {}: {}", username, e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error fetching GitHub user {}: {}", username, e.getMessage(), e);
        }
        return null;
    }

    public Map<String, String> getUserDetailsIfExists(String username) {
        String trimmedUsername = username.trim().toLowerCase();
        try {
            Optional<CustomUserDetails> optionalUser = customUserRepo.findByUsername(trimmedUsername);

            if (optionalUser.isPresent()) {
                CustomUserDetails user = optionalUser.get();
                Map<String, String> details = new HashMap<>();
                details.put("description", user.getDescription());
                details.put("linkedin", user.getLinkedin());
                log.info("Retrieved custom details for user: {}", trimmedUsername);
                return details;
            } else {
                log.warn("User details not found for: {}", trimmedUsername);
            }
        } catch (Exception e) {
            log.error("Error retrieving user details for {}: {}", trimmedUsername, e.getMessage(), e);
        }
        return null;
    }



    public List<GitHubRepo> getRepositories(String username) {
        try {
            return client.get()
                    .uri("/users/{username}/repos", username)
                    .retrieve()
                    .bodyToFlux(GitHubRepo.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            log.error("GitHub API error while fetching repos for {}: {}", username, e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error fetching repos for {}: {}", username, e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    public Set<String> extractLanguages(List<GitHubRepo> repos) {
        return repos.stream()
                .map(GitHubRepo::getLanguage)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public void saveUserDetails(String username, String description, String linkedin) {
        try {
            Optional<CustomUserDetails> optionalUser = customUserRepo.findByUsername(username.trim().toLowerCase());

            if (!optionalUser.isPresent()) {
                CustomUserDetails userDetails = new CustomUserDetails();
                userDetails.setUsername(username);
                userDetails.setDescription(description);
                userDetails.setLinkedin(linkedin);
                customUserRepo.save(userDetails);
                log.info("Saved new user details for {}", username);
            } else {
                log.info("User {} already exists. Skipping save.", username);
            }
        } catch (Exception e) {
            log.error("Error saving user details for {}: {}", username, e.getMessage(), e);
        }
    }



}
