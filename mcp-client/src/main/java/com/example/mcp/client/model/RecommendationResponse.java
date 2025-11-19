package com.example.mcp.client.model;

import java.util.List;

/**
 * 服务端返回的响应。
 */
public record RecommendationResponse(
        String summary,
        List<ComponentRecommendation> recommendations
) {
}
