<script setup lang="ts">
import { predictOption } from '@/types/mission'
import type { Handler } from '@/types/common'
import defaultImage from '@/assets/images/default-avatar.png'
import TextAtom from '@/components/atoms/TextAtom.vue'
const { imageUrl, customClass } = defineProps({
    customClass: {
        type: String
    },
    imageUrl: {
        type: String as () => string | null | undefined
    },
    predictType: {
        type: String as () => "YES" | "NO" | "UNKNOWN"
    }
})

const emit = defineEmits(['imageClick'])
const handleClick: Handler = () => {
    emit('imageClick')
}
</script>

<template>
    <div :class="customClass" @click="handleClick">
        <img :src="imageUrl ? imageUrl : defaultImage" alt="Avatar" class="w-full h-full object-fill" />
        <TextAtom v-if="predictType" class="absolute inset-0 flex justify-center items-center width text-[13rem] opacity-50"
            :class="{
                'text-A805Claret': predictOption[predictType] === 'O',
                'text-A805Violet': predictOption[predictType] === 'X',
                'text-A805LightGrey': predictOption[predictType] === '?'
            }">
            {{ predictOption[predictType] }}</TextAtom>

    </div>
</template>

<style scoped></style>

