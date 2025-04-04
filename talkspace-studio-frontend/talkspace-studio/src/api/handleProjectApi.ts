import instance from "./request";
import type { AiGenerateScriptApiType, AiGenerateUserPromptApiCallbackType, createProjectApiType, FinalProjectType, generatePodcastApiType, GetPublishedProjectListApiCallbackType, GetPublishedProjectListApiType, GetRandomTemplateApiCallbackType, ProjectListApiType, ProjectRoleListApiType, UserScriptWithCharacterNameApiType } from "./types/handleProjectApiType";

export const createProjectApi = async (data: createProjectApiType) => {
    try {
        const result = await instance.post("/project/create_project", data);
        return result.data;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export const getProjectListApi = async () => {
    try {
        const result = await instance.get<ProjectListApiType[]>("/project/get_project_list");
        return result.data;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export const aiGenerateScriptApi = async (data: AiGenerateScriptApiType) => {
    try {
        console.log("aiGenerateScriptApiDto: ", data);
        const result = await instance.post("/project/generate_user_script_ai", data);
        return result.data;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export const getProjectScriptApi = async (projectId: string) => {
    try {
        const result = await instance.get<UserScriptWithCharacterNameApiType>(`/project/get_user_script/${projectId}`);
        return result.data;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export const getProjectRoleListApi = async (projectId: string) => {
    try {
        const result = await instance.get<ProjectRoleListApiType[]>(`/project/get_project_role_list/${projectId}`);
        return result.data;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export const updateProjectScriptApi = async (data: UserScriptWithCharacterNameApiType) => {
    try {
        const result = await instance.post("/project/update_user_script", data);
        return result.data;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export const aiGeneratePodcastApi = async (data: generatePodcastApiType) => {
    try {
        const result = await instance.post("/project/generate_podcast_ai", data);
        return result.data;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export const getFinalProjectApi = async (projectId: string) => {
    try {
        const result = await instance.get<FinalProjectType>(`/project/get_final_project_list/${projectId}`);
        return result.data;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export const deleteProjectApi = async (projectId: string) => {
    try {
        const result = await instance.delete<string>(`/project/delete_project/${projectId}`);
        return result.data;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export const publishProjectApi = async (projectId: string) => {
    try {
        await instance.put<string>(`/project/publish_project/${projectId}`);
        return true;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export const cancelPublishProjectApi = async (projectId: string) => {
    try {
        await instance.put<string>(`/project/cancel_publish_project/${projectId}`);
        return true;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export const getPublishedProjectListApi = async (data: GetPublishedProjectListApiType) => {
    try {
        const result = await instance.post<GetPublishedProjectListApiCallbackType>(`/project/${data.path}`, {
            pageSize: data.pageSize,
            pageNum: data.pageNum,
        });
        return result.data;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export const collectProjectApi = async (projectId: string) => {
    try {
        await instance.put<string>(`/project/collect_project/${projectId}`);
        return true;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export const cancelCollectProjectApi = async (projectId: string) => {
    try {
        await instance.put<string>(`/project/cancel_collect_project/${projectId}`);
        return true;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export const GetRandomTemplateApi = async () => {
    try {
        const result = await instance.get<GetRandomTemplateApiCallbackType>("/project/get_random_template");
        return result.data;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export const AiGenerateUserPromptApi = async (data: AiGenerateScriptApiType) => {
    try {
        const result = await instance.post<AiGenerateUserPromptApiCallbackType>("/project/generate_user_prompt_ai", data);
        return result.data;
    } catch (error) {
        console.error(error);
        return null;
    }
}