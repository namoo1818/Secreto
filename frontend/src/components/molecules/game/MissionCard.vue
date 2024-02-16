<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import type { UserMission } from '@/types/mission'
import { BadgeRibbon } from 'ant-design-vue'

defineProps({
    mission: { type: Object as () => UserMission, required: true }
})
const emit = defineEmits(['certificateButtonHandle', 'certificateSuccessButtonHandle'])
</script>

<template>
    <div>
        <BadgeRibbon
            :text="mission.missionType === 'REGULAR' ? '정기 미션' : '돌발 미션'"
            :color="mission.missionType === 'REGULAR' ? 'green' : 'blue'"
        >
            <div class="h-[180px] w-[360px] flex flex-col shadow-rb rounded-md p-4">
                <div class="flex flex-col w-full justify-center items-center h-full gap-2">
                    <div class="flex flex-col w-full justify-center items-center h-full mt-4">
                        <TextAtom class="flex items-center font-bold text-[22px] text-center"
                            >{{ mission.content }}
                        </TextAtom>
                        <TextAtom class="text-[14px] text-A805Khaki">{{
                            mission.missionReceivedAt
                        }}</TextAtom>
                    </div>
                    <ButtonAtom
                        v-if="!mission.missionCertifyYn"
                        class="button-style-3 button-claret w-full"
                        @button-click="() => emit('certificateButtonHandle')"
                        >인증하기</ButtonAtom
                    >
                    <ButtonAtom
                        v-if="mission.missionCertifyYn"
                        class="button-style-3 button-lightGrey w-full"
                        @button-click="() => emit('certificateSuccessButtonHandle')"
                        >인증완료</ButtonAtom
                    >
                </div>
            </div>
        </BadgeRibbon>
    </div>
</template>

<style scoped></style>
