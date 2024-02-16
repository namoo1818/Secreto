<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import InputBox from '@/components/molecules/common/InputBox.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import CheckBox from '@/components/molecules/common/CheckBox.vue'
import { ref, type Ref } from 'vue'
import type { Handler } from '@/types/common'
import type { LoginRequestType } from '@/types/user'
import { useUserStore } from '@/stores/user'
const userStore = useUserStore()
const { userLogin, idRememberEmail } = userStore
const { idRemember } = storeToRefs(userStore)

import CloseButtonAtom from '@/components/atoms/CloseButtonAtom.vue'
import { storeToRefs } from 'pinia'

const emit = defineEmits([
    'findPasswordHandle',
    'closeButtonHandle',
    'goRegisterButtonHandle',
    'failHandle'
])
const loginRequest: Ref<LoginRequestType> = ref({
    email: idRemember ? idRememberEmail : '',
    password: ''
})
const loginButtonHandler: Handler = () => {
    userLogin(loginRequest.value, (): void => {
        emit('failHandle', '로그인에 실패했습니다.')
    })
}
const kakaoLoginButtonHandler: Handler = () => {
    window.location.href = 'https://i10a805.p.ssafy.io:8080/oauth2/authorization/kakao'
}
const googleLoginButtonHandler: Handler = () => {
    window.location.href = 'https://i10a805.p.ssafy.io:8080/oauth2/authorization/google'
}
const findPasswordButtonHandler: Handler = () => {
    emit('findPasswordHandle')
}
</script>

<template>
    <div class="card-container max-md:justify-center max-md:items-center md:shadow-rb">
        <CloseButtonAtom class="absolute top-2 right-2" @click="() => emit('closeButtonHandle')" />
        <TextAtom custom-class="text-[48px] font-Iceland">Login</TextAtom>
        <div class="w-full flex flex-col justify-between h-[200px]">
            <InputBox
                label="이메일"
                type="text"
                label-class="ps-[10px]"
                input-class="input-box-style-1 line-claret"
                custom-class="w-full"
                custom-id="email"
                place-holder="example@secreto.com"
                v-model="loginRequest.email"
                @keyup.enter="loginButtonHandler"
            ></InputBox>
            <InputBox
                label="비밀번호"
                type="password"
                label-class="ps-[10px]"
                input-class="input-box-style-1 line-claret"
                custom-class="w-full"
                custom-id="password"
                place-holder="비밀번호를 입력해주세요"
                v-model="loginRequest.password"
                @keyup.enter="loginButtonHandler"
            ></InputBox>
            <div class="text-[12px] w-full flex justify-between items-center">
                <CheckBox
                    custom-class="checkbox-molecule-style-1 text-[14px] accent-[#E0AED0]"
                    custom-id="remember"
                    v-model="idRemember"
                    >아이디 기억하기</CheckBox
                >
                <ButtonAtom custom-class="text-A805Blue" @button-click="findPasswordButtonHandler"
                    >비밀번호를 잊어버리셨나요?</ButtonAtom
                >
            </div>
        </div>
        <ButtonAtom
            custom-class="button-style-2 w-full button-shadow button-cream mt-[20px] mb-[10px]"
            @button-click="loginButtonHandler"
            >로그인</ButtonAtom
        >
        <ButtonAtom
            custom-class="text-A805Blue md:hidden my-2"
            @button-click="() => emit('goRegisterButtonHandle')"
            >회원가입 하러가기</ButtonAtom
        >
        <div class="flex justify-center w-full h-[60px]">
            <ButtonAtom @button-click="googleLoginButtonHandler">
                <img
                    class="w-[40px] h-[40px] mx-[20px]"
                    src="@/assets/images/button/login-google.png"
                />
            </ButtonAtom>
            <ButtonAtom @button-click="kakaoLoginButtonHandler">
                <img
                    class="w-[40px] h-[40px] mx-[20px]"
                    src="@/assets/images/button/login-kakao.png"
            /></ButtonAtom>
        </div>
    </div>
</template>

<style scoped></style>
