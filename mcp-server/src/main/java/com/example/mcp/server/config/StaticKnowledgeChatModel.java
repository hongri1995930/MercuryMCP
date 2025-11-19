package com.example.mcp.server.config;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;

import java.util.List;

/**
 * 一个轻量级的本地 ChatModel 实现，用于在没有真实大模型依赖时提供可预测的响应。
 */
class StaticKnowledgeChatModel implements ChatModel {

    @Override
    public ChatResponse call(Prompt prompt) {
        String content = "基于本地知识库暂未匹配到组件，请在响应中查阅候选建议。";
        Generation generation = new Generation(new AssistantMessage(content));
        return new ChatResponse(List.of(generation));
    }
}
