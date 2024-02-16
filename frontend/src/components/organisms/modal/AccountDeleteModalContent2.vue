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
        <TextAtom custom-class="text-[20px] my-[10px] text-center"
            >탈퇴하면 그 동안의 히스토리가<br />모두 삭제됩니다.<br />그래도
            탈퇴하시겠습니까?</TextAtom
        >
        <div class="flex flex-col mb-[10px]">
            <ButtonAtom
                custom-class="button-style-4 button-claret button-shadow w-[250px] mx-[10px] my-[10px]"
                @button-click="noButtonClick"
                >모든 히스토리 유지하기</ButtonAtom
            >
            <ButtonAtom
                custom-class="button-style-4 button-white button-shadow w-[250px] mx-[10px] my-[10px]"
                @button-click="yesButtonClick"
                >모든 히스토리 삭제하기</ButtonAtom
            >
        </div>
    </div>
</template>

<style scoped></style>
