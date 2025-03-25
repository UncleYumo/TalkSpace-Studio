<template>
  <div class="p-6 rounded-lg shadow-lg">
    <!-- 播放器头部 -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold">{{ currentEpisode?.subTitle }}</h3>
      <a-button type="primary" @click="togglePlay" :loading="isLoading">
        {{ isPlaying ? '暂停' : '播放' }}
      </a-button>
    </div>

    <!-- 音频播放区域 -->
    <div class="mb-6">
      <audio ref="audioPlayer" :src="currentAudioUrl" @timeupdate="updateProgress" @ended="handleEnded" />
      <div class="flex items-center gap-4">
        <span class="text-sm">{{ formatTime(currentTime) }}</span>
        <a-progress :percent="progress" :show-info="false" stroke-color="#1890ff" @click="seek" />
        <span class="text-sm">{{ formatTime(duration) }}</span>
      </div>
    </div>

    <!-- 剧集切换 -->
    <div class="mb-6">
      <a-select v-model:value="currentEpisodeIndex" class="w-full">
        <a-select-option v-for="(episode, index) in episodes" :key="index" :value="index">
          {{ episode.subTitle }}
        </a-select-option>
      </a-select>
    </div>

    <!-- 剧本内容 -->
    <div class="max-h-[200px] overflow-y-auto p-4  rounded-lg">
      <div v-for="(line, idx) in currentEpisode?.content" :key="idx" class="mb-4">
        <div class="flex gap-2 flex-col">
          <div><span class="font-bold">{{ line.characterName }}</span><span class="text-xs">（音色: {{ line.role }}）</span>
          </div>
          <span class="text-base">{{ line.text }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch, onMounted } from 'vue';
import type { FinalProjectType } from '../api/types/handleProjectApiType';

// PodcastPlayer.vue
const props = defineProps({
  finalProject: {
    type: Object as () => FinalProjectType | undefined,
    default: undefined
  }
})

const audioPlayer = ref<HTMLAudioElement | null>(null);
const currentEpisodeIndex = ref(0);
const currentTime = ref(0);
const duration = ref(0);
const isPlaying = ref(false);
const isLoading = ref(false);

const episodes = computed(() => props.finalProject?.episodes || []);
const currentEpisode = computed(() => episodes.value[currentEpisodeIndex.value]);
const currentAudioUrl = computed(() => currentEpisode.value?.audioUrl || '');

const progress = computed(() => {
  if (duration.value === 0) return 0;
  return (currentTime.value / duration.value) * 100;
});

watch(currentEpisodeIndex, async () => {
  if (audioPlayer.value) {
    audioPlayer.value.src = currentAudioUrl.value;
    audioPlayer.value.load();
    // 等待音频加载完成后再播放
    audioPlayer.value.addEventListener('canplay', () => {
      play().catch(() => {
        isLoading.value = false;
      });
    }, { once: true });
  }
}, { deep: true });

const togglePlay = () => {
  if (isPlaying.value) {
    pause();
  } else {
    play();
  }
};

const play = async () => {
  try {
    isLoading.value = true;
    await audioPlayer.value?.play();
    isPlaying.value = true;
  } catch (error) {
    console.error('播放失败:', error);
  } finally {
    isLoading.value = false;
  }
};

const pause = () => {
  if (audioPlayer.value) {
    audioPlayer.value.pause();
    isPlaying.value = false;
  }
};

// 在 updateProgress 中添加防御性检查
const updateProgress = () => {
  if (!audioPlayer.value) return;
  const current = audioPlayer.value.currentTime || 0;
  const total = audioPlayer.value.duration || 0;
  currentTime.value = current;
  duration.value = total;
};

const seek = (e: MouseEvent) => {
  if (audioPlayer.value && e.offsetX && e.target instanceof Element) {
    const rect = e.target.getBoundingClientRect();
    const percent = e.offsetX / rect.width;
    audioPlayer.value.currentTime = percent * duration.value;
  }
};


const handleEnded = () => {
  if (currentEpisodeIndex.value < episodes.value.length - 1) {
    currentEpisodeIndex.value++;
  } else {
    pause();
  }
};

// 在 formatTime 中处理 NaN
const formatTime = (time: number) => {
  if (isNaN(time)) return '0:00';
  const minutes = Math.floor(time / 60);
  const seconds = Math.floor(time % 60);
  return `${minutes}:${seconds.toString().padStart(2, '0')}`;
};

onMounted(() => {
  if (episodes.value.length > 0) {
    currentEpisodeIndex.value = 0;
  }
});
</script>

<style scoped>
:deep(.ant-progress-bg) {
  transition: width 0.3s ease-in-out;
}
</style>