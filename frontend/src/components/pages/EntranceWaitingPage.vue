<script setup lang="ts">
import WideCardTemplate from '@/components/template/WideCardTemplate.vue'
import type { Handler } from '@/types/common'
import type { Ref } from 'vue'
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { getRoom } from '@/api/room'
import { SSEConnect } from '@/api/sse'
const router = useRouter()
const route = useRoute()
const state = ref('')
const enterApprove: Ref<boolean> = ref(false)
let eventSource: EventSource

const prevPageButtonHandler: Handler = () => {
    router.go(-1)
}

const SSEConnection = (roomUserNo: number) => {
    eventSource = SSEConnect(roomUserNo)

    eventSource.onopen = () => {
        console.info('Server Sent Event 연결이 열렸습니다.')
    }

    eventSource.addEventListener('accept', (event) => {
        enterApprove.value = true
    })

    eventSource.addEventListener('start', (event) => {
        alert('게임 시작!')
        router.push(`/game/${route.params.roomNo}`)
    })

    eventSource.addEventListener('reject', (event) => {
        alert('입장이 거절되었습니다.')
        router.push('/')
    })
}

const getStatus = () => {
    getRoom(
        Number(route.params.roomNo),
        ({ data }) => {
            state.value = data.result.roomStatus
            SSEConnection(data.result.userInfo.roomUserNo)
            // 입장승인전
            if (state.value === 'WAIT') {
                return
            }
            //입장승인 됨, 게임시작안함
            else if (
                state.value === 'PARTICIPANT' &&
                (data.result.roomStartYn !== null ? !data.result.roomStartYn : false)
            ) {
                enterApprove.value = true
            }
            //게임시작 혹은 종료한 경우.
            else if (state.value === 'END' || data.result.roomStartYn) {
                eventSource.close()
                router.push(`/game/${route.params.roomNo}`)
            } else {
                alert('오류 발생!')
                router.replace('/main')
            }
        },
        (error) => {
            alert(error.response.data.message)
            router.replace('/main')
        }
    )
}

onMounted(() => {
    getStatus()
})
</script>

<template>
    <div class="bg-A805White h-full w-full flex justify-center items-center">
        <div class="card-template-container">
            <WideCardTemplate title="Waiting..." :content-messages="enterApprove
                ? ['입장이 승인되었습니다.', '', '게임이 시작될 때까지 기다려주세요']
                : ['', '방장의 입장 승인 대기중입니다.', '']
                " button-label="창 닫기" @close-button-handle="prevPageButtonHandler"
                class="max-md:max-w-full max-md:max-h-full max-md:h-full max-md:w-full"
                @button-click="prevPageButtonHandler" />
        </div>
    </div>
</template>
