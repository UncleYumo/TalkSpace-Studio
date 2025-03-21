<script setup lang="ts">
import { DownOutlined, UserSwitchOutlined, FormOutlined, BellOutlined } from '@ant-design/icons-vue';
import { ref } from 'vue';
import variables from '../styles/variables.module.scss'
import { useThemeStore } from '../stores/ThemeStore.ts'
import { useUserInfoStore } from '../stores/UserInfoStore.ts';
import { userLogoutApi } from '../api/handleUserApi.ts';
import router from '../router/index.ts';
import { useWebSocketMessageStore } from '../stores/WebSocketMessageStore.ts';
import { githubUrl } from '../assets/asset_urls.ts';

const messageColumns = [
    {
        title: '消息内容',
        dataIndex:'message',
        key:'message',
    },
    {
        title: '消息时间',
        dataIndex: 'createTime',
        key: 'createTime',
    },
];

const themeStore = useThemeStore()
const selectedKeys = ref<string[]>(['2']);
const userInfoStore = useUserInfoStore();

const isMessageModalVisible = ref<boolean>(false);

const doUserLogout = async () => {
    await userLogoutApi();
    // 触发页面刷新
    window.location.reload();
}

const goToGithubPage = () => {
    window.open(githubUrl, '_blank');
}

</script>

<template>
    <a-layout-header class="w-full" :style="{ padding: '0' }">
        <a-menu v-model:selectedKeys="selectedKeys" mode="horizontal" class="leading-[4rem] justify-between">
            <a-menu-item key="1">
                <img class="w-14 h-auto pt-1"
                    :src="themeStore.logoPath"
                    alt="TalkSpace Studio Logo"
                    @click="goToGithubPage"
                />
            </a-menu-item>
            <a-menu-item key="2" @click="router.push('/')">创意工坊</a-menu-item>
            <a-menu-item key="3" @click="router.push('/talkspace-community-page')">言境社区</a-menu-item>
            <a-menu-item key="4" @click="router.push('/my-creations-page')">我的创作</a-menu-item>
            <a-menu-item key="5">
                <a-select v-model:value="themeStore.themeName">
                    <a-select-option v-for="name in Object.keys(variables)" :value="name">{{ name
                    }}</a-select-option>
                </a-select>
            </a-menu-item>
            <a-menu-item key="6">
                <a-select v-model:value="themeStore.darkMode">
                    <a-select-option value="dark">夜间模式</a-select-option>
                    <a-select-option value="light">日间模式</a-select-option>
                </a-select>
            </a-menu-item key="7">
            <a-menu-item>
                <a-dropdown>
                    <a>
                        {{ userInfoStore.userInfo.username !== '' ? "个人中心" : '请登录' }}
                        <DownOutlined />
                    </a>
                    <template #overlay>
                        <a-menu @click="">
                            <a-menu-item key="1">
                                <a-space>
                                    <FormOutlined />
                                    编辑资料
                                </a-space>
                            </a-menu-item>
                            <a-menu-item key="2" @click="isMessageModalVisible = true">
                                <a-space>
                                    <BellOutlined />
                                    消息通知
                                </a-space>
                            </a-menu-item>
                            <a-menu-item key="3" @click="doUserLogout">
                                <a-space>
                                    <UserSwitchOutlined />
                                    退出登录
                                </a-space>
                            </a-menu-item>
                        </a-menu>
                    </template>
                    <template #icon>
                    </template>
                </a-dropdown>
            </a-menu-item>
            <a-menu-item>
                <a-popover placement="bottomRight">
                    <template #content>
                        <div class="p-2">
                            <p class="mb-2">用户ID：{{ userInfoStore.userInfo.id }}</p>
                            <p class="mb-2">用户名：{{ userInfoStore.userInfo.username }}</p>
                            <p>性&nbsp;&nbsp;&nbsp;&nbsp;别：{{ userInfoStore.userInfo.gender === 0 ? '男' : '女' }}</p>
                        </div>
                    </template>
                    <template #title>
                        <span>个人信息</span>
                    </template>
                    <a-avatar :src="userInfoStore.userInfo.avatar"></a-avatar>
                </a-popover>
            </a-menu-item>
        </a-menu>
    </a-layout-header>
    <a-modal v-model:open="isMessageModalVisible" title="消息通知" @ok="isMessageModalVisible = false"
        :okText="'确认'" :cancelText="'取消'"
    >
        <a-table
            :columns="messageColumns"
            :data-source="useWebSocketMessageStore().messageList"
        >
  </a-table>
    </a-modal>
</template>