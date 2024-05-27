<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import { BoardCategory } from '@/types/board'
import type { Handler } from '@/types/common'
import { computed } from 'vue'
import { useRoute } from 'vue-router'
const route = useRoute()

const emit = defineEmits(['noticeButtonHandle', 'certificateButtonHandle', 'BoastButtonHandle'])

const boardCategory = computed(() => {
    return String(route.query.boardCategory)
})
const clickNoticeBoardButtonHandler: Handler = () => {
    if (boardCategory.value !== BoardCategory.NOTICE) {
        emit('noticeButtonHandle')
    }
}
const clickCertificationBoardButtonHandler: Handler = () => {
    if (boardCategory.value !== BoardCategory.CERTIFICATE) {
        emit('certificateButtonHandle')
    }
}
const clickBoastBoardButtonHandler: Handler = () => {
    if (boardCategory.value !== BoardCategory.BOAST) {
        emit('BoastButtonHandle')
    }
}
</script>

<template>
    <div class="flex justify-center items-center text-A805DarkGrey">
        <div class="flex gap-[5rem]">
            <ButtonAtom
                :class="boardCategory === BoardCategory.NOTICE ? 'text-A805Black' : ''"
                custom-class="text-[28px]"
                @click="clickNoticeBoardButtonHandler"
            >
                공지 <span class="max-md:hidden">게시판</span>
            </ButtonAtom>
            <ButtonAtom
                :class="boardCategory === BoardCategory.CERTIFICATE ? 'text-A805Black' : ''"
                custom-class="text-[28px]"
                @click="clickCertificationBoardButtonHandler"
            >
                인증 <span class="max-md:hidden">게시판</span>
            </ButtonAtom>
            <ButtonAtom
                :class="boardCategory === BoardCategory.BOAST ? 'text-A805Black' : ''"
                custom-class="text-[28px]"
                @click="clickBoastBoardButtonHandler"
            >
                자랑 <span class="max-md:hidden">게시판</span>
            </ButtonAtom>
        </div>
    </div>
</template>

<style scoped></style>
