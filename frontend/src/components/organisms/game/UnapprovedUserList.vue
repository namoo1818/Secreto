<script setup lang="ts">
import CheckBox from '@/components/molecules/common/CheckBox.vue'
import InputBox from '@/components/molecules/common/InputBox.vue'
import { computed, ref, type ModelRef, type Ref, inject, watchEffect } from 'vue'
import type { DataHandler, Handler } from '@/types/common'
import type { ProfileInfoType, ProfileInfoCheckBoxType } from '@/types/user'
import ProfileInfo from '@/components/molecules/game/ProfileInfo.vue'
import { CheckOutlined, MinusCircleOutlined } from '@ant-design/icons-vue'
import type { userType } from '@/types/room'
import { acceptRoomUsers, denyRoomUsers, rematch, interceptUser } from '@/api/room'
import ModalTemplate from '@/components/template/ModalTemplate.vue'
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import IntrudingModalContent from '../modal/IntrudingModalContent.vue'
import type { RoomInfoType } from '@/types/room'
const emit = defineEmits(['usersApproved', 'usersDenied'])
const targetUsers = computed<number[]>(() => {
    return usersUnapproved.value.filter((user) => user.checked).map((user) => user.roomUserNo)
})
const usersUnapproved: ModelRef<userType[]> = defineModel({ required: true })

const allChangeHandler: Handler = () => {
    usersUnapproved.value.forEach((user: userType) => {
        user.checked = allUserChecked.value
    })
}

const allUserChecked: Ref<boolean> = ref(true)
const roomInfo: Ref<RoomInfoType> = inject('roomInfo') as Ref<RoomInfoType>

const acceptUserHandler = (no?: number) => {
    const targets = no ? [no] : targetUsers.value
    if (roomInfo.value.roomStartYn && roomInfo.value.roomStatus === 'PARTICIPANT') {
        storedTargets.value = targets
        modalSeen.value = true
        console.log(1)
        return
    }
    acceptRoomUsers(
        {
            roomUserNos: targets
        },
        ({ data }) => {
            console.log('acceptRoomUsers success', data)
            emit('usersApproved')
        },
        (error) => {
            console.log('error', error)
        }
    )
}
const rematchHandler = () => {
    console.log('rematch!')

    acceptRoomUsers(
        {
            roomUserNos: storedTargets.value
        },
        ({ data }) => {
            console.log('acceptRoomUsers success', data)
            rematch(
                { roomNo: roomInfo.value.roomNo },
                ({ data }) => {
                    console.log('rematch :)', data)
                },
                (error) => {
                    console.log('rematch :(', error.response.data.message)
                }
            )
            emit('usersApproved')
        },
        (error) => {
            console.log('error', error)
        }
    )
    modalSeen.value = false
}
const interceptHandler = () => {
    console.log('intercept!', { roomNo: roomInfo.value.roomNo, roomUserNos: storedTargets.value })

    interceptUser(
        { roomNo: roomInfo.value.roomNo, roomUserNos: storedTargets.value },
        (data) => {
            console.log('intercept :)', data)

            acceptRoomUsers(
                {
                    roomUserNos: storedTargets.value
                },
                ({ data }) => {
                    console.log('acceptRoomUsers success', data)
                    emit('usersApproved')
                },
                (error) => {
                    console.log('error', error)
                }
            )
        },
        (error) => {
            console.log('intercept :(', error.response.data.message)
        }
    )

    modalSeen.value = false
}

const denyUserHandler = (no?: number) => {
    const targets = no ? [no] : targetUsers.value
    denyRoomUsers(
        {
            roomUserNos: targets
        },
        ({ data }) => {
            console.log('denyRoomUser success', data)
            emit('usersDenied')
        },
        (error) => {
            console.log('error', error)
        }
    )
}

const modalSeen = ref<boolean>(false)
const storedTargets = ref<number[]>([])
</script>

<template>
    <div
        class="flex flex-col w-full bg-A805RealWhite overflow-hidden border-b-2 border-A805DarkGrey"
    >
        <div>
            <p class="text-3 px-6 py-2">대기 유저</p>
        </div>
        <div
            class="flex w-full items-center justify-between px-4 py-2 gap-3 bg-A805RealWhite border-b-2 border-A805DarkGrey"
        >
            <CheckBox
                v-model="allUserChecked"
                custom-class="text-[12pt] checkbox-molecule-style-1 gap-3"
                @change="allChangeHandler"
                >전체 선택</CheckBox
            >
            <div class="flex justify-between gap-3 text-sm">
                <p class="text-A805Blue cursor-pointer" @click="acceptUserHandler()">선택 승인</p>
                <p class="cursor-pointer" @click="denyUserHandler()">선택 거절</p>
            </div>
        </div>
        <div class="flex flex-col h-[220px] overflow-y-auto">
            <div v-for="user in usersUnapproved" :key="user.roomUserNo" class="px-4 py-2">
                <div class="flex items-center justify-between">
                    <CheckBox
                        :custom-id="user.roomUserNo"
                        v-model="user.checked"
                        custom-class="checkbox-molecule-style-1 gap-3"
                    >
                        <ProfileInfo
                            custom-class="w-full"
                            :name="user.nickname"
                            :image-url="user.profileUrl"
                            :title="user.email"
                        ></ProfileInfo>
                    </CheckBox>
                    <div class="flex gap-5 md:text-[2rem] text-[1.5rem]">
                        <CheckOutlined
                            class="text-A805Green"
                            @click="acceptUserHandler(user.roomUserNo)"
                        />
                        <MinusCircleOutlined
                            class="text-A805Red"
                            @click="denyUserHandler(user.roomUserNo)"
                        />
                    </div>
                </div>
            </div>
        </div>
    </div>
    <ModalTemplate
        :seen="modalSeen"
        custom-class="modal-template-style-1"
        @modal-close="
            () => {
                modalSeen = false
            }
        "
        ><IntrudingModalContent
            content-title="MANITO"
            title-class="font-iceland"
            content-message="새로운 유저의 매칭 방식을 선택하세요"
            @rematch="rematchHandler"
            @intercept="interceptHandler"
        ></IntrudingModalContent
    ></ModalTemplate>
</template>

<style scoped></style>
