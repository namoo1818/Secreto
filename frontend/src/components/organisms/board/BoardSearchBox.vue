<script setup lang="ts">
import { ref, type Ref } from 'vue'
import type { Handler } from '@/types/common'
import SelectBox from '@/components/molecules/common/SelectBox.vue'
import InputBox from '@/components/molecules/common/InputBox.vue'
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import { BoardCategory } from '@/types/board'

const emit = defineEmits(['searchHandle'])
const props = defineProps(['defaultCondition', 'defaultKeyword', 'boardCategory'])
const searchKeyword: Ref<string> = ref(props.defaultKeyword)
const condition: Ref<string> = ref(props.defaultCondition)
const searchButtonHandler: Handler = () => {
    emit('searchHandle', condition.value, searchKeyword.value)
}

const conditionOptions = [
    { label: '글 제목', value: 'title' },
    { label: '글 내용', value: 'content' },
    { label: '작성자명', value: 'writer' }
]
const conditionOptionsCertificate = [
    { label: '글 제목', value: 'title' },
    { label: '글 내용', value: 'content' },
    { label: '마니띠명', value: 'writer' }
]
</script>

<template>
    <div class="flex max-md:flex-col md:h-[35px] max-md:w-full">
        <SelectBox
            custom-class="w-[120px] max-md:w-full h-[35px] me-2 rounded-none !border-A805LightGrey max-md:mb-2"
            button-class="!bg-A805Claret !text-A805Khaki text-[20px]"
            v-model="condition"
            :options="
                boardCategory !== BoardCategory.CERTIFICATE
                    ? conditionOptions
                    : conditionOptionsCertificate
            "
        />
        <div class="flex">
            <InputBox
                custom-class="line-lightGrey text-A805Black w-[390px] max-md:w-full md:h-full px-3 bg-A805RealWhite max-md:h-8"
                v-model="searchKeyword"
                place-holder="검색어를 입력해주세요"
            />
            <ButtonAtom
                custom-class="button-claret text-A805Khaki w-[80px] text-[18px] md:h-full max-md:h-8"
                @click="searchButtonHandler"
                >검색</ButtonAtom
            >
        </div>
    </div>
</template>

<style scoped></style>
