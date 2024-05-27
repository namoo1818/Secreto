<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import { HeartOutlined, HeartFilled } from '@ant-design/icons-vue'
defineProps({
    likedCount: {
        type: Number
    },
    liked: {
        type: Boolean
    }
})
</script>

<template>
    <ButtonAtom class="like-button" custom-class="flex items-center gap-[6px]">
        <HeartFilled v-if="liked" class="heart-icon text-[24px] text-A805Red" />
        <HeartOutlined v-else class="heart-icon text-[24px]" />
        좋아요 <b>{{ likedCount }}</b></ButtonAtom
    >
</template>

<style scoped>
.like-button:hover .heart-icon {
    transform: scale(1.2);
}
.heart-icon {
    transition: all 0.3s;
}
</style>
