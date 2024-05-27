<script setup lang="ts">
import AvatarAtom from '@/components/atoms/AvatarAtom.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'

defineProps({
    name: {
        type: String,
        required: true
    },
    imageUrl: {
        type: String as () => string | null,
        required: true
    },
    customClass: {
        type: String
    }
})
</script>

<template>
    <div class="flex items-center gap-[20px]" :class="customClass">
        <AvatarAtom customClass="profile-style-1" :imageUrl="imageUrl" />
        <TextAtom class="text-[1rem] text-A805Blue">{{ name }}</TextAtom>
    </div>
</template>

<style scoped></style>
