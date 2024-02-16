import type { AxiosInstance, AxiosResponse } from 'axios'
import { localAxios } from '@/utils/http-commons'
const notificationInstance: AxiosInstance = localAxios()

async function getNotificationLists(
    param: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    await notificationInstance.get(`/alarm-list/${param}`).then(success).catch(fail)
}

async function notificationRead(
    alarmNo : number,
    roomNo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    await notificationInstance.get(`/alarm/${alarmNo}/room/${roomNo}`).then(success).catch(fail)
}


export { getNotificationLists, notificationRead}