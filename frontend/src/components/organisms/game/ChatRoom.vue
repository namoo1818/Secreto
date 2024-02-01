<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import ChatProfile from '@/components/molecules/game/ChatProfile.vue'
import { CloseOutlined } from '@ant-design/icons-vue'
import { ref, onMounted, onUnmounted } from 'vue'
import { io } from 'socket.io-client'
import type { Message } from '@/types/chat'
import type { Socket } from 'socket.io-client'

const { imageUrl, name } = defineProps({
    imageUrl: {
        type: String,
        default: ''
    },
    name: {
        type: String,
        default: ''
    },
    customClass: {
        type: String as () => string,
        default: ''
    }
})
const messages = ref<Message[]>([])
const socket: Socket = io('http://localhost:3000', {
    reconnectionDelay: 1000, // 1초마다 재시도
    reconnectionDelayMax: 5000, // 최대 5초까지 재시도 간격 증가
    reconnectionAttempts: 3 // 최대 5번 재시도
})
const attemp = ref(0)

const textMessage = ref<string>('')

onMounted(() => {
    handleResize()
    window.addEventListener('resize', handleResize)
})

socket.on('connect', () => {
    displayConnect('You are connected!', 'connect')
})

socket.on('connect_error', () => {
    if (attemp.value === 3) {
        displayConnect('서버 에러, 다음에 다시 시도해주세요', 'connect')
        return
    }
    displayConnect(`연결실패, 재시도중... ${++attemp.value}`, 'connect')
})

// socket.on('reconnect_failed', () => {
//     console.log('모든 재시도에 실패했습니다')
//     displayConnect('서버와 연결이 원활하지 않습니다', 'connect')
// })

socket.emit('join-room', name, (datas: Message[]) => {
    messages.value = datas
})

socket.on('message', ({ content, type }: Message) => {
    console.log(content, type)
    displayMessage(content, type)
})

const displayConnect = (message: string, type: string) => {
    messages.value.push({ content: message, type: type })
}

const displayMessage = (message: string, type: string) => {
    messages.value.push({ content: message, type: type })
}

const sendMessage = () => {
    // 첫번째 인자로 이벤트명, 두번째 인자로 데이터, 세번째 인자로 방식별자
    socket.emit('message', { content: textMessage.value, type: 'sent' }, name)
    displayMessage(textMessage.value, 'sent')
}

const emit = defineEmits(['close-chat-room'])

const closeChatRoom = () => {
    emit('close-chat-room', name)
}

const chatRoomRef = ref<HTMLElement | null>(null)
const dragging = ref(false)
const offsetX = ref(0)
const offsetY = ref(0)

const startDrag = (e: MouseEvent) => {
    if (!chatRoomRef.value || window.innerWidth <= 768) return

    dragging.value = true
    offsetX.value = e.clientX - chatRoomRef.value.offsetLeft
    offsetY.value = e.clientY - chatRoomRef.value.offsetTop
}

const drag = (e: MouseEvent) => {
    if (!dragging.value || !chatRoomRef.value) return
    chatRoomRef.value.style.left = e.clientX - offsetX.value + 'px'
    chatRoomRef.value.style.top = e.clientY - offsetY.value + 'px'
}

const stopDrag = () => {
    dragging.value = false
}

const handleResize = () => {
    if (window.innerWidth <= 768) {
        chatRoomRef.value?.style.removeProperty('left')
        chatRoomRef.value?.style.removeProperty('top')
    }
}

onUnmounted(() => {
    window.removeEventListener('resize', handleResize)
})
</script>

<template>
    <div
        class="modal md:absolute z-[1] max-md:relative flex flex-col flex-1 overflow-hidden max-md:w-screen max-md:h-full max-md:min-h-[600px] md:min-h-[600px] md:w-[350px] md:rounded-md md:border-2 md:border-solid md:border-A805Black bg-A805Grey md:left-[50%] md:top-[20%] cursor-pointer"
        ref="chatRoomRef"
        @mousedown="startDrag"
        @mousemove="drag"
        @mouseup="stopDrag"
    >
        <div
            class="flex flex-[1] px-[10px] items-center justify-between bg-A805White headerSection"
        >
            <ChatProfile :imageUrl="imageUrl" :name="name" /><CloseOutlined
                style="font-size: 24px"
                @click="closeChatRoom"
            />
        </div>
        <div class="flex flex-[6] flex-col bg-A805White contentsSection px-[10px]">
            <div v-for="(message, index) in messages" :key="index">
                <div class="flex items-end justify-start mb-2" v-if="message.type === 'received'">
                    <div class="bg-white p-3 rounded-md shadow-md">
                        <p>{{ message.content }}</p>
                    </div>
                </div>

                <div class="flex items-end justify-end mb-2" v-else-if="message.type === 'sent'">
                    <div class="bg-yellow-500 p-3 rounded-md shadow-md text-white">
                        <p>{{ message.content }}</p>
                    </div>
                </div>

                <div class="text-center" v-else>
                    <p>{{ message.content }}</p>
                </div>
            </div>
        </div>
        <div class="flex flex-[1] inputSection">
            <textarea
                class="resize-none rounded-md border-2 border-solid border-A805Black cursor-text w-full bg-A805White m-[10px] p-[10px]"
                v-model="textMessage"
                placeholder="내용을 입력해보세요"
            ></textarea>
        </div>
        <div class="flex justify-end">
            <ButtonAtom custom-class="chat-button rounded-md m-[10px]" @button-click="sendMessage"
                >전송</ButtonAtom
            >
        </div>
    </div>
</template>

<style scoped></style>