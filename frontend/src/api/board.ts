import type { AxiosInstance, AxiosResponse } from 'axios'
import { localAxios } from '@/utils/http-commons'
const boardInstance: AxiosInstance = localAxios()

async function getBoard(
    roomNo: number,
    param: object,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    boardInstance.get(`/board/${roomNo}`, { params: param }).then(success).catch(fail)
}

async function getPost(
    roomNo: number,
    boardNo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    boardInstance.get(`/post/${boardNo}/room/${roomNo}`).then(success).catch(fail)
}

async function createPost(
    roomNo: number,
    param: object,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    boardInstance.post(`/post/${roomNo}`, param).then(success).catch(fail)
}

async function modifyPost(
    boardNo: number,
    roomNo: number,
    param: object,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    boardInstance.put(`/post/${boardNo}/room/${roomNo}`, param).then(success).catch(fail)
}

async function deletePost(
    boardNo: number,
    roomNo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    boardInstance.delete(`/post/${boardNo}/room/${roomNo}`).then(success).catch(fail)
}

async function getReplies(
    roomNo: number,
    boardNo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    boardInstance.get(`/reply/${boardNo}/room/${roomNo}`).then(success).catch(fail)
}

async function postReply(
    roomNo: number,
    postNo: number,
    param: object,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    boardInstance.post(`/reply/${postNo}/room/${roomNo}`, param).then(success).catch(fail)
}

async function deleteReply(
    roomNo: number,
    replyNo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    boardInstance.delete(`/reply/${replyNo}/room/${roomNo}`).then(success).catch(fail)
}

async function modifyReply(
    roomNo: number,
    replyNo: number,
    param: object,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    boardInstance.put(`/reply/${replyNo}/room/${roomNo}`, param).then(success).catch(fail)
}

async function boardLike(
    roomNo: number,
    boardNo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    boardInstance.post(`/post/${boardNo}/like/room/${roomNo}`).then(success).catch(fail)
}

async function boardUnlike(
    roomNo: number,
    boardNo: number,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    boardInstance.delete(`/post/${boardNo}/unlike/room/${roomNo}`).then(success).catch(fail)
}

export {
    getBoard,
    getPost,
    modifyPost,
    deletePost,
    getReplies,
    postReply,
    deleteReply,
    modifyReply,
    createPost,
    boardLike,
    boardUnlike
}
