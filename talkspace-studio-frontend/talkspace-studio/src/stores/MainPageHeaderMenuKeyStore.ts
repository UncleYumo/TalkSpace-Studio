import { defineStore } from "pinia"
import { ref } from "vue"

export const useMainPageHeaderMenuKeyStore = defineStore("mainPageHeaderMenuKeyStore", () => {
    // 将字符串改为数组类型 [[1]][[3]]
    const currentKey = ref<string[]>(['workshop-page']); 

    const setKeyToWorkshopPage = () => {
        currentKey.value = ['workshop-page']; // 保持数组格式
    }

    const setKeyToTalkspaceCommunityPage = () => {
        currentKey.value = ['talkspace-community-page'];
    }

    const setKeyToMyCreationsPage = () => {
        currentKey.value = ['my-creations-page'];
    }

    const resetDefaultKey = () => {
        currentKey.value = ['workshop-page'];
    }

    return {
        currentKey,
        setKeyToWorkshopPage,
        setKeyToTalkspaceCommunityPage,
        setKeyToMyCreationsPage,
        resetDefaultKey
    }
}, {
    persist: true,
});