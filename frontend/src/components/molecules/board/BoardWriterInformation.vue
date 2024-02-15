<script setup lang="ts">
import AvatarAtom from '@/components/atoms/AvatarAtom.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import { convertStringToRegistrationDateTime } from '@/utils/date'
import manitoAvatar from '@/assets/images/manito-avatar.png'
defineProps([
    'writer',
    'writerEmail',
    'writerProfileUrl',
    'registerAt',
    'hit',
    'likedCount',
    'replyCount',
    'isCertificate'
])
</script>

<template>
    <div class="flex items-center">
        <AvatarAtom
            :image-url="isCertificate ? manitoAvatar : writerProfileUrl"
            custom-class="profile w-[60px] h-[60px] me-[10px]"
        />
        <div class="flex flex-col">
            <TextAtom
                ><span class="font-bold me-1">{{ writer }}</span>
                <span v-if="!isCertificate">({{ writerEmail }})</span>
            </TextAtom>
            <TextAtom class="text-[14px] text-A805Khaki">{{
                convertStringToRegistrationDateTime(registerAt)
            }}</TextAtom>
            <TextAtom class="text-[12px] text-A805Khaki flex gap-[15px]">
                <span>조회 : {{ hit }}</span>
                <span>좋아요 : {{ likedCount }}</span>
                <span>댓글 : {{ replyCount }}</span>
            </TextAtom>
        </div>
    </div>
</template>

<style scoped></style>
