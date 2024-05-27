<script setup lang="ts">
import TextAtom from '@/components/atoms/TextAtom.vue'
import AvatarAtom from '@/components/atoms/AvatarAtom.vue'
defineProps({
    title: { type: String, required: true },
    content: { type: Object as () => Array<String>, required: true },
    imageUrl: { type: String as () => string | null },
    isValid: { type: Boolean, default: true },
    failMessage: { type: String, default: '존재하지 않습니다.' }
})
</script>

<template>
    <div
        class="flex flex-col p-3 w-full md:max-w-[350px] border border-A805DarkGrey rounded-lg shadow-rb gap-3"
    >
        <TextAtom class="text-[20px] font-bold text-center">{{ title }}</TextAtom>
        <div class="flex h-full items-center">
            <template v-if="isValid">
                <AvatarAtom customClass="header-profile min-w-[90px]" :imageUrl="imageUrl" />
                <div class="flex flex-col text-[22px] ms-3">
                    <TextAtom class="text-A805Black">{{ content[0] }}</TextAtom>
                    <TextAtom class="text-[16px] text-A805Khaki font-bold">{{
                        content[1]
                    }}</TextAtom>
                    <TextAtom class="text-[14px] text-A805DarkGrey">{{ content[2] }}</TextAtom>
                </div>
            </template>
            <div v-if="!isValid" class="h-[90px] w-full flex items-center justify-center">
                <TextAtom>{{ failMessage }}</TextAtom>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
