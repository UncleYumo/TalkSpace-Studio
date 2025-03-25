<script lang="ts" setup>
import { ref } from 'vue';
import { Modal } from 'ant-design-vue';
import { h } from 'vue';
import MainPageHeader from '../../components/MainPageHeader.vue';
import { useUserInfoStore } from '../../stores/UserInfoStore';
import { useThemeStore } from '../../stores/ThemeStore';
import { RouterView } from 'vue-router';
import { useWebSocketMessageStore } from '../../stores/WebSocketMessageStore';
import router from '../../router';
import { CheckCircleOutlined, ExclamationCircleOutlined } from '@ant-design/icons-vue';
import { storeToRefs } from 'pinia';
import { useMainPageHeaderMenuKeyStore } from '../../stores/MainPageHeaderMenuKeyStore';

const userInfoStore = useUserInfoStore();
const { userInfo } = storeToRefs(userInfoStore);

const mainPageHeaderMenuKeyStore = useMainPageHeaderMenuKeyStore();
const { currentKey } = storeToRefs(mainPageHeaderMenuKeyStore);

const open = ref<boolean>(userInfo.value.id === "");

interface webSocketModalMessageDataType {
  title: string;
  content: string;
};

const webSocketModalSuccess = (data: webSocketModalMessageDataType) => {
  Modal.confirm({
    title: data.title,
    icon: h(CheckCircleOutlined),
    content: h('div', {}, [
      h('p', data.content),
    ]),
    okText: '查看',
    cancelText: '关闭',
    onOk() {
      router.push({ name: 'my-creations-page' }).then(() => {
        currentKey.value = ['my-creations-page'];
        window.location.reload();  // 仅在跳转完成后刷新
      });
    },
    onCancel() {},
  });
}

const webSocketModalWarning = (data: webSocketModalMessageDataType) => {
  Modal.confirm({
    title: data.title,
    icon: h(ExclamationCircleOutlined),
    content: h('div', {}, [
      h('p', data.content),
    ]),
    okText: '查看',
    cancelText: '关闭',
    onOk() {
      router.push({ name: 'my-creations-page' }).then(() => {
        currentKey.value = ['my-creations-page'];
        window.location.reload();  // 仅在跳转完成后刷新
      });
    },
    onCancel() {},
  });
}

const webSocketModalInfo = (data: webSocketModalMessageDataType) => {
  Modal.success({
    title: data.title,
    content: h('div', {}, [
      h('p', data.content),
    ]),
    okText: '确认',
    onOk() {
    },
  });
}

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
      // openProjectFinishedNotification(e[1]);
      webSocketModalSuccess({
        title: "AI剧本通知",
        content: e[1],
      });
      break;
    }
    case "AI播客生成完成": {
      useWebSocketMessageStore().addMessage(e[1]);
      // openProjectFinishedNotification(e[1]);
      webSocketModalSuccess({
        title: "AI播客通知",
        content: e[1],
      });
      break;
    }
    case "删除播客项目": {
      useWebSocketMessageStore().addMessage(e[1]);
      webSocketModalWarning({
        title: "敏感操作通知",
        content: e[1],
      });
      break;
    }
    default: {
      useWebSocketMessageStore().addMessage(event.data);
      webSocketModalInfo({
        title: "系统通知",
        content: event.data,
      });
      break;
    }
  }

};
ws.onclose = () => {
  console.log('WebSocket连接关闭');
};
ws.onerror = (event) => {
  console.log('WebSocket连接出错', event);
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

  <!-- <a-modal
  v-model:open="isWebSocketModalVisible"
  :title="webSocketModalMessageData?.title"
  @ok="handleWebSocketModalOk"
  @cancel="handleWebSocketModalCancel"
  :okText="'查看'"
  :cancelText="'取消'"
  >
  <p>{{ webSocketModalMessageData?.content }}</p>
</a-modal> -->
</template>