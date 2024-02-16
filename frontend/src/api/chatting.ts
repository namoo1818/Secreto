import type { AxiosInstance, AxiosResponse } from 'axios'
import { localAxios } from '@/utils/http-commons'
const messageInstance: AxiosInstance = localAxios()

async function getMessages(
    roomType: 'MANITI' | 'MANITO' | 'ALL',
    chatRoomNo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    console.log(roomType, chatRoomNo, '로 요청')
    messageInstance.get(`/chatting/${roomType}/${chatRoomNo}/`).then(success).catch(fail)
}

async function getChattingRoom(
    roomNo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    messageInstance.get(`/chat/chat_user/${roomNo}`).then(success).catch(fail)
}

export { getMessages, getChattingRoom } 