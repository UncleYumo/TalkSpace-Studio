import { defineStore } from "pinia";
import { ref } from "vue";
import type { UserLoginApiType } from "../api/types/handleUserApiType";

export const useRememberMeStore = defineStore("tss-remember-me", () => {
    const usernameWithPassword = ref<UserLoginApiType>({
        username: "",
        password: ""
    });
    const updateUsernameWithPassword = (username: string, password: string) => {
        usernameWithPassword.value = {
            username,
            password
        };
    };
    const resetUsernameWithPassword = () => {
        usernameWithPassword.value = {
            username: "",
            password: ""
        };
    };
    return {
        updateUsernameWithPassword,
        resetUsernameWithPassword,
        usernameWithPassword
    };
}, {
    persist: true
});