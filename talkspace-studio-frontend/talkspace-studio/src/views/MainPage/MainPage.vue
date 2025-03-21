<script lang="ts" setup>
import { ref } from 'vue';
import MainPageHeader from '../../components/MainPageHeader.vue';
import { notification, Button } from 'ant-design-vue';
import { h } from 'vue';
import { useUserInfoStore } from '../../stores/UserInfoStore';
import { useThemeStore } from '../../stores/ThemeStore';
import { RouterView } from 'vue-router';
import { useWebSocketMessageStore } from '../../stores/WebSocketMessageStore';
const open = ref<boolean>(useUserInfoStore().userInfo.id === "");

const openProjectFinishedNotification = (message: string) => {
  const key = `open${Date.now()}`;
  notification.open({
    message: '任务完成',
    description: message,
    placement: 'topLeft',
    duration: 0,
    btn: () => h(
      Button,
      {
        type: 'primary',
        size: 'small',
        onClick: () => notification.close(key),
      },
      { default: () => '确认' }
    ),
    key,
    onClose: () => {
      closeProjectFinishedNotification();
      console.log('onClose triggered'); // 调试事件触发
    },
  });
};

const openDefaultNotification = (message: string) => {
  const key = `open${Date.now()}`;
  notification.open({
    message: '系统消息',
    description: message,
    placement: 'topLeft',
    duration: 0,
    btn: () =>
      h(
        Button,
        {
          type: 'primary',
          size: 'small',
          onClick: () => {
            notification.close(key);
          },
        },
        { default: () => '确认' },
      ),
    key,
    onClose: () => {

    },
  });
};

// 进行WebSocket连接
const ws = new WebSocket(import.meta.env.VITE_WEBSOCKET_URL + "/" + useUserInfoStore().userInfo.id);
ws.onopen = () => {
  console.log('WebSocket连接成功');
};
ws.onmessage = (event) => {
  console.log('WebSocket收到消息：', event.data);

  let e: string[] = event.data.split("|");
  switch (e[0]) {
    case "AI剧本生成完成": {
      useWebSocketMessageStore().addMessage(e[1]);
      openProjectFinishedNotification(e[1]);
      break;
    }
    case "AI播客生成完成": {
      useWebSocketMessageStore().addMessage(e[1]);
      openProjectFinishedNotification(e[1]);
      break;
    }
    default: {
      // message.info(event.data);
      useWebSocketMessageStore().addMessage(event.data);
      openDefaultNotification(event.data);
      break;
    }
  }


};
ws.onclose = () => {
  console.log('WebSocket连接关闭');
};
ws.onerror = (event) => {
  console.log('WebSocket连接出错：', event);
};

const closeProjectFinishedNotification = () => {
  console.log('Notification closed');
};

</script>

<template>
  <a-layout class="container mx-auto max-w-[1400px]">
    <MainPageHeader />
    <a-divider />
    <a-layout-content class="px-12">
      <router-view />
    </a-layout-content>
    <a-divider />
    <!-- 修改前：<a-layout-footer style="text-align: center"> -->
    <a-layout-footer class="flex justify-center items-center">
      <div class="flex flex-row">
        <img target="_blank" class="w-8" :src="useThemeStore().logoPath" alt="TalkSpace Studio Logo">
        <p class="my-auto ml-2">TalkSpace Studio ©2025 Created by Uncle Yumo</p>
      </div>
    </a-layout-footer>
  </a-layout>
  <a-modal v-model:open="open" title="用户登录" width="700px" :footer="null" style="top: 40px;">
    <UserLoginPage />
  </a-modal>
</template>