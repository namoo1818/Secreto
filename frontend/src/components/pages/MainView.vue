<script setup lang="ts">
import WideCardTemplate from '@/components/template/WideCardTemplate.vue'
import { createRoom } from '@/api/room'
import type { DataHandler, Handler, WideCardTemplateType } from '@/types/common'
import type { Ref } from 'vue'
import { ref } from 'vue'
import ModalTemplate from '@/components/template/ModalTemplate.vue'
import MobileInvitationCodeBox from '@/components/molecules/main/MobileInvitationCodeBox.vue'
import MainCard from '@/components/organisms/main/MainCard.vue'
import RoomListView from '@/components/organisms/main/RoomListView.vue'
import MyPage from '@/components/organisms/main/MyPage.vue'
import ChangePasswordForm from '@/components/organisms/main/ChangePasswordForm.vue'
import RoomCreateModalContent from '@/components/organisms/modal/RoomCreateModalContent.vue'
import RoomDeleteModalContent from '@/components/organisms/modal/RoomDeleteModalContent.vue'
import RoomEnterModalContent from '@/components/organisms/modal/RoomEnterModalContent.vue'
import type { RoomCreateRequestType } from '@/types/room'
import { useUserStore } from '@/stores/user'
import MobileMiniHeader from '@/components/molecules/main/MobileMiniHeader.vue'
import InputBox from '@/components/molecules/common/InputBox.vue'
import ServiceFeature from '@/components/molecules/main/ServiceFeature.vue'
import { useRouter } from 'vue-router'
import YesModalContent from '@/components/organisms/modal/YesModalContent.vue'
import MainCardProfile from '../molecules/main/MainCardProfile.vue'
import { checkRoom, enterRoom } from '@/api/room'
const router = useRouter()
const userStore = useUserStore()
const { userLogout } = userStore
const ButtonLabel = Object.freeze({
    START: 'start',
    JOIN: 'join',
    LOGIN: 'login',
    GAME: 'game',
    ENTER: 'enter'
})
const State = Object.freeze({
    MAIN: 'main',
    LOGIN: 'login',
    JOIN: 'join',
    PASSWORD: 'find_password',
    MAIN_AFTER_LOGIN: 'main_after_login',
    MY_PAGE: 'my_page',
    CHANGE_PWD: 'change_password',
    TEMPLATE: 'template'
})
const ModalState = Object.freeze({
    NONE: 'none',
    ROOM_CREATE: 'room_create',
    ROOM_ENTER: 'room_enter',
    ROOM_LEAVE: 'room_leave'
})

const buttonLabel: Ref<string> = ref(ButtonLabel.ENTER)
const state: Ref<string> = ref(State.MAIN_AFTER_LOGIN)

const buttonClickHandler = () => {
    switch (state.value) {
        case State.MAIN_AFTER_LOGIN:
            invitationCodeCheck(entryCode.value)
            break
        case State.CHANGE_PWD:
        case State.MY_PAGE:
            state.value = State.MAIN_AFTER_LOGIN
            buttonLabel.value = ButtonLabel.ENTER
            break
    }
}

const roomModalChange = (data?: string) => {
    modalToggle()
    if (data === 'roomCreate') {
        modalState.value = ModalState.ROOM_CREATE
    } else {
        modalState.value = ModalState.ROOM_ENTER
    }
}

// template
const template: Ref<WideCardTemplateType> = ref({
    title: '',
    contentMessages: [],
    buttonLabel: '',
    buttonClickHandler: null
})

const entryCode = ref('')

// modal
const modalSeen: Ref<boolean> = ref(false)
const modalState: Ref<string> = ref(ModalState.NONE)

const yesModalSeen: Ref<boolean> = ref(false)
const yesModalTitle: Ref<string> = ref('')
const yesModalContent: Ref<string> = ref('')

const changePasswordSuccessHandler = () => {
    yesModalOpen('Success', '비밀번호 변경에 성공하였습니다.')
    state.value = State.MY_PAGE
}

const yesModalOpen = (title: string, content: string) => {
    yesModalTitle.value = title
    yesModalContent.value = content
    yesModalSeen.value = true
}

const yesModalClose = () => {
    yesModalTitle.value = ''
    yesModalContent.value = ''
    yesModalSeen.value = false
}

// delete Modal
const deleteModalStep: Ref<number> = ref(0)
const modalToggle: Handler = () => {
    if (deleteModalStep.value > 0) deleteModalStep.value = 0
    modalSeen.value = !modalSeen.value
}

// room
const roomCreateHandler: DataHandler<RoomCreateRequestType> = (request: RoomCreateRequestType) => {
    // alert('방 생성 ' + JSON.stringify(request))
    createRoom(
        request,
        ({ data }) => {
            console.log(data)
            router.go(0)
        },
        (error) => {
            console.error('error', error)
        }
    )
}
const roomLeaveHandler: DataHandler<number> = (roomNo: number) => {
    alert('방 나가기 ' + roomNo)
}
const roomEnterHandler: DataHandler<string> = (nickname: string) => {
    // 방 입장 api
    enterRoom(
        { entryCode: entryCode.value, nickname: nickname },
        ({ data }) => {
            console.log(data)
            console.log(entryCode, nickname)
            router.push('/waiting/' + data.result)
        },
        (error) => {
            console.error('error', error.response.data.message)
            alert(error.response.data.message)
        }
    )
}

const invitationCodeCheck = (code: string) => {
    console.log('초대코드 검증!', code)
    checkRoom(
        code,
        ({ data }) => {
            if (data.result) {
                entryCode.value = code
                roomModalChange('roomEnter')
                console.table(data)
            } else alert('존재하지 않는 방입니다.')
        },
        (error) => {
            console.error(error)
        }
    )
}

const LogoHandler = () => {
    console.log('clicked!')
    router.push({ name: 'info' })
}

// main
const profileClickHandler = () => {
    state.value = State.MY_PAGE
    buttonLabel.value = ButtonLabel.GAME
}
</script>

<template>
    <div class="bg-A805White h-full w-full flex justify-center items-center min-h-[500px]">
        {{}}
        <div
            class="card-template-container max-md:w-full max-md:h-full max-md:bg-A805Cream max-md:flex-col"
        >
            <!-- pc -->
            <MainCard
                class="max-md:hidden shadow-rb"
                v-if="state !== State.TEMPLATE"
                @button-click="buttonClickHandler"
                @logo-Handle="LogoHandler"
                :button-label-ref="buttonLabel"
            >
                <div
                    v-if="state === State.MAIN_AFTER_LOGIN"
                    class="flex flex-col justify-around items-center h-full pt-6"
                >
                    <MainCardProfile
                        @my-page-handle="profileClickHandler"
                        @logout-handle="userLogout"
                    />
                    <InputBox
                        custom-class="input-box-style-2 mt-[10px] bg-A805White w-[200px] h-[50px]"
                        input-class="text-center text-[24px]"
                        place-holder="초대코드 입력"
                        v-model="entryCode"
                    />
                </div>
                <ServiceFeature v-if="state !== State.MAIN_AFTER_LOGIN" />
            </MainCard>

            <div
                class="card-container max-md:max-w-full max-md:max-h-full max-md:h-full max-md:w-full md:shadow-r p-0"
                v-if="state === State.MAIN_AFTER_LOGIN"
            >
                <!-- mobile -->
                <div class="w-full md:hidden bg-A805Cream">
                    <MobileMiniHeader
                        class="my-2"
                        @my-page-handle="profileClickHandler"
                        @logout-handle="userLogout"
                    />
                    <MobileInvitationCodeBox @submit-button-handle="invitationCodeCheck" />
                </div>
                <RoomListView
                    class="max-w-full w-full h-full max-h-full md:shadow-rb"
                    @submit-button-handle="() => roomModalChange('roomCreate')"
                />
            </div>
            <MyPage
                class="max-md:max-w-full max-md:max-h-full max-md:h-full max-md:w-full"
                v-if="state === State.MY_PAGE"
                @password-change-handle="() => (state = State.CHANGE_PWD)"
                @close-button-handle="buttonClickHandler"
                @success-handle="(message: string) => yesModalOpen('Success', message)"
                @fail-handle="(message: string) => yesModalOpen('Fail', message)"
            />
            <ChangePasswordForm
                class="max-md:max-w-full max-md:max-h-full max-md:h-full max-md:w-full md:shadow-rb"
                v-if="state === State.CHANGE_PWD"
                @prev-button-handle="() => (state = State.MY_PAGE)"
                @close-button-handle="() => (state = State.MY_PAGE)"
                @success-handle="changePasswordSuccessHandler"
                @fail-handle="(message: string) => yesModalOpen('Fail', message)"
            />
            <WideCardTemplate
                v-if="state === State.TEMPLATE"
                :title="template.title"
                :content-messages="template.contentMessages"
                :button-label="template.buttonLabel"
                @button-click="template.buttonClickHandler"
                @close-button-handle="template.buttonClickHandler"
            />
        </div>
    </div>

    <ModalTemplate
        custom-id="modal"
        custom-class="modal-template-style-1 w-[350px]"
        :seen="modalSeen"
        v-if="modalSeen"
        @modal-close="modalToggle"
    >
        <RoomCreateModalContent
            v-if="modalState === ModalState.ROOM_CREATE"
            @yes-button-handle="roomCreateHandler"
            @no-button-handle="modalToggle"
        />
        <RoomDeleteModalContent
            v-if="modalState === ModalState.ROOM_LEAVE"
            @yes-button-handle="roomLeaveHandler"
            @no-button-handle="modalToggle"
        />
        <RoomEnterModalContent
            v-if="modalState === ModalState.ROOM_ENTER"
            @yes-button-handle="roomEnterHandler"
            @no-button-handle="modalToggle"
        />
    </ModalTemplate>

    <ModalTemplate
        custom-id="yesModal"
        custom-class="modal-template-style-1 w-[350px]"
        :seen="yesModalSeen"
        v-if="yesModalSeen"
        @modal-close="modalToggle"
    >
        <YesModalContent
            @yes-button-handle="yesModalClose"
            :content-title="yesModalTitle"
            :content-message="yesModalContent"
        />
    </ModalTemplate>
</template>
