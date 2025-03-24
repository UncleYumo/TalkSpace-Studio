import { defineStore } from "pinia";
import { ref } from "vue";
import { generateUUID16 } from "../utils/UuidUtil";

type WebSocketMessageStoreType = {
    id: string;
    message: string;
    createTime: string;
}

interface DataItem {
    title: string;
}

export const useWebSocketMessageStore = defineStore("WebSocketMessageStore", () => {
    const messageList = ref<WebSocketMessageStoreType[]>([
        {
            id: generateUUID16(),
            message: "欢迎使用Talkspace Studio（默认）",
            createTime: new Date().toLocaleString()
        }
    ]);

    const addMessage = (msg: string) => {
        messageList.value.unshift({
            id: generateUUID16(),
            message: msg,
            createTime: new Date().toLocaleString()
        });
    };

    const resetMessageList = () => {
        messageList.value = [];
        addMessage("欢迎使用Talkspace Studio（默认）");
    }

    const removeMessageById = (id: string) => {
        messageList.value = messageList.value.filter(item => item.id!== id);
    }

    const getMessageListAsDataItemList = () => {
        return messageList.value.map(item => {
            return {
                title: item.message
            } as DataItem;
        });
    } 

    return {
        resetMessageList,
        removeMessageById,
        messageList,
        addMessage,
        getMessageListAsDataItemList
    };
},{
    persist: true
});