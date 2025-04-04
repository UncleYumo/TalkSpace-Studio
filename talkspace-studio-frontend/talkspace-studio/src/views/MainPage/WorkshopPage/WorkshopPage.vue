<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import {
  SmileOutlined
} from '@ant-design/icons-vue';
import { message } from 'ant-design-vue';
import type { createProjectApiType, Role } from '../../../api/types/handleProjectApiType';
import type { ttsTimbreListApiType } from '../../../api/types/handleStaticResourceApiType';
import { ttsTimbreListApi } from '../../../api/handleStaticResourceApi';
import { createProjectApi } from '../../../api/handleProjectApi';
import router from '../../../router';
import { useMainPageHeaderMenuKeyStore } from '../../../stores/MainPageHeaderMenuKeyStore';
import { storeToRefs } from 'pinia';

const formRef = ref();

const mainPageHeaderMenuKeyStore = useMainPageHeaderMenuKeyStore();
const { currentKey } = storeToRefs(mainPageHeaderMenuKeyStore);

const breadcrumbPath = ref<string[]>(['首页', '创意工坊']);

const layout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 16 },
};

const ttsTimbreListColumns = [
  {
    title: '音色名称',
    dataIndex: 'timbreName',
    key: '1',
  },
  {
    title: '描述',
    dataIndex: 'description',
    key: '2',
  },
  {
    title: '擅长语言',
    dataIndex: 'language',
    key: '3',
  },
  {
    title: '操作',
    key: 'action',
    width: 120,
  },
];

const formState = reactive<createProjectApiType>({
  userPrompt: '',
  title: '',
  language: '',
  episodeCount: 0,
  roles: [],
  singleDuration: 0,
});

const rules = {
  title: [
    { required: true, message: '请输入播客标题', trigger: 'blur' },
    { max: 20, message: '标题长度不能超过20个字符', trigger: 'blur' },
  ],
  userPrompt: [
    { required: true, message: '请输入播客提示词', trigger: 'blur' },
    { max: 200, message: '播客提示词长度不能超过200个字符', trigger: 'blur' },
  ],
  language: [{ required: true, message: '请选择语言风格', trigger: 'change' }],
  singleDuration: [
    { required: true, message: '请输入单集时长', trigger: 'change' },
    { type: 'number', min: 1, max: 8, message: '时长需在1-8分钟之间', trigger: 'change' },
  ],
  episodeCount: [
    { required: true, message: '请输入播客集数', trigger: 'change' },
    { type: 'number', min: 1, message: '集数至少为1', trigger: 'change' },
  ],
};

const ttsTimbreListTableData = ref<ttsTimbreListApiType[]>([]);
const selectedTimbre = ref<ttsTimbreListApiType | null>(null);
const isModalVisible = ref(false);
const characterNameInput = ref('');
const isLoading = ref(false);

const refreshTtsTimbreListTableData = async () => {
  const result = await ttsTimbreListApi();
  if (result) {
    ttsTimbreListTableData.value = result.map(item => ({
      ...item,
      key: item.timbreName,
    }));
  }
};

const showAddModal = (record: ttsTimbreListApiType) => {
  if (formState.roles.some(role => role.timbre === record.timbreName)) {
    message.warning('该音色已添加');
    return;
  }
  selectedTimbre.value = record;
  characterNameInput.value = '';
  isModalVisible.value = true;
};

const handleAddRole = () => {
  if (!characterNameInput.value.trim()) {
    message.error('请输入角色名称');
    return;
  }

  const newRole: Role = {
    timbre: selectedTimbre.value!.timbreName,
    characterName: characterNameInput.value,
  };

  formState.roles.push(newRole);
  isModalVisible.value = false;
  message.success('角色添加成功');
};

const removeRole = (index: number) => {
  formState.roles.splice(index, 1);
  message.success('角色移除成功');
};

const doCreateProject = async () => {
  try {
    const isValid = await formRef.value.validate();
    if (isValid) {
      if (formState.roles.length === 0) {
        message.warning('请至少添加一个角色');
        return;
      }
      isLoading.value = true;
      const result = await createProjectApi(formState);
      if (result) {
        doResetData();
        await router.push('my-creations-page').then(() => {
          currentKey.value = ['my-creations-page'];
        });
      }
    }
    isLoading.value = false;
  } catch (error) {
    message.error('表单验证失败');
  }
};

const doResetData = () => {
  formRef.value.resetFields();
};

const getRandomTemplate = async () => {
  
}

const voiceInputData = async () => {

}

onMounted(() => {
  refreshTtsTimbreListTableData();
});
</script>

<template>
  <!-- 面包屑导航，展示当前页面路径 -->
  <a-breadcrumb class="mb-6">
    <a-breadcrumb-item v-for="(item, index) in breadcrumbPath" :key="index">
      {{ item }}
    </a-breadcrumb-item>
  </a-breadcrumb>
  <!-- 主要布局，包含左侧表单区域和右侧表格区域 -->
  <a-layout class="mt-4 rounded-4xl">
    <a-row :gutter="24" class="p-2">
      <!-- 左侧表单区域，用于输入播客的相关信息 -->
      <a-col :span="10">
        <a-layout class="rounded-lg h-full shadow-inner">
          <a-layout-content class="px-8 py-8">
            <p class="text-center mb-8 text-2xl font-bold">播客模板</p>
            <!-- 表单组件，包含多个输入项用于生成播客模板 -->
            <a-form ref="formRef" :model="formState" :rules="rules" :label-col="layout.labelCol"
              :wrapper-col="layout.wrapperCol" class="space-y-4">
              <!-- 播客标题输入项 -->
              <a-form-item label="播客标题" name="title" class="mb-6 ant-form-item-label font-medium">
                <a-input v-model:value="formState.title" class="rounded-md" />
              </a-form-item>

              <!-- 语言风格选择项 -->
              <a-form-item label="语言风格" name="language" class="mb-6 ant-form-item-label font-medium">
                <a-select v-model:value="formState.language" class="w-full" popupClassName="rounded-md shadow-lg">
                  <a-select-option value="中文为主">中文为主</a-select-option>
                  <a-select-option value="英文为主">英文为主</a-select-option>
                  <a-select-option value="中英混杂">中英混杂</a-select-option>
                </a-select>
              </a-form-item>

              <!-- 单集时长输入项 -->
              <a-form-item label="单集时长（分钟）" name="singleDuration" class="mb-6 ant-form-item-label font-medium">
                <a-input-number v-model:value="formState.singleDuration" class="w-full"
                  :controls-class="'text-blue-500 hover:text-blue-600'" :min="1" :max="8" />
              </a-form-item>

              <!-- 播客集数输入项 -->
              <a-form-item label="播客集数" name="episodeCount" class="mb-6 ant-form-item-label font-medium">
                <a-input-number v-model:value="formState.episodeCount" class="w-full"
                  :controls-class="'text-blue-500 hover:text-blue-600'" :min="1" />
              </a-form-item>

              <!-- 播客提示词输入项 -->
              <a-form-item label="播客提示词" name="userPrompt" class="mb-6 ant-form-item-label font-medium">
                <a-textarea v-model:value="formState.userPrompt" class="rounded-md" />
              </a-form-item>

              <!-- 角色列表展示区域 -->
              <a-form-item label="角色列表" class="mb-6 font-medium">
                <div class="space-y-2 w-40">
                  <a-tag v-for="(role, index) in formState.roles" :key="index" closable @close="removeRole(index)"
                    class="flex items-center justify-between w-full">
                    <span class="font-bold">{{ role.characterName }}</span>
                    <span class="ml-2 text-xs">{{ role.timbre }}</span>
                  </a-tag>
                </div>
              </a-form-item>

              <a-form-item :wrapper-col="{ span: 24, offset: 5 }">
                <div class="flex gap-2">
                  <a-tooltip>
                    <template #title>随机获取播客标题和提示词模板</template>
                    <a-button @click.prevent="getRandomTemplate">生成随机模板</a-button>
                  </a-tooltip>

                  <a-tooltip>
                    <template #title>根据播客标题AI生成播客提示词</template>
                    <a-button @click.prevent="voiceInputData">AI生成提示词</a-button>
                  </a-tooltip>
                  
                </div>
              </a-form-item>

              <!-- 播客模板生成和重置按钮 -->
              <a-form-item :wrapper-col="{ span: 24, offset: 5 }">
                <div class="flex gap-2">
                  <a-button type="primary" @click.prevent="doCreateProject">生成播客模板</a-button>
                  <a-button @click="doResetData">重置播客模板</a-button>
                </div>
              </a-form-item>

            </a-form>
          </a-layout-content>
        </a-layout>
      </a-col>
      <!-- 右侧表格区域，展示可用的音色列表 -->
      <a-col :span="14">
        <a-layout class="rounded-lg shadow-inner h-full">
          <a-layout-content class="px-4 py-4">
            <!-- 音色列表表格 -->
            <a-table :columns="ttsTimbreListColumns" :data-source="ttsTimbreListTableData" :scroll="{ y: 400 }"
              class="rounded-lg overflow-hidden">

              <!-- 自定义表格体单元格内容，主要用于添加角色的按钮 -->
              <template #bodyCell="{ column, record }">
                <template v-if="column.key === 'action'">
                  <a-button type="primary" size="small" @click="showAddModal(record)"
                    :disabled="formState.roles.some(role => role.timbre === record.timbreName)">
                    添加
                  </a-button>
                </template>
              </template>

              <!-- 自定义表格表头单元格内容，添加图标和加粗样式 -->
              <template #headerCell="{ column }">
                <span class="font-semibold">
                  <smile-outlined class="mr-2" />
                  {{ column.title }}
                </span>
              </template>
            </a-table>
          </a-layout-content>
        </a-layout>
      </a-col>
    </a-row>
  </a-layout>

  <!-- 用于添加新角色的模态框 -->
  <a-modal width="300px" v-model:open="isModalVisible" title="输入角色姓名" @ok="handleAddRole">
    <div class="mx-2">
      <a-input v-model:value="characterNameInput" placeholder="请输入该音色所扮演的角色姓名" />
    </div>
  </a-modal>
</template>