export type IEpisode = {
    subTitle: String,
    content: String
}

export type IProject = {
    title: String,
    description: String,
    episodes: IEpisode[]
}