<script setup lang="ts">
import type { Handler } from '@/types/common'
import TextAtom from '@/components/atoms/TextAtom.vue'
import BadgeAtom from '@/components/atoms/BadgeAtom.vue'
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import type { RoomListInfoType } from '@/types/room'

const props = defineProps({
    roomInfo: {
        type: Object as () => RoomListInfoType,
        required: true
    },
    isMaster: {
        type: Boolean,
        default: false
    }
})
const emit = defineEmits(['favoriteHandle', 'deleteHandle'])

const likeButtonClick: Handler = () => {
    emit('favoriteHandle')
}
const deleteButtonClick: Handler = () => {
    emit('deleteHandle')
}
</script>

<template>
    <div
        class="bg-A805Cream w-[45%] aspect-[5/3] rounded-lg shadow-rb cursor-pointer relative hover:scale-[102%] transition ease-in-out">
        <div class="absolute top-[2px] left-[2px] flex gap-[5px] items-center">
            <BadgeAtom custom-class="text-[1rem] bg-A805Purple text-A805White m-1 py-[1px] w-[60px] text-center"
                v-if="roomInfo.standbyYn">입장전</BadgeAtom>
            <BadgeAtom custom-class="text-[1rem] bg-A805Khaki text-A805White m-1 py-[1px] w-[60px] text-center "
                v-else-if="!roomInfo.roomStartYn">시작전</BadgeAtom>
            <BadgeAtom custom-class="text-[1rem] bg-A805DarkGrey text-A805White m-1 py-[1px] w-[60px] text-center"
                v-else-if="roomInfo.roomStatus === 'END'
                    ">종료</BadgeAtom>
            <BadgeAtom custom-class="text-[1rem] bg-A805Red text-A805White m-1 py-[1px] w-[60px] text-center" v-else>게임중
            </BadgeAtom>

            <TextAtom custom-class="text-[1.2rem]">{{ $props.roomInfo.participantCnt }}명</TextAtom>
            <div class="flex items-center">
                <img v-if="isMaster"
                    src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/People/Woman%20Fairy.png"
                    alt="Woman Fairy" width="35" height="35" />
            </div>
        </div>

        <div class="flex flex-col justify-center items-center h-full pt-[20px] text-center">
            <TextAtom custom-class="text-[1.4rem] font-bold truncate w-full px-[15px]">{{
                props.roomInfo.roomName + (isMaster ? ' (방장)' : '')
            }}</TextAtom>
            <TextAtom custom-class="text-[1.1rem] truncate w-full px-[30px]">
                {{ props.roomInfo.nickname }}
            </TextAtom>
        </div>
        <ButtonAtom class="absolute bottom-0 right-2 text-[20px] hover:scale-[125%] transition ease-in-out"
            @click.stop="likeButtonClick">
            <TextAtom class="text-A805Black" v-show="!roomInfo.bookmarkYn">♡</TextAtom>
            <TextAtom class="text-A805Red" v-show="roomInfo.bookmarkYn">♥</TextAtom>
        </ButtonAtom>
        <ButtonAtom
            class="absolute top-[-10px] right-[-3px] font-bold text-[16px] hover:scale-[125%] hover:text-A805Red transition ease-in-out"
            @click.stop="deleteButtonClick">✕</ButtonAtom>
    </div>
</template>

<style scoped></style>
