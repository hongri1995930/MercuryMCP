package com.example.mcp.client.model;

import java.util.List;

/**
 * 客户端的请求模型，与服务端保持字段一致。
 */
public record RecommendationRequest(
        String domain,
        List<String> constraints,
        String description
) {
}
