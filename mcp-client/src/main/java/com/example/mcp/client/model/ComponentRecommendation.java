package com.example.mcp.client.model;

import java.util.List;

/**
 * 用于展示的组件推荐实体。
 */
public record ComponentRecommendation(
        String name,
        String description,
        String pomCoordinate,
        List<String> configurations,
        String usageExample
) {
}
