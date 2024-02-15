export type BoardRequestType = {
    boardCategory: string
    title: string | null
    content: string | null
    writer: string | null
    page: number
    size: number
}

export type BoardResponseType = {
    boardNo: number
    title: string
    writer: string
    writerEmail: string
    writerProfileUrl: string | null
    registerAt: string
    hit: number
    boardCategory: string
    publicYn: boolean
    userMissionNo: number | null
    userMission: string | null
    likedCount: number
    replyCount: number
    imgUrl: string | null
}

export type BoardDetailResponseType = {
    boardNo: number
    roomUserNo: number
    title: string
    content: string
    writer: string
    writerEmail: string
    writerProfileUrl: string | null
    registerAt: string
    hit: number
    boardCategory: string
    publicYn: boolean
    likedYn: boolean
    userMissionNo: number | null
    userMission: string | null
    likedCount: number
}

export type ReplyResponseType = {
    replyNo: number
    content: string
    registerAt: string
    parentReplyNo: number | null
    tagUserNickname: string | null
    roomUserNo: number
    writer: string
    writerEmail: string
    writerProfileUrl: string | null
    children?: ReplyResponseType[] | undefined
    anonymityYn?: boolean
    deleteYn?: boolean
}

export type ReplyModifyRequestType = {
    content: string
    anonymityYn: boolean
}

export type ReplyWriteRequestType = {
    content: string
    parentReplyNo?: number
    tagUserNo?: number
    anonymityYn?: boolean
}

export type BoardWriteRequestType = {
    title: string
    content: string
    imgUrl: string | null
    boardCategory: string
    publicYn: boolean
    userMissionNo: number | null
}

export type BoardModifyRequestType = {
    title: string
    content: string
    imgUrl: string | null
    publicYn: boolean
}

export const BoardCategory = Object.freeze({
    BOAST: 'BOAST',
    CERTIFICATE: 'CERTIFICATE',
    NOTICE: 'NOTICE'
})
