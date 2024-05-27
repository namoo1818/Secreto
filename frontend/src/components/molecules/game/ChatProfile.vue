<script setup lang="ts">
import AvatarAtom from '@/components/atoms/AvatarAtom.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'

defineProps({
    imageUrl: {
        type: String,
        default: ''
    },
    name: {
        type: String,
        default: ''
    }
})
</script>
<template>
    <div class="flex gap-[10px] items-center">
        <AvatarAtom :image-url="imageUrl" custom-class="chat-profile" />
        <TextAtom>{{ name }}</TextAtom>
    </div>
</template>

<style scoped></style>
