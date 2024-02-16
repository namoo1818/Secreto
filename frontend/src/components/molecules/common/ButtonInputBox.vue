<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import { ref, type Ref } from 'vue'
import type { Handler } from '@/types/common'

const props = defineProps([
    'label',
    'customClass',
    'labelClass',
    'inputClass',
    'buttonClass',
    'customId',
    'defaultValue',
    'placeHolder',
    'type',
    'readonly',
    'hidden',
    'min',
    'max',
    'name',
    'buttonLabel',
    'disabled'
])
const emit = defineEmits(['inputChange', 'buttonClick'])
const inputRef: Ref<string> = ref(props.defaultValue ? props.defaultValue : '')
const model = defineModel()
const handleChange: Handler = () => {
    emit('inputChange', inputRef.value)
}
const buttonClick: Handler = () => {
    emit('buttonClick', inputRef.value)
}
</script>

<template>
    <div class="flex flex-col" :class="customClass">
        <label :class="labelClass" :for="customId" v-if="label && !hidden">{{ label }}</label>
        <div class="flex items-center">
            <input
                class="rounded-e-none"
                :id="customId"
                :type="type"
                :class="inputClass"
                :placeholder="placeHolder"
                :readonly="readonly"
                :name="name"
                :min="min"
                :max="max"
                :hidden="hidden"
                @change="handleChange"
                v-model.lazy="model"
                :disabled="disabled"
            />
            <ButtonAtom
                class="rounded-s-none button-style-6 button-claret pe-[3px]"
                :class="buttonClass"
                :disabled="disabled"
                @buttonClick="buttonClick"
                >{{ buttonLabel }}</ButtonAtom
            >
        </div>
    </div>
</template>

<style scoped></style>
