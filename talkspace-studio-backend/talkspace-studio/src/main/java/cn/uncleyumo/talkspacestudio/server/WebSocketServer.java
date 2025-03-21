package cn.uncleyumo.talkspacestudio.server;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Legion_133
 */
@ServerEndpoint("/ws/{userId}")
@Slf4j
@Component
public class WebSocketServer {

    // 使用线程安全的集合存储会话（支持同一用户多设备连接）
    private static final ConcurrentHashMap<String, CopyOnWriteArraySet<Session>> SESSION_POOL = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        // 将新会话加入连接池
        SESSION_POOL.computeIfAbsent(userId, k -> new CopyOnWriteArraySet<>()).add(session);
        log.info("[WebSocket] 新连接建立：用户ID={}，当前连接数={}", userId, getSessionCount(userId));
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        // 从连接池移除关闭的会话
        SESSION_POOL.getOrDefault(userId, new CopyOnWriteArraySet<>()).remove(session);
        log.info("[WebSocket] 连接关闭：用户ID={}，剩余连接数={}", userId, getSessionCount(userId));
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userId") String userId) {
        log.info("[WebSocket] 收到消息：用户ID={}，内容={}", userId, message);
        session.getAsyncRemote().sendText("服务端已收到消息：" + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("[WebSocket] 连接异常：{}", error.getMessage());
    }

    /**
     * 主动向指定用户发送消息
     * @param userId  目标用户ID
     * @param message 要发送的消息内容
     */
    public static void sendMessage(String userId, String message) {
        CopyOnWriteArraySet<Session> sessions = SESSION_POOL.get(userId);
        if (sessions != null && !sessions.isEmpty()) {
            sessions.forEach(session -> {
                try {
                    if (session.isOpen()) {
                        session.getAsyncRemote().sendText(message);
                        log.debug("[WebSocket] 消息已发送：用户ID={}，内容={}", userId, message);
                    }
                } catch (Exception e) {
                    log.error("[WebSocket] 消息发送失败：用户ID={}，错误={}", userId, e.getMessage());
                }
            });
        } else {
            log.warn("[WebSocket] 用户未连接：userId={}", userId);
        }
    }

    /**
     * 获取指定用户的当前连接数
     */
    private static int getSessionCount(String userId) {
        return SESSION_POOL.getOrDefault(userId, new CopyOnWriteArraySet<>()).size();
    }
}
