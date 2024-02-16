<script setup lang="ts">
import { ref, onMounted, inject, type Ref } from 'vue'
import type { HistoryArrayType } from '@/types/history'
import type { RoomUserInfoType } from '@/types/room';
import TextAtom from '@/components/atoms/TextAtom.vue'
import { useRouter } from 'vue-router';
const router = useRouter()
const isActive = ref(false)

const RoomUserInfo: Ref<RoomUserInfoType> = inject('roomUserInfo', ref({
    roomNo: 0,
    roomUserNo: 0,
    roomName: '',
    roomNickname: '',
    profileUrl: ''
}))

const prop = defineProps({
    HistoryData: {
        type: Object as () => HistoryArrayType,
        required: true
    }
})

onMounted(() => {
    console.log('HistoryData : ', prop.HistoryData.value)
})

const routerHandler = (link: number, category: string) => {
    router.push({
        path: `/game/${RoomUserInfo.value.roomNo}/board-detail/`,
        query: { boardNo: link, boardCategory: category }
    })
    console.log(RoomUserInfo.value.roomNo + '의' + link + '로 이동 하는 로직 추가 필요')
}
</script>

<template >
    <div class="flex flex-col w-full">
        <div>
            <div class="flex flex-col">
                <div class="flex justify-between">
                    <TextAtom>{{ HistoryData.date }}</TextAtom>
                    <TextAtom>{{ HistoryData.source }}</TextAtom>
                </div>
                <TextAtom>{{ HistoryData.value.post[0].title }}</TextAtom>
            </div>
            <div class="w-full max-h-[250px] my-[10px] cursor-pointer overflow-hidden"
                @click="routerHandler(HistoryData.value.post[0].postId, HistoryData.value.post[0].category)">
                <img v-if="HistoryData.value.post[0].thumbnail" :src="HistoryData.value.post[0].thumbnail"
                    class="object-cover rounded-lg w-auto h-full" />
                <TextAtom v-else v-html="HistoryData.value.post[0].content" class="relative"></TextAtom>
            </div>

            <div v-if="HistoryData.value.post.length > 1 && !isActive" class="flex flex-col">
                <hr class="border-solid border-1 border-A805Cream my-[10px]" />
                <TextAtom>외 {{ HistoryData.value.post.length - 1 }}건</TextAtom>
                <TextAtom @click="isActive = true" customClass="cursor-pointer">자세히 보러가기</TextAtom>
            </div>
            <div class="flex flex-col" v-else>
                <div v-for="(data, index) in HistoryData.value.post.slice(1)" :key="index">
                    <hr class="border-solid border-1 border-A805Cream mt-[10px]" />
                    <div class="flex flex-col">
                        <div class="flex justify-between">
                            <TextAtom>{{ HistoryData.date }}</TextAtom>
                            <TextAtom> {{ HistoryData.source }}</TextAtom>
                        </div>
                        <TextAtom>{{ data.title }}</TextAtom>
                    </div>
                    <div class="w-full my-[10px] cursor-pointer" @click="routerHandler(data.postId, data.category)">
                        <img v-if="data.thumbnail" :src="data.thumbnail"
                            class="object-cover rounded-lg w-full h-full max-h-[250px]" />
                        <TextAtom v-else v-html="data.content"></TextAtom>
                    </div>
                </div>
                <TextAtom>총 {{ HistoryData.value.post.length }}건...</TextAtom>
                <TextAtom @click="isActive = false" custom-class="cursor-pointer" v-if="HistoryData.value.post.length > 1">
                    접기</TextAtom>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
