<script setup lang="ts">
import AvatarAtom from '@/components/atoms/AvatarAtom.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'

defineProps({
    name: {
        type: String,
        default: '익명'
    },
    imageUrl: {
        type: String as () => string | null
    },
    customClass: {
        type: String
    }
})
</script>

<template>
    <div class="flex items-center gap-[20px]" :class="customClass">
        <AvatarAtom customClass="header-profile" :imageUrl="imageUrl" />
        <TextAtom class="text-4"
            ><span class="text-A805Blue">{{ name }}</span
            >님<br />만나서 반갑습니다</TextAtom
        >
    </div>
</template>

<style scoped></style>
