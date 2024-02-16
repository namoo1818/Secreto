export type Mission = {
    id?: number
    content: string
    checked?: boolean
}

export type RoomMission = {
    content: string
}

export type UserMission = {
    userMissionNo: number
    content: string
    missionReceivedAt: string
    missionType: 'SUDDEN' | 'REGULAR'
    missionRerollCount: number
    missionCertifyYn: boolean
}

export type SuddenMissionResponse = {
    content: string
    missionSubmitAt: string
}

export type predictTypes = {
    roomNo: number
    memo: string
    manitoPredictType: 'YES' | 'NO' | 'UNKNOWN'
    memoTo: number
}

export enum predictOption {
    "YES" = 'O',
    "NO" = 'X',
    "UNKNOWN" = '?'
}