<script setup lang="ts">
import { useUserStore } from '@/stores/user'
import { useRouter, useRoute } from 'vue-router'
const userStore = useUserStore()
const { updateTokens } = userStore

const router = useRouter()
const route = useRoute()

type TokenType = {
    accessToken: string
    refreshToken: string
}

const parseQueryString = (queryString: string): TokenType => {
    const tokenPair: TokenType = {
        accessToken: '',
        refreshToken: ''
    }
    const query = new URLSearchParams(queryString)
    for (const [key, value] of query) {
        if (key === 'accessToken') tokenPair.accessToken = value
        if (key === 'refreshToken') tokenPair.refreshToken = value
    }
    return tokenPair
}

const tokens: TokenType = parseQueryString(route.fullPath.split('?')[1] || '')
const accessToken = tokens.accessToken || ''
const refreshToken = tokens.refreshToken || ''
updateTokens(
    accessToken,
    refreshToken,
    (response) => {
        const data = response.data
        if (data.status === 'OK') {
            router.push({ name: 'main' })
        }
    },
    (error) => {
        router.push({ name: 'main' })
        alert('로그인에 실패했습니다.')
    }
)
</script>

<template>
    <div>accessToken: {{ accessToken }}, refreshToken: {{ refreshToken }}</div>
</template>

<style scoped></style>
