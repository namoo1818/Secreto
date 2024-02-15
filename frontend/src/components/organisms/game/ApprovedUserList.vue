<script setup lang="ts">
import CheckBox from '@/components/molecules/common/CheckBox.vue'
import InputBox from '@/components/molecules/common/InputBox.vue'
import { Dropdown, Menu, MenuItem } from 'ant-design-vue'
import { ref, type Ref, type ModelRef, inject, watch, onMounted } from 'vue'
import type { Handler } from '@/types/common'
import type { ProfileInfoType } from '@/types/user'
import ProfileInfo from '@/components/molecules/game/ProfileInfo.vue'
import type { RoomInfoType, userType } from '@/types/room'
import { rollbackRoomUsers } from '@/api/room'
import { assignHost } from '@/api/room'
import router from '@/router'

defineProps({
    userList: {
        type: Array<ProfileInfoType>
    }
})
const emit = defineEmits(['goToParticipatePage'])

const roomInfo = inject('roomInfo') as Ref<RoomInfoType>

const hostNo = ref<number>(-1)
const ri = ref<RoomInfoType>({
    entryCode: '',
    commonYn: true,
    hostParticipateYn: '',
    hostRoomUserNo: 0,
    missionSubmitTime: '',
    missionStartAt: '',
    roomEndAt: '',
    roomName: '',
    roomNo: 0,
    roomStartAt: '',
    roomStartYn: false,
    roomStatus: '',
    userInfo: {
        nickname: '',
        profileUrl: '',
        roomUserNo: 0
    }
})
watch(
    () => roomInfo,
    (newval) => {
        console.log('watch?', newval)
        hostNo.value = newval?.value?.hostRoomUserNo
        ri.value = newval?.value
    },
    { deep: true }
)

const usersApproved: ModelRef<userType[]> = defineModel({ required: true })
const assignHandler = (newHostNo: number) => {
    console.log('assignHandler', ri.value.roomNo, newHostNo)
    assignHost(
        { roomNo: ri.value.roomNo, newHost: newHostNo },
        ({ data }) => {
            console.log('위임 성공', data)
            emit('goToParticipatePage')
        },
        (error) => {
            console.log('위임 실패', error)
        }
    )
}

onMounted(() => {
    hostNo.value = roomInfo?.value?.hostRoomUserNo
    ri.value = roomInfo.value
})
</script>

<template>
    <!-- <button @click="testHandler">RollBack</button> -->
    <!-- <div>{{ hostNo }}! {{ ri }}</div> -->
    <div class="flex flex-col bg-A805RealWhite overflow-hidden">
        <div>
            <p class="text-3 px-6 py-3">참여 유저</p>
        </div>
        <div class="overflow-y-auto">
            <div v-for="user in usersApproved" :key="user.roomUserNo" class="px-4 py-2">
                <Dropdown class="cursor-pointer">
                    <ProfileInfo
                        :name="user.nickname"
                        :image-url="user.profileUrl"
                        :title="user.email"
                    ></ProfileInfo>

                    <template #overlay>
                        <Menu v-show="user.roomUserNo !== hostNo && ri.roomStartYn">
                            <p class="text-center text-[1.2rem] font-bold my-2 px-3">
                                {{ user.nickname }}
                            </p>
                            <MenuItem @click="() => assignHandler(user.roomUserNo)">
                                권한 위임하기
                            </MenuItem>
                        </Menu>
                    </template>
                </Dropdown>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
