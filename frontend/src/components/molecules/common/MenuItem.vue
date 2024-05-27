<script setup lang="ts">
import TextAtom from '@/components/atoms/TextAtom.vue'
import type { Handler } from '@/types/common'

const props = defineProps({
    customClass: {
        type: String,
        default: ''
    },
    active: {
        type: Boolean,
        default: false
    }
})

const emit = defineEmits(['menuClick'])
const handleItemClick: Handler = () => {
    emit('menuClick', props.active)
}
</script>

<template>
    <div :class="[customClass, { active: active }]" @click="handleItemClick">
        <TextAtom
            class="truncate"
            :customClass="`${customClass === 'menu-item' ? 'ml-[40px]' : 'ml-[80px]'}`"
        >
            <slot></slot
        ></TextAtom>
    </div>
</template>

<style scoped></style>
