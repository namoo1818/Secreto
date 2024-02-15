<script setup lang="ts">
import type { Handler } from '@/types/common'

defineProps([
    'label',
    'customClass',
    'labelClass',
    'inputClass',
    'customId',
    'placeHolder',
    'type',
    'readonly',
    'hidden',
    'min',
    'max',
    'name',
    'maxLength',
])
const emit = defineEmits(['inputEnter'])
const model = defineModel()
const handleEnter: Handler = () => {
    emit('inputEnter')
}
</script>

<template>
    <div class="flex flex-col" :class="customClass">
        <label :class="labelClass" :for="customId" v-if="label && !hidden">{{ label }}</label>
        <input
            :id="customId"
            :type="type"
            :class="inputClass"
            :placeholder="placeHolder"
            :readonly="readonly"
            :name="name"
            :min="min"
            :max="max"
            :hidden="hidden"
            v-model="model"
            :maxlength="maxLength"
            @keypress.enter="handleEnter"
        />
    </div>
</template>

<style scoped></style>
