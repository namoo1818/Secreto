<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import InputBox from '@/components/molecules/common/InputBox.vue'
import type { Handler } from '@/types/common'
import { ref, type Ref } from 'vue'
import type { RoomCreateRequestType } from '@/types/room'
import { useUserStore } from '@/stores/user'
import { storeToRefs } from 'pinia'
const userStore = useUserStore()
const {userInfo} = storeToRefs(userStore)  

const emit = defineEmits(['yesButtonHandle', 'noButtonHandle'])
const nickname: Ref<string> = ref(userInfo.value.nickname)
const yesButtonClick: Handler = () => {
    emit('yesButtonHandle', nickname.value)
}
const noButtonClick: Handler = () => {
    emit('noButtonHandle')
}
</script>

<template>
    <div class="flex flex-col justify-center items-center">
        <TextAtom custom-class="text-[48px] font-Iceland text-A805Black">MANITO</TextAtom>
        <TextAtom custom-class="text-[20px] my-[10px] text-center">닉네임을 입력해주세요</TextAtom>
        <div class="flex flex-col justify-center items-center gap-[10px]">
            <InputBox
                type="text"
                label-class="ps-[3px]"
                v-model="nickname"
                input-class="input-box-style-2 bg-A805White line-claret w-[250px] h-[40px]"
                place-holder="닉네임을 입력해주세요"
            />
            <div class="flex gap-[20px] mt-[15px] mb-[10px]">
                <ButtonAtom
                    custom-class="button-style-5 button-claret button-shadow w-[100px]"
                    @button-click="yesButtonClick"
                    >입장</ButtonAtom
                >
                <ButtonAtom
                    custom-class="button-style-5 button-claret button-shadow w-[100px]"
                    @button-click="noButtonClick"
                    >취소</ButtonAtom
                >
            </div>
        </div>
    </div>
</template>

<style scoped></style>
