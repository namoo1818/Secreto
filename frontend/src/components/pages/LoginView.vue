<script setup lang="ts">
import FindPasswordForm from '@/components/organisms/main/FindPasswordForm.vue'
import JoinForm from '@/components/organisms/main/JoinForm.vue'
import LoginForm from '@/components/organisms/main/LoginForm.vue'
import MainCard from '@/components/organisms/main/MainCard.vue'
import WideCardTemplate from '@/components/template/WideCardTemplate.vue'
import ServiceFeature from '@/components/molecules/main/ServiceFeature.vue'
import type { DataHandler, Handler, WideCardTemplateType } from '@/types/common'
import type { PasswordFindMailRequest } from '@/types/user'
import type { Ref } from 'vue'
import { onMounted, ref } from 'vue'
import { ViewState, useUserStore } from '@/stores/user'
import { storeToRefs } from 'pinia'
import ModalTemplate from '../template/ModalTemplate.vue'
import YesModalContent from '../organisms/modal/YesModalContent.vue'

const userStore = useUserStore()
const { viewState } = storeToRefs(userStore)
const ButtonLabel = Object.freeze({
    START: 'start',
    JOIN: 'join',
    LOGIN: 'login',
    GAME: 'game',
    ENTER: 'enter'
})
const buttonLabel: Ref<string> = ref(ButtonLabel.START)
const buttonClickHandler: Handler = () => {
    switch (viewState.value) {
        case ViewState.MAIN:
            viewState.value = ViewState.LOGIN
            buttonLabel.value = ButtonLabel.JOIN
            break
        case ViewState.LOGIN:
            viewState.value = ViewState.JOIN
            buttonLabel.value = ButtonLabel.LOGIN
            break
        case ViewState.JOIN:
        case ViewState.PASSWORD:
            viewState.value = ViewState.LOGIN
            buttonLabel.value = ButtonLabel.JOIN
            break
    }
}

// template
const template: Ref<WideCardTemplateType> = ref({
    title: '',
    contentMessages: [],
    buttonLabel: '',
    buttonClickHandler: null
})

// login view
const findPasswordHandler: Handler = () => {
    viewState.value = ViewState.PASSWORD
    buttonLabel.value = ButtonLabel.LOGIN
}
const cardCloseHandler: Handler = () => {
    viewState.value = ViewState.MAIN
    buttonLabel.value = ButtonLabel.START
}

// find password view
const findPasswordEmailSendSuccessHandler: DataHandler<PasswordFindMailRequest> = (
    findRequest: PasswordFindMailRequest
) => {
    console.log(JSON.stringify(findRequest))
    template.value.title = 'Verify Please'
    template.value.contentMessages = [
        '사용자 이메일로 비밀번호 변경 링크를 전송했어요.',
        '이메일을 통해 비밀번호 변경을 완료해주세요.'
    ]
    template.value.buttonLabel = '로그인 페이지 이동'
    template.value.buttonClickHandler = () => {
        viewState.value = ViewState.LOGIN
        buttonLabel.value = ButtonLabel.JOIN
    }
    viewState.value = ViewState.TEMPLATE
}
const findPasswordPrevPageHandler: Handler = () => {
    viewState.value = ViewState.LOGIN
    buttonLabel.value = ButtonLabel.JOIN
}

// modal
const yesModalSeen: Ref<boolean> = ref(false)
const yesModalTitle: Ref<string> = ref('')
const yesModalContent: Ref<string> = ref('')
const yesModalClose = () => {
    yesModalTitle.value = ''
    yesModalContent.value = ''
    yesModalSeen.value = false
}
const yesModalButtonHandler: Ref<(() => void) | undefined> = ref(yesModalClose)
const yesModalOpen = (title: string, content: string, buttonHandler?: () => void) => {
    yesModalTitle.value = title
    yesModalContent.value = content
    yesModalSeen.value = true
    if (buttonHandler) yesModalButtonHandler.value = buttonHandler
    else yesModalButtonHandler.value = yesModalClose
}

onMounted(() => {
    if (viewState.value === ViewState.TEMPLATE) {
        viewState.value = ViewState.MAIN
    }
})
</script>

<template>
    <div class="bg-A805White h-full w-full flex justify-center items-center min-h-[500px]">
        <div class="card-template-container max-md:bg-A805Cream max-md:flex-col">
            <MainCard
                class="max-md:max-w-full max-md:h-full"
                :class="viewState !== ViewState.MAIN ? 'max-md:hidden' : ''"
                v-if="viewState !== ViewState.TEMPLATE"
                @button-click="buttonClickHandler"
                :button-label-ref="buttonLabel"
            >
                <ServiceFeature />
            </MainCard>
            <LoginForm
                class="max-md:max-w-full max-md:max-h-full max-md:h-full max-md:w-full"
                v-if="viewState === ViewState.LOGIN"
                @find-password-handle="findPasswordHandler"
                @close-button-handle="cardCloseHandler"
                @go-register-button-handle="buttonClickHandler"
                @fail-handle="(message: string) => yesModalOpen('Fail', message)"
            />
            <JoinForm
                class="max-md:max-w-full max-md:max-h-full max-md:h-full max-md:w-full"
                v-if="viewState === ViewState.JOIN"
                @close-button-handle="cardCloseHandler"
                @go-login-button-handle="buttonClickHandler"
                @join-success-handle="
                    (message: string) => {
                        yesModalOpen('Success', message, () => {
                            buttonClickHandler()
                            yesModalClose()
                        })
                    }
                "
                @success-handle="(message: string) => yesModalOpen('Success', message)"
                @fail-handle="(message: string) => yesModalOpen('Fail', message)"
            />
            <FindPasswordForm
                class="max-md:max-w-full max-md:max-h-full max-md:h-full max-md:w-full"
                v-if="viewState === ViewState.PASSWORD"
                @email-send-success-handle="findPasswordEmailSendSuccessHandler"
                @prev-page-handle="findPasswordPrevPageHandler"
                @close-button-handle="buttonClickHandler"
                @success-handle="(message: string) => yesModalOpen('Success', message)"
                @fail-handle="(message: string) => yesModalOpen('Fail', message)"
            />
            <WideCardTemplate
                class="max-md:max-w-full max-md:max-h-full max-md:h-full max-md:w-full"
                v-if="viewState === ViewState.TEMPLATE"
                :title="template.title"
                :content-messages="template.contentMessages"
                :button-label="template.buttonLabel"
                @button-click="template.buttonClickHandler"
                @close-button-handle="template.buttonClickHandler"
            />
        </div>

        <ModalTemplate
            custom-id="yesModal"
            custom-class="modal-template-style-1 w-[350px]"
            :seen="yesModalSeen"
            v-if="yesModalSeen"
            @modal-close="yesModalClose"
        >
            <YesModalContent
                @yes-button-handle="yesModalButtonHandler"
                :content-title="yesModalTitle"
                :content-message="yesModalContent"
            />
        </ModalTemplate>
    </div>
</template>
