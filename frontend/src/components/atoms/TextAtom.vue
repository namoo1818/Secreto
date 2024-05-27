<script setup lang="ts">
defineProps(['customClass'])
</script>

<template>
    <text :class="customClass">
        <slot></slot>
    </text>
</template>

<style scoped></style>
