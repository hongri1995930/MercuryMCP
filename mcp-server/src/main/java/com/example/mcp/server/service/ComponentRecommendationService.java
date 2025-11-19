package com.example.mcp.server.service;

import com.example.mcp.server.model.ComponentRecommendation;
import com.example.mcp.server.model.RecommendationRequest;
import com.example.mcp.server.model.RecommendationResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.TemplateFormat;
import org.springframework.ai.chat.prompt.templating.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 组件推荐核心服务。
 * 通过本地知识库结合 Spring AI 的 Prompt 模板能力，实现对技术组件的智能推荐。
 */
@Service
public class ComponentRecommendationService {

    private final ChatClient chatClient;
    private final Map<String, List<ComponentRecommendation>> knowledgeBase;

    public ComponentRecommendationService(ChatClient chatClient) {
        this.chatClient = chatClient;
        this.knowledgeBase = ComponentRecommendationTemplates.defaultKnowledgeBase();
    }

    /**
     * 根据用户需求返回推荐结果。
     */
    public RecommendationResponse recommend(RecommendationRequest request) {
        List<ComponentRecommendation> candidates = new ArrayList<>(knowledgeBase.getOrDefault(request.domain(), List.of()));

        if (candidates.isEmpty()) {
            // 如果没有命中预置场景，则尝试由 LLM 返回一个兜底结果。
            PromptTemplate template = new PromptTemplate(TemplateFormat.TEXT,
                    "请基于以下需求，推荐一个 Java 技术组件，并给出 pom 坐标、关键配置和示例代码。" +
                            "需求描述: {description}，约束条件: {constraints}");
            String promptText = template.render(Map.of(
                    "description", request.description(),
                    "constraints", request.constraints() == null || request.constraints().isEmpty() ? "无" : String.join("，", request.constraints())
            ));

            String responseText = chatClient.prompt()
                    .user(promptText)
                    .call()
                    .content();
            ComponentRecommendation generated = new ComponentRecommendation(
                    "LLM 推荐组件",
                    responseText,
                    "待确认",
                    List.of("请根据描述手工解析配置"),
                    "// 根据模型输出补充示例"
            );
            candidates.add(generated);
        }

        String summary = "共推荐 " + candidates.size() + " 个组件，涵盖需求领域：" + request.domain();
        return new RecommendationResponse(summary, candidates);
    }
}
