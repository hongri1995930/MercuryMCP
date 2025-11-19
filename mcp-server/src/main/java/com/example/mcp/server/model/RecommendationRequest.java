package com.example.mcp.server.model;

import java.util.List;

/**
 * 推荐请求模型，包含业务场景、技术栈等信息。
 */
public record RecommendationRequest(
        String domain,
        List<String> constraints,
        String description
) {
}
