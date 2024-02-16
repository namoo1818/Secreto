<script setup lang="ts">
import type { Handler } from '@/types/common'

defineProps(['customClass'])

const emit = defineEmits(['imageClick'])
const handleClick: Handler = () => {
    emit('imageClick')
}
</script>

<template>
    <div :class="customClass" @click="handleClick">
        <img src="@/assets/images/character.png" alt="Character" />
    </div>
</template>

<style scoped></style>
