<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import type { Handler } from '@/types/common'

const emit = defineEmits(['yesButtonHandle', 'noButtonHandle'])
const yesButtonClick: Handler = () => {
    emit('yesButtonHandle')
}
const noButtonClick: Handler = () => {
    emit('noButtonHandle')
}
</script>

<template>
    <div class="flex flex-col justify-center items-center">
        <TextAtom custom-class="text-[48px] font-Iceland text-A805Red">WARN</TextAtom>
        <TextAtom custom-class="text-[24px] my-[10px]">탈퇴하시겠습니까?</TextAtom>
        <div class="flex">
            <ButtonAtom
                custom-class="button-style-4 button-claret button-shadow w-[100px] mx-[10px] my-[20px]"
                @button-click="yesButtonClick"
                >예</ButtonAtom
            >
            <ButtonAtom
                custom-class="button-style-4 button-violet button-shadow w-[100px] mx-[10px] my-[20px]"
                @button-click="noButtonClick"
                >아니오</ButtonAtom
            >
        </div>
    </div>
</template>

<style scoped></style>
