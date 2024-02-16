<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
defineProps(['contentTitle', 'contentMessage', 'titleClass'])
const emit = defineEmits(['yesButtonHandle', 'noButtonHandle'])
</script>

<template>
    <div class="flex flex-col justify-center items-center">
        <TextAtom custom-class="text-[48px] font-Iceland" :class="titleClass">{{
            contentTitle
        }}</TextAtom>
        <TextAtom custom-class="text-[20px] my-[10px] text-center">{{ contentMessage }}</TextAtom>
        <div class="flex">
            <ButtonAtom
                custom-class="button-style-4 button-claret button-shadow w-[100px] mx-[10px] my-[20px]"
                @button-click="() => emit('yesButtonHandle')"
                >예</ButtonAtom
            >
            <ButtonAtom
                custom-class="button-style-4 button-violet button-shadow w-[100px] mx-[10px] my-[20px]"
                @button-click="() => emit('noButtonHandle')"
                >아니오</ButtonAtom
            >
        </div>
    </div>
</template>

<style scoped></style>
