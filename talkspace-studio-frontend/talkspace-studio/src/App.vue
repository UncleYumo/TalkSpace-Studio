<script setup lang="ts">
import { onUnmounted, onMounted, reactive } from 'vue';
let ws: WebSocket | null = null;
const message = reactive<String[]>([]);
let reconnectTimer: ReturnType<typeof setTimeout> | null = null;
const RECONNECT_INTERVAL = 5000; // 重连间隔时间，单位：毫秒

const initWebSocket = () => {
    ws = new WebSocket('ws://localhost:8081/ws');

    ws.onopen = function() {
        console.log('WebSocket连接已建立');
        if (reconnectTimer) {
            clearTimeout(reconnectTimer);
            reconnectTimer = null;
        }
    };

    ws.onmessage = function(event) {
        // Bug 修复：使用 push 方法将消息添加到数组中
        message.push('收到消息：' + event.data); 
    };

    ws.onclose = function() {
        console.log('WebSocket连接已关闭');
        if (!reconnectTimer) {
            reconnectTimer = setTimeout(() => {
                console.log('尝试重新连接...');
                initWebSocket();
            }, RECONNECT_INTERVAL);
        }
    };

    ws.onerror = function(error) {
        console.error('WebSocket错误：', error);
    };
}

onMounted(() => {
    initWebSocket();
});

onUnmounted(() => {
    console.log("准备关闭WebSocket连接");
    if (reconnectTimer) {
        clearTimeout(reconnectTimer);
        reconnectTimer = null;
    }
    ws?.close();
});

</script>
<template>
    <!-- 修改为 div 标签 -->
    <div>
        <div class=" text-2xl text-fuchsia-900" v-for="(item, index) in message" :key="index">
            {{ item }}
        </div>
    </div>
</template>

<style scoped>
/* 保留作用域样式区域 */
</style>
