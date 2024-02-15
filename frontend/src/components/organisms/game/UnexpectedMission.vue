<script setup lang="ts">
import CheckBox from '@/components/molecules/common/CheckBox.vue'
import InputBox from '@/components/molecules/common/InputBox.vue'
import { ref } from 'vue'
import type { Handler } from '@/types/common'
import type { Mission } from '@/types/mission'
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import { DatePicker } from 'ant-design-vue'
import dayjs from 'dayjs'
import { type Dayjs } from 'dayjs'

const emit = defineEmits(['addUnexpectedMission'])
const content = defineModel<string>('content', { required: true })
const isReserved = defineModel('reserved', { required: true })
const reservationTime = defineModel<Dayjs>('time', { required: true })
</script>

<template>
    <div class="flex flex-col gap-3 justify-between overflow-hidden">
        <InputBox
            v-model="content"
            label="돌발 미션"
            label-class="text-2xl"
            input-class="input-box-style-3 rounded-[100px] text-center line-darkgrey bg-white w-full min-h-[3rem]"
            custom-class="flex gap-2"
            @input-enter="emit('addUnexpectedMission')"
        ></InputBox>
        <!-- <div name="mission-options" class="flex max-md:flex-col">
            <CheckBox v-model="isReserved" custom-class="flex flex-1 gap-2 px-2">예약하기</CheckBox>
            <DatePicker
                class="flex flex-1"
                :disabled="true"
                v-model:value="reservationTime"
            ></DatePicker>
        </div> -->
        <ButtonAtom
            custom-class="flex flex-1 button-blue text-A805RealWhite items-center justify-center min-h-[3rem] max-h-[4rem] text-2xl"
            @button-click="emit('addUnexpectedMission')"
            >미션 추가</ButtonAtom
        >
    </div>
</template>

<style scoped></style>
