export interface Message {
    message?: string
    profileUrl?: string
    chatNo? : number
    readYn? : boolean
    registeredAt? : string
    sender? : string
    senderId? : number
    sendAt? : string
    type?: string
}
export interface ChatRoomType {
    name: string
    imageUrl: string
}

export interface chatInfo {
    chatNo : number,
    chatUserNo : number,
    chattingUserType : string,
    roomUserNo : number,
}
