<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import InputBox from '@/components/molecules/common/InputBox.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import { ref, type Ref } from 'vue'
import type { Handler } from '@/types/common'
import type { PasswordResetRequestType } from '@/types/user'
import { resetPassword } from '@/api/user'
import { useRoute } from 'vue-router'
const route = useRoute()

const emit = defineEmits(['resetPasswordSuccessHandle', 'successHandle', 'failHandle'])

const password: Ref<string> = ref('')
const passwordConfirm: Ref<string> = ref('')
const resetPasswordButtonHandler: Handler = () => {
    if (password.value !== passwordConfirm.value) {
        emit('failHandle', '비밀번호 확인이 일치하지 않습니다.')
        return
    }
    const passwordResetRequest: PasswordResetRequestType = {
        password: password.value,
        validationCode: String(route.query.code)
    }
    resetPassword(
        passwordResetRequest,
        (response) => {
            const data = response.data
            if (data.status === 'OK') {
                console.log(response.data)
                emit('resetPasswordSuccessHandle', data.message)
            }
        },
        (error) => {
            // alert(error.response.data.message)
            console.error(error)
            emit('failHandle', error.response.data.message)
        }
    )

    console.log(passwordResetRequest)
}
</script>

<template>
    <div class="card-container max-md:justify-center max-md:items-center">
        <div class="flex h-full w-full justify-between flex-col items-center max-md:h-[500px]">
            <div class="flex flex-col items-center">
                <TextAtom custom-class="text-[44px] font-Iceland">New Password</TextAtom>
                <TextAtom custom-class="text-[14px]"
                    >새로 사용할 비밀번호를 입력해 주세요.</TextAtom
                >
            </div>
            <div class="flex flex-col w-full gap-5">
                <InputBox
                    label="비밀번호"
                    type="password"
                    label-class="ps-[10px]"
                    input-class="input-box-style-1 line-claret"
                    custom-class="w-full"
                    custom-id="newPassword"
                    place-holder="비밀번호를 입력해주세요"
                    v-model="password"
                ></InputBox>
                <InputBox
                    label="비밀번호 확인"
                    type="password"
                    label-class="ps-[10px]"
                    input-class="input-box-style-1 line-claret"
                    custom-class="w-full"
                    custom-id="newPasswordConfirm"
                    place-holder="비밀번호를 한번 더 입력해주세요"
                    v-model="passwordConfirm"
                ></InputBox>
            </div>
            <ButtonAtom
                custom-class="button-style-2 w-full button-shadow button-cream my-[15px]"
                @button-click="resetPasswordButtonHandler"
                >비밀번호 변경</ButtonAtom
            >
        </div>
    </div>
</template>

<style scoped></style>
