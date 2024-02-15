<script setup lang="ts">
//TODO: 마니또 예측 카드 컴포넌트 구현 필요
import TextAtom from '@/components/atoms/TextAtom.vue'
import type { HistoryArrayType } from '@/types/history'

const prop = defineProps({
    HistoryData: {
        type: Object as () => HistoryArrayType,
        required: true
    }
})
</script>

<template>
    <div>
        <div class="flex flex-col">
            <div class="flex w-full justify-between">
                <TextAtom>{{ HistoryData.date }}</TextAtom>
                <TextAtom>마니또 예측</TextAtom>
            </div>
            <TextAtom>{{ HistoryData.value.predictor[HistoryData.value.predictor.length - 1].predictorNickname }} -> {{
                HistoryData.value.predictor[HistoryData.value.predictor.length - 1].targetNickName }}</TextAtom>
        </div>
        <div class="w-full my-[10px] cursor-pointer">
            <TextAtom> {{ HistoryData.value.predictor[HistoryData.value.predictor.length - 1].predictReason }} 의 사유로
                {{ HistoryData.value.predictor[HistoryData.value.predictor.length - 1].targetNickName }}님을 마니또로 예측하셨습니다
            </TextAtom>
        </div>
    </div>
</template>

<style scoped></style>
