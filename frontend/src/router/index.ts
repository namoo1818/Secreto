import sswTestVue from '@/components/pages/sswTest.vue'
import { createRouter, createWebHistory, createWebHashHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { storeToRefs } from 'pinia'
import RoomView from '@/components/pages/RoomView.vue'
import WithdrawalSuccessPage from '@/components/pages/WithdrawalSuccessPage.vue'
import ResetPasswordPage from '@/components/pages/ResetPasswordPage.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            name: 'beforeLogin',
            component: () => import('@/components/pages/LoginView.vue'),
            meta: {
                hide: true
            },
            // 로그인이 된 상태라면 메인으로 이동
            beforeEnter(to, from, next) {
                const userStore = useUserStore()
                const { accessToken, refreshToken } = storeToRefs(userStore)

                // 토큰이 있으면 로그인 상태로 간주 한다
                if (accessToken.value && refreshToken.value) {
                    console.log('로그인이 되어있어서 메인으로 이동합니다.')
                    next('/main')
                } else {
                    next()
                }
            }
        },
        {
            path: '/main',
            name: 'main',
            component: () => import('@/components/pages/MainView.vue'),
            meta: {
                hide: true
            },
            beforeEnter(to, from, next) {
                const userStore = useUserStore()
                const { accessToken, refreshToken } = storeToRefs(userStore)

                if (accessToken.value && refreshToken.value) {
                    next()
                } else {
                    next('/')
                }
            }
        },
        {
            path: '/withdrawal',
            name: 'withdrawal',
            component: WithdrawalSuccessPage,
            meta: {
                hide: true
            }
        },
        {
            path: '/find_password',
            name: 'find_password',
            component: ResetPasswordPage,

            meta: {
                hide: true
            }
        },
        {
            path: '/game/:roomNo',
            name: 'game',
            component: RoomView,
            children: [
                {
                    path: 'board',
                    name: 'game-board',
                    component: () => import('@/components/pages/board/BoardPage.vue'),
                    props: true
                },
                {
                    path: 'board',
                    name: 'game-board-detail',
                    component: () => import('@/components/pages/board/BoardDetailPage.vue'),
                    props: true
                },
                {
                    path: 'board',
                    name: 'game-board-write',
                    component: () => import('@/components/pages/board/BoardWritePage.vue'),
                    props: true
                },
                {
                    path: 'mission',
                    name: 'game-mission',
                    component: sswTestVue
                },
                {
                    path: 'participate',
                    name: 'game-participate',
                    component: () => import('@/components/pages/ParticipatePage.vue')
                },
                {
                    path: 'timeline',
                    name: 'game-timeline',
                    component: () => import('@/components/pages/TimeLinePage.vue')
                },
                {
                    path: 'review',
                    name: 'game-review',
                    component: () => import('@/components/pages/ReviewPage.vue')
                },
                {
                    path: 'statistic',
                    name: 'game-statistic',
                    component: () => import('@/components/pages/StatisticPage.vue')
                },
                {
                    path: 'roomsettings',
                    name: 'game-roomsettings',
                    component: () => import('@/components/pages/RoomSetting.vue')
                },
                {
                    path: 'wordcloud',
                    name: 'game-wordcloud',
                    component: () => import('@/components/pages/ReviewPage.vue')
                }
            ],
            beforeEnter(to, from, next) {
                const userStore = useUserStore()
                const { accessToken, refreshToken } = storeToRefs(userStore)

                if (accessToken.value && refreshToken.value) {
                    next()
                } else {
                    next('/')
                }
            }
        },
        {
            path: '/board',
            name: 'board',
            component: () => import('@/components/pages/board/BoardPage.vue')
        },
        {
            path: '/chat',
            name: 'chat',
            component: () => import('@/components/pages/ChattingView.vue')
        },
        {
            path: '/info',
            name: 'info',
            component: () => import('@/components/pages/ServiceView.vue')
        },
        {
            path: '/roomsettings',
            name: 'roomsettings',
            component: () => import('@/components/pages/RoomSetting.vue'),
            meta: {
                hide: true
            }
        }
    ]
})

export default router
