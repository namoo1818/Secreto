<script setup lang="ts">
import { computed, inject, ref, type Ref } from 'vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import { ReloadOutlined } from '@ant-design/icons-vue'
import type { UserMission } from '@/types/mission'
import { rerollMission } from '@/api/mission'
const props = defineProps({
    userMission: {
        type: Object as () => UserMission[],
        required: true
    },
    customClass: {
        type: String,
        default: ''
    },
    showAllMission: {
        type: Boolean
    }
})
const roomNo: Ref<number> = inject('roomNo', ref(0))
const emit = defineEmits(['reroll', 'headerModalOpen'])
const userMissionLength = computed<number>(() => {
    return props.userMission.length
})
const lastMissionNo = computed<number>(() => {
    if (userMissionLength.value > 0) {
        return props.userMission[0].userMissionNo
    } else {
        return 0
    }
})

const rerollHandler = () => {
    console.log('reroll')
    rerollMission(
        {
            roomNo: roomNo.value,
            userMissionNo: lastMissionNo.value
        },
        ({ data }) => {
            console.log(':)', data)
            emit('reroll')
        },
        (error) => {
            console.log(':(', error.response.data.message)
            alert(error.response.data.message)
        }
    )
}
</script>

<template>
    <div
        name="mission-header"
        class="flex flex-col justify-between items-center py-2 w-full overflow-x-auto"
        :class="customClass"
    >
        <div class="flex w-full justify-end" v-if="showAllMission">
            <ButtonAtom
                class="button-cream text-A805 w-[210px] button-style-2 max-md:w-full max-md:rounded-none max-md:h-16 mb-2 max-md:mb-6"
                @button-click="() => emit('headerModalOpen')"
                >전체 미션 보기</ButtonAtom
            >
        </div>
        <div class="flex flex-col w-full h-full justify-center items-center">
            <TextAtom class="text-[24px] font-bold">최근 받은 미션</TextAtom>
            <div name="now-mission" class="text-[24px] flex max-md:flex-col max-md:text-[22px]">
                <TextAtom class="text-center">
                    {{ userMissionLength > 0 ? userMission[0].content : '없음' }}
                </TextAtom>
                <ButtonAtom
                    v-if="userMissionLength > 0 && userMission[0].missionType === 'REGULAR'"
                    class="md:ms-5 relative inline-flex text-[16pt] max-md:mt-2 justify-center items-center"
                    @button-click="rerollHandler"
                >
                    <ReloadOutlined class="text-[32pt] absolute"></ReloadOutlined>
                    <p>{{ userMission[0].missionRerollCount }}</p>
                </ButtonAtom>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
