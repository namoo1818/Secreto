<script setup lang="ts">
import { CloseOutlined } from '@ant-design/icons-vue'
const { customClass, customId, seen } = defineProps(['customClass', 'customId', 'seen'])
const emit = defineEmits(['modalClose'])

const handleClose = () => {
    emit('modalClose')
}
</script>

<template>
    <div
        v-show="seen"
        class="w-full h-full fixed z-10 top-0 left-0 flex justify-center items-center bg-A805Black bg-opacity-50"
    >
        <div :class="customClass" class="shadow-2xl">
            <div class="flex w-full justify-end">
                <CloseOutlined
                    @click="handleClose()"
                    class="cursor-pointer"
                    style="font-size: 32px; color: #e0aed0"
                />
            </div>
            <div class="flex w-full flex-col items-center">
                <slot></slot>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
