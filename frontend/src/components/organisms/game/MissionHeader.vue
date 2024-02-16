<script setup lang="ts">
import TextAtom from '@/components/atoms/TextAtom.vue'
defineProps(['customClass'])
</script>

<template>
    <div class="flex flex-col justify-center md:border-b border-A805Black" :class="customClass">
        <div class="flex flex-wrap max-md:flex-col gap-[20px]">
            <TextAtom custom-class="md:text-2 max-md:text-2 max-md:text-center max-md:font-bold">
                진행중인 미션
            </TextAtom>
            <TextAtom custom-class="md:text-2 max-md:text-3 max-md:text-center "
                >마니띠에게 삼행시 지어주기</TextAtom
            >
        </div>
    </div>
</template>

<style scoped></style>
