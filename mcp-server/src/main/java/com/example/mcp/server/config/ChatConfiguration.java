package com.example.mcp.server.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ChatClient 配置，结合自定义的本地模型实现。
 */
@Configuration
public class ChatConfiguration {

    @Bean
    public ChatModel localChatModel() {
        return new StaticKnowledgeChatModel();
    }

    @Bean
    public ChatClient chatClient(ChatModel localChatModel) {
        // 通过 ChatClient 构造器创建客户端，保留替换真实模型的扩展点。
        return ChatClient.builder(localChatModel).build();
    }
}
