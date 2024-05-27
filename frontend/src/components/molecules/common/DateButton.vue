<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import { watch, ref, type Ref } from 'vue'
import type { Handler } from '@/types/common'

const props = defineProps([
    'label',
    'customClass',
    'labelClass',
    'inputClass',
    'buttonClass',
    'slotClass',
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
    'period'
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
const plusNumber: Handler = () => {
    if (typeof model.value === 'number') {
        model.value = model.value + 1
    }
}
const minusNumber: Handler = () => {
    if (typeof model.value === 'number') {
        model.value = model.value - 1
    }
}
watch(model, (newPeriod) => {
    if (model.value == '') {
        model.value = 1
    } else if (typeof newPeriod == 'number') {
        model.value = newPeriod > 1000 ? 1000 : newPeriod < 1 ? 1 : newPeriod
    } else {
        model.value = 1
    }
    console.log('!')
})
</script>

<template>
    <div class="flex flex-col">
        <label :class="labelClass" :for="customId" v-if="label && !hidden">{{ label }}</label>
        <div class="flex items-center" :class="customClass">
            <ButtonAtom class="rounded-e-none" :class="buttonClass" @buttonClick="minusNumber"
                >-</ButtonAtom
            >
            <div class="flex justify-center content-center items-center px-3">
                <input
                    class="text-right w-[100%]"
                    :id="customId"
                    :type="type"
                    :class="inputClass"
                    :readonly="readonly"
                    :name="name"
                    :hidden="hidden"
                    v-model="model"
                />
                <div class="text-left w-full">일마다</div>
                <div :class="slotClass"></div>
            </div>
            <ButtonAtom class="rounded-s-none" :class="buttonClass" @buttonClick="plusNumber"
                >+</ButtonAtom
            >
        </div>
    </div>
</template>

<style scoped></style>
