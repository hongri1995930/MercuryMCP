package com.example.mcp.server.controller;

import com.example.mcp.server.model.RecommendationRequest;
import com.example.mcp.server.model.RecommendationResponse;
import com.example.mcp.server.service.ComponentRecommendationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对外暴露的 REST 接口，模拟 MCP Server 的能力。
 */
@RestController
@RequestMapping(path = "/mcp", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecommendationController {

    private final ComponentRecommendationService recommendationService;

    public RecommendationController(ComponentRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * 根据用户输入推荐技术组件。
     */
    @PostMapping(path = "/recommend", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RecommendationResponse recommend(@RequestBody RecommendationRequest request) {
        return recommendationService.recommend(request);
    }
}
