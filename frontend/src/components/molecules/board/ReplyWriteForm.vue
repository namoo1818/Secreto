<script setup lang="ts">
import { postReply } from '@/api/board'
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import { useUserStore } from '@/stores/user'
import { inject, ref, type Ref } from 'vue'
import type { ReplyWriteRequestType } from '@/types/board'
import { useRoute } from 'vue-router'
const route = useRoute()

const roomNo: Ref<number> = inject('roomNo', ref(-1))
const boardNo: Ref<number> = inject('boardNo', ref(-1))
const userStore = useUserStore()
const emit = defineEmits(['submitReplySuccessHandle'])
const roomUserInfo = inject('roomUserInfo', {
    roomNo: Number(route.params.roomNo),
    roomUserNo: -1,
    roomName: '',
    roomNickname: '',
    profileUrl: ''
})
const roomUserNo: Ref<number> = inject('roomUserNo', ref(-1))
const textArea = ref<HTMLInputElement | null>(null)
const resize = () => {
    if (textArea.value) {
        textArea.value.style.height = 'auto'
        textArea.value.style.height = textArea.value.scrollHeight + 'px'
    }
}

const replyWriteHandler = () => {
    const replyRequest: ReplyWriteRequestType = {
        content: ''
    }
    if (textArea.value) {
        if (textArea.value.value === '') {
            alert('댓글을 입력하세요!')
            return
        }
        replyRequest.content = textArea.value.value
    }
    console.log(replyRequest)
    postReply(
        roomNo.value,
        boardNo.value,
        replyRequest,
        (response) => {
            if (response.data.status === 'OK') {
                console.log(response.data.message)
                emit('submitReplySuccessHandle')
                if (textArea.value) {
                    textArea.value.value = ''
                    resize()
                }
            }
        },
        (error) => alert(error.message)
    )
}
</script>

<template>
    <div class="flex flex-col flex-1 border-2 rounded-lg p-3 text-[16px]">
        <TextAtom custom-class="font-bold">
            {{ roomUserInfo.roomNickname }} ({{ userStore.userInfo.email }})</TextAtom
        >
        <textarea
            ref="textArea"
            class="min-h-[40px] resize-none focus:outline-none my-2"
            placeholder="댓글을 남겨보세요"
            @input="resize"
        ></textarea>
        <ButtonAtom
            custom-class="button-claret rounded-md font-bold"
            @button-click="replyWriteHandler"
        >
            등록
        </ButtonAtom>
    </div>
</template>

<style scoped></style>
