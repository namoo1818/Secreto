<script setup lang="ts">
import { ref } from 'vue'
import { CloseSquareOutlined } from '@ant-design/icons-vue'
</script>

<template>
    <div class="flex justify-between items-center p-4 text-[16pt] overflow-hidden">
        <li>
            <slot></slot>
        </li>
        <CloseSquareOutlined />
    </div>
</template>

<style scoped></style>
