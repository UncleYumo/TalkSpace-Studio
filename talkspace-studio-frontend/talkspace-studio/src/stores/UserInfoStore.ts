import { defineStore } from "pinia";
import { ref } from "vue";
import type { UserInfoStoreType } from "../api/types/storeType";

export const useUserInfoStore = defineStore("tss-userinfo", () => {
        const userInfo = ref<UserInfoStoreType>({
            id: "",
            username: "",
            gender: -1,
            avatar: "",
            createTime: [],
            updateTime: []
        });
        const resetUserInfo = () => {
            userInfo.value = {
                id: "",
                username: "",
                gender: -1,
                avatar: "",
                createTime: [],
                updateTime: []
            }
        }
        const updateUserInfo = (data: UserInfoStoreType) => {
            userInfo.value = data;
        }
        return {
            userInfo,
            resetUserInfo,
            updateUserInfo
        }
    },{
        persist: true
    });