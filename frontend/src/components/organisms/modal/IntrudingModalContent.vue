<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
defineProps(['contentTitle', 'contentMessage', 'titleClass'])
const emit = defineEmits(['rematch', 'intercept'])
</script>

<template>
    <div class="flex flex-col justify-center items-center">
        <TextAtom custom-class="text-[48px] font-Iceland" :class="titleClass">{{
            contentTitle
        }}</TextAtom>
        <TextAtom custom-class="text-[20px] my-[10px] text-center">{{ contentMessage }}</TextAtom>
        <div class="flex max-md:flex-col">
            <ButtonAtom
                custom-class="button-style-intruding button-claret button-shadow mx-[10px] my-[20px]"
                @button-click="() => emit('rematch')"
                >모두 다시 매칭</ButtonAtom
            >
            <ButtonAtom
                custom-class="button-style-intruding button-violet button-shadow mx-[10px] my-[20px]"
                @button-click="() => emit('intercept')"
                >사이에 끼어들기</ButtonAtom
            >
        </div>
    </div>
</template>

<style scoped></style>
