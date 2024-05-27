<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import { BoardCategory } from '@/types/board'
import type { Handler } from '@/types/common'
import { EditOutlined, CaretUpOutlined } from '@ant-design/icons-vue'
import { computed } from 'vue'
import { useRoute } from 'vue-router'
defineProps(['isPostWriter', 'isHostUser'])

const route = useRoute()
const boardCategory = computed(() => {
    return String(route.query.boardCategory)
})
const emit = defineEmits([
    'writeButtonHandle',
    'modifyButtonHandle',
    'deleteButtonHandle',
    'topButtonHandle',
    'listButtonHandle'
])
const writeButtonClick: Handler = () => {
    emit('writeButtonHandle')
}
</script>

<template>
    <div class="w-full md:h-[40px] flex justify-between items-center max-md:flex-col">
        <div class="flex gap-[10px] max-md:flex-col max-md:w-full">
            <ButtonAtom
                custom-class="button-style-4 button-claret text-[18px] w-[95px] font-bold flex justify-center items-center gap-[5px] max-md:w-full"
                @button-click="writeButtonClick"
                v-if="boardCategory !== BoardCategory.NOTICE || isHostUser"
                ><EditOutlined /><span>글쓰기</span></ButtonAtom
            >
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
        <div class="flex gap-[10px] max-md:flex-col-reverse max-md:w-full max-md:mt-3">
            <!-- <ButtonAtom
                custom-class="button-style-4 button-lightGrey text-[18px] w-[80px] font-bold flex justify-center items-center  max-md:w-full"
                @button-click="() => emit('topButtonHandle')"
                ><CaretUpOutlined /><span>TOP</span></ButtonAtom
            > -->
            <ButtonAtom
                custom-class="button-style-4 button-lightGrey text-[18px] w-[70px] font-bold  max-md:w-full"
                @button-click="() => emit('listButtonHandle')"
                >목록</ButtonAtom
            >
        </div>
    </div>
</template>

<style scoped></style>
