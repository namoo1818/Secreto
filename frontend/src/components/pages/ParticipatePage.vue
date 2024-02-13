<script setup lang="ts">
import MissionHeader from '@/components/organisms/game/MissionHeader.vue'
import GameHeader from '@/components/organisms/game/GameHeader.vue';
import PaticipateProfile from '@/components/molecules/game/PaticipateProfile.vue'
import ModalTemplate from '@/components/template/ModalTemplate.vue';
import InferenceModalContent from '@/components/organisms/modal/InferenceModalContent.vue';
import type { userType, RoomUserInfoType } from '@/types/room'
import type { UserMission } from '@/types/mission';
import { ref, onMounted, inject, type Ref } from 'vue'
import { useRoute } from 'vue-router'

defineProps({
    userMission: {
        type: Object as () => UserMission[],
        required: true
    }
})
const route = useRoute()
const userList = inject('userList') as Ref<userType[]>
const modalSeen = ref(false)

const modalToggle = () => {
    modalSeen.value = false
}

const predictInfo = ref() as Ref<userType>

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

const predictManito = (user: userType) => {
    modalSeen.value = true
    console.log(user)
    predictInfo.value = user
}
</script>

<template>
    <div class="flex flex-1 flex-col bg-A805White ">
        <ModalTemplate custom-id="modal" custom-class="modal-template-style-1" :seen="modalSeen" v-if="modalSeen"
            @modal-close="modalToggle">
            <InferenceModalContent :predict-info="predictInfo" @refresh-data="getRooms()" />
        </ModalTemplate>
        <GameHeader :user-mission="userMission" />
        <hr />
        <div class="flex flex-1 items-center justify-center">
            <div class="flex flex-wrap items-center justify-center gap-[50px] md:m-[80px] max-md:m-[60px]">
                <template v-for="user in userList" :key="user.userNo">
                    <PaticipateProfile v-if="user.roomUserNo === roomUserInfo.roomUserNo"
                        :nick-name="user.nickname + ' (나)'" :image-url="user.profileUrl" :title="user.email" :mine="true"
                        class="border-A805Black border-2 border-solid" />
                    <PaticipateProfile :nick-name="user.nickname + ' (마니띠)'" :image-url="user.profileUrl" :maniti="true"
                        :title="user.email" v-else-if="user.usersManito === roomUserInfo.roomUserNo"
                        class="border-A805LightBlue border-2 border-solid" />
                    <PaticipateProfile :nick-name="user.nickname" :image-url="user.profileUrl" :title="user.email"
                        @predict-manito="predictManito(user)" :predict-type="user.manitoPredictType" v-else />
                </template>
            </div>
        </div>
    </div>
</template>

<style scoped></style>