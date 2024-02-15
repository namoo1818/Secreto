<script setup lang="ts">
import AvatarAtom from '@/components/atoms/AvatarAtom.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import { ArrowRightOutlined, CheckCircleOutlined, CloseCircleOutlined } from '@ant-design/icons-vue'

defineProps({
    maniti: {
        type: Object as () => { profileUrl: string | null; nickname: string },
        required: true
    },
    manito: {
        type: Object as () => { profileUrl: string | null; nickname: string },
        required: true
    },
    predictCorrect: { type: Boolean, required: true }
})
</script>

<template>
    <div class="flex flex-col text-4 justify-center items-center w-full text-[20px] border-b py-3">
        <div class="flex items-center gap-2 w-full">
            <AvatarAtom customClass="predict-result-profile" :imageUrl="maniti.profileUrl" />
            <TextAtom>
                <span class="text-A805LightBlue">{{ maniti.nickname }}</span
                >의 마니또</TextAtom
            >
        </div>
        <div class="flex items-center gap-2 w-full justify-end">
            <ArrowRightOutlined class="text-[28px]" />
            <AvatarAtom customClass="predict-result-profile" :imageUrl="manito.profileUrl" />
            <TextAtom class="text-A805LightBlue">{{ manito.nickname }}</TextAtom>
            <CheckCircleOutlined v-if="predictCorrect" class="ms- text-[32px] text-A805Green" />
            <CloseCircleOutlined v-else class="ms- text-[32px] text-A805Red" />
        </div>
    </div>
</template>

<style scoped></style>
