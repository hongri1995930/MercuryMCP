package com.example.mcp.server.model;

import java.util.List;

/**
 * 推荐响应，封装多个组件推荐结果。
 */
public record RecommendationResponse(
        String summary,
        List<ComponentRecommendation> recommendations
) {
}
