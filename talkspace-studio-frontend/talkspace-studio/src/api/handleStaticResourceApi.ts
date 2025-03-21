import instance from "./request";
import type { ttsTimbreListApiType } from "./types/handleStaticResourceApiType";

export const ttsTimbreListApi = async () => {
    try {
        const result = await instance.get<ttsTimbreListApiType[]>("/static_resource/tts_timbre_List");
        return result.data;
    } catch (error) {
        console.error(error);
        return null;
    }
}