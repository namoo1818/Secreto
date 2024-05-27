<script setup lang="ts">
import TextAtom from '@/components/atoms/TextAtom.vue'

defineProps(['missions'])
</script>

<template>
    <div
        class="flex flex-col justify-center items-center w-full max-md:max-h-[80vh] max-h-[500px] scroll-container"
    >
        <TextAtom custom-class="text-[24pt] font-Iceland text-A805Black">MISSIONS</TextAtom>
        <ul
            class="bg-A805RealWhite m-2 text-center text-[1.0rem] md:text-[1.25rem] lg:text-[1.5rem] overflow-y-auto scroll-container"
        >
            <li v-for="mission in missions" :key="mission" class="p-3">
                {{ mission.content }}
            </li>
        </ul>
    </div>
</template>

<style scoped></style>
