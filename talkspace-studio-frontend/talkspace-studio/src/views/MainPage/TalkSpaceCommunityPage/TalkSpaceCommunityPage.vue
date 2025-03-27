<script setup lang="ts">
import { onMounted, ref, watch } from 'vue';
import PodcastPlayer from '../../../components/PodcastPlayer.vue';
import type { GetPublishedProjectListApiCallbackType, GetPublishedProjectListApiType, FinalProjectType } from '../../../api/types/handleProjectApiType';
import { cancelCollectProjectApi, collectProjectApi, getFinalProjectApi, getPublishedProjectListApi } from '../../../api/handleProjectApi';
import { formatCreateTime } from '../../../utils/TimeUtil';
const breadcrumbPath = ref<string[]>(['首页', '言境社区']);
const pageSizeOptions = ref<string[]>(['10', '20', '30']);
const currentKey = ref<string[]>(["community_works"]);
const totalCount = ref<number>(10);
const publishedProjectData = ref<GetPublishedProjectListApiCallbackType>();
const publishedProjectApiData = ref<GetPublishedProjectListApiType>({
  pageSize: 10,
  pageNum: 1,
  path: currentKey.value[0]
});

const finalProjectData = ref<FinalProjectType>();
const isShowPlayer = ref(false);
const refreshPage = () => {
  window.location.reload();
};
const previewPodcast = async (projectId: string) => {
  const result = await getFinalProjectApi(projectId);
  if (result) {
    finalProjectData.value = result;
    isShowPlayer.value = true;
  }
}

const onShowSizeChange = (current: number, pageSize: number) => {
  console.log(current, pageSize);
};

watch(publishedProjectApiData, () => {
  // 当pageSize变化时，重新请求数据
  console.log('publishedProjectApiData', publishedProjectApiData.value);
  refreshPublishedProjectList();
}, { deep: true });

watch(currentKey, () => {
  // 当currentKey变化时，重新请求数据
  console.log('currentKey', currentKey.value);
  publishedProjectApiData.value.path = currentKey.value[0];
  refreshPublishedProjectList();
}, { deep: true });

const refreshPublishedProjectList = async () => {
  // 当currentKey变化时，重新请求数据
  console.log('currentKey', currentKey.value);
  let result = await getPublishedProjectListApi(publishedProjectApiData.value);
  if (result) {
    publishedProjectData.value = result;
    totalCount.value = result.totalCount;
  }
}

const doCancelCollectProject = async (projectId: string) => {
  let result = await cancelCollectProjectApi(projectId);
  if (result) {
    refreshPublishedProjectList();
  }
}

const doCollectProject = async (projectId: string) => {
  let result = await collectProjectApi(projectId);
  if (result) {
    refreshPublishedProjectList();
  }
}

onMounted(() => {
  refreshPublishedProjectList();
})

</script>

<template>
  <!-- 面包屑导航保持原样 -->
  <a-breadcrumb class="mb-6">
    <a-breadcrumb-item v-for="(item, index) in breadcrumbPath" :key="index">
      {{ item }}
    </a-breadcrumb-item>
  </a-breadcrumb>
  <a-layout-header class="w-full mt-4" :style="{ padding: '0' }">
    <a-menu v-model:selectedKeys="currentKey" mode="horizontal" class="">
      <a-menu-item key="community_works" @click="">社区作品</a-menu-item>
      <a-menu-item key="my_published_works" @click="">我的发布</a-menu-item>
      <a-menu-item key="my_collection_works" @click="">我的收藏</a-menu-item>
    </a-menu>
  </a-layout-header>

  <!-- 使用Ant Design的栅格系统容器 -->
  <a-row :gutter="[24, 24]" class="mt-4"> <!-- 设置水平和垂直间距 -->
    <!-- 响应式列设置 -->
    <a-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6"
      v-for="(publishedProjectVo, index) in publishedProjectData?.records" :key="index"
      class="transition-all duration-300 hover:-translate-y-1">
      <!-- Ant Design卡片组件 -->
      <a-card hoverable class="rounded-2xl shadow-md border-0" :body-style="{ padding: '24px' }">
        <!-- 卡片内容 -->
        <div class="flex flex-col items-center space-y-2">
          <div class="rounded-full flex items-center justify-center">
            <a-popover>
              <template #content>
                <div class="p-1">
                  <p class="mb-1">用户名：{{ publishedProjectVo.username }}</p>
                  <p>性&nbsp;&nbsp;&nbsp;&nbsp;别：{{ publishedProjectVo.gender == 1 ? '男' : '女' }}</p>
                </div>
              </template>
              <template #title>
                <span>作者信息</span>
              </template>
              <a-avatar :src="publishedProjectVo.avatar"></a-avatar>
            </a-popover>
          </div>
          <h3 class="text-lg font-semibold">{{ publishedProjectVo.title }}</h3>
          <a-button type="primary" class="w-24 mt-2 rounded-full"
            @click="previewPodcast(publishedProjectVo.projectId)"
          >
            收听播客
          </a-button>
        </div>
        <a-divider></a-divider>
        <div class="flex flex-col space-y-2 px-4">
          <p class="text-sm">分集总数：{{ publishedProjectVo.episodeCount }}</p>
          <p class="text-sm">收藏总数：{{ publishedProjectVo.collectionCount }}</p>
          <p class="text-sm">语言风格：{{ publishedProjectVo.language }}</p>
          <p class="text-sm">发布时间：{{ formatCreateTime(publishedProjectVo.publishedTime) }}</p>
        </div>
        <!-- 底部操作按钮组 -->
        <div class="flex justify-between items-center mt-4">
          <a-popover>
            <template #content>
              <div class="w-40">
                {{ publishedProjectVo.userPrompt }}
              </div>
            </template>
            <template #title>
              <span>提示工程（PROMPT）</span>
            </template>
            <a-button type="link" class="">复制提示词</a-button>
          </a-popover>
          <a-button v-if="publishedProjectVo.isCollected" type="link" @click="doCancelCollectProject(publishedProjectVo.projectId)">取消收藏</a-button>
          <a-button v-else type="link" @click="doCollectProject(publishedProjectVo.projectId)">添加到收藏</a-button>
        </div>
      </a-card>
    </a-col>
  </a-row>
  <a-divider></a-divider>
  <!-- 页码组件 -->
  <a-pagination v-model:current="publishedProjectApiData.pageNum" v-model:pageSize="publishedProjectApiData.pageSize"
    show-size-changer :total="totalCount" :responsive="true" @showSizeChange="onShowSizeChange"
    :page-size-options="pageSizeOptions">
  </a-pagination>

  <a-modal v-model:open="isShowPlayer" title="播客预览" width="900px" @ok="isShowPlayer = false" @cancel="refreshPage"
    :okText="'关闭'" centered :cancelText="'刷新'">
    <PodcastPlayer :finalProject="finalProjectData" />
  </a-modal>
</template>