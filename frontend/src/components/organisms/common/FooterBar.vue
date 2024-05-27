<script setup lang="ts">
import LogoAtom from '@/components/atoms/LogoAtom.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
</script>

<template>
    <div
        class="flex flex-col md:h-[140px] bg-A805White w-full max-md:h-[240px] md:justify-center items-center"
    >
        <div class="flex max-md:flex-col">
            <TextAtom class="text-3 max-md:text-center max-md:mt-[32px]"
                >ⓒ Copyright 2024&nbsp;
            </TextAtom>
            <TextAtom class="text-3 max-md:text-center">SSAFY A805&nbsp;</TextAtom>
            <TextAtom class="text-3 max-md:text-center"> All Rights Reserved</TextAtom>
        </div>
        <LogoAtom custom-class="w-[140px] h-[50px] max-md:mt-[32px]" />
    </div>
</template>

<style scoped></style>
