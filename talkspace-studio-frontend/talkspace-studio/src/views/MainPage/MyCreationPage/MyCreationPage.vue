<script setup lang="ts">
import { onMounted, ref, watch } from 'vue';
import type { ProjectListApiType, ProjectRoleListApiType, UserScriptWithCharacterNameApiType } from '../../../api/types/handleProjectApiType';
import { aiGenerateScriptApi, getProjectListApi, getProjectRoleListApi, getProjectScriptApi, updateProjectScriptApi } from '../../../api/handleProjectApi';
import { LoadingOutlined } from '@ant-design/icons-vue';
import { formatCreateTime } from '../../../utils/TimeUtil';
import { message } from 'ant-design-vue';

type ProjectStatus =
    | 'DRAFT'
    | 'DRAFT_SCRIPT'
    | 'SCRIPT'
    | 'PODCAST_SCRIPT'
    | 'PODCAST'
    | 'PUBLISHED';

const statusMapping: Record<ProjectStatus, string> = {
    DRAFT: '草稿',
    DRAFT_SCRIPT: '剧本生成中',
    SCRIPT: '剧本',
    PODCAST_SCRIPT: '播客生成中',
    PODCAST: '播客',
    PUBLISHED: '已发布',
};

const breadcrumbPath = ref<string[]>(['首页', '我的创作']);

const isLoading = ref<boolean>(false);

const projectListTableData = ref<ProjectListApiType[]>([]);
// 新增：用于跟踪每个项目的按钮加载状态
const buttonLoadingStatus = ref<Record<string, boolean>>({});

// 用户剧本
const userScript = ref<UserScriptWithCharacterNameApiType>();

const projectRoleList = ref<ProjectRoleListApiType[]>([]);

// 获取用户剧本
const refreshUserScript = async (projectId: string) => {
    const result = await getProjectScriptApi(projectId);
    if (result) {
        userScript.value = result;
    }
};

// 获取剧本角色列表
const refreshProjectRoleList = async (projectId: string) => {
    const result = await getProjectRoleListApi(projectId);
    if (result) {
        projectRoleList.value = result
    }
};

// 刷新项目列表数据
const refreshProjectListTableData = async () => {
    const result = await getProjectListApi();
    if (result) {
        projectListTableData.value = result;
    }
};

// 生成用户剧本，处理按钮加载状态
const generateUserScript = async (projectId: string, userId: string) => {
    // 1. 设置当前按钮为加载状态
    buttonLoadingStatus.value[projectId] = true;
    try {
        const result = await aiGenerateScriptApi({
            userId: userId,
            projectId: projectId,
        });
        if (result) {
            // window.location.reload();
        }
    } finally {
        // 3. 无论成功或失败，最后都重置加载状态
        buttonLoadingStatus.value[projectId] = false;
        // 4. 刷新项目列表数据
        refreshProjectListTableData();
    }
};

const editScript = async (projectId: string) => {
    await Promise.all([
        refreshUserScript(projectId),
        refreshProjectRoleList(projectId)
    ]);
};
// 新增响应式变量
const editableScript = ref<UserScriptWithCharacterNameApiType['userScriptWithCharacterName']>({
    title: '',
    episodesWithCharacterName: []
});

// 模态框相关
const addContentVisible = ref(false);
const activeEpisodeKeys = ref<number[]>([]);
const currentEpisodeIndex = ref<number>(0);
const newContent = ref<{ role: string; text: string }>({
    role: '',
    text: ''
});

// 监听userScript变化初始化编辑数据
watch(userScript, (newVal) => {
    if (newVal) {
        editableScript.value = JSON.parse(JSON.stringify(newVal.userScriptWithCharacterName));
        activeEpisodeKeys.value = Array.from({ length: newVal.userScriptWithCharacterName.episodesWithCharacterName.length }, (_, i) => i);
    }
}, { deep: true });

// 重置编辑器
const resetEditor = () => {
    if (userScript.value) {
        editableScript.value = JSON.parse(JSON.stringify(userScript.value.userScriptWithCharacterName));
    }
};

// 新增内容相关方法
const showAddContentModal = (epIndex: number) => {
    currentEpisodeIndex.value = epIndex;
    addContentVisible.value = true;
};

const addContent = () => {
    if (newContent.value.role && newContent.value.text) {
        editableScript.value.episodesWithCharacterName[currentEpisodeIndex.value]
            .contentWithCharacterName.push({
                role: newContent.value.role,
                text: newContent.value.text,
                characterName: projectRoleList.value.find(r =>
                    r.role === newContent.value.role
                )?.characterName || ''
            });
    }
};

// 删除内容
const removeContent = (epIndex: number, conIndex: number) => {
    editableScript.value.episodesWithCharacterName[epIndex].contentWithCharacterName.splice(conIndex, 1);
};

// 保存剧本（待实现API）
const saveScript = async () => {
    if (!userScript.value) return;

    if (!editableScript.value.title.trim()) {
        message.warning('请填写剧本标题');
        return;
    }

    for (const episode of editableScript.value.episodesWithCharacterName) {
        if (!episode.subTitle.trim()) {
            message.warning('存在未命名的剧集');
            return;
        }
        for (const content of episode.contentWithCharacterName) {
            if (!content.text.trim()) {
                message.warning('存在空内容的对话');
                return;
            }
        }
    }
    isLoading.value = true;
    // 构造符合接口要求的参数
    const scriptData: UserScriptWithCharacterNameApiType = {
        projectId: userScript.value.projectId,
        userScriptWithCharacterName: editableScript.value
    };

    // 调用更新接口
    const result = await updateProjectScriptApi(scriptData);
    
    // 处理成功响应
    if (result) {
        message.success('剧本保存成功');
        // 刷新项目列表状态
        await refreshProjectListTableData();
        // 重新加载当前剧本数据保持同步
        await refreshUserScript(scriptData.projectId);
    }
    isLoading.value = false;
};

onMounted(async () => {
    await refreshProjectListTableData();
    // 默认加载第一个项目的编辑页面
    if (projectListTableData.value.length > 0) {
        editScript(projectListTableData.value[0].id);
    }
});
</script>

<template>
    <a-breadcrumb class="mb-6">
        <a-breadcrumb-item v-for="(item, index) in breadcrumbPath" :key="index">
            {{ item }}
        </a-breadcrumb-item>
    </a-breadcrumb>

    <a-layout class="mt-4 h-screen rounded-4xl">
        <a-layout-content class="h-full">
            <a-row :gutter="[24, 24]">
                <!-- 左侧卡片区域 -->
                <a-col :span="6" class="h-full">
                    <div class="flex flex-col h-full">
                        <div class="flex flex-wrap gap-4 overflow-y-auto h-180">
                            <div v-if="projectListTableData.length === 0">
                                <a-space>
                                    <h1>播客模板</h1>
                                </a-space>
                                <a-empty />
                            </div>
                            <h1>播客模板</h1>
                            <div v-if="projectListTableData.length !== 0" v-for="project in projectListTableData"
                                :key="project.id" class="w-60">
                                <a-card :title="project.title" class="w-full">
                                    <template #extra>
                                        <a-button type="link"
                                            :disabled="['DRAFT_SCRIPT', 'PODCAST_SCRIPT'].includes(project.status)"
                                            @click="editScript(project.id)">
                                            编辑剧本
                                        </a-button>
                                    </template>
                                    <a-col class="mb-2">
                                        <a-row>
                                            <span>语言风格：<span class="font-bold">{{ project.language }}</span></span>
                                        </a-row>
                                        <a-row>
                                            <span>项目状态：
                                                <span class="font-bold">
                                                    {{ statusMapping[(project.status as unknown) as ProjectStatus] ||
                                                        '未知状态' }}
                                                </span>
                                            </span>
                                            <!-- 显示项目本身的生成状态 -->
                                            <loading-outlined
                                                v-if="['DRAFT_SCRIPT', 'PODCAST_SCRIPT'].includes(project.status)"
                                                class="ml-2" />
                                        </a-row>
                                        <a-row>
                                            <span>播客集数：共{{ project.episodeCount }}集</span>
                                        </a-row>
                                        <a-row>
                                            <span>每集时长：{{ project.singleDuration }}分钟</span>
                                        </a-row>
                                        <a-row>
                                            <span class="font-bold">
                                                {{ formatCreateTime(project.createTime) }}
                                            </span>
                                        </a-row>
                                    </a-col>
                                    <!-- 生成剧本按钮 -->
                                    <a-button type="link" :loading="buttonLoadingStatus[project.id]"
                                        :disabled="['DRAFT_SCRIPT', 'PODCAST_SCRIPT'].includes(project.status)"
                                        @click="generateUserScript(project.id, project.userId)">
                                        生成剧本
                                    </a-button>
                                    <!-- 其他按钮保持原有逻辑 -->
                                    <a-button type="link"
                                        :disabled="['DRAFT_SCRIPT', 'PODCAST_SCRIPT'].includes(project.status)">生成播客</a-button>
                                    <a-button type="link"
                                        :disabled="['DRAFT_SCRIPT', 'PODCAST_SCRIPT'].includes(project.status)">公开发布</a-button>
                                    <a-button type="link"
                                        :disabled="['DRAFT_SCRIPT', 'PODCAST_SCRIPT'].includes(project.status)">删除模板</a-button>
                                </a-card>
                            </div>
                        </div>
                    </div>
                </a-col>

                <!-- 右侧编辑器区域滚动条修复 -->
                <a-col :span="18" class="h-full mt-7 p-2 overflow-y-auto"> <!-- 新增overflow-y-auto -->
                    <a-card v-if="userScript" title="剧本编辑器" :bordered="false" class="h-full">
                        <!-- 剧本标题 -->
                        <p class="text-2xl font-bold mb-4">{{editableScript.title}}</p>
                        <!-- 补充placeholder避免空状态样式问题 -->
                        <!-- 剧集列表 -->
                        <a-collapse v-model:activeKey="activeEpisodeKeys" class="mb-6 max-h-[68vh] overflow-y-auto">
                            <!-- 新增高度限制和滚动 -->
                            <!-- 原有内容 -->
                            <!-- 剧集列表 -->
                            <a-collapse v-model:activeKey="activeEpisodeKeys" class="mb-6">
                                <a-collapse-panel v-for="(episode, epIndex) in editableScript.episodesWithCharacterName"
                                    :key="epIndex" :header="`${episode.subTitle}`">
                                    <!-- 内容条目 -->
                                    <div v-for="(content, conIndex) in episode.contentWithCharacterName" :key="conIndex"
                                        class="mb-4 p-4 border rounded">
                                        <div class="flex justify-between items-center mb-2">
                                            <span class="font-medium">
                                                角色：{{ content.role }}（{{ content.characterName }}）
                                            </span>
                                            <a-popconfirm title="确定删除该内容？" @confirm="removeContent(epIndex, conIndex)">
                                                <a-button type="link" danger>删除</a-button>
                                            </a-popconfirm>
                                        </div>
                                        <a-textarea v-model:value="content.text" placeholder="输入对话内容..." :rows="3"
                                            class="w-full" />
                                    </div>

                                    <!-- 新增内容按钮 -->
                                    <a-button type="dashed" block @click="showAddContentModal(epIndex)">
                                        <template #icon><plus-outlined /></template>
                                        <p class="font-bold">新增内容</p>
                                    </a-button>
                                </a-collapse-panel>
                            </a-collapse>
                        </a-collapse>
                        <!-- 操作按钮 -->
                        <div class="flex justify-end gap-4 sticky bottom-0 pt-4"> <!-- 新增sticky定位保持按钮可见 -->
                            <a-button @click="resetEditor">重置</a-button>
                            <a-button type="primary" @click="saveScript" :loading="isLoading">保存修改</a-button>
                        </div>
                    </a-card>
                    <a-empty v-else description="请选择要编辑的剧本" />
                </a-col>

                <!-- 新增内容模态框 -->
                <a-modal v-model:visible="addContentVisible" title="新增内容" :footer="null" width="600px">
                    <a-form :model="newContent" layout="vertical">
                        <a-form-item label="选择角色" required>
                            <a-select v-model:value="newContent.role" placeholder="请选择角色" class="w-full">
                                <a-select-option v-for="role in projectRoleList" :key="role.role" :value="role.role">
                                    {{ role.role }} - {{ role.characterName }}
                                </a-select-option>
                            </a-select>
                        </a-form-item>

                        <a-form-item label="输入内容" required>
                            <a-textarea v-model:value="newContent.text" placeholder="输入对话内容..." :rows="4" />
                        </a-form-item>

                        <div class="flex justify-end gap-4">
                            <a-button @click="addContentVisible = false">取消</a-button>
                            <a-button type="primary" @click="addContent">确定</a-button>
                        </div>
                    </a-form>
                </a-modal>
            </a-row>
        </a-layout-content>
    </a-layout>
</template>