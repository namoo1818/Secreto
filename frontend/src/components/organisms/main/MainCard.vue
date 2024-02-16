<script setup lang="ts">
import MainLogo from '@/components/molecules/common/MainLogo.vue'
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import type { Handler } from '@/types/common'

defineProps(['buttonLabelRef', 'buttonHide'])
const emit = defineEmits(['buttonClick', 'logoHandle'])
const buttonClickHandler: Handler = () => {
    emit('buttonClick')
}
</script>

<template>
    <div class="card-container bg-A805Cream md:shadow-rb">
        <MainLogo @click="emit('logoHandle')" />
        <slot></slot>
        <ButtonAtom custom-class="button-style-1 button-claret button-shadow m-[20px]"
            :class="buttonHide ? 'invisible' : ''" @button-click="buttonClickHandler">{{ buttonLabelRef }}</ButtonAtom>
    </div>
</template>

<style scoped></style>
