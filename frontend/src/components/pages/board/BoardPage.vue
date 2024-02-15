<script setup lang="ts">
import BoardHeader from '@/components/molecules/board/BoardHeader.vue'
import LineAtom from '@/components/atoms/LineAtom.vue'

import { useRouter } from 'vue-router'

const router = useRouter()
</script>

<template>
    <div class="flex flex-1 flex-col justify-start mt-8 w-full items-center">
        <BoardHeader
            class="ms-6"
            @notice-button-handle="
                () => router.push({ name: 'game-board-list', query: { boardCategory: 'NOTICE' } })
            "
            @certificate-button-handle="
                () =>
                    router.push({
                        name: 'game-board-list',
                        query: { boardCategory: 'CERTIFICATE' }
                    })
            "
            @boast-button-handle="
                () => router.push({ name: 'game-board-list', query: { boardCategory: 'BOAST' } })
            "
        ></BoardHeader>
        <LineAtom custom-class="my-4 border-A805LightGrey" />
        <RouterView />
    </div>
</template>

<style scoped></style>
