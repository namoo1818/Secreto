<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import CharacterAtom from '@/components/atoms/CharacterAtom.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import type { Handler } from '@/types/common'
import CloseButtonAtom from '@/components/atoms/CloseButtonAtom.vue'

defineProps([
    'title',
    'titleClass',
    'contentMessages',
    'contentClass',
    'buttonLabel',
    'hideCloseButton'
])
const emit = defineEmits(['buttonClick', 'closeButtonHandle'])
const handleClick: Handler = () => {
    emit('buttonClick')
}
</script>

<template>
    <div class="wide-card-container max-md:justify-center">
        <CloseButtonAtom
            class="absolute top-2 right-2"
            @click="() => emit('closeButtonHandle')"
            v-if="!hideCloseButton"
        />
        <div class="flex flex-col items-center justify-between h-full w-full max-md:h-[500px]">
            <TextAtom custom-class="text-[48px] font-Iceland" :class="titleClass">{{
                title
            }}</TextAtom>
            <CharacterAtom class="w-[160px]"></CharacterAtom>
            <div
                class="h-[80px] text-[24px] leading-[24px] flex flex-col justify-between items-center text-center max-md:text-[20px]"
            >
                <TextAtom v-for="(message, i) in contentMessages" :key="i + message">
                    {{ message }}
                </TextAtom>
            </div>
            <ButtonAtom
                custom-class="button-style-2 w-[300px] button-shadow button-cream my-[30px]"
                @button-click="handleClick"
                >{{ buttonLabel }}</ButtonAtom
            >
        </div>
    </div>
</template>

<style scoped></style>
