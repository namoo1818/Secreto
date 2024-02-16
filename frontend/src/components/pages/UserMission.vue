<script setup lang="ts">
import { ref, type Ref, onMounted, inject } from 'vue'
import type { UserMission, Mission } from '@/types/mission'
import type { DataHandler, Handler } from '@/types/common'
import ModalTemplate from '@/components/template/ModalTemplate.vue'
import RoomMissionModalContent from '@/components/organisms/modal/RoomMissionModalContent.vue'
import { getRoomMission } from '@/api/mission'
import { type RoomUserInfoType } from '@/types/room'
import { useRoute } from 'vue-router'
import router from '@/router'
import GameHeader from '@/components/organisms/game/GameHeader.vue'
import MissionCard from '@/components/molecules/game/MissionCard.vue'
import LineAtom from '../atoms/LineAtom.vue'

const props = defineProps({
    userMission: {
        type: Object as () => UserMission[],
        required: true
    }
})
const emit = defineEmits(['refreshUserMission'])
const route = useRoute()
const roomUserInfo = inject<Ref<RoomUserInfoType>>(
    'roomUserInfo',
    ref({
        roomNo: Number(route.params.roomNo),
        roomUserNo: 0,
        roomName: '',
        roomNickname: '',
        profileUrl: ''
    })
)

const roomMissions = ref<Array<Mission>>([])

const roomMissionsModalOpen = ref<boolean>(false)
const goToMissionCertificationPage: DataHandler<UserMission> = (mission) => {
    console.log(mission)
    if (!mission.missionCertifyYn) {
        router.push({
            name: 'game-board-write',
            query: { boardCategory: 'CERTIFICATE' }
        })
    } else {
        router.push({
            name: 'game-board-list',
            query: { boardCategory: 'CERTIFICATE' }
        })
    }
}
const modalOpenHandler: Handler = () => {
    roomMissionsModalOpen.value = true
}
const modalCloseHandler: Handler = () => {
    roomMissionsModalOpen.value = false
}
const getRoomMissionHandler: Handler = () => {
    console.log('getRoomMission')
    getRoomMission(
        roomUserInfo.value.roomNo,
        ({ data }) => {
            console.log(':)', data)
            roomMissions.value = data.result
            roomMissions.value.reverse()
        },
        (error) => {
            console.log(':(', error)
        }
    )
}
onMounted(async () => {
    emit('refreshUserMission')
    getRoomMissionHandler()
})
</script>

<template>
    <div
        name="header"
        class="flex flex-1 h-full w-full flex-col justify-start items-center bg-A805RealWhite pt-5 pb-10"
    >
        <GameHeader
            class="mb-5 md:min-w-[568px] max-w-[1400px] max-md:min-w-0 md:px-4"
            :user-mission="props.userMission"
            @reroll="emit('refreshUserMission')"
            @header-modal-open="modalOpenHandler"
            :show-all-mission="true"
        ></GameHeader>
        <LineAtom class="border-A805Grey mb-8" />
        <div
            class="flex flex-col w-full md:min-w-[568px] max-w-[1400px] max-md:min-w-0 items-center relative px-4"
        >
            <div class="flex flex-wrap gap-10 items-center justify-center">
                <MissionCard
                    v-for="mission in userMission"
                    :key="mission.userMissionNo"
                    :mission="mission"
                    @certificate-button-handle="() => goToMissionCertificationPage(mission)"
                    @certificate-success-button-handle="() => goToMissionCertificationPage(mission)"
                />
            </div>
        </div>
        <ModalTemplate
            custom-id="modal"
            custom-class="modal-template-style-1 max-w-[800px]"
            :seen="roomMissionsModalOpen"
            @modal-close="modalCloseHandler"
            ><RoomMissionModalContent :missions="roomMissions"
        /></ModalTemplate>
    </div>
</template>
