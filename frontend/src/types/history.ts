export type HistoryType = {
    index: number
    title: string
    date: string
    content: string
    imageUrl: string | null
    link: string
    activity: string
}

export type HistoryArrayType = {
    date : string,
    source : string,
    value :       
    {
        post: HistoryTypes[]; 
        predictor: PredictType[]; 
    }
}

export type HistoryTypes = {
    title: string
    thumbnail? : string
    postId: number
    nickName : string
    mission? : string
    hit : number
    entryAt : string
    content: string
    category : string
}


export type PredictType = {
    id : number
    entryAt : string
    predictReason: string
    predictorNickname : string
    targetNickName : string
}