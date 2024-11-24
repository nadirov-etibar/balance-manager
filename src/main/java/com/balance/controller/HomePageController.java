package com.balance.controller;

import com.balance.request.homePage.HomePageDTO;
import com.balance.response.HomePageResponseDTO;
import com.balance.service.HomePageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
@CrossOrigin("*")
public class HomePageController {
    private final HomePageService homePageService;

    public HomePageController(HomePageService homePageService) {
        this.homePageService = homePageService;
    }

    @GetMapping("/home-page")
    @Operation(summary = "Home page", description = "Getting all data for home page")
    public ResponseEntity<HomePageResponseDTO> homePage(HttpServletRequest request) {
        HomePageDTO response = homePageService.getHomePage(request);

        return ResponseEntity.ok(new HomePageResponseDTO(response));
    }
}
