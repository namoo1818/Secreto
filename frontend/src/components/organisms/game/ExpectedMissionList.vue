<script setup lang="ts">
import CheckBox from '@/components/molecules/common/CheckBox.vue'
import InputBox from '@/components/molecules/common/InputBox.vue'
import { ref, onMounted } from 'vue'
import type { Handler } from '@/types/common'
import type { Mission, SuddenMissionResponse } from '@/types/mission'
import { PlusSquareOutlined } from '@ant-design/icons-vue'
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import { DatePicker, Calendar } from 'ant-design-vue'
import ExpectedMission from '@/components/molecules/game/ExpectedMission.vue'
import { getSuddenMission } from '@/api/mission'
import { useRoute } from 'vue-router'

defineProps(['unexpectedMissionList'])
const route = useRoute()
const roomNo = ref<number>(Number(route.params.roomNo))
// 데이터 바뀌면 v-model로 바꿀 예정
const missionList = ref<SuddenMissionResponse[]>([])

const getSuddenMissionHandler: Handler = () => {
    console.log('???', roomNo.value)
    getSuddenMission(
        roomNo.value,
        (res) => {
            missionList.value = res.data.result
            console.log('sudden missions', res.data)
        },
        () => {
            console.log('2')
        }
    )
}

onMounted(async () => {
    getSuddenMissionHandler()
})
</script>

<template>
    <div class="flex flex-1 flex-col gap-3 overflow-hidden">
        <h1 class="text-2xl p-2">예약 리스트</h1>
        <div
            name="list-container"
            class="flex flex-1 flex-col-reverse border-2 min-h-[200px] max-h-[400px] overflow-y-auto"
        >
            <ExpectedMission v-for="(event, index) in missionList" :key="index"
                >{{ event.content }}{{ event.missionSubmitAt }}</ExpectedMission
            >
        </div>
    </div>
</template>

<style scoped></style>
