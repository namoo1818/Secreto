<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import { useUserStore } from '@/stores/user'
import { inject, ref, type Ref } from 'vue'
import { modifyReply } from '@/api/board'
import type { ReplyModifyRequestType } from '@/types/board'
import { useRoute } from 'vue-router'
const route = useRoute()

const roomNo: Ref<number> = ref(Number(route.params.roomNo))
const props = defineProps(['nested', 'reply'])

const userStore = useUserStore()
const emit = defineEmits(['modifyReplySuccessHandle', 'cancelButtonHandle'])

const textArea = ref<HTMLInputElement | null>(null)
const resize = () => {
    if (textArea.value) {
        textArea.value.style.height = 'auto'
        textArea.value.style.height = textArea.value.scrollHeight + 'px'
    }
}

const replyModifyHandler = () => {
    if (textArea.value == undefined) return
    const modifyRequest: ReplyModifyRequestType = {
        content: textArea.value?.value,
        anonymityYn: props.reply.anonymityYn || false
    }
    modifyReply(
        roomNo.value,
        props.reply.replyNo,
        modifyRequest,
        (response) => {
            if (response.data.status === 'OK') {
                console.log(response.data.message)
                emit('modifyReplySuccessHandle')
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
            {{ userStore.userInfo.nickname }} ({{ userStore.userInfo.email }})</TextAtom
        >
        <textarea
            ref="textArea"
            class="min-h-[40px] resize-none focus:outline-none my-2"
            placeholder="댓글을 남겨보세요"
            :value="reply.content"
            @input="resize"
        ></textarea>
        <ButtonAtom
            custom-class="button-claret rounded-md font-bold"
            @button-click="replyModifyHandler"
        >
            수정
        </ButtonAtom>
        <ButtonAtom
            v-if="nested"
            custom-class="button-white rounded-md font-bold mt-2"
            @button-click="() => emit('cancelButtonHandle')"
        >
            취소
        </ButtonAtom>
    </div>
</template>

<style scoped></style>
