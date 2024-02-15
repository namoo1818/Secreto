<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import InputBox from '@/components/molecules/common/InputBox.vue'
import ButtonInputBox from '@/components/molecules/common/ButtonInputBox.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import { ref, type Ref } from 'vue'
import type { Handler } from '@/types/common'
import type { JoinRequestType, JoinEmailVerificationRequestType } from '@/types/user'
import { signup, signUpEmailVerificaionMailSend, signUpEmailVerify } from '@/api/user'
import CloseButtonAtom from '@/components/atoms/CloseButtonAtom.vue'

const emit = defineEmits([
    'closeButtonHandle',
    'goLoginButtonHandle',
    'failHandle',
    'successHandle',
    'joinSuccessHandle'
])
const passwordConfirm: Ref<string> = ref('')
const verificationCode: Ref<string> = ref('')
const joinRequest: Ref<JoinRequestType> = ref({
    email: '',
    password: '',
    nickname: ''
})
const emailVerificationButtonHandler = () => {
    signUpEmailVerificaionMailSend(
        joinRequest.value.email,
        (response) => {
            const data = response.data
            if (data.status === 'OK') {
                console.log(response.data)
                emit('successHandle', data.message)
            }
        },
        (error) => {
            console.error(error)
            emit('failHandle', error.response.data.message)
        }
    )
}
const verificationCodeButtonHandler = () => {
    const verificationRequest: JoinEmailVerificationRequestType = {
        email: joinRequest.value.email,
        validateCode: verificationCode.value
    }

    signUpEmailVerify(
        verificationRequest,
        (response) => {
            const data = response.data
            if (data.status === 'OK') {
                console.log(response.data)
                emit('successHandle', data.message)
            }
        },
        (error) => {
            console.error(error)
            emit('failHandle', error.response.data.message)
        }
    )
}
const joinButtonHandler: Handler = () => {
    if (joinRequest.value.password !== passwordConfirm.value) {
        emit('failHandle', '비밀번호 확인이 일치하지 않습니다.')
        return
    }
    signup(
        joinRequest.value,
        (response) => {
            const data = response.data
            if (data.status === 'OK') {
                console.log(response.data)
                emit('joinSuccessHandle', data.message)
            }
        },
        (error) => {
            // alert(error.response.data.message)
            console.error(error)
            emit('failHandle', error.response.data.message)
        }
    )
}
</script>

<template>
    <div
        class="card-container scroll-container max-md:justify-center max-md:items-center md:shadow-rb"
    >
        <CloseButtonAtom class="absolute top-2 right-2" @click="() => emit('closeButtonHandle')" />
        <TextAtom custom-class="text-[48px] font-Iceland">Join</TextAtom>
        <div class="w-full mb-[20px]">
            <ButtonInputBox
                label="이메일"
                type="email"
                label-class="ps-[10px]"
                input-class="input-box-style-1 line-claret"
                custom-class="w-full my-[30px]"
                custom-id="email"
                place-holder="example@secreto.com"
                v-model="joinRequest.email"
                button-label="인증"
                @button-click="emailVerificationButtonHandler"
                @keyup.enter="emailVerificationButtonHandler"
            ></ButtonInputBox>
            <ButtonInputBox
                label="인증코드 입력"
                type="text"
                label-class="ps-[10px]"
                input-class="input-box-style-1 line-claret"
                custom-class="w-full my-[30px]"
                custom-id="verificationCode"
                place-holder="인증코드를 입력해주세요"
                v-model="verificationCode"
                button-label="확인"
                @button-click="verificationCodeButtonHandler"
                @keyup.enter="verificationCodeButtonHandler"
            ></ButtonInputBox>
            <InputBox
                label="비밀번호"
                type="password"
                label-class="ps-[10px]"
                input-class="input-box-style-1 line-claret"
                custom-class="w-full my-[30px]"
                custom-id="password"
                place-holder="비밀번호를 입력해주세요"
                v-model="joinRequest.password"
                @keyup.enter="joinButtonHandler"
            ></InputBox>
            <InputBox
                label="비밀번호 확인"
                type="password"
                label-class="ps-[10px]"
                input-class="input-box-style-1 line-claret"
                custom-class="w-full my-[30px]"
                custom-id="passwordConfirm"
                place-holder="비밀번호 확인을 입력해주세요"
                v-model="passwordConfirm"
                @keyup.enter="joinButtonHandler"
            ></InputBox>
            <InputBox
                label="닉네임"
                type="text"
                label-class="ps-[10px]"
                input-class="input-box-style-1 line-claret"
                custom-class="w-full my-[30px]"
                custom-id="nickname"
                place-holder="닉네임을 입력해주세요"
                v-model="joinRequest.nickname"
                @keyup.enter="joinButtonHandler"
            ></InputBox>
            <ButtonAtom
                custom-class="button-style-2 w-full button-shadow button-cream mt-[15px]"
                @button-click="joinButtonHandler"
                >회원가입</ButtonAtom
            >
            <ButtonAtom
                custom-class="text-A805Blue md:hidden w-full mt-4"
                @button-click="() => emit('goLoginButtonHandle')"
                >로그인하러가기</ButtonAtom
            >
        </div>
    </div>
</template>

<style scoped></style>
@/api/user
