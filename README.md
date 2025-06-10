# 言境工坊 (Podcaster Workshop)

## 📖 项目概述 (Overview)

在 AIGC 和大模型技术飞速发展的今天，音频内容消费市场潜力巨大。然而，播客创作依然面临着技术门槛高、制作周期长、灵感枯竭等挑战。

**《言境工坊》** 正是为了解决这些痛点而生。我们致力于将复杂的前沿 AI 技术封装成一个简单易用的在线平台，赋能每一位有创意的用户。在这里，你无需专业的音频剪辑技能或昂贵的设备，只需通过简单的文字输入和点击，即可一站式完成从**灵感构思、剧本生成、多角色语音合成**到**作品发布**的全过程，真正实现“让创意发声”。

## ✨ 主要功能 (Key Features)

| 功能模块 | 特色功能 | 描述 |
| :--- | :--- | :--- |
| **🎨 创意工坊** | `AI 驱动创作` `多角色支持` | 输入标题即可由 AI 生成创作提示词，支持自定义多角色（不同音色）配置，一键生成高质量、情景化的播客剧本。 |
| **✍️ 我的创作** | `全流程管理` `可视化编辑` | 集中管理您的所有播客项目。提供强大的剧本编辑器，可对 AI 生成的内容进行修改、调整，并随时预览、生成最终音频。 |
| **🌐 言境社区** | `内容生态` `互动分享` | 将您的作品发布到社区，与其他创作者和听众分享。可以浏览、收听、收藏他人的作品，甚至查看创作剧本，构建起一个开放、互助的内容生态。 |
| **🤖 AI 问答助手** | `7x24 在线` `智能支持` | 集成基于项目知识库的 AI 问答助手，随时解答您在使用过程中遇到的问题，提供流畅的用户体验。 |

## 🛠️ 技术栈 (Technology Stack)

本系统采用前后端分离架构，集成了业界主流的开源技术与先进的云服务。

-   **前端 (Frontend)**
    -   **框架:** Vue 3 + Vite
    -   **语言:** TypeScript
    -   **状态管理:** Pinia (支持持久化)
    -   **UI 库:** Ant Design Vue
    -   **AI 助手:** 阿里云 Dify 问答助手

-   **后端 (Backend)**
    -   **框架:** Spring Boot 3
    -   **鉴权:** Sa-Token
    -   **ORM:** MyBatis-Plus
    -   **实时通信:** WebSocket
    -   **对象存储:** MinIO

-   **数据库 (Database)**
    -   **关系型:** MySQL 8.0
    -   **缓存:** Redis

-   **核心 AI 服务 (AI Services)**
    -   **语言模型 (LLM):** 阿里云百炼平台 Qwen 系列 (主力 `qwen2.5-max`)，用于生成提示词和剧本。
    -   **语音合成 (TTS):** 阿里云百炼平台 CosyVoice 系列 (主力 `cosyvoice-v1`)，用于生成多角色、高质量音频。

## 🏛️ 系统架构 (Architecture)

系统整体遵循分层设计理念，确保高内聚、低耦合，易于维护和扩展。

1.  **展示层 (Presentation Layer):** 基于 Vue 3 构建的现代化单页应用 (SPA)，负责用户交互和界面渲染。
2.  **逻辑层 (Logic Layer):** 基于 Spring Boot 3 构建的后端服务，处理核心业务逻辑，如用户管理、项目创建、AI 服务调度等。
3.  **数据访问层 (Data Access Layer):** 使用 MyBatis-Plus 和 Redis，提供高效、可靠的数据持久化和缓存服务。
4.  **信息服务层 (Information Service Layer):** 整合 MySQL、Redis 及 MinIO 对象存储，构成了稳定、可扩展的数据基础。

## 🚀 快速开始 (Getting Started)

### 环境准备 (Prerequisites)

-   JDK 17+
-   Node.js 18+
-   Maven 3.6+
-   MySQL 8.0
-   Redis
-   MinIO
-   阿里云账号并开通百炼 LLM 和 TTS 服务

### 后端启动 (Backend)

1.  克隆仓库到本地：
    ```bash
    git clone https://github.com/your-username/your-repo.git
    cd your-repo/backend
    ```

2.  修改配置文件 `application.yml`：
    -   配置 MySQL、Redis、MinIO 的连接信息。
    -   填入您的阿里云 AccessKey 和百炼大模型相关配置。

3.  启动项目：
    ```bash
    mvn clean package
    java -jar target/podcaster-workshop-0.0.1-SNAPSHOT.jar
    ```

### 前端启动 (Frontend)

1.  进入前端项目目录：
    ```bash
    cd ../frontend
    ```

2.  安装依赖：
    ```bash
    npm install
    ```

3.  运行开发服务器：
    ```bash
    npm run dev
    ```

4.  打开浏览器访问 `http://localhost:5173`。

## 🗺️ 未来展望 (Roadmap)

我们对《言境工坊》的未来充满期待，并计划在以下方向持续优化和探索：

-   **[ ] 功能优化**
    -   引入更先进的 LLM，提升剧本逻辑连贯性和情感表达。
    -   集成背景音乐（BGM）自动生成与语音情绪识别。
    -   开发“AI 剧本优化建议”功能，辅助用户进行二次创作。
-   **[ ] 系统扩展**
    -   增加创作者等级、作品评分等社区激励机制。
    -   适配移动端，实现跨平台创作与收听。
    -   支持多语言界面与内容生成（如英语、日语等）。
-   **[ ] 商业化路径**
    -   推出“高级音色库”、“定制化模板”等增值服务。
    -   与教育、出版等行业合作，提供定制化播客生成解决方案。

---
<div align="center">
  由 AIGC 驱动，为创意而生。
</div>
