<script setup lang="ts">
import CheckBox from '@/components/molecules/common/CheckBox.vue'
import InputBox from '@/components/molecules/common/InputBox.vue'
import { ref, type ModelRef } from 'vue'
import type { Handler } from '@/types/common'
import type { Mission } from '@/types/mission'
import { PlusSquareOutlined } from '@ant-design/icons-vue'

const myMisssionName = ref('')
const myMissionChecked = ref(true)
const allMissionChecked = ref(false)
const missionInputVisibility = ref(false)
const vFocus = {
    mounted: (el: HTMLElement) => {
        el.focus()
        el.scrollIntoView()
    }
}
// 데이터 바뀌면 v-model로 바꿀 예정

const test = ref(null)

const missionList: ModelRef<Mission[]> = defineModel({ required: true })
const addInputBox: Handler = () => {
    if (!missionInputVisibility.value) {
        missionInputVisibility.value = true
    }
}
const addMission: Handler = () => {
    missionList.value.push({
        content: myMisssionName.value,
        checked: myMissionChecked.value
    })
    myMisssionName.value = ''
}
const allChangeHandler: Handler = () => {
    missionList.value.forEach((mission: Mission) => {
        mission.checked = allMissionChecked.value
    })
}
</script>

<template>
    <div
        class="flex flex-col relative bg-A805RealWhite border-2 border-A805DarkGrey rounded-md overflow-hidden h-full max-h-[440px]"
    >
        <!-- 전체 선택 -->
        <div class="bg-A805RealWhite border-b-2 border-A805DarkGrey">
            <CheckBox
                v-model="allMissionChecked"
                custom-class="checkbox-molecule-style-1"
                @change="allChangeHandler"
                >전체 선택</CheckBox
            >
            <!-- <hr class="border-A805DarkGrey" /> -->
        </div>
        <!-- 목록 -->
        <div class="flex flex-col overflow-y-auto scroll-container">
            <CheckBox
                v-for="(mission, index) in missionList"
                :key="index"
                custom-class="checkbox-molecule-style-1"
                :custom-id="'mission' + String(index)"
                v-model="mission.checked"
                >{{ mission.content }}</CheckBox
            >
            <!-- 추가 미션 -->
            <CheckBox
                v-if="missionInputVisibility"
                custom-class="checkbox-molecule-style-1"
                custom-id="new-mission-checkbox"
                v-model="myMissionChecked"
            >
                <InputBox
                    v-model="myMisssionName"
                    custom-class="input-box-style-1 px-0 text-[14pt] text-A805DarkGrey"
                    custom-id="new-mission"
                    @input-enter="addMission"
                    ref="test"
                    v-focus
                />
            </CheckBox>
        </div>
        <!-- 추가하기 버튼 -->
        <div class="bg-A805RealWhite bottom-0">
            <div
                class="flex items-center"
                custom-class="checkbox-molecule-style-1"
                @click="addInputBox()"
            >
                <PlusSquareOutlined class="text-A805DarkGrey my-[5px] mx-[8px] text-[24pt]" />
                추가하기
            </div>
        </div>
    </div>
</template>

<style scoped></style>
