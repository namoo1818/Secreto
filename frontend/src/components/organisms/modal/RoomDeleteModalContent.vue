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
            >정말로 방에서 나가시겠습니까?</TextAtom
        >
        <div class="flex flex-col justify-center items-center gap-[10px]">
            <div class="flex gap-[20px] mt-[15px] mb-[10px]">
                <ButtonAtom
                    custom-class="button-style-5 button-white button-shadow w-[100px]"
                    @button-click="yesButtonClick"
                    >예</ButtonAtom
                >
                <ButtonAtom
                    custom-class="button-style-5 button-claret button-shadow w-[100px]"
                    @button-click="noButtonClick"
                    >아니오</ButtonAtom
                >
            </div>
        </div>
    </div>
</template>

<style scoped></style>
