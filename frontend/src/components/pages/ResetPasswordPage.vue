<script setup lang="ts">
import MainCard from '@/components/organisms/main/MainCard.vue'
import ServiceFeature from '@/components/molecules/main/ServiceFeature.vue'
import ModalTemplate from '@/components/template/ModalTemplate.vue'
import YesModalContent from '@/components/organisms/modal/YesModalContent.vue'
import { ref, type Ref } from 'vue'
import ResetPasswordForm from '@/components/organisms/main/ResetPasswordForm.vue'
import WideCardTemplate from '@/components/template/WideCardTemplate.vue'
import { useRouter } from 'vue-router'

const router = useRouter()
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

const beforeReset: Ref<boolean> = ref(true)
</script>

<template>
    <div class="bg-A805White h-full w-full flex justify-center items-center">
        <div class="card-template-container max-md:bg-A805Cream max-md:flex-col">
            <MainCard v-if="beforeReset" class="max-md:max-w-full max-md:h-full" button-hide="true">
                <ServiceFeature />
            </MainCard>
            <ResetPasswordForm
                v-if="beforeReset"
                @reset-password-success-handle="() => (beforeReset = false)"
                @fail-handle="(message: string) => yesModalOpen('Fail', message)"
            />
            <WideCardTemplate
                class="max-md:max-w-full max-md:max-h-full max-md:h-full max-md:w-full"
                v-if="!beforeReset"
                title="Success"
                :content-messages="[
                    '비밀번호 변경에 성공하였습니다.',
                    '',
                    '원래 페이지로 이동해 로그인해주세요.'
                ]"
                button-label="메인페이지"
                @button-click="() => router.push({ name: 'beforeLogin' })"
                hide-close-button="ture"
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
