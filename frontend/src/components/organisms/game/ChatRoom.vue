<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import ChatProfile from '@/components/molecules/game/ChatProfile.vue'
import SockJs from 'sockjs-client'
import type { Message } from '@/types/chat'
import type { Ref } from 'vue'
import type { RoomUserInfoType } from '@/types/room'
import type { chatInfo } from '@/types/chat'
import { useUserStore } from '@/stores/user'
import { getChattingRoom, getMessages } from '@/api/chatting'
import { CloseOutlined } from '@ant-design/icons-vue'
import { ref, onMounted, onUnmounted, watch, nextTick, inject } from 'vue'
import { storeToRefs } from 'pinia'
import { over } from 'stompjs';
import AvatarAtom from '@/components/atoms/AvatarAtom.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import { convertStringToRegistrationDateTime } from '@/utils/date'
const userStore = useUserStore()
const { accessToken } = storeToRefs(userStore)

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

const emit = defineEmits(['close-chat-room'])

const chattingData = ref({
    authUrl: '',
    stompUrl: 'https://i10a805.p.ssafy.io:8080/chatting',
    destination: '/send/chatting/1',
    subscribe: '/topic/1',
    headers: {
        "AccessToken": `bearer ${accessToken.value}`
    },
    credentials: true,
});

const roomNo = inject('roomNo') as Ref<number>
const chatNo = ref(-1)
const isConnected = ref(false)
const roomType = ref<'MANITO' | 'MANITI' | 'ALL'>('ALL')
const stompClient = ref<any>();
const roomUserInfo = inject('roomUserInfo') as Ref<RoomUserInfoType>
const messages = ref<Message[]>([])
const textMessage = ref<string>('')
const chatRoomRef = ref<HTMLElement | null>(null)
const dragging = ref(false)
const offsetX = ref(0)
const offsetY = ref(0)
const messageContainer = ref<HTMLDivElement | null>(null);

const connectToStompServer = () => {
    var sock = new SockJs(chattingData.value.stompUrl);
    stompClient.value = over(sock);
    stompClient.value.debug = () => { }
    stompClient.value.connect(chattingData.value.headers, () => {
        displayConnect('채팅방에 입장하였습니다.', 'info');
        isConnected.value = true
        subscribe();

        getMessages(roomType.value, chatNo.value, ({ data }) => {
            messages.value.push(...data.result)
        }, (error) => {
            console.log(error)
        })

    }, (error: any) => {
        console.error('WebSocket connection error', error);
        displayConnect('연결에 실패하였습니다.', 'info');

    });
};

const sendMessage = () => {
    if (textMessage.value === '') return
    stompClient.value.send(
        chattingData.value.destination,
        { 'content-type': 'application/json', "AccessToken": chattingData.value.headers.AccessToken },
        JSON.stringify({
            sender: roomUserInfo.value.roomNickname,
            senderId: roomUserInfo.value.roomUserNo,
            message: textMessage.value,
            readYn: false,
            chatNo: chatNo.value,
            sendAt: getCurrentTime(),
            profileUrl: roomUserInfo.value.profileUrl
        })
    );
    textMessage.value = '';
};

const getCurrentTime = () => {
    const currentTime = new Date();
    currentTime.setHours(currentTime.getHours() + 9);
    return currentTime;
}

const getMessage = (message: any) => {
    if (message.body) {
        const data1 = JSON.parse(message.body).body.result;
        messages.value.push(data1)
    }
};

const subscribe = () => {
    stompClient.value.subscribe(chattingData.value.subscribe, getMessage);
};

const displayConnect = (message: string, type: string) => {
    messages.value.push({ message, type })
}

const getChatInfo = async () => {
    getChattingRoom(roomNo.value, ({ data }) => {
        let type = 'MANITO'
        if (name === '마니또') {
            type = 'MANITO'
            roomType.value = 'MANITO'
        }
        else if (name === '단체 채팅') {
            type = 'ALL'
            roomType.value = 'ALL'
        }
        else {
            type = 'MANITI'
            roomType.value = 'MANITI'
        }

        const chatInfo = data.result.find((chatInfo: chatInfo) => chatInfo.chattingUserType === type);

        if (chatInfo) {
            chattingData.value.destination = `/send/chatting/${chatInfo.chatNo}`;
            chattingData.value.subscribe = `/topic/${chatInfo.chatNo}`;
            chatNo.value = chatInfo.chatNo;
        }
    },
        (error) => {
            console.log(error)
        })
}

const closeChatRoom = () => {
    if (stompClient.value) {
        stompClient.value.disconnect()
    }
    emit('close-chat-room', name)
}

const startDrag = (e: MouseEvent) => {
    if (!chatRoomRef.value || window.innerWidth <= 768) return

    dragging.value = true
    offsetX.value = e.clientX - chatRoomRef.value.offsetLeft
    offsetY.value = e.clientY - chatRoomRef.value.offsetTop
}

const drag = (e: MouseEvent) => {
    if (!dragging.value || !chatRoomRef.value) return

    const maxX = window.innerWidth - chatRoomRef.value.clientWidth - 10
    const maxY = window.innerHeight - chatRoomRef.value.clientHeight - 10

    let newLeft = e.clientX - offsetX.value
    let newTop = e.clientY - offsetY.value

    newLeft = Math.max(0, Math.min(newLeft, maxX))
    newTop = Math.max(0, Math.min(newTop, maxY))

    chatRoomRef.value.style.left = newLeft + 'px'
    chatRoomRef.value.style.top = newTop + 'px'
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

const scrollToBottom = () => {
    const container = messageContainer.value;
    if (container) {
        container.scrollTop = container.scrollHeight;
    }
};



const handleKeyDown = (event: KeyboardEvent) => {
    if (event.key === 'Enter') {
        event.preventDefault();
        if (event.shiftKey) {
            textMessage.value += '\n'; // 줄바꿈 추가
            return;
        }
        event.preventDefault();
        sendMessage();

    }
};

watch(() => messages, () => {
    nextTick(() => {
        scrollToBottom();
    });
}, { deep: true });

onMounted(() => {
    handleResize()
    connectToStompServer()
    getChatInfo()

})

onUnmounted(() => {
    window.removeEventListener('resize', handleResize)
})
</script>

<template>
    <div class="md:absolute z-10 max-md:relative max-md:min-h-[650px]  max-md:max-h-[650px] flex flex-col  overflow-hidden max-md:w-screen max-md:h-full  md:h-[600px] md:w-[350px] md:rounded-md md:border-2 md:border-solid md:border-A805Black bg-A805Grey md:left-[50%] md:top-[20%]"
        ref="chatRoomRef" @keydown="handleKeyDown">
        <div class="flex h-[15%]  px-[10px] items-center justify-between headerSection cursor-pointer"
            @mousedown="startDrag" @mousemove="drag" @mouseup="stopDrag">
            <ChatProfile :imageUrl="imageUrl" :name="name" />
            <CloseOutlined style="font-size: 24px" @click="closeChatRoom" />
        </div>
        <div class="flex h-[65%] flex-col bg-A805White contentsSection px-[10px]  overflow-y-scroll" ref="messageContainer">
            <div v-for="(message, index) in messages" :key="index">
                <div class="text-center" v-if="message.type === 'info'">
                    <p class="whitespace-pre-line">{{ message.message }}</p>
                </div>
                <div class="flex items-end justify-end mb-2 gap-[5px]"
                    v-else-if="message.senderId === roomUserInfo.roomUserNo">
                    <p class="text-[10px]"> {{ message.sendAt ? convertStringToRegistrationDateTime(message.sendAt, 12, 17)
                        :
                        '' }}</p>
                    <div class="bg-yellow-500 p-3 rounded-md shadow-md text-white truncate">
                        <p class="whitespace-pre-line">{{ message.message }}</p>
                    </div>
                    <div class="flex flex-col items-center">
                        <AvatarAtom custom-class="w-[40px] h-[40px]" :imageUrl="roomUserInfo.profileUrl" />
                        <TextAtom custom-class="text-xs">나</TextAtom>
                    </div>

                </div>
                <div class="flex items-end justify-start mb-2 gap-[5px]" v-else>
                    <div class="flex flex-col items-center">
                        <AvatarAtom custom-class="w-[40px] h-[40px]" v-if="name === '마니또'" :imageUrl="imageUrl" />
                        <AvatarAtom custom-class="w-[40px] h-[40px]" v-else :imageUrl="message.profileUrl" />
                        <TextAtom custom-class="text-xs">{{ name === '마니또' ? '마니또' : message.sender?.slice(0, 3) }}
                        </TextAtom>
                    </div>
                    <div class="bg-white p-3 rounded-md shadow-md truncate">
                        <p class="whitespace-pre-line">{{ message.message }}</p>
                    </div>
                    <p class="text-[10px]"> {{ message.sendAt ? convertStringToRegistrationDateTime(message.sendAt, 12, 17)
                        :
                        '' }}</p>
                </div>
            </div>
        </div>
        <div class="flex flex-[1] inputSection">
            <textarea
                class="resize-none rounded-md border-2 border-solid border-A805Black cursor-text w-full bg-A805White m-[10px] p-[10px]"
                v-model.trim="textMessage" placeholder="내용을 입력해보세요"></textarea>
        </div>
        <div class="flex  justify-end">
            <ButtonAtom custom-class="chat-button rounded-md m-[10px]" @button-click="sendMessage">전송</ButtonAtom>
        </div>
    </div>
</template>

<style scoped></style>
