<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
defineProps(['isPostWriter', 'isHostUser'])
const emit = defineEmits(['modifyButtonHandle', 'deleteButtonHandle', 'listButtonHandle'])
</script>

<template>
    <div class="w-full h-[40px] flex justify-between items-center">
        <div class="flex gap-[10px]">
            <ButtonAtom
                custom-class="button-style-4 button-lightGrey text-[18px] w-[70px] font-bold max-md:w-full"
                @button-click="() => emit('modifyButtonHandle')"
                v-if="isPostWriter"
                >수정</ButtonAtom
            >
            <ButtonAtom
                custom-class="button-style-4 button-lightGrey text-[18px] w-[70px] font-bold  max-md:w-full"
                @button-click="() => emit('deleteButtonHandle')"
                v-if="isPostWriter"
                >삭제</ButtonAtom
            >
        </div>
        <ButtonAtom
            custom-class="button-style-4 button-lightGrey text-[18px] w-[70px] font-bold  max-md:w-full"
            @button-click="() => emit('listButtonHandle')"
            >목록</ButtonAtom
        >
    </div>
</template>

<style scoped></style>
