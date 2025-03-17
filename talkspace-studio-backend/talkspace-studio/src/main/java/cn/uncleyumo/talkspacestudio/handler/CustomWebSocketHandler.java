package cn.uncleyumo.talkspacestudio.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author uncle_yumo
 * @fileName CustomWebSocketHandler
 * @createDate 2025/3/17 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Component
@Slf4j
public class CustomWebSocketHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        sessions.put(sessionId, session);
        log.info("WebSocket连接建立成功：{}", sessionId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("收到消息：{}", payload);

        // 发送回复消息
        String replyMessage = "服务器收到消息：" + payload;
        session.sendMessage(new TextMessage(replyMessage));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        sessions.remove(sessionId);
        log.info("连接关闭：{}，原因：{}", sessionId, status.getReason());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket传输错误", exception);
    }

    // 广播消息给所有连接的客户端
    public void broadcastMessage(String message) {
        log.info("准备广播消息");
        log.info("当前连接数：{}", sessions.size());
        sessions.values().forEach(session -> {
            try {
                session.sendMessage(new TextMessage(message));
                log.info("广播消息给客户端：{}", session.getId());
            } catch (IOException e) {
                log.error("广播消息失败", e);
            }
        });
    }

    // 广播消息给所有连接的客户端
    public void sendTargetMessage(String message) {
        log.info("准备单独发送消息");
        log.info("当前连接数：{}", sessions.size());

    }

    @Scheduled(fixedRate = 10000) // 每10秒发送一次心跳，需要启动类或配置类上添加@EnableScheduling
    public void sendHeartbeat() {
        String heartbeat = "heartbeat";
        sessions.values().forEach(session -> {
            try {
                session.sendMessage(new TextMessage(heartbeat));
            } catch (IOException e) {
                log.error("发送心跳消息失败", e);
            }
        });
        log.info("发送心跳消息");
    }

}
