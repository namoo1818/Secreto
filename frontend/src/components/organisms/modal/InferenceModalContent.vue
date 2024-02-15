<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import InputBox from '@/components/molecules/common/InputBox.vue'
import type InputBoxVue from '@/components/molecules/common/InputBox.vue'
import PaticipateProfile from '@/components/molecules/game/PaticipateProfile.vue'
import type { DataHandler, Handler } from '@/types/common'
import type { userType } from '@/types/room'
import { ref, onMounted, type Ref } from 'vue'
import { memoUser, getUserMemo } from '@/api/mission'
// 유저 정보 props
const { predictInfo } = defineProps({
    predictInfo: {
        type: Object as () => userType,
        required: true
    }
})

const emit = defineEmits(['refresh-data'])


const memoType = Object.freeze({
    "YES": "마니또로 예측",
    "NO": "마니또가 아닌것 같음",
    "UNKNOWN": "잘 모르겠음"
});

const memo = ref<string>('')

const submitHandler: Handler = () => {
    if (confirm(`${predictInfo?.nickname}에 대하여 메모를 작성하시겠습니까? 예측타입: ${memoType[inference.value]}`)) {
        memoUser({
            roomNo: predictInfo?.roomNo,
            memo: memo.value,
            manitoPredictType: inference.value,
            memoTo: predictInfo?.roomUserNo
        }, ({ data }) => {
            alert(data.message)
            emit('refresh-data')
        }, (error) => {
            console.error('error', error)
        })
    }
    console.log(memo.value)
}

const getMemo = () => {
    if (predictInfo?.roomNo !== undefined && predictInfo?.roomUserNo !== undefined) {
        getUserMemo(predictInfo?.roomNo, predictInfo?.roomUserNo, ({ data }) => {
            memo.value = data.result.memo
            inference.value = data.result.manitoPredictType
        }, (error) => {
            console.error('error', error)
        })
    }
}

const inferenceChanged: DataHandler<'YES' | 'NO' | 'UNKNOWN'> = (guess) => {
    inference.value = guess
}

onMounted(() => {
    getMemo()
})
// 추리 내용 (API에 맞춰 변경 예정)
const inference = ref<'YES' | 'NO' | 'UNKNOWN'>('YES')
</script>

<template>
    <div class="flex flex-col justify-center items-center w-full gap-2 overflow-y-auto z-40">
        <div name="user=profile-container" class="flex content-center gap-3 max-md:flex-col items-center">
            <PaticipateProfile :image-url="predictInfo?.profileUrl" custom-class="max-md:w-"></PaticipateProfile>
            <div name="inference-container" class="flex flex-col justify-between md:h-[200px]">
                <div class="md:w-[380px]">
                    <span class="text-A805Blue text-[16pt]"> {{ predictInfo?.nickname }}</span> 님을
                    마니또로 예측하시겠습니까?
                </div>
                <div name="buttons-container" class="flex justify-between">
                    <ButtonAtom class="button-style-4 w-[100px] button-blue" :class="{ 'bg-A805Blue': inference === 'YES' }"
                        @button-click="inferenceChanged('YES')">O</ButtonAtom>
                    <ButtonAtom class="button-style-4 w-[100px] button-claret"
                        :class="{ 'bg-A805Violet': inference === 'NO' }" @button-click="inferenceChanged('NO')">X
                    </ButtonAtom>
                    <ButtonAtom class="button-style-4 w-[100px] button-claret"
                        :class="{ 'bg-A805Violet': inference === 'UNKNOWN' }" @button-click="inferenceChanged('UNKNOWN')">?
                    </ButtonAtom>
                </div>
            </div>
        </div>
        <div name="memo-container" class="w-full">
            <textarea v-model="memo" class="w-full md:h-[200px] rounded-[10px] p-5 focus:outline-none"
                placeholder="마니또로 예측한 이유를 작성해주세요."></textarea>
        </div>
        <div name="submit-button-container" class="flex justify-end w-full">
            <ButtonAtom class="button-style-4 button-claret" @button-click="submitHandler">전송</ButtonAtom>
        </div>
    </div>
</template>

<style scoped></style>
