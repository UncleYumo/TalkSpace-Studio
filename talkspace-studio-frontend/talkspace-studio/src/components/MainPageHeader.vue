<script setup lang="ts">
import { storeToRefs } from 'pinia';
import { DownOutlined, UserSwitchOutlined, FormOutlined, BellOutlined } from '@ant-design/icons-vue';
import { ref } from 'vue';
import variables from '../styles/variables.module.scss'
import { useThemeStore } from '../stores/ThemeStore.ts'
import { useUserInfoStore } from '../stores/UserInfoStore.ts';
import { userLogoutApi } from '../api/handleUserApi.ts';
import router from '../router/index.ts';
import { useWebSocketMessageStore } from '../stores/WebSocketMessageStore.ts';
import { githubUrl } from '../assets/asset_urls.ts';
import { useMainPageHeaderMenuKeyStore } from '../stores/MainPageHeaderMenuKeyStore.ts';

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

const mainPageHeaderMenuKeyStore = useMainPageHeaderMenuKeyStore();
const { currentKey } = storeToRefs(mainPageHeaderMenuKeyStore);
const userInfoStore = useUserInfoStore();
const { userInfo } = storeToRefs(userInfoStore);

const isMessageModalVisible = ref<boolean>(false);

const handleClick = (key: string) => {
    switch (key) {
        case 'workshop-page':
            router.push('/').then(()=>{
            useMainPageHeaderMenuKeyStore().setKeyToWorkshopPage();
            })
            break;
        case 'talkspace-community-page':
            router.push('/talkspace-community-page').then(()=> {
            useMainPageHeaderMenuKeyStore().setKeyToTalkspaceCommunityPage();
            })
            break;
        case 'my-creations-page':
            router.push('/my-creations-page').then(() => {
                useMainPageHeaderMenuKeyStore().setKeyToMyCreationsPage();
            })
            break;
    }
}

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
        <a-menu v-model:selectedKeys="currentKey" mode="horizontal" class="leading-[4rem] justify-between">
            <a-menu-item key="logo">
                <img class="w-14 h-auto pt-1"
                    :src="themeStore.logoPath"
                    alt="TalkSpace Studio Logo"
                    @click="goToGithubPage"
                />
            </a-menu-item>
            <a-menu-item key="workshop-page" @click="handleClick('workshop-page')">创意工坊</a-menu-item>
            <a-menu-item key="talkspace-community-page" @click="handleClick('talkspace-community-page')">言境社区</a-menu-item>
            <a-menu-item key="my-creations-page" @click="handleClick('my-creations-page')">我的创作</a-menu-item>
            <a-menu-item key="5">
                <a-select v-model:value="themeStore.themeName">
                    <a-select-option v-for="name in Object.keys(variables)" :value="name">{{ name
                    }}</a-select-option>
                </a-select>
            </a-menu-item>
            <a-menu-item key="change-mode">
                <a-select v-model:value="themeStore.darkMode">
                    <a-select-option value="dark">夜间模式</a-select-option>
                    <a-select-option value="light">日间模式</a-select-option>
                </a-select>
            </a-menu-item key="user-info">
            <a-menu-item>
                <a-dropdown>
                    <a>
                        {{ userInfo.username !== '' ? "个人中心" : '请登录' }}
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
                            <p class="mb-2">用户ID：{{ userInfo.id }}</p>
                            <p class="mb-2">用户名：{{ userInfo.username }}</p>
                            <p>性&nbsp;&nbsp;&nbsp;&nbsp;别：{{ userInfo.gender === 0 ? '男' : '女' }}</p>
                        </div>
                    </template>
                    <template #title>
                        <span>个人信息</span>
                    </template>
                    <a-avatar :src="userInfo.avatar"></a-avatar>
                </a-popover>
            </a-menu-item>
        </a-menu>
    </a-layout-header>
    <a-modal v-model:open="isMessageModalVisible" title="消息通知"
        @ok="useWebSocketMessageStore().resetMessageList()"
        @cancel="isMessageModalVisible = false"
        :okText="'清空历史消息'"
        :cancelText="'确认'"
    >
        <a-table
            :columns="messageColumns"
            :data-source="useWebSocketMessageStore().messageList"
        >
  </a-table>
    </a-modal>
</template>