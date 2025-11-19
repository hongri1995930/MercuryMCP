package com.example.mcp.server.model;

import java.util.List;

/**
 * 组件推荐结果，包含了 pom 坐标、配置示例等信息。
 */
public record ComponentRecommendation(
        String name,
        String description,
        String pomCoordinate,
        List<String> configurations,
        String usageExample
) {
}
