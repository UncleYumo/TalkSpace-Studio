export type createProjectApiType = {
    userPrompt: string;
    title: string;
    language: string;
    episodeCount: number;
    roles: Role[],
    singleDuration: number;
};

export interface Role {
    timbre: string;
    characterName: string;
}


export interface ProjectListApiType {
    id: string;
    userId: string;
    title: string;
    language: string;
    userPrompt: string;
    episodeCount: number;
    singleDuration: number;
    status: string; // 或者根据实际需求定义枚举类型
    userScript: string | null;
    createTime: number[]; // ISO 8601 格式日期字符串
    updateTime: number[]; // ISO 8601 格式日期字符串
}

export interface AiGenerateScriptApiType {
    userId: string;
    projectId: string;
}

export interface UserScriptWithCharacterNameApiType {
    projectId: string;
    userScriptWithCharacterName: {
        title: string;
        episodesWithCharacterName: EpisodeWithCharacterNameType[];
    };
}

export interface EpisodeWithCharacterNameType {
    subTitle: string;
    contentWithCharacterName: ContentWithCharacterNameType[];
}

export interface ContentWithCharacterNameType {
    role: string;
    text: string;
    characterName: string;
}

export interface ProjectRoleListApiType {
    role: string;
    characterName: string;
}

export interface generatePodcastApiType {
    projectId: string;
    userId: string;
    speechRate: number;
    pitchRate: number;
    volume: number;
}