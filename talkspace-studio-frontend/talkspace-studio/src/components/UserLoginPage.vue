<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue';
import { useThemeStore } from '../stores/ThemeStore';
import { userLoginApi, userRegisterApi } from '../api/handleUserApi';
import { useRememberMeStore } from '../stores/RememberMeStore';
import type { UserRegisterApiType } from '../api/types/handleUserApiType';

const themeStore = useThemeStore();
const isLoginPage = ref<boolean>(true);

interface FormState {
    username: string;
    password: string;
    remember: boolean;
}

const formState = reactive<FormState>({
    username: '',
    password: '',
    remember: true,
});

const onFinish = (values: any) => {
    console.log('Success:', values);
};

const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo);
};
const disabled = computed(() => {
    return !(formState.username && formState.password);
});

const doUserLogin = async () => {
    let result = await userLoginApi({
        username: formState.username,
        password: formState.password,
    });
    if (result?.id !== "") {
        if (formState.remember) {
            useRememberMeStore().updateUsernameWithPassword(formState.username, formState.password);
        } else {
            useRememberMeStore().resetUsernameWithPassword();
        }
    };
    // 触发页面刷新
    window.location.reload();
}

// 注册表单状态 [[3]]
const registerFormState = reactive<UserRegisterApiType>({
    username: '',
    password: '',
    gender: -1
});

// 注册按钮禁用状态计算 [[5]]
const registerDisabled = computed(() => {
    return !(registerFormState.username && registerFormState.password.length >= 6);
});

// 注册表单提交成功处理
const onRegisterFinish = async (values: any) => {
    console.log('注册成功:', values);
    // 这里添加实际注册API调用
    let result = await userRegisterApi(registerFormState);
    if (result == null) {
        return;
    }
    // 成功后自动登录
    window.location.reload();
};

// 注册表单提交失败处理
const onRegisterFailed = (errorInfo: any) => {
    console.log('注册失败:', errorInfo);
};

onMounted(() => {
    if (useRememberMeStore().usernameWithPassword.username !== '' && useRememberMeStore().usernameWithPassword.password !== '') {
        formState.username = useRememberMeStore().usernameWithPassword.username;
        formState.password = useRememberMeStore().usernameWithPassword.password;
    }
})

</script>


<template>
    <!-- 登录 -->
    <div class="flex" v-if="isLoginPage">
        <div class="flex-1">
            <img :src="themeStore.logoPath" alt="Talkspace Studio Logo" class="w-3/4 mx-auto">
        </div>
        <a-divider type="vertical" />
        <div class="flex-1 p-4">
            <a-form :model="formState" name="normal_login" class="login-form" @finish="onFinish"
                @finishFailed="onFinishFailed">
                <a-form-item label="用户名" name="username" :rules="[{ required: true, message: '请输入用户名!' }]">
                    <a-input v-model:value="formState.username">
                        <template #prefix>
                            <UserOutlined class="site-form-item-icon" />
                        </template>
                    </a-input>
                </a-form-item>
                <a-form-item label="密码&nbsp;&nbsp;&nbsp;&nbsp;" name="password"
                    :rules="[{ required: true, message: '请输入密码!' }]">
                    <a-input-password v-model:value="formState.password">
                        <template #prefix>
                            <LockOutlined class="site-form-item-icon" />
                        </template>
                    </a-input-password>
                </a-form-item>

                <a-form-item>
                    <a-form-item name="remember" no-style>
                        <a-checkbox v-model:checked="formState.remember">记住我</a-checkbox>
                    </a-form-item>
                </a-form-item>

                <a-form-item>
                    <a-button :disabled="disabled" @Click="doUserLogin" type="primary" html-type="submit"
                        class="login-form-button">
                        登录
                    </a-button>
                    或
                    <a @click="isLoginPage = false">立即注册!</a>
                </a-form-item>
            </a-form>
        </div>
    </div>
    <!-- 注册 -->
    <div class="flex" v-else>
        <div class="flex-1">
            <!-- 保持与登录页相同的logo展示 -->
            <img :src="themeStore.logoPath" alt="Talkspace Studio Logo" class="w-3/4 mx-auto">
        </div>
        <a-divider type="vertical" />
        <div class="flex-1 p-4">
            <!-- 注册表单使用相同UI组件 -->
            <a-form :model="registerFormState" name="normal_register" class="login-form" @finish="onRegisterFinish"
                @finishFailed="onRegisterFailed">
                <!-- 用户名输入 -->
                <a-form-item label="用户名" name="username"
                    :rules="[{ required: true, message: '请输入用户名!' },
                             { min: 8, message: '用户名至少8位字符' }]"
                    >
                    <a-input v-model:value="registerFormState.username">
                        <template #prefix>
                            <UserOutlined class="site-form-item-icon" />
                        </template>
                    </a-input>
                </a-form-item>

                <!-- 密码输入 -->
                <a-form-item label="密码&nbsp;&nbsp;&nbsp;&nbsp;" name="password" :rules="[
                    { required: true, message: '请输入密码!' },
                    { min: 8, message: '密码至少8位字符' }
                ]">
                    <a-input-password v-model:value="registerFormState.password">
                        <template #prefix>
                            <LockOutlined class="site-form-item-icon" />
                        </template>
                    </a-input-password>
                </a-form-item>

                <!-- 性别选择 男:0 女:1 -->
                <a-form-item label="性别&nbsp;&nbsp;&nbsp;&nbsp;" name="gender"
                    :rules="[{ required: true, message: '请选择性别!' }]">
                    <a-radio-group v-model:value="registerFormState.gender">
                        <a-radio value="0">男</a-radio>
                        <a-radio value="1">女</a-radio>
                    </a-radio-group>
                </a-form-item>
                <!-- 提交按钮与返回登录链接 -->
                <a-form-item>
                    <a-button :disabled="registerDisabled" type="primary" html-type="submit" class="">
                        注册
                    </a-button>
                    <!-- 返回登录链接 -->
                    或
                    <a @click="isLoginPage = true">立即登录！</a>
                </a-form-item>
            </a-form>
        </div>
    </div>
</template>