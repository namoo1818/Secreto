<script setup lang="ts">
defineProps(['customClass'])
</script>

<template>
    <div class="rounded-md px-[5px] py-[3px] flex justify-center items-center" :class="customClass">
        <slot></slot>
    </div>
</template>

<style scoped></style>
