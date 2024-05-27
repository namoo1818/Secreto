<script setup lang="ts">
import { getRoomList } from '@/api/room'
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import { ref, watch, type Ref, onMounted, onUnmounted } from 'vue'
import type { Handler, DataHandler } from '@/types/common'
import type { RoomInfoType, RoomListInfoType } from '@/types/room'
import RoomCard from '@/components/molecules/main/RoomCard.vue'
import RoomCreateCard from '@/components/molecules/main/RoomCreateCard.vue'
import { useRouter } from 'vue-router'
import { exitRoom, checkFavorite } from '@/api/room'
import { useUserStore } from '@/stores/user'
import { storeToRefs } from 'pinia'
const userStore = useUserStore()
const { userInfo } = storeToRefs(userStore)
const router = useRouter()

const emit = defineEmits(['submit-button-handle'])

/**
 * dummy data
 */
const roomInfoList = ref<Array<RoomListInfoType>>([])
const SelectState = {
    ALL: 'ALL',
    WAITING: 'WAIT',
    IN_PROGRESS: 'PARTICIPANT',
    TERMINATED: 'END',
    FAVORITES: 'BOOKMARK'
}
const selectState: Ref<string> = ref(SelectState.ALL)
watch(selectState, () => {
})

const roomEnterHandler: DataHandler<RoomListInfoType> = (roomInfo: RoomListInfoType) => {
    if (roomInfo.hostUserNo === userInfo.value.id || (!roomInfo.standbyYn && (roomInfo.roomStatus === 'END' || roomInfo.roomStartYn))) {
        router.push('/game/' + roomInfo.roomNo)
    }
    else if (roomInfo.standbyYn && roomInfo.roomStatus === 'END') {
        alert('종료된 방에는 입장하실 수 없습니다')
    }
    else {
        router.push('/waiting/' + roomInfo.roomNo)
    }

}

const roomFavoriteHandler: DataHandler<number> = (roomNo: number) => {
    checkFavorite(roomNo, ({ data }) => {
        if (data.result) {
            alert('즐겨찾기를 성공했습니다.')
        }
        else {
            alert('즐겨찾기를 해제하였습니다.')
        }
        getRoomLists()

    }, (error) => console.log(error)
    )
}

const roomLeaveHandler: DataHandler<number> = async (roomNo: number) => {
    await exitRoom(roomNo, ({ data }) => {
        alert(data.message); getRoomLists()
    }, (error) => console.log(error))

}
const roomCreateHandler: Handler = () => {
    emit('submit-button-handle', 'roomCreate')
}

const getRoomLists = () => {
    getRoomList(
        ({ data }) => {
            roomInfoList.value = data.result
        },
        (error) => {
            console.error('error', error.response.data.message)
        }
    )
}

// 방 리스트 정보 불러오기
onMounted(() => {
    getRoomLists()
})
</script>

<template>
    <div class="card-container scroll-container justify-start">
        <div class="w-full flex gap-[30px] px-[10px] pb-[10px] text-A805DarkGrey border-b-[1px] border-A805Black">
            <ButtonAtom :custom-class="selectState === SelectState.ALL ? 'text-A805Black font-bold' : ''"
                @click="() => (selectState = SelectState.ALL)">전체</ButtonAtom>
            <ButtonAtom :custom-class="selectState === SelectState.WAITING ? 'text-A805Black font-bold' : ''
                " @click="() => (selectState = SelectState.WAITING)">대기중</ButtonAtom>
            <ButtonAtom :custom-class="selectState === SelectState.IN_PROGRESS ? 'text-A805Black font-bold' : ''
                " @click="() => (selectState = SelectState.IN_PROGRESS)">참여중</ButtonAtom>
            <ButtonAtom :custom-class="selectState === SelectState.TERMINATED ? 'text-A805Black font-bold' : ''
                " @click="() => (selectState = SelectState.TERMINATED)">종료</ButtonAtom>
            <ButtonAtom :custom-class="selectState === SelectState.FAVORITES ? 'text-A805Black font-bold' : ''
                " @click="() => (selectState = SelectState.FAVORITES)">즐겨찾기</ButtonAtom>
        </div>
        <div class="w-full flex justify-between items-start flex-wrap gap-x-[10px] gap-y-[30px] my-[20px]">
            <RoomCreateCard @click="roomCreateHandler" />
            <template v-for="roomInfo in roomInfoList" :key="roomInfo.roomNo">
                <RoomCard
                    v-if="(selectState === SelectState.ALL) || (selectState === roomInfo.roomStatus) || (selectState === SelectState.FAVORITES) && (roomInfo.bookmarkYn)"
                    :room-info="roomInfo" :is-master="roomInfo.hostUserNo === userInfo.id"
                    @click="() => roomEnterHandler(roomInfo)" @favorite-handle="() => roomFavoriteHandler(roomInfo.roomNo)"
                    @delete-handle="() => roomLeaveHandler(roomInfo.roomNo)" />
            </template>
        </div>
    </div>
</template>

<style scoped></style>
