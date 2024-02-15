<script setup lang="ts">
import TextAtom from '@/components/atoms/TextAtom.vue'
import type { notificationTypes } from '@/types/notify'
import { notificationRead } from '@/api/notification'
import { inject, type Ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { convertStringToRegistrationDateTime } from '@/utils/date'
import ButtonAtom from '../atoms/ButtonAtom.vue'
const emit = defineEmits(['refreshNotify'])

const router = useRouter()
const route = useRoute()
const notificationLists = inject('notifyLists') as Ref<notificationTypes[]>

const notifyReading = (alarmNo: number) => {
    notificationRead(
        alarmNo,
        Number(route.params.roomNo),
        ({ data }) => {
            emit('refreshNotify')
        },
        (error) => {
            console.log(error)
        }
    )
}

const isBoardNotify = (notify: notificationTypes) => {
    return !!Number(notify.author) === true
}
const boardDetailMove = (notify: notificationTypes) => {
    if (isBoardNotify(notify)) {
        router.push({
            name: 'game-board-detail',
            query: { boardNo: notify.author }
        })
    }
}
</script>

<template>
    <div class="flex flex-1 flex-col bg-A805White p-10">
        <TextAtom custom-class="text-1 w-full text-center">알림 리스트</TextAtom>
        <template v-for="notify in notificationLists.slice().reverse()" :key="notify.alarmNo">
            <div v-if="!notify.readYn"
                class="flex items-center justify-center h-[60px] md:mx-[30px] max-md:mx-[10px] rounded-md border-black border-solid border-2 mt-[30px] bg-A805RealWhite md:gap-[30px] max-md:gap-[10px] px-[10px] cursor-pointer"
                @click="() => notifyReading(notify.alarmNo)">
                <TextAtom custom-class="text-A805Red">New!</TextAtom>
                <TextAtom class="font-bold">{{ notify.content }}
                    <span class="text-A805DarkGrey text-[14px]">({{
                        convertStringToRegistrationDateTime(notify.generatedAt, 2, 17) }})</span>
                </TextAtom>
            </div>
            <div v-else @click="() => notifyReading(notify.alarmNo)"
                class="flex items-center justify-center h-[60px] md:mx-[30px] max-md:mx-[10px] rounded-md border-black border-solid border-2 mt-[30px] bg-A805Grey bg-opacity-50 text-A805Black max-md:gap-[10px] md:gap-[30px] px-[10px]">
                <TextAtom>{{ notify.content }}
                    <span class="text-A805DarkGrey text-[14px]">({{
                        convertStringToRegistrationDateTime(notify.generatedAt, 2, 17) }})</span>
                </TextAtom>
                <TextAtom>읽기 완료</TextAtom>
                <ButtonAtom class="text-A805Blue" v-if="isBoardNotify(notify)" @click="() => boardDetailMove(notify)">게시글 이동
                </ButtonAtom>
            </div>
        </template>
    </div>
</template>

<style scoped></style>
