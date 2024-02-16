<script setup lang="ts">
import TextAtom from '@/components/atoms/TextAtom.vue'
import { BoardCategory } from '@/types/board'
import { convertStringToMobileRegistrationDate } from '@/utils/date'
defineProps(['board'])
</script>

<template>
    <div
        class="flex flex-1 justify-between items-center cursor-pointer md:hidden text-left border-b mx-[20px] py-[20px] border-A805Grey w-[90%]"
    >
        <div class="flex flex-col">
            <TextAtom custom-class="truncate text-[16px] text-A805Black">
                <span
                    v-if="board.boardCategory === BoardCategory.CERTIFICATE"
                    class="text-A805DarkGrey"
                >
                    [{{ board.userMission }}]</span
                >
                {{ board.title }}
            </TextAtom>
            <TextAtom custom-class=" text-[14px] text-A805Khaki">{{ board.writer }}</TextAtom>
            <div class="flex justify-start text-left text-[12px] text-A805Khaki">
                <span class="w-[70px]">{{
                    convertStringToMobileRegistrationDate(board.registerAt)
                }}</span>
                <span class="w-[60px]">조회 : {{ board.hit }}</span>
                <span class="w-[60px]">추천 : {{ board.likedCount }}</span>
                <span class="w-[60px]">댓글 : {{ board.replyCount }}</span>
            </div>
        </div>
        <img class="w-[70px] h-[70px]" :src="board.imgUrl" v-if="board.imgUrl != null" />
    </div>
</template>

<style scoped></style>
