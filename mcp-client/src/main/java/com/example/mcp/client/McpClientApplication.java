package com.example.mcp.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * MCP 客户端程序入口，通过命令行的方式与 MCP Server 交互。
 */
@SpringBootApplication
public class McpClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpClientApplication.class, args);
    }

    @Bean
    public WebClient mcpWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081/mcp")
                .build();
    }

    @Bean
    CommandLineRunner commandLineRunner(WebClient mcpWebClient) {
        return new RecommendationClientRunner(mcpWebClient);
    }
}
