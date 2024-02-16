import type { AxiosInstance, AxiosResponse } from 'axios'
import { localAxios } from '@/utils/http-commons'
const historyInstance: AxiosInstance = localAxios()

async function getPredictResult(
    roomNo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    await historyInstance.get(`/history/${roomNo}/predict`).then(success).catch(fail)
}

async function getSummary(
    roomNo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    await historyInstance.get(`/history/${roomNo}/summary`).then(success).catch(fail)
}

// 나의 활동 기록
async function getMyHistoryList(
    roomUserNo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    await historyInstance.get(`/history/${roomUserNo}/maniti`).then(success).catch(fail)
}

// 내 마니또의 활동 기록
async function getManitoHisotryList(
    roomUserNo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    await historyInstance.get(`/history/${roomUserNo}/manito`).then(success).catch(fail)
}

async function getWordCloud(
    param: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    await historyInstance.get(`/history/${param}/wordCloud`).then(success).catch(fail)
}

async function sendWord(
    param: number,
    text: string,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    await historyInstance
        .post(`/history/${param}/wordCloud`, { contents: text })
        .then(success)
        .catch(fail)
}

export {
    getPredictResult,
    getSummary,
    getWordCloud,
    sendWord,
    getMyHistoryList,
    getManitoHisotryList
}
