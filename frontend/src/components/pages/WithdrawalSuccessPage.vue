<script setup lang="ts">
import WideCardTemplate from '@/components/template/WideCardTemplate.vue'
import { useRouter } from 'vue-router'
const router = useRouter()
</script>

<template>
    <div class="bg-A805White h-full w-full flex justify-center items-center">
        <div class="card-template-container max-md:bg-A805Cream max-md:flex-col">
            <WideCardTemplate
                class="max-md:max-w-full max-md:max-h-full max-md:h-full max-md:w-full"
                title="Withdrawal Successful"
                :content-messages="[
                    '계정 정보를 삭제하였습니다.',
                    '',
                    '그동안 이용해주셔서 감사합니다.'
                ]"
                button-label="메인 화면으로 이동하기"
                @button-click="router.push({ name: 'beforeLogin' })"
                @close-button-handle="router.push({ name: 'beforeLogin' })"
            />
        </div>
    </div>
</template>
