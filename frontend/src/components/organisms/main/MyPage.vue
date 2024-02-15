<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import InputBox from '@/components/molecules/common/InputBox.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import AvatarAtom from '@/components/atoms/AvatarAtom.vue'
import { onMounted, ref, type Ref } from 'vue'
import type { Handler } from '@/types/common'
import type { ModifyRequestType } from '@/types/user'
import CloseButtonAtom from '@/components/atoms/CloseButtonAtom.vue'
import InputImageAtom from '@/components/atoms/InputImageAtom.vue'
import { getUser, withdraw, modify } from '@/api/user'
import ModalTemplate from '@/components/template/ModalTemplate.vue'
import AccountDeleteModalContent1 from '@/components/organisms/modal/AccountDeleteModalContent1.vue'
import AccountDeleteModalContent2 from '@/components/organisms/modal/AccountDeleteModalContent2.vue'
import AccountDeleteModalContent3 from '@/components/organisms/modal/AccountDeleteModalContent3.vue'
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'

const router = useRouter()
const userStore = useUserStore()
const { getUserToStore, clearUserStore, userInfo } = userStore
const emit = defineEmits([
    'passwordChangeHandle',
    'closeButtonHandle',
    'successHandle',
    'failHandle'
])
const email: Ref<string> = ref('')
const userInfoModifyRequest: Ref<ModifyRequestType> = ref({
    nickname: '',
    profileUrl: null
})
const profileImageChangeHandler = (imageUrl: string) => {
    userInfoModifyRequest.value.profileUrl = imageUrl
}
const modifyButtonHandler: Handler = () => {
    modify(
        userInfoModifyRequest.value,
        (response) => {
            const data = response.data
            if (data.status === 'OK') {
                console.log(response.data)
                getUserToStore()
                emit('successHandle', data.message)
            }
        },
        (error) => {
            console.error(error)
            emit('failHandle', error.response.data.message)
        }
    )
}
const profileImageDeleteButtonHandler: Handler = () => {
    userInfoModifyRequest.value.profileUrl = null
}
const changePasswordButtonHandler: Handler = () => {
    emit('passwordChangeHandle')
}
const withdrawalButtonHandler: Handler = () => {
    withdrawModalSeen.value = true
}

// delete Modal
const withdrawModalSeen: Ref<boolean> = ref(false)
const withdrawModalStep: Ref<number> = ref(0)
const modalToggle: Handler = () => {
    if (withdrawModalStep.value > 0) withdrawModalStep.value = 0
    withdrawModalSeen.value = !withdrawModalSeen.value
}

const withdrawSubmitButtonHandle = (password: string) => {
    withdraw(
        { password: password },
        (response) => {
            const data = response.data
            if (data.status === 'OK') {
                console.log(response.data)
                clearUserStore()
                router.push({ name: 'withdrawal' })
            }
            modalToggle()
        },
        (error) => {
            console.error(error)
            emit('failHandle', error.response.data.message)
        }
    )
}
const withdrawOauth = () => {
    alert('OAuth 로그인 회원 삭제')
}

onMounted(() => {
    getUser(
        (response) => {
            const data = response.data
            console.log('유저 정보 조회:', data.message)
            console.log(data.result)
            email.value = data.result.email
            userInfoModifyRequest.value.nickname = data.result.nickname
            userInfoModifyRequest.value.profileUrl = data.result.profileUrl
        },
        (error) => {
            console.error(error)
        }
    )
})
</script>

<template>
    <div class="card-container max-md:justify-center max-md:items-center md:shadow-rb">
        <CloseButtonAtom class="absolute top-2 right-2" @click="() => emit('closeButtonHandle')" />
        <div class="flex h-full w-full justify-between flex-col items-center max-md:h-[500px]">
            <TextAtom custom-class="text-[48px] font-Iceland">My Page</TextAtom>
            <div class="w-full flex flex-col justify-between h-[270px]">
                <InputBox
                    label="이메일"
                    type="text"
                    label-class="ps-[10px]"
                    input-class="input-box-style-1 line-claret"
                    custom-class="w-full"
                    custom-id="email"
                    v-model="email"
                    readonly
                ></InputBox
                ><InputBox
                    label="닉네임"
                    type="text"
                    label-class="ps-[10px]"
                    input-class="input-box-style-1 line-claret"
                    custom-class="w-full"
                    custom-id="email"
                    v-model="userInfoModifyRequest.nickname"
                ></InputBox>
                <div class="w-full flex justify-left ps-[20px] items-center">
                    <InputImageAtom @image-upload-handle="profileImageChangeHandler">
                        <AvatarAtom
                            custom-class="my-page-profile h-[100px] cursor-pointer"
                            :image-url="userInfoModifyRequest.profileUrl"
                        ></AvatarAtom
                    ></InputImageAtom>
                    <div class="flex flex-col text-[12px] text-A805DarkGrey ms-[14px]">
                        <TextAtom>200 X 200</TextAtom>
                        <TextAtom>5MB 이내</TextAtom>
                        <TextAtom>jpg, png, gif 파일</TextAtom>
                        <ButtonAtom
                            custom-class="mt-[10px] font-bold text-left text-A805Blue"
                            @button-click="profileImageDeleteButtonHandler"
                            >삭제</ButtonAtom
                        >
                    </div>
                </div>
            </div>
            <ButtonAtom
                custom-class="button-style-2 w-full button-shadow button-cream"
                @button-click="modifyButtonHandler"
                >수&emsp;정</ButtonAtom
            >
            <div class="flex justify-around w-full">
                <ButtonAtom
                    v-if="userInfo.provider === 'none'"
                    custom-class="text-[14px] text-A805Blue"
                    @button-click="changePasswordButtonHandler"
                    >비밀번호 변경</ButtonAtom
                >
                <ButtonAtom
                    custom-class="text-[14px] text-A805DarkGrey"
                    @button-click="withdrawalButtonHandler"
                    >회원 탈퇴</ButtonAtom
                >
            </div>
        </div>

        <ModalTemplate
            custom-id="modal"
            custom-class="modal-template-style-1 w-[350px]"
            :seen="withdrawModalSeen"
            v-if="withdrawModalSeen"
            @modal-close="modalToggle"
        >
            <AccountDeleteModalContent1
                v-if="withdrawModalStep === 0"
                @yes-button-handle="() => ++withdrawModalStep"
                @no-button-handle="modalToggle"
            />
            <AccountDeleteModalContent2
                v-if="withdrawModalStep === 1"
                @yes-button-handle="
                    () => {
                        if (userInfo.provider === 'none') ++withdrawModalStep
                        else withdrawOauth()
                    }
                "
                @no-button-handle="modalToggle"
            />
            <AccountDeleteModalContent3
                v-if="withdrawModalStep === 2"
                @submit-button-handle="withdrawSubmitButtonHandle"
            />
        </ModalTemplate>
    </div>
</template>

<style scoped></style>
