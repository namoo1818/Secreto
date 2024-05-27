import type { AxiosInstance, AxiosResponse } from 'axios'
import { localAxios } from '@/utils/http-commons'

const userInstance: AxiosInstance = localAxios()

async function login(
    param: object,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    userInstance.post(`/users/log-in`, param).then(success).catch(fail)
}

async function logout(
    param: object,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    userInstance.post(`/users/log-out`, param).then(success).catch(fail)
}

async function signup(
    param: object,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    userInstance.post(`/users/sign-up`, param).then(success).catch(fail)
}

async function signUpEmailVerificaionMailSend(
    email: string,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    userInstance.get(`/cert/${email}`).then(success).catch(fail)
}

async function signUpEmailVerify(
    param: object,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    userInstance.post(`/cert`, param).then(success).catch(fail)
}

async function getUser(success: (response: AxiosResponse) => void, fail: (error: any) => void) {
    userInstance.get(`/users`).then(success).catch(fail)
}

async function withdraw(
    param: object,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    userInstance.put(`/users/delete`, param).then(success).catch(fail)
}

async function modify(
    param: object,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    userInstance.put(`/users/modify`, param).then(success).catch(fail)
}

async function userDetail(
    param: string,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    userInstance.get(`/users/${param}`).then(success).catch(fail)
}

async function changePassword(
    param: object,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    userInstance.put(`/users/change/password`, param).then(success).catch(fail)
}

async function findPasswordMailSend(
    param: object,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    userInstance.post(`/users/password/request`, param).then(success).catch(fail)
}

async function changePasswordConfirm(
    certCode: string,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    userInstance.post(`/users/password/${certCode}`).then(success).catch(fail)
}

async function resetPassword(
    param: object,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    userInstance.put(`/users/password/reset`, param).then(success).catch(fail)
}

async function regenerateToken(
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    await userInstance.get(`/users/refreshAccess`).then(success).catch(fail)
}

export {
    getUser,
    login,
    logout,
    signup,
    signUpEmailVerificaionMailSend,
    signUpEmailVerify,
    withdraw,
    modify,
    changePassword,
    findPasswordMailSend,
    userDetail,
    changePasswordConfirm,
    resetPassword,
    regenerateToken
}
