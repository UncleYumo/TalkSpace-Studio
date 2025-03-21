<script setup lang="ts">
import { RouterView } from 'vue-router'
import { useThemeStore } from './stores/ThemeStore.ts'
const themeStore = useThemeStore();

</script>

<template>
  <div class="min-h-screen flex justify-center bg-white">
    <a-config-provider :theme="themeStore.themeConfig">
      <RouterView />
    </a-config-provider>
  </div>
</template>