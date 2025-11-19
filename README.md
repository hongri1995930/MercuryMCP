# 研发平台 MCP 示例

该项目提供了一个简单的基于 Spring Boot 与 Spring AI 的 MCP Server 与 Client 示例，能够根据需求推荐合适的技术组件，并返回 pom 坐标、配置示例和代码片段。

## 模块说明
- `mcp-server`：提供 REST 风格的 MCP 服务端，实现组件推荐逻辑。
- `mcp-client`：命令行客户端，收集用户需求并展示推荐结果。

## 快速开始
1. 启动服务端
   ```bash
   mvn -pl mcp-server spring-boot:run
   ```
2. 启动客户端
   ```bash
   mvn -pl mcp-client spring-boot:run
   ```
3. 根据命令行提示输入业务领域、约束条件和需求描述，即可获取推荐的组件信息。

> 由于示例使用本地静态知识库与轻量级 ChatModel，在无外部大模型依赖的情况下同样可运行；后续可按需接入真实的 Spring AI Model Provider。
