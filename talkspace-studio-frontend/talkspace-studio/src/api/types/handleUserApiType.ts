export type UserLoginApiType = {
    username: string,
    password: string
}

export type UserLoginApiCallbackType = {
    id: string;
    username: string;
    gender: number;
    avatar: string;
    createTime: number[];
    updateTime: number[];
}

export type UserRegisterApiType = {
    username: string,
    password: string,
    gender: number
}

export type UserRegisterApiCallbackType = {
    id: string;
    username: string;
    gender: number;
    avatar: string;
    createTime: number[];
    updateTime: number[];
}