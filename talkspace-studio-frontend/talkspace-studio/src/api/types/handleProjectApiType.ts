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

// 新增顶层响应接口
export interface FinalProjectType {
    projectId: string;
    userId: string;
    language: string;
    userPrompt: string;
    episodeCount: number;
    singleDuration: number;
    episodes: FinalEpisodeType[]; // 新增剧集详情数组
  }
  
  // 剧集详情接口
  export interface FinalEpisodeType {
    subTitle: string;
    content: ContentWithCharacterNameType[]; // 对话内容数组
    duration: number;
    audioUrl: string;
    sequence: number;
  }

  export interface GetPublishedProjectListApiType {
    pageSize: number;
    pageNum: number;
    path: string;
  }

  export interface GetPublishedProjectListApiCallbackType {
    pageNum: number;
    pageSize: number;
    totalCount: number;
    totalPage: number;
    records: PublishedProjectApiType[];
  }

  export interface PublishedProjectApiType {
    projectId: string;  // 项目ID
    userId: string;  // 用户ID
    username: string;  // 用户名
    avatar: string;  // 用户头像
    gender: number;
    title: string;  // 播客标题
    language: string;  // 语言风格
    userPrompt: string;  // 用户提示词
    episodeCount: number;  // 分集总数
    collectionCount: string;  // 收藏数
    isCollected: boolean;  // 是否收藏
    publishedTime: number[];  // ISO 8601 格式日期字符串
  }