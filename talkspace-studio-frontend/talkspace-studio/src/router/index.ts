import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            name: 'main-page',
            component: () => import('../views/MainPage/MainPage.vue'),
            children: [
                {
                    path: '',
                    name: 'workshop-page',
                    component: () => import('../views/MainPage/WorkshopPage/WorkshopPage.vue')
                },
                {
                    path: '/talkspace-community-page',
                    name: 'talkspace-community-page',
                    component: () => import('../views/MainPage/TalkSpaceCommunityPage/TalkSpaceCommunityPage.vue')
                },
                {
                    path: '/my-creations-page',
                    name: 'my-creations-page',
                    component: () => import('../views/MainPage/MyCreationPage/MyCreationPage.vue')
                }
            ]
        }
    ]
})

export default router