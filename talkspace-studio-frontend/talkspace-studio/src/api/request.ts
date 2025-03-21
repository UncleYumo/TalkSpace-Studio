import axios from "axios";  // 引入axios库的类型定义
import type {AxiosInstance, AxiosRequestConfig, AxiosResponse} from "axios"
// import cookies from "vue-cookies";
import { message } from 'ant-design-vue';

// const useCookies = cookies.VueCookies;

const baseURL: string = import.meta.env.VITE_API_BASE_URL;

type ResponseDataType<T> = {
    code: number;
    msg: string;
    data: T;
};

interface AxiosInstanceWithResponseType extends AxiosInstance {
    <T = any>(config: AxiosRequestConfig): Promise<T>;
}

// 修改为 baseURL 以符合 Axios 的配置选项
const instance: AxiosInstanceWithResponseType = axios.create({ baseURL: baseURL }) as AxiosInstanceWithResponseType

// 请求拦截器
instance.interceptors.request.use(
    (config) => {
        // 设置请求头
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)

// 响应拦截器
instance.interceptors.response.use(
    // @ts-ignore
    async <T>(response: AxiosResponse<ResponseDataType<T>>) => {
        const result = response.data
        console.log("响应拦截器开始");
        switch (result.code) {
            case 200:
                if (result.msg === "ok") {
                    return result;
                }
                message.success(result.msg)
                console.log(result);
                return result;
            case 401:
                message.error(result.msg);
                console.log(result);
                return Promise.reject(new Error(result.msg));
            default:
                message.warning(result.msg);
                console.log(result);
                return Promise.reject(new Error(result.msg));
        }
    },
    error => {
        message.error("发生未知的请求错误，请联系管理员");
        console.log("响应拦截器错误");
        return Promise.reject(error);
    }
)

export default instance;