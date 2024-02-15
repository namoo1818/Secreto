<script setup lang="ts">
import type { Handler } from '@/types/common'

defineProps(['customClass', 'disabled'])
const emit = defineEmits(['buttonClick'])

const handleClick: Handler = () => {
    emit('buttonClick')
}
</script>

<template>
    <button :class="customClass" :disabled="disabled" @click="handleClick">
        <slot></slot>
    </button>
</template>

<style scoped></style>
