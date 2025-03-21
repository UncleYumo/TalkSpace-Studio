import { message } from "ant-design-vue";
import instance from "./request"
import type { UserLoginApiCallbackType, UserLoginApiType, UserRegisterApiCallbackType, UserRegisterApiType } from "./types/handleUserApiType"
import { useUserInfoStore } from "../stores/UserInfoStore";

export const userLoginApi = async (userLoginApiType: UserLoginApiType) => {
    try {
        message.loading({
            content: "登录中",
            key: "login"
        });
        const result = await instance.post<UserLoginApiCallbackType>("/user/login", userLoginApiType);
        useUserInfoStore().updateUserInfo(result.data);
        message.success({
            content: "登录成功",
            key: "login"
        });
        return result.data;
    } catch (error) {
        console.log(error);
        return null;
    }
}

export const userRegisterApi = async (userRegisterApiType: UserRegisterApiType) => {
    try {
        message.loading({
            content: "注册中",
            key: "register"
        });
        const result = await instance.post<UserRegisterApiCallbackType>("/user/register", userRegisterApiType);
        useUserInfoStore().updateUserInfo(result.data);
        message.success({
            content: "注册成功，已自动登录",
            key: "register"
        });
        return result.data;
    } catch (error) {
        console.log(error);
        return null;
    }
}

export const userLogoutApi = async () => {
    try {
        const result = await instance.get("/user/logout");
        useUserInfoStore().resetUserInfo();
        message.success({
            content: "退出登录成功",
            key: "logout"
        });
        return result.data;
    } catch (error) {
        console.log(error);
        return null;
    }
}