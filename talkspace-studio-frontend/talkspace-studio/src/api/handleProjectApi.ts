import instance from "./request";
import type { AiGenerateScriptApiType, createProjectApiType, ProjectListApiType, ProjectRoleListApiType, UserScriptWithCharacterNameApiType } from "./types/handleProjectApiType";

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