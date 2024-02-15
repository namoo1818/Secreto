<script setup lang="ts">
import TextAtom from '@/components/atoms/TextAtom.vue'
import defaultImage from '@/assets/images/character2.png'
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
        class="flex flex-col p-3 md:max-w-[350px] w-full border border-A805DarkGrey rounded-lg shadow-rb gap-3 items-center justify-between"
    >
        <TextAtom class="text-[20px] font-bold text-center">{{ title }}</TextAtom>
        <img
            :src="imageUrl ? imageUrl : defaultImage"
            class="w-full rounded-xl bg-A805LightGrey h-[130px]"
            :class="imageUrl ? 'object-cover' : 'object-contain'"
        />
        <div class="flex flex-col text-[22px] w-full items-center gap-1">
            <template v-if="isValid">
                <TextAtom class="text-A805Black">{{ content[0] }}</TextAtom>
                <div class="flex w-full justify-around">
                    <TextAtom class="text-[16px] text-A805DarkGrey">{{ content[1] }}</TextAtom>
                    <TextAtom class="text-[16px] text-A805Khaki font-bold">{{
                        content[2]
                    }}</TextAtom>
                </div>
            </template>
            <div v-if="!isValid" class="h-[90px] w-full flex items-center justify-center">
                <TextAtom>{{ failMessage }}</TextAtom>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
