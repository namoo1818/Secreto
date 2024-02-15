<script setup lang="ts">
import LogoAtom from '@/components/atoms/LogoAtom.vue'
import CharacterAtom from '@/components/atoms/CharacterAtom.vue'
</script>

<template>
    <div class="flex flex-col justify-center items-center w-[40%] relative">
        <CharacterAtom customClass="w-full flex justify-center" />
        <LogoAtom customClass="w-full flex justify-center" />
    </div>
</template>

<style scoped></style>
