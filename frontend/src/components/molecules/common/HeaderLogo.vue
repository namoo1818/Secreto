<script setup lang="ts">
import LogoAtom from '@/components/atoms/LogoAtom.vue'
import CharacterAtom from '@/components/atoms/CharacterAtom.vue'
</script>

<template>
    <div class="flex items-center md:w-[280px] md:h-[95px] max-md:w-[206px] max-md:h-[57px]">
        <CharacterAtom customClass="md:w-[90px] max-md:w-[66px]" />
        <LogoAtom customClass="md:w-[190px] max-md:w-[140px]" />
    </div>
</template>

<style scoped></style>
