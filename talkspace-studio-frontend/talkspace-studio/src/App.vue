<script setup lang="ts">
import ComponentDemo from './components/ComponentDemo.vue';
import type { IProject } from './type/types';
import { reactive } from 'vue';

// 使用 reactive 定义响应式对象
const testProject = reactive<IProject>({
    title: '示例项目标题',
    description: '这是一个示例项目的描述信息。',
    episodes: [
        {
            subTitle: '第一集副标题',
            content: '第一集的具体内容。'
        },
        {
            subTitle: '第二集副标题',
            content: '第二集的具体内容。'
        }
    ]
});

const testProject2 = reactive<IProject>({
    title: '示例项目标题',
    description: '这是一个示例项目的描述信息。',
    episodes: [
        {
            subTitle: '第一集副标题',
            content: '第一集的具体内容。'
        },
        {
            subTitle: '第二集副标题',
            content: '第二集的具体内容。'
        }
    ]
});


const changeProject = () => {
    testProject.title += 'x';
    testProject.description += 'y';
};

</script>
<template>
    <div class=" justify-center text-center">
        <button class=" bg-gray-500 w-40 h-20 hover:w-30 hover:h-15 hover:bg-amber-200"
            @click="changeProject">改变项目</button>
    </div>
    <div class=" bg-amber-200">
        <ComponentDemo :project="testProject" />
        <ComponentDemo :project="testProject2" />
    </div>
</template>

<style scoped>
/* 保留作用域样式区域 */
</style>
