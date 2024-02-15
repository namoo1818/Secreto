import type { AxiosInstance, AxiosResponse } from 'axios'
import { localAxios } from '@/utils/http-commons'
import type { predictTypes } from '@/types/mission'
const missonInstance: AxiosInstance = localAxios()

async function getSystemMission(
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    missonInstance.get(`/mission`).then(success).catch(fail)
}

async function getSuddenMission(
    roomNo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    missonInstance.get(`/mission/sudden/${roomNo}`).then(success).catch(fail)
}

async function addUnexpectedMission(
    param: {
        roomNo: number
        content: string
        missionSubmitAt?: string
    },
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    missonInstance.post(`/mission/sudden`, param).then(success).catch(fail)
}

async function getUserMission(
    roomNo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    missonInstance.get(`/mission/user/${roomNo}`).then(success).catch(fail)
}

async function getRoomMission(
    roomNo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    missonInstance.get(`/mission/${roomNo}`).then(success).catch(fail)
}

async function memoUser(
    param: predictTypes,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    missonInstance.post(`/mission/memo_user`, param).then(success).catch(fail)
}

async function getUserMemo(
    roomNo: number,
    memoTo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    missonInstance.get(`/mission/memo_user/${roomNo}?memoTo=${memoTo}`).then(success).catch(fail)
}

async function rerollMission(
    param: { roomNo: number; userMissionNo: number },
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    missonInstance.put(`/mission/reroll`, param).then(success).catch(fail)
}

export {
    getSystemMission,
    getSuddenMission,
    getUserMission,
    getRoomMission,
    addUnexpectedMission,
    memoUser,
    getUserMemo,
    rerollMission
}
