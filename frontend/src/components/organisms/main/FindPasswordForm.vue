<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import InputBox from '@/components/molecules/common/InputBox.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import { ref, type Ref } from 'vue'
import type { Handler } from '@/types/common'
import type { PasswordFindMailRequest } from '@/types/user'
import { findPasswordMailSend } from '@/api/user'
import CloseButtonAtom from '@/components/atoms/CloseButtonAtom.vue'

const emit = defineEmits([
    'emailSendSuccessHandle',
    'prevPageHandle',
    'closeButtonHandle',
    'failHandle',
    'successHandle'
])

const passwordFindMailRequest: Ref<PasswordFindMailRequest> = ref({
    email: ''
})
const findPasswordButtonHandler = () => {
    console.log(passwordFindMailRequest.value)
    findPasswordMailSend(
        passwordFindMailRequest.value,
        (response) => {
            const data = response.data
            if (data.status === 'OK') {
                console.log(response.data)
                emit('emailSendSuccessHandle', data.message)
            }
        },
        (error) => {
            console.error(error)
            emit('failHandle', error.response.data.message)
        }
    )
}

const prevPageButtonHandler: Handler = () => {
    emit('prevPageHandle')
}
</script>

<template>
    <div class="card-container max-md:justify-center max-md:items-center md:shadow-rb">
        <CloseButtonAtom class="absolute top-2 right-2" @click="() => emit('closeButtonHandle')" />
        <div class="flex h-full w-full justify-between flex-col items-center max-md:h-[350px]">
            <div class="flex flex-col items-center">
                <TextAtom custom-class="text-[44px] font-Iceland">Find Password</TextAtom>
                <TextAtom custom-class="text-[14px]"
                    >기존에 사용하던 이메일을 입력해주세요.</TextAtom
                >
            </div>
            <InputBox
                label="이메일"
                type="text"
                label-class="ps-[10px]"
                input-class="input-box-style-1 line-claret"
                custom-class="w-full"
                custom-id="email"
                place-holder="example@secreto.com"
                v-model="passwordFindMailRequest.email"
            ></InputBox>
            <div class="w-full mb-[20px]">
                <ButtonAtom
                    custom-class="button-style-2 w-full button-shadow button-cream my-[15px]"
                    @button-click="findPasswordButtonHandler"
                    >비밀번호 찾기</ButtonAtom
                >
                <div class="flex justify-around w-full">
                    <ButtonAtom
                        custom-class="text-[14px] text-A805DarkGrey"
                        @button-click="prevPageButtonHandler"
                        >뒤로가기</ButtonAtom
                    >
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
