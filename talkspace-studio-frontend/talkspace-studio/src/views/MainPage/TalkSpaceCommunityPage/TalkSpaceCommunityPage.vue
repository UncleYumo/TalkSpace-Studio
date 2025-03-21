<script setup lang="ts">
import { ref } from 'vue';
const breadcrumbPath = ref<string[]>(['首页', '言境社区']);
</script>

<template>
    <a-breadcrumb class="mb-6">
        <a-breadcrumb-item v-for="(item, index) in breadcrumbPath" :key="index">
            {{ item }}
        </a-breadcrumb-item>
    </a-breadcrumb>

    <a-layout class="mt-4 border rounded-lg shadow-sm">
        <h1>TalkSpace Community</h1>
    </a-layout>
</template>