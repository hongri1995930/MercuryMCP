package com.example.mcp.server.service;

import com.example.mcp.server.model.ComponentRecommendation;

import java.util.List;
import java.util.Map;

/**
 * 本地知识库，用于在没有调用真实大模型时提供高质量的推荐结果。
 */
final class ComponentRecommendationTemplates {

    private ComponentRecommendationTemplates() {
    }

    static Map<String, List<ComponentRecommendation>> defaultKnowledgeBase() {
        return Map.of(
                "微服务",
                List.of(
                        new ComponentRecommendation(
                                "Spring Cloud Alibaba Nacos",
                                "提供注册中心与配置中心功能，适合快速搭建微服务治理能力。",
                                "com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config",
                                List.of(
                                        "spring.cloud.nacos.config.server-addr=127.0.0.1:8848",
                                        "spring.application.name=demo-service"
                                ),
                                """
                                        @NacosValue("${demo.value:default}")
                                        private String value;
                                        // 通过 Nacos 管理配置
                                        """
                        ),
                        new ComponentRecommendation(
                                "Spring Cloud Gateway",
                                "高性能 API 网关，用于统一路由和鉴权。",
                                "org.springframework.cloud:spring-cloud-starter-gateway",
                                List.of(
                                        "spring.cloud.gateway.routes[0].id=demo",
                                        "spring.cloud.gateway.routes[0].uri=lb://demo-service",
                                        "spring.cloud.gateway.routes[0].predicates[0]=Path=/api/**"
                                ),
                                """
                                        @Bean
                                        public RouteLocator customRoutes(RouteLocatorBuilder builder) {
                                            return builder.routes()
                                                    .route("demo", r -> r.path("/api/**").uri("lb://demo-service"))
                                                    .build();
                                        }
                                        """
                        )
                ),
                "数据处理",
                List.of(
                        new ComponentRecommendation(
                                "Apache Flink",
                                "分布式实时流处理框架，适合实时 ETL 和复杂事件处理。",
                                "org.apache.flink:flink-java",
                                List.of(
                                        "execution.runtime-mode=STREAMING",
                                        "parallelism.default=4"
                                ),
                                """
                                        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
                                        DataStream<String> stream = env.fromElements("hello", "flink");
                                        stream.print();
                                        env.execute();
                                        """
                        ),
                        new ComponentRecommendation(
                                "Apache Doris",
                                "MPP 架构的实时分析数据库，支持高并发查询。",
                                "org.apache.doris:doris-spark-connector",
                                List.of(
                                        "doris.fe.nodes=127.0.0.1:8030",
                                        "doris.request.auth.user=root"
                                ),
                                """
                                        Dataset<Row> df = spark.read()
                                            .format("doris")
                                            .option("table.identifier", "demo.db.table")
                                            .load();
                                        df.show();
                                        """
                        )
                )
        );
    }
}
