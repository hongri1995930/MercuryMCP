package com.example.mcp.client;

import com.example.mcp.client.model.ComponentRecommendation;
import com.example.mcp.client.model.RecommendationRequest;
import com.example.mcp.client.model.RecommendationResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * 命令行 Runner，负责采集用户输入并调用服务端。
 */
class RecommendationClientRunner implements Runnable, org.springframework.boot.CommandLineRunner {

    private final WebClient mcpWebClient;

    RecommendationClientRunner(WebClient mcpWebClient) {
        this.mcpWebClient = mcpWebClient;
    }

    @Override
    public void run(String... args) {
        run();
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            System.out.println("请输入业务领域（例如：微服务/数据处理）：");
            String domain = reader.readLine();
            System.out.println("请输入约束条件（逗号分隔，可为空）：");
            String constraintLine = reader.readLine();
            List<String> constraints = StringUtils.hasText(constraintLine) ?
                    Arrays.stream(constraintLine.split(",")).map(String::trim).filter(StringUtils::hasText).toList() :
                    List.of();
            System.out.println("请输入需求描述：");
            String description = reader.readLine();

            RecommendationRequest request = new RecommendationRequest(domain, constraints, description);
            RecommendationResponse response = invokeServer(request).block();
            if (response == null) {
                System.out.println("未获取到推荐结果，请检查服务端状态。");
                return;
            }

            printResponse(response);
        } catch (Exception ex) {
            System.err.println("调用过程出现异常: " + ex.getMessage());
        }
    }

    private Mono<RecommendationResponse> invokeServer(RecommendationRequest request) {
        return mcpWebClient.post()
                .uri("/recommend")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(RecommendationResponse.class);
    }

    private void printResponse(RecommendationResponse response) {
        System.out.println("========== 推荐摘要 ==========");
        System.out.println(response.summary());
        System.out.println("========== 推荐列表 ==========");
        int index = 1;
        for (ComponentRecommendation recommendation : response.recommendations()) {
            System.out.printf("[%d] 组件：%s%n", index++, recommendation.name());
            System.out.println("描述：" + recommendation.description());
            System.out.println("依赖：" + recommendation.pomCoordinate());
            if (!recommendation.configurations().isEmpty()) {
                System.out.println("配置：");
                recommendation.configurations().forEach(item -> System.out.println("  - " + item));
            }
            System.out.println("示例：\n" + recommendation.usageExample());
            System.out.println("------------------------------");
        }

        // 终端提示额外说明，帮助用户理解下一步动作。
        System.out.println("AI 提示：可根据组件示例直接复制 pom 坐标到当前项目，并按需调整配置。");
    }
}
