package com.luminar.snacpack.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luminar.snacpack.model.GitHubRepo;
import com.luminar.snacpack.model.GitHubUser;
import com.luminar.snacpack.service.GitHubService;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Controller
public class PortfolioController {

    @Autowired
    GitHubService gitHubService;

    @GetMapping("/")
    public String getUsernameForm() {
        return "form";
    }

    @PostMapping("/")
    public String getCustomForm(@RequestParam("usernameIn") String username,
                                @RequestParam("bio") String description,
                                @RequestParam("linkedin") String linkedin,
                                Model model) {
        try {
            gitHubService.saveUserDetails(username, description, linkedin);
            model.addAttribute("message", "Details saved successfully!");
        } catch (Exception e) {
            log.error("Error saving user details for {}: {}", username, e.getMessage(), e);
            model.addAttribute("error", "An error occurred while saving details.");
        }
        return "form";
    }


    @PostMapping("/portfolio/{username}")
    public String generatePortfolio(@PathVariable String username, Model model) {
        return loadPortfolio(username, model);
    }

    @GetMapping("/portfolio/{username}")
    public String fetchPortfolio(@PathVariable String username, Model model) {
        return loadPortfolio(username, model);
    }

    private String loadPortfolio(final String username, Model model) {
        try {
            GitHubUser user = gitHubService.getUserProfile(username);
            List<GitHubRepo> repos = gitHubService.getRepositories(username);
            Set<String> languages = gitHubService.extractLanguages(repos);

            model.addAttribute("user", user);
            model.addAttribute("repos", repos);
            model.addAttribute("languages", languages);
            model.addAttribute("chartUrl", "https://ghchart.rshah.org/" + username);

            Map<String, String> userDetails = gitHubService.getUserDetailsIfExists(username);
            if (userDetails != null) {
                model.addAttribute("description", userDetails.get("description"));
                model.addAttribute("linkedin", userDetails.get("linkedin"));
            }

        } catch (Exception e) {
            log.error("Failed to load portfolio for {}: {}", username, e.getMessage(), e);
            model.addAttribute("error", "Unable to load portfolio. Please try again.");
            return "error";
        }

        return "portfolio";
    }


}
