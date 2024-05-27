export type LoginRequestType = {
    email: string
    password: string
}

export type LogoutRequestType = {
    email: string
    provider: string
}

export type JoinRequestType = {
    email: string
    password: string
    nickname: string
}

export type JoinEmailVerificationRequestType = {
    email: string
    validateCode: string
}

export type ModifyRequestType = {
    nickname: string
    profileUrl: string | null
}

export type PasswordResetRequestType = {
    password: string
    validationCode: string
}

export type PasswordChangeRequest = {
    oldPassword: string
    newPassword: string
}

export type PasswordFindMailRequest = {
    email: string
}

export type ProfileInfoType = {
    id: number
    nickname: string
    profileUrl: string
    email: string
}

export type ProfileInfoCheckBoxType = {
    id: number
    nickname: string
    profileUrl: string
    email: string
    checked: boolean
}
