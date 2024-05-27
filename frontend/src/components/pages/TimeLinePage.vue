<script setup lang="ts">
import { ref, watch, onMounted, inject, type Ref } from 'vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import HistoryCard from '@/components/organisms/game/HistoryCard.vue'
import PredictCard from '@/components/organisms/game/PredictCard.vue'
import type { HistoryArrayType } from '@/types/history'
import { getMyHistoryList, getManitoHisotryList } from '@/api/history'
import type { RoomUserInfoType } from '@/types/room'

const roomUserInfo = inject('roomUserInfo') as Ref<RoomUserInfoType>
const manitiData: any = ref([])
const manitoData: any = ref([])

const myActivity = ref(true)

const setMyActivity = (value: boolean) => {
    myActivity.value = value
}

const getMyActivity = () => {
    getMyHistoryList(roomUserInfo.value.roomNo, ({ data }) => {
        manitiData.value = data.result.result.maniti;
        manitoData.value = data.result.result.manito
        ArrayHistoryData()
    }, (error) => console.error(error))
}

// 나의 마니또의 활동 기록 가져오는 API
const getMyManito = () => {
    getManitoHisotryList(roomUserInfo.value.roomNo, ({ data }) => {
        manitiData.value = data.result.result.maniti;
        manitoData.value = data.result.result.manito
        ArrayHistoryData()

    }, (error) => console.error(error))
}

// 마니띠, 마니또의 데이터를 합쳐서 화면에 뿌려주기 위한 데이터

const combinedArray = ref<HistoryArrayType[]>()

const ArrayHistoryData = () => {
    combinedArray.value = [
        ...Object.keys(manitiData.value).map(date => ({ date, source: 'maniti', value: manitiData.value[date] })),
        ...Object.keys(manitoData.value).map(date => ({ date, source: 'manito', value: manitoData.value[date] }))
    ];

    combinedArray.value.sort((a, b) => {
        return new Date(a.date).getTime() - new Date(b.date).getTime();
    });

};


const getTimeLine = () => {
    if (myActivity.value) {
        getMyActivity()
    } else {
        getMyManito()
    }
}

watch(myActivity, getTimeLine)

onMounted(() => {
    getTimeLine()
})
</script>

<template>
    <div class="flex flex-1  bg-A805Neutral">
        <div class="flex flex-1 flex-col">
            <div class="flex flex-col md:ml-[30px] max-md:mx-auto gap-[10px] mt-[30px]">
                <TextAtom custom-class="text-2">마니또 타임라인</TextAtom>
                <div class="flex gap-[30px]">
                    <TextAtom @click="setMyActivity(true)" :custom-class="{
                        'text-4': true,
                        'cursor-pointer': true,
                        'text-A805Black': myActivity,
                        'text-A805Grey': !myActivity
                    }">나의 기록</TextAtom>
                    <TextAtom @click="setMyActivity(false)" :custom-class="{
                        'text-4': true,
                        'cursor-pointer': true,
                        'text-A805Black': !myActivity,
                        'text-A805Grey': myActivity
                    }">내 마니또 기록</TextAtom>
                </div>
            </div>
            <div class="flex flex-col mt-[10px]">
                <TextAtom customClass="text-center text-2">TIMELINE</TextAtom>
                <!-- <TextAtom customClass="text-center">2024.01.12~2024.01.30일간 진행했던</TextAtom>
                <TextAtom customClass="text-center"> [방 제목] 에서의 마니또 기록이에요</TextAtom> -->
            </div>

            <div class="timeline relative mx-auto w-full">
                <template v-for="history in combinedArray">
                    <div class="container left" v-if="history.value.predictor">
                        <div class="predict">
                            <PredictCard :HistoryData="history" />
                        </div>
                    </div>
                    <div class="container right" v-else-if="history.source === 'manito'">
                        <div class="content">
                            <HistoryCard :HistoryData="history" />
                        </div>
                    </div>
                    <div class="container left" v-else-if="history.source === 'maniti'">
                        <div class="content">
                            <HistoryCard :HistoryData="history" />
                        </div>
                    </div>
                </template>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
