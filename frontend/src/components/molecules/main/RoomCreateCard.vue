<script setup lang="ts">
import TextAtom from '@/components/atoms/TextAtom.vue'
</script>

<template>
    <div
        class="bg-A805White w-[45%] aspect-[5/3] rounded-lg shadow-rb cursor-pointer hover:scale-[102%] transition ease-in-out flex justify-center items-center border-solid border-A805DarkGrey border-4"
    >
        <div
            class="h-[50px] w-[50px] border-solid border-A805DarkGrey border-4 flex justify-center items-center rounded-full"
        >
            <TextAtom custom-class="text-[60px] text-A805DarkGrey ">＋</TextAtom>
        </div>
    </div>
</template>

<style scoped></style>
